package mett.palemannie.quakeweapons.net.packets;

import mett.palemannie.quakeweapons.client.ClientWeaponRecoil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import java.util.function.Supplier;

public record WeaponRecoilS2CPacket(float pitch, float roll, float yaw) {
    public static void encode(WeaponRecoilS2CPacket packet, FriendlyByteBuf buffer) {
        buffer.writeFloat(packet.pitch);
        buffer.writeFloat(packet.roll);
        buffer.writeFloat(packet.yaw);
    }

    public static WeaponRecoilS2CPacket decode(FriendlyByteBuf buffer) {
        return new WeaponRecoilS2CPacket(buffer.readFloat(), buffer.readFloat(), buffer.readFloat());
    }

    public static void handle(WeaponRecoilS2CPacket packet, Supplier<NetworkEvent.Context> supplier) {
        supplier.get().enqueueWork(() -> ClientWeaponRecoil.kick(packet.pitch, packet.roll, packet.yaw));
        supplier.get().setPacketHandled(true);
    }
}
