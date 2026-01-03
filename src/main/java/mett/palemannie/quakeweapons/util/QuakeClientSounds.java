package mett.palemannie.quakeweapons.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public final class QuakeClientSounds {

    private static QWInvisLoopSound invisSound;

    public static void startInvisLoopSound(LocalPlayer player) {
        if (invisSound != null) return;

        invisSound = new QWInvisLoopSound(player);
        Minecraft.getInstance().getSoundManager().play(invisSound);
    }

    public static void stopInvisLoopSound() {
        if (invisSound != null) {
            invisSound.stop();
            invisSound = null;
        }
    }


}