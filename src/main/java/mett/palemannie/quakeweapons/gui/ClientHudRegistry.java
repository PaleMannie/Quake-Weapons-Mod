package mett.palemannie.quakeweapons.gui;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.event.EffectOverlayRenderClientEvent;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = QuakeWeapons.MODID, value = Dist.CLIENT)
public class ClientHudRegistry {

    @SubscribeEvent
    public static void registerGuiOverlays(RegisterGuiLayersEvent event) {

        event.registerAbove(VanillaGuiLayers.HOTBAR,
                Identifier.fromNamespaceAndPath(QuakeWeapons.MODID, "weapon_ammo"),
                AmmoHudOverlay.HUD);
        event.registerAbove(VanillaGuiLayers.CROSSHAIR,
                Identifier.fromNamespaceAndPath(QuakeWeapons.MODID, "powerup_overlay"),
                EffectOverlayRenderClientEvent::onRenderOverlay);
    }
}
