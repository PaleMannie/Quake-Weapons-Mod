/*package mett.palemannie.quakeweapons.event;

import mett.palemannie.quakeweapons.item.custom.AbstractWeapon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class QuakeClientInput {

    private static boolean wasDown = false;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player == null) return;

        ItemStack stack = player.getMainHandItem();
        if (!(stack.getItem() instanceof AbstractWeapon weapon)) {
            wasDown = false;
            return;
        }

        boolean isDown = mc.options.keyAttack.isDown();

        // gedrückt halten ODER Single-Click
        if (isDown && !player.isUsingItem()) {
            stack.onUseTick(player.level(), player, weapon.getUseDuration(stack, player));
        }

        wasDown = isDown;
    }
}*/