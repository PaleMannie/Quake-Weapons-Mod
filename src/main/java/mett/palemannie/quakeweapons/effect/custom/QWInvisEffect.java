package mett.palemannie.quakeweapons.effect.custom;

import mett.palemannie.quakeweapons.effect.ModEffects;
import mett.palemannie.quakeweapons.net.ModMessages;
import mett.palemannie.quakeweapons.net.packets.S2CInvisPacket;
import mett.palemannie.quakeweapons.net.packets.S2CPowerupSoundPacket;
import mett.palemannie.quakeweapons.util.PowerupSoundEventType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;

public class QWInvisEffect extends MobEffect {

    public QWInvisEffect(MobEffectCategory pCategory, int pColor) {
        super(MobEffectCategory.BENEFICIAL, 0x4D194D);
    }

    @Override
    public void onEffectAdded(LivingEntity entity, int pAmplifier) {

        ModMessages.sendToTrackingEntityAndSelf(new S2CInvisPacket(entity.getId(), true), entity);
        ModMessages.sendToTrackingEntityAndSelf(new S2CPowerupSoundPacket(entity.getId(), ModEffects.QW_INVIS.getId(), PowerupSoundEventType.ADD), entity);
    }

    @Override
    public boolean applyEffectTick(ServerLevel sevel, LivingEntity entity, int amplifier) {


        ModMessages.sendToTrackingEntityAndSelf(new S2CPowerupSoundPacket(entity.getId(), ModEffects.QW_INVIS.getId(), PowerupSoundEventType.EXPIRING), entity);
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int pAmplifier) {
        return duration == 60;
    }

    ///Assuring that upon breaking invis your armor and items in hand get visible again
    @Override
    public void removeAttributeModifiers(AttributeMap map) {
        super.removeAttributeModifiers(map);
    }
}
