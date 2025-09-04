package mett.palemannie.quakeweapons.util;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = QuakeWeapons.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCreativeModeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, QuakeWeapons.MODID);

    public static final RegistryObject<CreativeModeTab> QUAKEWEAPONS_TAB = CREATIVE_MODE_TABS.register("quakeweapons_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.NAILGUN.get()))
                    .title(Component.translatable("quakeweapons.creativetab.quakeweapons_tab"))
                    .displayItems((pParameters, pOutput) -> {

                        pOutput.accept(ModItems.QWAXE.get());
                        pOutput.accept(ModItems.SHOTGUN.get());
                        pOutput.accept(ModItems.NAILGUN.get());
                        pOutput.accept(ModItems.SUPER_SHOTGUN.get());
                        pOutput.accept(ModItems.SUPER_NAILGUN.get());
                        pOutput.accept(ModItems.GRENADELAUNCHER.get());
                        pOutput.accept(ModItems.ROCKETLAUNCHER.get());
                        pOutput.accept(ModItems.THUNDERBOLT.get());
                        pOutput.accept(ModItems.NAIL.get());
                        pOutput.accept(ModItems.SHELL.get());
                        pOutput.accept(ModItems.GRENADE.get());
                        pOutput.accept(ModItems.ROCKET.get());
                        pOutput.accept(ModItems.CELL.get());

                        pOutput.accept(ModItems.QUAD_DAMAGE_POWERUP.get());
                        pOutput.accept(ModItems.PENTAGRAM_POWERUP.get());
                        pOutput.accept(ModItems.RING_POWERUP.get());
                        pOutput.accept(ModItems.BIOSUIT_POWERUP.get());

                    })
                    .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}