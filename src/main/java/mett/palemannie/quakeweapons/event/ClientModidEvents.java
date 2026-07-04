package mett.palemannie.quakeweapons.event;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.item.custom.AbstractWeapon;
import mett.palemannie.quakeweapons.util.PowerupOverlay;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = QuakeWeapons.MODID, value = Dist.CLIENT)
public class ClientModidEvents {

    @SubscribeEvent
    public static void onRenderStage(CustomizeGuiOverlayEvent.Chat event) {

        PowerupOverlay.render(event.getGuiGraphics(), event.getPartialTick(),
                event.getWindow().getGuiScaledWidth(),
                event.getWindow().getGuiScaledHeight());
    }
}