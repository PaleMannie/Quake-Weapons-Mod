package mett.palemannie.quakeweapons.event;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.effect.ModEffects;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = QuakeWeapons.MODID, value = Dist.CLIENT)
public class RingOfShadowsRenderClientEvent {

    @SubscribeEvent
    public static void onRenderPlayer(RenderLivingEvent.Pre<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> event) {

        LivingEntity entity = event.getEntity();
        if (entity.hasEffect(ModEffects.QW_INVIS.getHolder().get())) {

            event.setCanceled(true);
        }
    }
}