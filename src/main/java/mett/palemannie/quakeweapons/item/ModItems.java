package mett.palemannie.quakeweapons.item;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.item.custom.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(BuiltInRegistries.ITEM, QuakeWeapons.MODID);

    public static final DeferredHolder<Item, Item> NAILGUN = ITEMS.register("nailgun",
            () -> new NailgunItem(new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(QuakeWeapons.MODID, "nailgun")))
                    .stacksTo(1)));

    public static final DeferredHolder<Item, Item> NAIL = ITEMS.register("nail",
            () -> new Item(new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(QuakeWeapons.MODID, "nail")))
                    .stacksTo(64)));

    public static final DeferredHolder<Item, Item> SUPER_NAILGUN = ITEMS.register("super_nailgun",
            () -> new SuperNailgunItem(new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(QuakeWeapons.MODID, "super_nailgun")))
                    .stacksTo(1)));

    public static final DeferredHolder<Item, Item> CELL = ITEMS.register("cell",
            () -> new Item(new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(QuakeWeapons.MODID, "cell")))
                    .stacksTo(64)));

    public static final DeferredHolder<Item, Item> THUNDERBOLT = ITEMS.register("thunderbolt",
            () -> new ThunderboltItem(new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(QuakeWeapons.MODID, "thunderbolt")))
                    .stacksTo(1)));

    public static final DeferredHolder<Item, Item> SHELL = ITEMS.register("shell",
            () -> new Item(new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(QuakeWeapons.MODID, "shell")))
                    .stacksTo(64)));

    public static final DeferredHolder<Item, Item> SHOTGUN = ITEMS.register("shotgun",
            () -> new ShotgunItem(new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(QuakeWeapons.MODID, "shotgun")))
                    .stacksTo(1)));

    public static final DeferredHolder<Item, Item> SUPER_SHOTGUN = ITEMS.register("super_shotgun",
            () -> new SuperShotgunItem(new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(QuakeWeapons.MODID, "super_shotgun")))
                    .stacksTo(1)));

    public static final DeferredHolder<Item, Item> ROCKET = ITEMS.register("rocket",
            () -> new Item(new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(QuakeWeapons.MODID, "rocket")))
                    .stacksTo(64)));

    public static final DeferredHolder<Item, Item> ROCKETLAUNCHER = ITEMS.register("rocketlauncher",
            () -> new RocketlauncherItem(new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(QuakeWeapons.MODID, "rocketlauncher")))
                    .stacksTo(1)));

    public static final DeferredHolder<Item, Item> GRENADE = ITEMS.register("grenade",
            () -> new Item(new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(QuakeWeapons.MODID, "grenade")))
                    .stacksTo(64)));

    public static final DeferredHolder<Item, Item> GRENADELAUNCHER = ITEMS.register("grenadelauncher",
            () -> new GrenadelauncherItem(new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(QuakeWeapons.MODID, "grenadelauncher")))
                    .stacksTo(1)));

    public static final DeferredHolder<Item, Item> QWAXE = ITEMS.register("qwaxe",
            () -> new QWAxeItem(new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(QuakeWeapons.MODID, "qwaxe")))
                    .stacksTo(1)));

    public static final DeferredHolder<Item, Item> QUAD_DAMAGE_POWERUP = ITEMS.register("quad_damage",
            () -> new QuadDamageItem(new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(QuakeWeapons.MODID, "quad_damage")))
                    .stacksTo(1)));

    public static final DeferredHolder<Item, Item> PENTAGRAM_POWERUP = ITEMS.register("pentagram_of_protection",
            () -> new PentagramItem(new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(QuakeWeapons.MODID, "pentagram_of_protection")))
                    .stacksTo(1)));

    public static final DeferredHolder<Item, Item> RING_POWERUP = ITEMS.register("ring_of_shadows",
            () -> new RingOfShadowsItem(new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(QuakeWeapons.MODID, "ring_of_shadows")))
                    .stacksTo(1)));

    public static final DeferredHolder<Item, Item> BIOSUIT_POWERUP = ITEMS.register("biosuit",
            () -> new BiosuitItem(new Item.Properties()
                    .setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(QuakeWeapons.MODID, "biosuit")))
                    .stacksTo(1)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
