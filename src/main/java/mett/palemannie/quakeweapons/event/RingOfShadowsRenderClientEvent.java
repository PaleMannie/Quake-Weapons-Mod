package mett.palemannie.quakeweapons.event;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.effect.ModEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = QuakeWeapons.MODID, value = Dist.CLIENT)
public class RingOfShadowsRenderClientEvent {

    @SubscribeEvent
    public static boolean onRenderPlayer(RenderLivingEvent.Pre<?, ?, ?> event) {
        if (!(event.getState() instanceof AvatarRenderState avatar) || Minecraft.getInstance().level == null) return false;
        return Minecraft.getInstance().level.getEntity(avatar.id) instanceof AbstractClientPlayer player
                && player.hasEffect(ModEffects.QW_INVIS.getHolder().orElseThrow());
    }
}