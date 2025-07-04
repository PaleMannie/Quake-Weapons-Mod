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

                        pOutput.accept(ModItems.NAILGUN.get());


                    })
                    .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}