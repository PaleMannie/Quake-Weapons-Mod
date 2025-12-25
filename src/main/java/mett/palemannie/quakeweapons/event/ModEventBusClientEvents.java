package mett.palemannie.quakeweapons.event;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.entity.client.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = QuakeWeapons.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {

        event.registerLayerDefinition(NailProjectileModel.NAIL_LAYER, NailProjectileModel::createBodyLayer);
        event.registerLayerDefinition(SuperNailProjectileModel.SUPER_NAIL_LAYER, SuperNailProjectileModel::createBodyLayer);
        event.registerLayerDefinition(MuzzleflashModel.FLASH_LAYER, MuzzleflashModel::createBodyLayer);
        event.registerLayerDefinition(RocketProjectileModel.ROCKET_LAYER, RocketProjectileModel::createBodyLayer);
        event.registerLayerDefinition(GrenadeProjectileModel.GRENADE_LAYER, GrenadeProjectileModel::createBodyLayer);
    }
}