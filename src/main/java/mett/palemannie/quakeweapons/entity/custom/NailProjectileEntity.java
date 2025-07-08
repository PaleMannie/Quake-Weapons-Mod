package mett.palemannie.quakeweapons.entity.custom;

import mett.palemannie.quakeweapons.block.ModBlocks;
import mett.palemannie.quakeweapons.entity.ModEntities;
import mett.palemannie.quakeweapons.sound.ModSounds;
import mett.palemannie.quakeweapons.util.ModDamageTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
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

import javax.annotation.Nullable;

public class NailProjectileEntity extends Projectile {

    public NailProjectileEntity(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
    }

    public NailProjectileEntity(Level level, Player player){
        this(ModEntities.NAIL_PROJECTILE.get(), level);
        this.setOwner(player);
        this.setPos(player.getX(), player.getEyeY()-0.2d, player.getZ());
    }

    @Override
    protected void defineSynchedData() {}

    @Override
    public boolean isNoGravity() {
        return true;
    }

    private BlockPos lightPos;

    void tryPlaceLight() {
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

    void cleanupLight() {
        if (this.lightPos != null) {
            BlockState state = this.level().getBlockState(this.lightPos);
            if (state.getBlock() == Blocks.LIGHT) {
                this.level().removeBlock(this.lightPos, false);
            } else if (state.getBlock() == ModBlocks.LIGHT_WATER.get()) {
                this.level().setBlock(this.lightPos, Blocks.WATER.defaultBlockState(), 3);
            }
        }
    }

    void muzzleFlashHandler(){

        if (!this.level().isClientSide) {
            if (this.tickCount == 1) {
                this.tryPlaceLight();
            }

            if (this.tickCount == 4) {
                this.cleanupLight();
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

        Vec3 vec3 = this.getDeltaMovement();
        double d0 = this.getX() + vec3.x;
        double d1 = this.getY() + vec3.y;
        double d2 = this.getZ() + vec3.z;
        this.updateRotation();

        this.setDeltaMovement(vec3.scale(1f));
        this.setDeltaMovement(this.getDeltaMovement().add(0f, 0f, 0f));

        this.setPos(d0, d1, d2);
    }

    void handleDamage(EntityHitResult pResult, Level level, ResourceKey<DamageType> damageType){

        DamageSource source = level.damageSources().source(damageType, null, null);
        DamageSource source2 = level.damageSources().source(DamageTypes.PLAYER_ATTACK, this.getOwner(), this.getOwner());
        if(pResult.getEntity() instanceof LivingEntity entity){

            entity.hurt(source2, Float.MIN_VALUE);
            entity.hurt(source, 2f);
        }
    }

    void handleProjectileBlockHitEffects(){

        if(level() instanceof ServerLevel)
            ((ServerLevel) level()).sendParticles(ParticleTypes.SMOKE, this.getX(), this.getY(), this.getZ(), 1, 0f, 0f, 0f, 0f);
    }

    void handleGore(Level level){

        if(level instanceof ServerLevel)
            ((ServerLevel) level).sendParticles(ParticleTypes.LANDING_LAVA, this.getX(), this.getY(), this.getZ(), 3, 0.1f, 0.1f, 0.1f, 0.1f);
    }

    void handleHitSound(@Nullable EntityHitResult entityHitResult, @Nullable BlockHitResult blockHitResult, Level level, SoundEvent soundEvent, float volume, float pitch){

        if(entityHitResult != null && blockHitResult == null) level.playSound(null, entityHitResult.getEntity().blockPosition(), soundEvent, SoundSource.NEUTRAL,volume, pitch);
        if(blockHitResult != null && entityHitResult == null) level.playSound(null, blockHitResult.getBlockPos(), soundEvent, SoundSource.NEUTRAL, volume, pitch);
    }

    @Override
    public void tick() {
        super.tick();

        muzzleFlashHandler();
        hitResultHandler();
        projectileFlyStraight();
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {

        cleanupLight();
        var soundEvent = ModSounds.NAILGUN_HIT.get();

        if (!this.level().isClientSide) {
            this.level().broadcastEntityEvent(this, (byte)3);
            this.discard();
        }

        handleHitSound(null, pResult, level(), soundEvent, 1f, 1f);
        handleProjectileBlockHitEffects();

        super.onHitBlock(pResult);
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);

        var damageType = ModDamageTypes.NAILGUN_DAMAGE;
        var soundEvent = ModSounds.NAILGUN_HIT.get();

        handleDamage(pResult, level(), damageType);
        handleGore(level());
        handleHitSound(pResult, null, level(), soundEvent, 1f, 1f);

        cleanupLight();
        this.discard();
    }
}