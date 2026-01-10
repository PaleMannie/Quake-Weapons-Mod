package mett.palemannie.quakeweapons.compat;

import mett.palemannie.quakeweapons.net.ModMessages;
import mett.palemannie.quakeweapons.net.packets.C2SSquakeEnabledPacket;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class SquakeEnabledSync {
    private static boolean last = false;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent e) {
        if (e.phase != TickEvent.Phase.END) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;
        if (mc.getConnection() == null) return;

        boolean now = SquakeCompat.isSquakeMovementEnabledClient();
        if (now != last) {
            last = now;
            ModMessages.sendToServer(new C2SSquakeEnabledPacket(now));
        }
    }
}