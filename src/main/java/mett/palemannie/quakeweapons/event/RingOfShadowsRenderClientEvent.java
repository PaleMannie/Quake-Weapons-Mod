package mett.palemannie.quakeweapons.event;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.effect.ModEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.player.AbstractClientPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.RenderLivingEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = QuakeWeapons.MODID, value = Dist.CLIENT)
public class RingOfShadowsRenderClientEvent {

    @SubscribeEvent
    public static void onRenderPlayer(RenderLivingEvent.Pre<?, ?, ?> event) {
        if (!(event.getRenderState() instanceof AvatarRenderState avatar) || Minecraft.getInstance().level == null) return;
        event.setCanceled(Minecraft.getInstance().level.getEntity(avatar.id) instanceof AbstractClientPlayer player
                && player.hasEffect(ModEffects.QW_INVIS));
    }
}
