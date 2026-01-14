package mett.palemannie.quakeweapons.effect.custom;

import mett.palemannie.quakeweapons.effect.ModEffects;
import mett.palemannie.quakeweapons.net.ModMessages;
import mett.palemannie.quakeweapons.net.packets.S2CInvisPacket;
import mett.palemannie.quakeweapons.net.packets.S2CPowerupSoundPacket;
import mett.palemannie.quakeweapons.sound.ModSounds;
import mett.palemannie.quakeweapons.util.PowerupSoundEventType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.level.Level;

public class QWInvisEffect extends MobEffect {

    public QWInvisEffect(MobEffectCategory pCategory, int pColor) {
        super(MobEffectCategory.BENEFICIAL, 0x4D194D);
    }

    @Override
    public void onEffectAdded(LivingEntity entity, int pAmplifier) {

        Level level = entity.level();
        if (!level.isClientSide) {

            ModMessages.sendToTrackingEntityAndSelf(new S2CInvisPacket(entity.getId(), true), entity);

            if (entity instanceof ServerPlayer player) {

                ModMessages.sendToPlayer(new S2CPowerupSoundPacket(entity.getId(), ModEffects.QW_INVIS.getId(), PowerupSoundEventType.ADD), player);
            }
        }
    }

    @Override
    public boolean applyEffectTick(ServerLevel sevel, LivingEntity entity, int amplifier) {

        Level level = entity.level();
        if (!level.isClientSide) {

            if (entity instanceof ServerPlayer player) {
                ModMessages.sendToPlayer(new S2CPowerupSoundPacket(entity.getId(), ModEffects.QW_INVIS.getId(), PowerupSoundEventType.EXPIRING), player);
            }
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int pAmplifier) {
        return duration == 60;
    }

    ///Assuring that upon breaking invis you get visible again by removing "QWInvis"
    @Override
    public void removeAttributeModifiers(AttributeMap map) { super.removeAttributeModifiers(map); }
}
