package mett.palemannie.quakeweapons.util;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class QWPowerupSoundInstance extends AbstractTickableSoundInstance {

    private final LocalPlayer player;
    private final SoundEvent sound;

    public QWPowerupSoundInstance(LocalPlayer player, SoundEvent sound) {
        super(sound, SoundSource.PLAYERS, SoundInstance.createUnseededRandom());
        this.player = player;
        this.sound = sound;

        this.looping = false;
        this.volume = 1.0F;
        this.pitch = 1.0F;
        this.delay = 0;

        this.x = player.getX();
        this.y = player.getY();
        this.z = player.getZ();
    }

    @Override
    public void tick() {
        if (player.isRemoved()) {
            this.stop();
            return;
        }

        this.x = player.getX();
        this.y = player.getY();
        this.z = player.getZ();
    }
}