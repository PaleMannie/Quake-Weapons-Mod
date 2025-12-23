package mett.palemannie.quakeweapons.event;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.effect.ModEffects;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = QuakeWeapons.MODID, value = Dist.CLIENT)
public class RingOfShadowsRenderClientEvent {

    @SubscribeEvent
    public static void onRenderPlayer(RenderLivingEvent.Pre<AbstractClientPlayer, LivingEntityRenderState, EntityModel<? super EntityRenderState> > event) {

        /*LivingEntity entity = event.getEntity();
        if (entity.hasEffect(ModEffects.QW_INVIS.getHolder().get())) {

            event.setCanceled(true);
        }*/
    }
}