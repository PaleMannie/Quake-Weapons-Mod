package mett.palemannie.quakeweapons.net;

import mett.palemannie.quakeweapons.net.packets.*;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

public final class ModMessages {

    public static void register(RegisterPayloadHandlersEvent event) {
        var registrar = event.registrar("1");
        registrar.playToClient(S2CInvisPacket.TYPE, S2CInvisPacket.STREAM_CODEC);
        registrar.playToClient(WeaponRecoilS2CPacket.TYPE, WeaponRecoilS2CPacket.STREAM_CODEC);
        registrar.playToClient(ExplosionImpulseS2CPacket.TYPE, ExplosionImpulseS2CPacket.STREAM_CODEC);
    }
    public static void sendToTrackingEntityAndSelf(CustomPacketPayload message, LivingEntity entity) {
        PacketDistributor.sendToPlayersTrackingEntityAndSelf(entity, message);
    }
    public static void sendToPlayer(CustomPacketPayload message, ServerPlayer player) {
        PacketDistributor.sendToPlayer(player, message);
    }
}
