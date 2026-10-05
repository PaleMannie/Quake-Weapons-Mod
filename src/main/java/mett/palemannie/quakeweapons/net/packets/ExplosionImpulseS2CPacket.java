package mett.palemannie.quakeweapons.net.packets;

import mett.palemannie.quakeweapons.QuakeWeapons;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;

public record ExplosionImpulseS2CPacket(Vec3 impulse) implements CustomPacketPayload {

    public static final Type<ExplosionImpulseS2CPacket> TYPE = new Type<>(Identifier.fromNamespaceAndPath(QuakeWeapons.MODID, "explosion_impulse"));
    public static final StreamCodec<FriendlyByteBuf, ExplosionImpulseS2CPacket> STREAM_CODEC = StreamCodec.ofMember(ExplosionImpulseS2CPacket::encode, ExplosionImpulseS2CPacket::decode);

    @Override
    public Type<ExplosionImpulseS2CPacket> type() { return TYPE; }

    public static void encode(ExplosionImpulseS2CPacket packet, FriendlyByteBuf buffer) {
        buffer.writeDouble(packet.impulse.x);
        buffer.writeDouble(packet.impulse.y);
        buffer.writeDouble(packet.impulse.z);
    }

    public static ExplosionImpulseS2CPacket decode(FriendlyByteBuf buffer) {
        return new ExplosionImpulseS2CPacket(new Vec3(buffer.readDouble(), buffer.readDouble(), buffer.readDouble()));
    }
}
