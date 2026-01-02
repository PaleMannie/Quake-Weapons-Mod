package mett.palemannie.quakeweapons.event;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.item.custom.AbstractWeapon;
import mett.palemannie.quakeweapons.util.PowerupOverlay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ComputeFovModifierEvent;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = QuakeWeapons.MODID, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void onRenderStage(CustomizeGuiOverlayEvent event) {

        PowerupOverlay.render(event.getGuiGraphics(), event.getPartialTick(),
                event.getWindow().getGuiScaledWidth(),
                event.getWindow().getGuiScaledHeight());
    }

    /// the anti use-slowdown unfortunately changes FOV massively. This event turns the FOV change to normal
    @SubscribeEvent
    public static void onComputeFov(ComputeFovModifierEvent event) {

        LocalPlayer player = (LocalPlayer) event.getPlayer();
        ItemStack stack = player.getMainHandItem();

        if(stack.getItem() instanceof AbstractWeapon) {

            event.setNewFovModifier(1f);
        }

        if(player.isSprinting() && stack.getItem() instanceof AbstractWeapon) {

            event.setNewFovModifier(1.15f);
        }
    }
}