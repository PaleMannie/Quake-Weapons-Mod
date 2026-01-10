package mett.palemannie.quakeweapons.net.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.network.CustomPayloadEvent;

public class C2SSquakeEnabledPacket {
    private final boolean enabled;

    public C2SSquakeEnabledPacket(boolean enabled) { this.enabled = enabled; }
    public C2SSquakeEnabledPacket(FriendlyByteBuf buf) { this.enabled = buf.readBoolean(); }
    public void toBytes(FriendlyByteBuf buf) { buf.writeBoolean(enabled); }

    public static void handle(C2SSquakeEnabledPacket msg, CustomPayloadEvent.Context ctx) {
        ctx.enqueueWork(() -> {
            ServerPlayer sp = ctx.getSender();
            if (sp == null) return;
            sp.getPersistentData().putBoolean("QWSquakeEnabled", msg.enabled);
        });
        ctx.setPacketHandled(true);
    }
}
