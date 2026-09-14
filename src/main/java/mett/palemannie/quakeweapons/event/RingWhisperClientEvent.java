package mett.palemannie.quakeweapons.event;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.effect.ModEffects;
import mett.palemannie.quakeweapons.sound.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.EntityBoundSoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = QuakeWeapons.MODID, value = Dist.CLIENT)
public class RingWhisperClientEvent {

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent.Post event) {

        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.isPaused() || minecraft.player == null) return;

        MobEffectInstance effect = minecraft.player.getEffect(ModEffects.QW_INVIS.getHolder().get());
        if (effect == null) return;

        int remaining = effect.getDuration();
        if (remaining > 50 && remaining % 60 == 0) {
            minecraft.getSoundManager().play(new EntityBoundSoundInstance(
                    ModSounds.RING_USE.get(), SoundSource.PLAYERS, 1f, 1f,
                    minecraft.player, minecraft.player.getRandom().nextLong()));
        }
    }
}
