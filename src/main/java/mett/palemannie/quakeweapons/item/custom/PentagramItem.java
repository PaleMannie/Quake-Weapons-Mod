package mett.palemannie.quakeweapons.item.custom;

import mett.palemannie.quakeweapons.effect.ModEffects;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;

public class PentagramItem extends AbstractPowerupItem{

    public PentagramItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public Holder<MobEffect> getPowerupEffect() {
        return ModEffects.INVULNERABILITY.getHolder().get();
    }
}
