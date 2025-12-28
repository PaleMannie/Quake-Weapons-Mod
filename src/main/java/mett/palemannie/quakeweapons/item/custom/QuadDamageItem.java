package mett.palemannie.quakeweapons.item.custom;

import mett.palemannie.quakeweapons.effect.ModEffects;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;

public class QuadDamageItem extends AbstractPowerupItem{

    public QuadDamageItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public Holder<MobEffect> getPowerupEffect() {
        return ModEffects.QUAD_DAMAGE.getHolder().get();
    }
}
