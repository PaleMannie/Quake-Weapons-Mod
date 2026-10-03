package mett.palemannie.quakeweapons.item.custom;

import mett.palemannie.quakeweapons.effect.ModEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class RingOfShadowsItem extends AbstractPowerupItem{

    public RingOfShadowsItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public net.minecraft.core.Holder<MobEffect> getPowerupEffect() {
        return ModEffects.QW_INVIS;
    }

    @Override
    protected void onPowerupUse(Level level, Player player, ItemStack stack, int duration) {
        player.addEffect(new MobEffectInstance(ModEffects.QW_INVIS, duration, 0, false, false, false));
    }
}
