package mett.palemannie.quakeweapons.gui;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.event.EffectOverlayRenderClientEvent;
import net.minecraft.resources.Identifier;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.AddGuiOverlayLayersEvent;
import net.minecraftforge.client.gui.overlay.ForgeLayeredDraw;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = QuakeWeapons.MODID, value = Dist.CLIENT)
public class ClientHudRegistry {
    @SubscribeEvent
    public static void registerGuiOverlays(AddGuiOverlayLayersEvent event) {

        event.getLayeredDraw().addAbove(ForgeLayeredDraw.PRE_SLEEP_STACK,
                Identifier.fromNamespaceAndPath(QuakeWeapons.MODID, "weapon_ammo"),
                ForgeLayeredDraw.HOTBAR_AND_DECOS, AmmoHudOverlay.HUD);
        event.getLayeredDraw().addAbove(ForgeLayeredDraw.POST_SLEEP_STACK,
                Identifier.fromNamespaceAndPath(QuakeWeapons.MODID, "powerup_overlay"),
                EffectOverlayRenderClientEvent::onRenderOverlay);
    }
}
