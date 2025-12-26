package mett.palemannie.quakeweapons.effect.custom;

import mett.palemannie.quakeweapons.effect.ModEffects;
import mett.palemannie.quakeweapons.net.ModMessages;
import mett.palemannie.quakeweapons.net.packets.S2CPowerupSoundPacket;
import mett.palemannie.quakeweapons.sound.ModSounds;
import mett.palemannie.quakeweapons.util.PowerupSoundEventType;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
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

    @Override
    public void onEffectAdded(LivingEntity entity, int pAmplifier) {

        ModMessages.sendToTrackingEntityAndSelf(new S2CPowerupSoundPacket(entity.getId(), ModEffects.BIOSUIT.getId(), PowerupSoundEventType.ADD), entity);
    }

    @Override
    public boolean applyEffectTick(ServerLevel sevel, LivingEntity entity, int amplifier) {

        entity.setAirSupply(entity.getMaxAirSupply());

        ModMessages.sendToTrackingEntityAndSelf(new S2CPowerupSoundPacket(entity.getId(), ModEffects.BIOSUIT.getId(), PowerupSoundEventType.EXPIRING), entity);

        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return duration == 60;
    }
}
