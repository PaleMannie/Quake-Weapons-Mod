package mett.palemannie.quakeweapons.net.packets;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

public class S2CInvisPacket implements CustomPacketPayload {
    public static final Type<S2CInvisPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("quakeweapons", "s2cinvispacket"));
    public static final StreamCodec<FriendlyByteBuf, S2CInvisPacket> STREAM_CODEC = StreamCodec.of(S2CInvisPacket::encode, S2CInvisPacket::decode);
    @Override public Type<S2CInvisPacket> type() { return TYPE; }

    private final int entityId;
    private final boolean invisible;

    public S2CInvisPacket(int entityId, boolean invisible) {
        this.entityId = entityId;
        this.invisible = invisible;
    }

    public static void encode(FriendlyByteBuf buf, S2CInvisPacket msg) {
        buf.writeInt(msg.entityId);
        buf.writeBoolean(msg.invisible);
    }

    public static S2CInvisPacket decode(FriendlyByteBuf buf) {
        return new S2CInvisPacket(buf.readInt(), buf.readBoolean());
    }

    public static void handle(S2CInvisPacket msg, IPayloadContext ctx) {
        ctx.enqueueWork(() -> handleClient(msg));
    }

    @OnlyIn(Dist.CLIENT)
    private static void handleClient(S2CInvisPacket msg) {
        Level level = Minecraft.getInstance().level;
        if (level == null) return;

        Entity e = level.getEntity(msg.entityId);
        if (e instanceof LivingEntity living) {
            living.getPersistentData().putBoolean("QWInvis", msg.invisible);
        }
    }
}
