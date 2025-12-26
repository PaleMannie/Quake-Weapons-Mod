package mett.palemannie.quakeweapons.util;

import mett.palemannie.quakeweapons.effect.ModEffects;
import mett.palemannie.quakeweapons.sound.ModSounds;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class QWInvisLoopSound extends AbstractTickableSoundInstance {

    private final LocalPlayer player;

    public QWInvisLoopSound(LocalPlayer player) {
        super(ModSounds.RING_USE.get(), SoundSource.PLAYERS, SoundInstance.createUnseededRandom());
        this.player = player;

        this.looping = true;
        this.volume = 0.8F;
        this.pitch = 1.0F;
        this.delay = 0;

        this.x = player.getX();
        this.y = player.getY();
        this.z = player.getZ();
    }

    @Override
    public void tick() {
        if (player.isRemoved() || !player.hasEffect(ModEffects.QW_INVIS.getHolder().get())) {
            this.stop();
            return;
        }

        this.x = player.getX();
        this.y = player.getY();
        this.z = player.getZ();
    }
}