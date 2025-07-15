package mett.palemannie.quakeweapons.item;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.item.custom.HitscanTest;
import mett.palemannie.quakeweapons.item.custom.NailGunItem;
import mett.palemannie.quakeweapons.item.custom.SuperNailgunItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, QuakeWeapons.MODID);

    public static final RegistryObject<Item> NAILGUN = ITEMS.register("nailgun",
            () -> new NailGunItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> NAIL = ITEMS.register("nail",
            () -> new Item(new Item.Properties().stacksTo(64)));

    public static final RegistryObject<Item> SUPER_NAILGUN = ITEMS.register("super_nailgun",
            () -> new SuperNailgunItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> HITSCANTEST = ITEMS.register("hitscantest",
            () -> new HitscanTest(new Item.Properties().stacksTo(1)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
