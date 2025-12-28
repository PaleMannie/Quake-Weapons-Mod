package mett.palemannie.quakeweapons.item.custom;

import mett.palemannie.quakeweapons.effect.ModEffects;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;

public class BiosuitItem extends AbstractPowerupItem{

    public BiosuitItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public Holder<MobEffect> getPowerupEffect() {
        return ModEffects.BIOSUIT.getHolder().get();
    }
}
