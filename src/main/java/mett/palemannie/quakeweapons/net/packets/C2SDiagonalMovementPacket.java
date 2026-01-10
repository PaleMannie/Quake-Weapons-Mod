package mett.palemannie.quakeweapons.net.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.network.CustomPayloadEvent;

public class C2SDiagonalMovementPacket {

    private final boolean diagonal;

    public C2SDiagonalMovementPacket(boolean diagonal) {
        this.diagonal = diagonal;
    }

    public C2SDiagonalMovementPacket(FriendlyByteBuf buf) {
        this.diagonal = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(diagonal);
    }

    public static void handle(C2SDiagonalMovementPacket msg, CustomPayloadEvent.Context ctx) {
        ctx.enqueueWork(() -> {
            ServerPlayer sender = ctx.getSender();
            if (sender == null) return;

            // Optional: einfache Plausibilitätsprüfung (Anti-Cheat-light)
            // Wenn du willst, dass der Server nicht blind vertraut:
            // boolean serverSeesDiagonal = sender.xxa != 0.0F && sender.zza != 0.0F;
            // boolean diagonal = msg.diagonal && serverSeesDiagonal;
            boolean diagonal = msg.diagonal;

            sender.getPersistentData().putBoolean("QWDiagonal", diagonal);
        });

        ctx.setPacketHandled(true);
    }
}