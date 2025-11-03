package mett.palemannie.quakeweapons.item.custom;

import mett.palemannie.quakeweapons.effect.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class BiosuitItem extends AbstractPowerupItem{

    public BiosuitItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected void onPowerupUse(Level level, Player player, ItemStack stack, int duration) {
        player.addEffect(new MobEffectInstance(ModEffects.BIOSUIT.get(), getPowerupDuration()));
    }
}
