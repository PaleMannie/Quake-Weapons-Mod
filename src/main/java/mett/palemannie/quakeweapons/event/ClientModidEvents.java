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

    /// the anti use-slowdown unfortunately changes FOV massively. This event turns the FOV change to normal depending on the usual movement speed
    @SubscribeEvent
    public static void onComputeFov(ComputeFovModifierEvent event) {

        LocalPlayer player = (LocalPlayer) event.getPlayer();
        ItemStack stack = player.getMainHandItem();

        if (!(stack.getItem() instanceof AbstractWeapon)) { return; }

        float fov = 1.0F;
        double speedMultiplier = 1.0;

        var speed = player.getEffect(MobEffects.SPEED);
        if (speed != null) {
            speedMultiplier += 0.2 * (speed.getAmplifier() + 1);
        }

        var slow = player.getEffect(MobEffects.SLOWNESS);
        if (slow != null) {
            speedMultiplier -= 0.15 * (slow.getAmplifier() + 1);
        }

        if (player.isSprinting()) {
            speedMultiplier *= 1.3;
        }

        fov *= (float)((speedMultiplier + 1.0) / 2.0);

        event.setNewFovModifier(fov);
    }
}