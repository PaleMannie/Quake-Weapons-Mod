package mett.palemannie.quakeweapons.net.packets;

import mett.palemannie.quakeweapons.QuakeWeapons;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record S2CInvisPacket(int entityId, boolean invisible) implements CustomPacketPayload {
    public static final Type<S2CInvisPacket> TYPE = new Type<>(Identifier.fromNamespaceAndPath(QuakeWeapons.MODID, "invisibility"));
    public static final StreamCodec<FriendlyByteBuf, S2CInvisPacket> STREAM_CODEC = StreamCodec.ofMember(S2CInvisPacket::encode, S2CInvisPacket::decode);

    @Override
    public Type<S2CInvisPacket> type() { return TYPE; }

    public static void encode(S2CInvisPacket packet, FriendlyByteBuf buffer) {
        buffer.writeInt(packet.entityId);
        buffer.writeBoolean(packet.invisible);
    }

    public static S2CInvisPacket decode(FriendlyByteBuf buffer) {
        return new S2CInvisPacket(buffer.readInt(), buffer.readBoolean());
    }
}
