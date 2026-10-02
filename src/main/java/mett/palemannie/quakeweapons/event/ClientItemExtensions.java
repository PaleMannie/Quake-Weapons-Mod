package mett.palemannie.quakeweapons.event;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.item.ModItems;
import mett.palemannie.quakeweapons.item.custom.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

@EventBusSubscriber(modid = QuakeWeapons.MODID, value = Dist.CLIENT)
public final class ClientItemExtensions {

    @SubscribeEvent
    public static void register(RegisterClientExtensionsEvent event) {

        ((NailgunItem) ModItems.NAILGUN.get()).initializeClient(extension -> event.registerItem(extension, ModItems.NAILGUN.get()));
        ((SuperNailgunItem) ModItems.SUPER_NAILGUN.get()).initializeClient(extension -> event.registerItem(extension, ModItems.SUPER_NAILGUN.get()));
        ((ShotgunItem) ModItems.SHOTGUN.get()).initializeClient(extension -> event.registerItem(extension, ModItems.SHOTGUN.get()));
        ((SuperShotgunItem) ModItems.SUPER_SHOTGUN.get()).initializeClient(extension -> event.registerItem(extension, ModItems.SUPER_SHOTGUN.get()));
        ((RocketlauncherItem) ModItems.ROCKETLAUNCHER.get()).initializeClient(extension -> event.registerItem(extension, ModItems.ROCKETLAUNCHER.get()));
        ((GrenadelauncherItem) ModItems.GRENADELAUNCHER.get()).initializeClient(extension -> event.registerItem(extension, ModItems.GRENADELAUNCHER.get()));
        ((ThunderboltItem) ModItems.THUNDERBOLT.get()).initializeClient(extension -> event.registerItem(extension, ModItems.THUNDERBOLT.get()));
        ((QWAxeItem) ModItems.QWAXE.get()).initializeClient(extension -> event.registerItem(extension, ModItems.QWAXE.get()));
    }
}
