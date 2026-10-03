package mett.palemannie.quakeweapons.event;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.effect.ModEffects;
import mett.palemannie.quakeweapons.sound.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.EntityBoundSoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = QuakeWeapons.MODID, value = Dist.CLIENT)
public class RingWhisperClientEvent {

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {


        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.isPaused() || minecraft.player == null) return;

        MobEffectInstance effect = minecraft.player.getEffect(ModEffects.QW_INVIS);
        if (effect == null) return;

        int remaining = effect.getDuration();
        if (remaining > 50 && remaining % 60 == 0) {
            minecraft.getSoundManager().play(new EntityBoundSoundInstance(
                    ModSounds.RING_USE.get(), SoundSource.PLAYERS, 1f, 1f,
                    minecraft.player, minecraft.player.getRandom().nextLong()));
        }
    }
}
