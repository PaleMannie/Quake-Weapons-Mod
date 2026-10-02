package mett.palemannie.quakeweapons.event;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.QuakeWeaponsConfig;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;

@EventBusSubscriber(modid = QuakeWeapons.MODID)
public class QWConfigEvents {

    /// A way to set config values after they've been (re)loaded upon game start

    @SubscribeEvent
    public static void onConfigLoad(ModConfigEvent.Loading event) {
        if (event.getConfig().getSpec() == QuakeWeaponsConfig.SERVER_SPEC) {

            System.out.println("[QW] SERVER config loaded — reloading spawner values");

            PowerupSpawner.reloadConfigValues();
        }

    }

    @SubscribeEvent
    public static void onConfigReload(ModConfigEvent.Reloading event) {
        if (event.getConfig().getSpec() == QuakeWeaponsConfig.SERVER_SPEC) {

            System.out.println("[QW] SERVER config reloaded — reloading spawner values");

            PowerupSpawner.reloadConfigValues();
        }

    }
}
