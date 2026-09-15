package mett.palemannie.quakeweapons.util;

import net.minecraft.core.Holder;
import net.minecraft.network.protocol.game.ClientboundSoundEntityPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.PacketDistributor;

public final class FollowingSoundHelper {
    private FollowingSoundHelper() {}

    public static void playForTrackingPlayers(Player player, SoundEvent sound) {
        if (!(player instanceof ServerPlayer serverPlayer)) return;

        PacketDistributor.TRACKING_ENTITY_AND_SELF.with(serverPlayer).send(
                new ClientboundSoundEntityPacket(Holder.direct(sound), SoundSource.PLAYERS, serverPlayer,
                        1.0F, 1.0F, serverPlayer.getRandom().nextLong()));
    }
}
