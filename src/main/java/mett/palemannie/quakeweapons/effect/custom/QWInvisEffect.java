package mett.palemannie.quakeweapons.effect.custom;

import mett.palemannie.quakeweapons.effect.ModEffects;
import mett.palemannie.quakeweapons.sound.ModSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class QWInvisEffect extends MobEffect {

    public QWInvisEffect(MobEffectCategory pCategory, int pColor) {
        super(MobEffectCategory.BENEFICIAL, 0x4D194D);
    }

    /// Effect done through Events
    /// Only Expiring sounds here (and vanilla invis)

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {

        entity.setInvisible(true);

        if(!entity.hasEffect(ModEffects.QW_INVIS)){

            entity.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 20, 0, false, false, false));
            entity.setInvisible(false);
        }

        MobEffectInstance inst = entity.getEffect(ModEffects.QW_INVIS);
        if (inst != null) {
            int remaining = inst.getDuration();

            if (remaining == 60) {
                if (entity.level().isClientSide) {

                    entity.level().playLocalSound(entity.getX(), entity.getY(), entity.getZ(), ModSounds.RING_EXPIRE.get(), SoundSource.PLAYERS, 3f, 1f, false);
                }
            }
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

}
