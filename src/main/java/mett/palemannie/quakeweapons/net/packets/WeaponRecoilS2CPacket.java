package mett.palemannie.quakeweapons.net.packets;

import mett.palemannie.quakeweapons.QuakeWeapons;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record WeaponRecoilS2CPacket(float pitchKick, float rollKick, float yawKick) implements CustomPacketPayload {

    public static final Type<WeaponRecoilS2CPacket> TYPE = new Type<>(Identifier.fromNamespaceAndPath(QuakeWeapons.MODID, "weapon_recoil"));
    public static final StreamCodec<FriendlyByteBuf, WeaponRecoilS2CPacket> STREAM_CODEC = StreamCodec.ofMember(WeaponRecoilS2CPacket::encode, WeaponRecoilS2CPacket::decode);

    @Override
    public Type<WeaponRecoilS2CPacket> type() { return TYPE; }

    public static void encode(WeaponRecoilS2CPacket packet, FriendlyByteBuf buffer) {
        buffer.writeFloat(packet.pitchKick);
        buffer.writeFloat(packet.rollKick);
        buffer.writeFloat(packet.yawKick);
    }

    public static WeaponRecoilS2CPacket decode(FriendlyByteBuf buffer) {
        return new WeaponRecoilS2CPacket(buffer.readFloat(), buffer.readFloat(), buffer.readFloat());
    }
}
