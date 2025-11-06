package mett.palemannie.quakeweapons.event;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.QuakeWeaponsConfig;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = QuakeWeapons.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class QWConfigEvents {

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
