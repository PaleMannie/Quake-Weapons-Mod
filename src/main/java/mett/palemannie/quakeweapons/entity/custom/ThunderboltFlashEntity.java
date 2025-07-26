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

public class ThunderboltFlashEntity extends Projectile {

    public ThunderboltFlashEntity(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
    }

    public ThunderboltFlashEntity(Level level, Player player){
        this(ModEntities.THUNDERBOLT_FLASH.get(), level);
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

    @Override
    public void tick() {
        super.tick();

        muzzleFlashHandler();

        if(this.tickCount > 10) this.discard();
    }
}