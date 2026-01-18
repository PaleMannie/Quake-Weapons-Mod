package mett.palemannie.quakeweapons.event;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.QuakeWeaponsConfig;
import mett.palemannie.quakeweapons.item.custom.AbstractWeapon;
import mett.palemannie.quakeweapons.item.custom.GrenadelauncherItem;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = QuakeWeapons.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class QWConfigEvents {

    /// A way to set config values after they've been (re)loaded upon game start

    @SubscribeEvent
    public static void onConfigLoad(ModConfigEvent.Loading event) {
        if (event.getConfig().getSpec() == QuakeWeaponsConfig.SERVER_SPEC) {

            System.out.println("[QW] SERVER config loaded — reloading spawner values");

            PowerupSpawner.reloadConfigValues();
            AbstractWeaponItemDroppedAnimationFixer.reloadConfigValues();
        }

        if(event.getConfig().getSpec() == QuakeWeaponsConfig.COMMON_SPEC){

            System.out.println("[QW] COMMON config reloaded — reloading animation switches");

            AbstractWeapon.reloadAltModelConfig();
        }
    }

    @SubscribeEvent
    public static void onConfigReload(ModConfigEvent.Reloading event) {
        if (event.getConfig().getSpec() == QuakeWeaponsConfig.SERVER_SPEC) {

            System.out.println("[QW] SERVER config reloaded — reloading spawner values");

            PowerupSpawner.reloadConfigValues();
            AbstractWeaponItemDroppedAnimationFixer.reloadConfigValues();
        }

        if(event.getConfig().getSpec() == QuakeWeaponsConfig.COMMON_SPEC){

            System.out.println("[QW] COMMON config reloaded — reloading animation switches");

            AbstractWeapon.reloadAltModelConfig();
        }
    }
}
