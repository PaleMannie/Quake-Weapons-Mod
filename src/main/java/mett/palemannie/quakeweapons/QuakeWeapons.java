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
import mett.palemannie.quakeweapons.util.PowerupSoundRegistry;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import software.bernie.geckolib.GeckoLib;

@Mod(QuakeWeapons.MODID)
public class QuakeWeapons {

    /// TODO: gedroppte Waffen sind unsichtbar und Hotbar spinnt displaymäßig rum mit den Waffen beim aufheben

    public static final String MODID = "quakeweapons";
    public static final Logger LOGGER = LogUtils.getLogger();

    public QuakeWeapons(FMLJavaModLoadingContext context){

        var modBusGroup = context.getModBusGroup();
        FMLCommonSetupEvent.getBus(modBusGroup).addListener(QuakeWeapons::commonSetup);

        GeckoLib.DATA_COMPONENTS_REGISTER.register(modBusGroup);

        ModItems.register(modBusGroup);
        ModEffects.register(modBusGroup);
        ModEntities.register(modBusGroup);
        ModSounds.register(modBusGroup);
        ModBlocks.register(modBusGroup);
        ModCreativeModeTabs.register(modBusGroup);

        QuakeWeaponsConfig.registerConfigs();
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event){

            EntityRenderers.register(ModEntities.NAIL_PROJECTILE.get(), NailProjectileRenderer::new);
            EntityRenderers.register(ModEntities.SUPER_NAIL_PROJECTILE.get(), SuperNailProjectileRenderer::new);
            EntityRenderers.register(ModEntities.MUZZLE_FLASH.get(), MuzzleflashRenderer::new);
            EntityRenderers.register(ModEntities.ROCKET_PROJECTILE.get(), RocketProjectileRenderer::new);
            EntityRenderers.register(ModEntities.GRENADE_PROJECTILE.get(), GrenadeProjectileRenderer::new);
            EntityRenderers.register(ModEntities.QUAD_DAMAGE_POWERUP.get(), QuaddamagePowerupRenderer::new);
            EntityRenderers.register(ModEntities.PENTAGRAM_POWERUP.get(), PentagramPowerupRenderer::new);
            EntityRenderers.register(ModEntities.RING_POWERUP.get(), RingPowerupRenderer::new);
            EntityRenderers.register(ModEntities.BIOSUIT_POWERUP.get(), BiosuitPowerupRenderer::new);

            PowerupSoundRegistry.init();
        }
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void clientSetup(FMLClientSetupEvent e) {
    }

    private static void commonSetup(final FMLCommonSetupEvent event) {

        event.enqueueWork(ModMessages::register);
    }
}
