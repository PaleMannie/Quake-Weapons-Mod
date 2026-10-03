package mett.palemannie.quakeweapons;

import com.mojang.logging.LogUtils;
import mett.palemannie.quakeweapons.block.ModBlocks;
import mett.palemannie.quakeweapons.effect.ModEffects;
import mett.palemannie.quakeweapons.entity.ModEntities;
import mett.palemannie.quakeweapons.entity.client.*;
import mett.palemannie.quakeweapons.item.ModItems;
import mett.palemannie.quakeweapons.net.ModMessages;
import mett.palemannie.quakeweapons.sound.ModSounds;
import mett.palemannie.quakeweapons.util.ModCreativeModeTabs;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.ModContainer;
import org.slf4j.Logger;

@Mod(QuakeWeapons.MODID)
public class QuakeWeapons {

    /// TODO: Axt Neues Modell (irgendwann wenn Skidmark wieder Lust hat)
    /// TODO: (Super)Nailgun Neues Modell (irgendwann wenn Skidmark wieder Lust hat)
    /// TODO: (Super)Shotgun Neues Modell (irgendwann wenn Skidmark wieder Lust hat)
    /// TODO: Raketenwerfer Neues Modell (irgendwann wenn Skidmark wieder Lust hat)
    /// TODO: Granatenwerfer Neues Modell (irgendwann wenn Skidmark wieder Lust hat)
    /// TODO: Thunderbolt Neues Modell (irgendwann wenn Skidmark wieder Lust hat)

    public static final String MODID = "quakeweapons";
    public static final Logger LOGGER = LogUtils.getLogger();

    public QuakeWeapons(IEventBus modEventBus, ModContainer container){

        modEventBus.addListener(ModMessages::register);

        ModCreativeModeTabs.register(modEventBus);
        ModItems.register(modEventBus);
        ModEffects.register(modEventBus);
        ModEntities.register(modEventBus);
        ModSounds.register(modEventBus);
        ModBlocks.register(modEventBus);

        QuakeWeaponsConfig.registerConfigs(container);
    }

    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event){

            event.registerEntityRenderer(ModEntities.NAIL_PROJECTILE.get(), NailProjectileRenderer::new);
            event.registerEntityRenderer(ModEntities.SUPER_NAIL_PROJECTILE.get(), SuperNailProjectileRenderer::new);
            event.registerEntityRenderer(ModEntities.MUZZLE_FLASH.get(), MuzzleflashRenderer::new);
            event.registerEntityRenderer(ModEntities.ROCKET_PROJECTILE.get(), RocketProjectileRenderer::new);
            event.registerEntityRenderer(ModEntities.GRENADE_PROJECTILE.get(), GrenadeProjectileRenderer::new);

            event.registerEntityRenderer(ModEntities.QUAD_DAMAGE_POWERUP.get(), QuaddamagePowerupRenderer::new);
            event.registerEntityRenderer(ModEntities.PENTAGRAM_POWERUP.get(), PentagramPowerupRenderer::new);
            event.registerEntityRenderer(ModEntities.RING_POWERUP.get(), RingPowerupRenderer::new);
            event.registerEntityRenderer(ModEntities.BIOSUIT_POWERUP.get(), BiosuitPowerupRenderer::new);

            event.registerEntityRenderer(ModEntities.SHELLS_AMMOPICKUP.get(), ShellsAmmopickupRenderer::new);
            event.registerEntityRenderer(ModEntities.NAILS_AMMOPICKUP.get(), NailsAmmopickupRenderer::new);
            event.registerEntityRenderer(ModEntities.CELLS_AMMOPICKUP.get(), CellsAmmopickupRenderer::new);
            event.registerEntityRenderer(ModEntities.GRENADES_AMMOPICKUP.get(), GrenadesAmmopickupRenderer::new);
            event.registerEntityRenderer(ModEntities.ROCKETS_AMMOPICKUP.get(), RocketsAmmopickupRenderer::new);

            event.registerEntityRenderer(ModEntities.MEGAHEALTH_PICKUP.get(), MegahealthPickupRenderer::new);
        }
    }

}
