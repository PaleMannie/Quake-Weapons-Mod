package mett.palemannie.quakeweapons.gui;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.event.EffectOverlayRenderClientEvent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = QuakeWeapons.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientHudRegistry {

    private ClientHudRegistry() {}

    @SubscribeEvent
    public static void register(RegisterGuiLayersEvent event) {
        event.registerAbove(VanillaGuiLayers.CAMERA_OVERLAYS, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "powerup_tint"), EffectOverlayRenderClientEvent::render);
        event.registerAbove(VanillaGuiLayers.PLAYER_HEALTH, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "weapon_ammo"), AmmoHudOverlay.HUD);
    }
}
