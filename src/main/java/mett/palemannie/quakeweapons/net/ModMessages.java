package mett.palemannie.quakeweapons.net;

import mett.palemannie.quakeweapons.net.packets.*;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

public final class ModMessages {
    public static void register(RegisterPayloadHandlersEvent event) {
        var registrar = event.registrar("1");
        registrar.playToClient(S2CInvisPacket.TYPE, S2CInvisPacket.STREAM_CODEC, S2CInvisPacket::handle);
        registrar.playToClient(WeaponRecoilS2CPacket.TYPE, WeaponRecoilS2CPacket.STREAM_CODEC, WeaponRecoilS2CPacket::handle);
        registrar.playToClient(ExplosionImpulseS2CPacket.TYPE, ExplosionImpulseS2CPacket.STREAM_CODEC, ExplosionImpulseS2CPacket::handle);
    }
    public static void sendToPlayer(CustomPacketPayload message, ServerPlayer player) {
        PacketDistributor.sendToPlayer(player, message);
    }
}
