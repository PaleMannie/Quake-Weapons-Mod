package mett.palemannie.quakeweapons.util;

import mett.palemannie.quakeweapons.item.custom.AbstractWeapon;
import mett.palemannie.quakeweapons.net.ModMessages;
import mett.palemannie.quakeweapons.net.packets.C2SDiagonalMovementPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class QWPlayer {

    private static boolean lastDiagonal = false;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent e) {
        if (e.phase != TickEvent.Phase.END) return;

        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;

        boolean usingWeapon = player.isUsingItem()
                && player.getUseItem().getItem() instanceof AbstractWeapon;

        boolean diagonal = false;
        if (usingWeapon) {
            diagonal = (player.input.keyPresses.forward() || player.input.keyPresses.backward())
                    && (player.input.keyPresses.left() || player.input.keyPresses.right());
        }

        if (diagonal != lastDiagonal) {
            lastDiagonal = diagonal;
            ModMessages.sendToServer(new C2SDiagonalMovementPacket(diagonal));
        }
    }
}
