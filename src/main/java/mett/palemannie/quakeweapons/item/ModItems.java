package mett.palemannie.quakeweapons.item;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.item.custom.*;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, QuakeWeapons.MODID);

    public static final RegistryObject<Item> NAILGUN = ITEMS.register("nailgun",
            () -> new NailgunItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> NAIL = ITEMS.register("nail",
            () -> new Item(new Item.Properties().stacksTo(64)));

    public static final RegistryObject<Item> SUPER_NAILGUN = ITEMS.register("super_nailgun",
            () -> new SuperNailgunItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> CELL = ITEMS.register("cell",
            () -> new Item(new Item.Properties().stacksTo(64)));

    public static final RegistryObject<Item> THUNDERBOLT = ITEMS.register("thunderbolt",
            () -> new ThunderboltItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> SHELL = ITEMS.register("shell",
            () -> new Item(new Item.Properties().stacksTo(64)));

    public static final RegistryObject<Item> SHOTGUN = ITEMS.register("shotgun",
            () -> new ShotgunItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> SUPER_SHOTGUN = ITEMS.register("super_shotgun",
            () -> new SuperShotgunItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> ROCKET = ITEMS.register("rocket",
            () -> new Item(new Item.Properties().stacksTo(64)));

    public static final RegistryObject<Item> ROCKETLAUNCHER = ITEMS.register("rocketlauncher",
            () -> new RocketlauncherItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> GRENADE = ITEMS.register("grenade",
            () -> new Item(new Item.Properties().stacksTo(64)));

    public static final RegistryObject<Item> GRENADELAUNCHER = ITEMS.register("grenadelauncher",
            () -> new GrenadelauncherItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> QWAXE = ITEMS.register("qwaxe",
            () -> new QWAxeItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> QUAD_DAMAGE_POWERUP = ITEMS.register("quad_damage",
            () -> new QuadDamageItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> PENTAGRAM_POWERUP = ITEMS.register("pentagram_of_protection",
            () -> new PentagramItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> RING_POWERUP = ITEMS.register("ring_of_shadows",
            () -> new RingOfShadowsItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> BIOSUIT_POWERUP = ITEMS.register("biosuit",
            () -> new BiosuitItem(new Item.Properties().stacksTo(1)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
