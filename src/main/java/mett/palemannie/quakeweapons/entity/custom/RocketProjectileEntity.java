package mett.palemannie.quakeweapons.entity.custom;

import mett.palemannie.quakeweapons.QuakeWeaponsConfig;
import mett.palemannie.quakeweapons.block.ModBlocks;
import mett.palemannie.quakeweapons.util.ModDamageTypes;
import mett.palemannie.quakeweapons.util.QWConfigStats;
import mett.palemannie.quakeweapons.util.QWExplosionHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;
import net.minecraftforge.event.ForgeEventFactory;

public class RocketProjectileEntity extends Projectile {

    public RocketProjectileEntity(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    private boolean exploded;

    private void quakeExplosion(Level level) {
        if (!(level instanceof ServerLevel serverLevel) || exploded) return;
        exploded = true;

        AABB area = new AABB(this.blockPosition()).inflate(QWConfigStats.RocketlauncherRadius);
        for (LivingEntity entity : this.level().getEntitiesOfClass(LivingEntity.class, area)) {
            if (entity != this.getOwner()) {
                entity.hurt(this.damageSources().source(DamageTypes.PLAYER_ATTACK, this, this.getOwner()), Float.MIN_VALUE);
            }
        }

        Vec3 center = this.position();
        QWExplosionHelper.rocketExplosion(serverLevel, null, null, center, this.getOwner());

        serverLevel.sendParticles(ParticleTypes.FLAME,
                center.x, center.y, center.z,
                40,
                0.0, 0.0, 0.0,
                0.2);

        serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE,
                center.x, center.y, center.z,
                20,
                0.0, 0.0, 0.0,
                0.1);



        this.cleanupLight();
        this.discard();
    }

    private BlockPos lightPos;

    private void tryPlaceLight() {
        BlockPos origin = this.blockPosition();
        Level level = this.level();

        int[] dyOrder = {0, -1, 1};
        for (int dy : dyOrder) {
            for (int dx = -1; dx <= 1; dx++) {
                for (int dz = -1; dz <= 1; dz++) {
                    BlockPos candidate = origin.offset(dx, dy, dz);
                    BlockState state = level.getBlockState(candidate);

                    if (state.is(Blocks.AIR)) {
                        level.setBlock(candidate, ModBlocks.LIGHT_AIR.get().defaultBlockState(), 3);
                        this.lightPos = candidate;
                        return;
                    } else if (state.getBlock() == Blocks.WATER) {
                        level.setBlock(candidate, ModBlocks.LIGHT_WATER.get().defaultBlockState(), 3);
                        this.lightPos = candidate;
                        return;
                    }
                }
            }
        }
    }

    private void cleanupLight() {
        if (this.lightPos != null) {
            BlockState state = this.level().getBlockState(this.lightPos);
            if (state.getBlock() == ModBlocks.LIGHT_AIR.get()) {
                this.level().setBlock(this.lightPos, Blocks.AIR.defaultBlockState(), 3);
            } else if (state.getBlock() == ModBlocks.LIGHT_WATER.get()) {
                this.level().setBlock(this.lightPos, Blocks.WATER.defaultBlockState(), 3);
            }
        }
    }

    void hitResultHandler(){

        HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
        if (hitresult.getType() != HitResult.Type.MISS && !ForgeEventFactory.onProjectileImpact(this, hitresult)) {
            this.onHit(hitresult);
        }
    }

    void projectileFlyStraight(){

        if (this.level().isClientSide()) {
            Vec3 motion = this.getDeltaMovement().normalize().scale(-0.25);
            double px = this.getX() + motion.x;
            double py = this.getY() + motion.y;
            double pz = this.getZ() + motion.z;

            this.level().addParticle(ParticleTypes.SMOKE, px, py, pz, 0, 0, 0);
            this.level().addParticle(ParticleTypes.FLAME, px, py, pz, 0, 0, 0);
        }

        Vec3 vec3 = this.getDeltaMovement();
        double d0 = this.getX() + vec3.x;
        double d1 = this.getY() + vec3.y;
        double d2 = this.getZ() + vec3.z;
        this.updateRotation();

        this.setDeltaMovement(vec3.scale(1f));
        this.setDeltaMovement(this.getDeltaMovement().add(0f, 0f, 0f));

        this.setPos(d0, d1, d2);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {}

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide() && QuakeWeaponsConfig.COMMON.enableRocketTrailLight.get()) {
            if (this.tickCount % 2 == 0) {
                this.cleanupLight();
                this.tryPlaceLight();
            }
        }

        hitResultHandler();
        projectileFlyStraight();

        if(this.tickCount > 400) {

            quakeExplosion(this.level());
            cleanupLight();
            this.discard();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {

        if (!this.level().isClientSide()) {
            // Explode at the impact, just outside the solid face, so floor/wall jumps use
            // the actual hit position and the visibility traces do not start inside a block.
            this.setPos(pResult.getLocation().add(
                    pResult.getDirection().getStepX() * 0.01D,
                    pResult.getDirection().getStepY() * 0.01D,
                    pResult.getDirection().getStepZ() * 0.01D));
            this.level().broadcastEntityEvent(this, (byte)3);
            this.discard();
        }

        quakeExplosion(this.level());

        cleanupLight();
        super.onHitBlock(pResult);
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        if (this.level() instanceof ServerLevel) {
            Entity owner = this.getOwner();
            pResult.getEntity().hurt(level().damageSources().source(ModDamageTypes.ROCKETLAUNCHER_DAMAGE, this, owner),
                    QWConfigStats.RocketlauncherDamage * RandomSource.create().nextFloat() / 4);
        }

        quakeExplosion(this.level());

        cleanupLight();
        this.discard();
    }
}
