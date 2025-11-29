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
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import software.bernie.geckolib.GeckoLib;

@Mod(QuakeWeapons.MODID)
public class QuakeWeapons {

    public static final String MODID = "quakeweapons";
    private static final Logger LOGGER = LogUtils.getLogger();
    public static QuakeWeapons instance;

    public QuakeWeapons(){

        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
        eventBus.register(this);
        instance = this;

        eventBus.addListener(this::addCreative);

        GeckoLib.DATA_COMPONENTS_REGISTER.register(eventBus);

        ModCreativeModeTabs.register(eventBus);
        ModItems.register(eventBus);
        ModEffects.register(eventBus);
        ModEntities.register(eventBus);
        ModSounds.register(eventBus);
        ModBlocks.register(eventBus);

        QuakeWeaponsConfig.registerConfigs();
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
    }

    /*@SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }*/

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
        }
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void clientSetup(FMLClientSetupEvent e) {
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

        event.enqueueWork(ModMessages::register);
    }
}
