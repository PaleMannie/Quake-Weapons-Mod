package mett.palemannie.quakeweapons.effect.custom;

import mett.palemannie.quakeweapons.effect.ModEffects;
import mett.palemannie.quakeweapons.sound.ModSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;

public class QWInvisEffect extends MobEffect {

    public QWInvisEffect(MobEffectCategory pCategory, int pColor) {
        super(MobEffectCategory.BENEFICIAL, 0x4D194D);
    }

    /// Effect done through Events
    /// Only Expiring sounds here (and vanilla invis)
    ///
    /// @return

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {

        entity.setInvisible(true);

        if(!entity.hasEffect(ModEffects.QW_INVIS.getHolder().get())){

            entity.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 20, 0, false, false, false));
            entity.setInvisible(false);
        }

        MobEffectInstance inst = entity.getEffect(ModEffects.QW_INVIS.getHolder().get());
        if (inst != null) {
            int remaining = inst.getDuration();

            if(remaining % 60 == 0 && remaining > 50){

                entity.level().playLocalSound(entity.getX(), entity.getY(), entity.getZ(), ModSounds.RING_USE.get(), SoundSource.PLAYERS, 1f, 1f, false);
            }

            if (remaining == 60) {
                if (entity.level().isClientSide) {

                    entity.level().playLocalSound(entity.getX(), entity.getY(), entity.getZ(), ModSounds.RING_EXPIRE.get(), SoundSource.PLAYERS, 3f, 1f, false);
                }
            }
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int pDuration, int pAmplifier) {
        return true;
    }

    ///Assuring that upon breaking invis your armor and items in hand get visible again
    @Override
    public void removeAttributeModifiers(AttributeMap map) {
        super.removeAttributeModifiers(map);
    }
}
