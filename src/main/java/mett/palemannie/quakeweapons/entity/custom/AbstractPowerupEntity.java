package mett.palemannie.quakeweapons.entity.custom;

import mett.palemannie.quakeweapons.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public abstract class AbstractPowerupEntity extends Entity {

    protected AbstractPowerupEntity(EntityType<?> type, Level level) {
        super(type, level);
        this.noPhysics = true; // bewegt sich nicht
    }

    @Override
    protected void defineSynchedData() {}

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
    }

    @Override
    public void tick() {
        super.tick();

        // despawn
        if (this.tickCount > 20*20 /*5*60*20*/) {
            discard();
            return;
        }

        // Kollisions-Check mit Spielern
        if (!level().isClientSide) {
            for (Player player : level().getEntitiesOfClass(Player.class, getBoundingBox().inflate(0.5))) {
                onPickup(player);
                discard();
                break;
            }
        }
    }

    // Effekt-Logik beim Aufheben
    protected abstract void onPickup(Player player);

    // Item-Drop beim „Killen mit Nether Star“
    protected abstract Item getPowerupItem();

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level().isClientSide && stack.is(Items.NETHER_STAR)) {

            this.spawnAtLocation(getPowerupItem());
            level().playSound(null, blockPosition(), ModSounds.EXPLOSION.get(), SoundSource.PLAYERS, 1.0F, 2.0F);

            if(!player.isCreative()){

                stack.shrink(1);
            }
            this.discard();

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Override
    public boolean isPickable() {
        return true;
    }
}