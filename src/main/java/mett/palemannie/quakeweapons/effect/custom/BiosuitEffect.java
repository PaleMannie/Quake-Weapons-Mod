package mett.palemannie.quakeweapons.effect.custom;

import mett.palemannie.quakeweapons.effect.ModEffects;
import mett.palemannie.quakeweapons.sound.ModSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class BiosuitEffect extends MobEffect {

    public BiosuitEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    /// Effect done through Events
    /// Only Expiring sounds here
    ///
    /// @return

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {

        entity.setAirSupply(entity.getMaxAirSupply());

        MobEffectInstance inst = entity.getEffect(ModEffects.BIOSUIT.getHolder().get());
        if (inst != null) {
            int remaining = inst.getDuration();

            if (remaining == 60) {
                if (entity.level().isClientSide) {

                    entity.level().playLocalSound(entity.getX(), entity.getY(), entity.getZ(), ModSounds.BIOSUIT_EXPIRE.get(), SoundSource.PLAYERS, 3f, 1f, false);
                }
            }
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int pDuration, int pAmplifier) {
        return true;
    }
}
