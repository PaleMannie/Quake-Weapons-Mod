package mett.palemannie.quakeweapons.item.custom;

import mett.palemannie.quakeweapons.QuakeWeaponsConfig;
import mett.palemannie.quakeweapons.effect.ModEffects;
import mett.palemannie.quakeweapons.sound.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public abstract class AbstractPowerupItem extends Item {

    public AbstractPowerupItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide()) {

            int duration = QuakeWeaponsConfig.SERVER.powerupEffectDuration.get();
            onPowerupUse(level, player, stack, duration);
            SoundEvent pickupSound = getPickupSound();
            if (pickupSound != null) {
                level.playSound(null, player.blockPosition(), pickupSound, SoundSource.PLAYERS, 1.0F, 1.0F);
            }

            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Nullable
    public MobEffect getPowerupEffect() {
        return null;
    }

    @Nullable
    protected SoundEvent getPickupSound() {
        MobEffect effect = getPowerupEffect();
        if (effect == ModEffects.QUAD_DAMAGE.get()) return ModSounds.QUAD_DAMAGE_PICKUP.get();
        if (effect == ModEffects.INVULNERABILITY.get()) return ModSounds.PENTAGRAM_PICKUP.get();
        if (effect == ModEffects.QW_INVIS.get()) return ModSounds.RING_PICKUP.get();
        if (effect == ModEffects.BIOSUIT.get()) return ModSounds.BIOSUIT_PICKUP.get();
        return null;
    }

    protected abstract void onPowerupUse(Level level, Player player, ItemStack stack, int duration);

    protected int getPowerupDuration() {
        return QuakeWeaponsConfig.SERVER.powerupEffectDuration.get();
    }
}
