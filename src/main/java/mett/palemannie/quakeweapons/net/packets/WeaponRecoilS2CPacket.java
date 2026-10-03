package mett.palemannie.quakeweapons.net.packets;

import mett.palemannie.quakeweapons.client.ClientWeaponRecoil;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

public record WeaponRecoilS2CPacket(float pitch, float roll, float yaw) implements CustomPacketPayload {
    public static final Type<WeaponRecoilS2CPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("quakeweapons", "weaponrecoils2cpacket"));
    public static final StreamCodec<FriendlyByteBuf, WeaponRecoilS2CPacket> STREAM_CODEC = StreamCodec.of(WeaponRecoilS2CPacket::encode, WeaponRecoilS2CPacket::decode);
    @Override public Type<WeaponRecoilS2CPacket> type() { return TYPE; }

    public static void encode(FriendlyByteBuf buffer, WeaponRecoilS2CPacket packet) {
        buffer.writeFloat(packet.pitch);
        buffer.writeFloat(packet.roll);
        buffer.writeFloat(packet.yaw);
    }

    public static WeaponRecoilS2CPacket decode(FriendlyByteBuf buffer) {
        return new WeaponRecoilS2CPacket(buffer.readFloat(), buffer.readFloat(), buffer.readFloat());
    }

    public static void handle(WeaponRecoilS2CPacket packet, IPayloadContext supplier) {
        supplier.enqueueWork(() -> ClientWeaponRecoil.kick(packet.pitch, packet.roll, packet.yaw));
    }
}
