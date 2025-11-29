package mett.palemannie.quakeweapons.net.packets;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.network.CustomPayloadEvent;

public class S2CInvisPacket {
    private final int entityId;
    private final boolean invisible;

    public S2CInvisPacket(int entityId, boolean invisible) {
        this.entityId = entityId;
        this.invisible = invisible;
    }

    public static void encode(S2CInvisPacket msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.entityId);
        buf.writeBoolean(msg.invisible);
    }

    public static S2CInvisPacket decode(FriendlyByteBuf buf) {
        return new S2CInvisPacket(buf.readInt(), buf.readBoolean());
    }

    public static void handle(S2CInvisPacket msg, CustomPayloadEvent.Context ctx) {
        ctx.enqueueWork(() -> handleClient(msg));
        ctx.setPacketHandled(true);
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
