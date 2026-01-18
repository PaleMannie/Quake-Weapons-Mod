package mett.palemannie.quakeweapons.item.custom;

import mett.palemannie.quakeweapons.QuakeWeaponsConfig;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
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

            int duration = getPowerupDuration();
            applyPowerupTo(player, duration);

            if (!player.getAbilities().instabuild) {

                stack.shrink(1);
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {

        Level level = player.level();

        if (!level.isClientSide()) {

            int duration = getPowerupDuration();
            applyPowerupTo(target, duration);
            level.playSound(null, player.blockPosition(), SoundEvents.HORSE_EAT, SoundSource.PLAYERS, 1f, 1f);

            if (!player.getAbilities().instabuild) {

                stack.shrink(1);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.CONSUME;
    }

    private void applyPowerupTo(LivingEntity entity, int duration) {

        Holder<MobEffect> effect = getPowerupEffect();

        if (effect != null) {

            entity.addEffect(new MobEffectInstance(effect, duration, 0, false, false, true));
        }

    }

    @Nullable
    public Holder<MobEffect> getPowerupEffect() {
        return null;
    }

    protected int getPowerupDuration() {
        return QuakeWeaponsConfig.SERVER.powerupEffectDuration.get();
    }
}
