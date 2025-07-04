package mett.palemannie.quakeweapons.event;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.util.KeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


public class ClientEvents {

    @Mod.EventBusSubscriber(modid = QuakeWeapons.MODID, value = Dist.CLIENT)
    public static class ClientForgeEvents {

        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event){

            Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.player == null) return;
            LivingEntity player = minecraft.player;

            if(KeyBinding.SHOOT_QUAKE_WEAPONS.consumeClick()) {

            }
        }

        @Mod.EventBusSubscriber(modid = QuakeWeapons.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
        public static class ClientModBusEvents {
            @SubscribeEvent
            public static void onKeyRegister(RegisterKeyMappingsEvent event){
                event.register(KeyBinding.SHOOT_QUAKE_WEAPONS);
            }
        }
    }
}

