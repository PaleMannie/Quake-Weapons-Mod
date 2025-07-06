package mett.palemannie.quakeweapons.entity.custom;

import mett.palemannie.quakeweapons.block.ModBlocks;
import mett.palemannie.quakeweapons.entity.ModEntities;
import mett.palemannie.quakeweapons.sound.ModSounds;
import mett.palemannie.quakeweapons.util.ModDamageTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LightBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;

public class NailProjectileEntity extends Projectile {

    public NailProjectileEntity(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
    }

    public NailProjectileEntity(Level level, Player player){
        this(ModEntities.NAIL_PROJECTILE.get(), level);
        this.setOwner(player);
        this.setPos(player.getX(), player.getEyeY()-0.2d, player.getZ());
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

                    if (state.isAir()) {
                        level.setBlock(candidate, Blocks.LIGHT.defaultBlockState().setValue(LightBlock.LEVEL, 15), 3);
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
            if (state.getBlock() == Blocks.LIGHT) {
                this.level().removeBlock(this.lightPos, false);
            } else if (state.getBlock() == ModBlocks.LIGHT_WATER.get()) {
                this.level().setBlock(this.lightPos, Blocks.WATER.defaultBlockState(), 3);
            }
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide) {
            if (this.tickCount == 1) {
                this.tryPlaceLight();
            }

            if (this.tickCount == 4) {
                this.cleanupLight();
            }
        }

        HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
        if (hitresult.getType() != HitResult.Type.MISS && !ForgeEventFactory.onProjectileImpact(this, hitresult)) {
            this.onHit(hitresult);
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
    public boolean isNoGravity() {
        return true;
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {

        this.cleanupLight();

        if (!this.level().isClientSide) {
            this.level().broadcastEntityEvent(this, (byte)3);
            this.discard();
        }
        level().playSound(null, pResult.getBlockPos(), ModSounds.NAILGUN_HIT.get(), SoundSource.NEUTRAL, 1f, 1f);
        if(level() instanceof ServerLevel)
            ((ServerLevel) level()).sendParticles(ParticleTypes.SMOKE, this.getX(), this.getY(), this.getZ(), 1, 0f, 0f, 0f, 0f);

        super.onHitBlock(pResult);
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);

        Level level = this.level();

        DamageSource source = level.damageSources().source(ModDamageTypes.NAILGUN_DAMAGE, null, null);
        DamageSource source2 = level.damageSources().source(DamageTypes.PLAYER_ATTACK, this.getOwner(), this.getOwner());
        if(pResult.getEntity() instanceof LivingEntity entity){

            entity.hurt(source2, Float.MIN_VALUE);
            entity.hurt(source, 2f);
        }

        if(level instanceof ServerLevel)
            ((ServerLevel) level).sendParticles(ParticleTypes.LANDING_LAVA, this.getX(), this.getY(), this.getZ(), 3, 0.1f, 0.1f, 0.1f, 0.1f);

        level.playSound(null, pResult.getEntity().blockPosition(), ModSounds.NAILGUN_HIT.get(), SoundSource.NEUTRAL, 0.25f, 1f);
        this.discard();
        this.cleanupLight();
    }

    @Override
    protected void defineSynchedData() {}

    public void recreateFromPacket(ClientboundAddEntityPacket packet) {
        super.recreateFromPacket(packet);
    }
}