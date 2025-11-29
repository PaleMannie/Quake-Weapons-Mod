package mett.palemannie.quakeweapons.item.custom;

import mett.palemannie.quakeweapons.effect.ModEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class PentagramItem extends AbstractPowerupItem{

    public PentagramItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public MobEffect getPowerupEffect() {
        return ModEffects.INVULNERABILITY.get();
    }

    @Override
    protected void onPowerupUse(Level level, Player player, ItemStack stack, int duration) {
        player.addEffect(new MobEffectInstance(ModEffects.INVULNERABILITY.getHolder().get(), getPowerupDuration()));
    }
}
