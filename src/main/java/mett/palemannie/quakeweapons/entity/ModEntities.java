package mett.palemannie.quakeweapons.entity;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.entity.custom.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, QuakeWeapons.MODID);

    private static <T extends Entity> EntityType<T> build(EntityType.Builder<T> builder, String type) {
        ResourceKey<EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(QuakeWeapons.MODID, type));
        return builder.build(key);
    }


    public static final RegistryObject<EntityType<@NotNull MuzzleflashEntity>> MUZZLE_FLASH =
            ENTITY_TYPES.register("muzzleflash", () -> build(EntityType.Builder.<MuzzleflashEntity>of(MuzzleflashEntity::new, MobCategory.MISC)
                            .sized(0.01f, 0.01f)
                            .fireImmune()
                    , "muzzleflash"));

    public static final RegistryObject<EntityType<@NotNull MegahealthPickupEntity>> MEGAHEALTH_PICKUP =
            ENTITY_TYPES.register("megahealth_pickup", () -> build(EntityType.Builder
                            .<MegahealthPickupEntity>of(MegahealthPickupEntity::new, MobCategory.MISC)
                            .sized(1f, 1f)
                            .fireImmune()
                            .clientTrackingRange(256)
                            .updateInterval(1)
                    , "megahealth_pickup"));


    public static final RegistryObject<EntityType<@NotNull ShellsAmmopickupEntity>> SHELLS_AMMOPICKUP =
            ENTITY_TYPES.register("shells_ammopickup", () -> build(EntityType.Builder
                            .<ShellsAmmopickupEntity>of(ShellsAmmopickupEntity::new, MobCategory.MISC)
                            .sized(1f, 1f)
                            .fireImmune()
                            .clientTrackingRange(256)
                            .updateInterval(1)
                    , "shells_ammopickup"));

    public static final RegistryObject<EntityType<@NotNull NailsAmmopickupEntity>> NAILS_AMMOPICKUP =
            ENTITY_TYPES.register("nails_ammopickup", () -> build(EntityType.Builder
                            .<NailsAmmopickupEntity>of(NailsAmmopickupEntity::new, MobCategory.MISC)
                            .sized(1f, 1f)
                            .fireImmune()
                            .clientTrackingRange(256)
                            .updateInterval(1)
                    , "nails_ammopickup"));
    
    public static final RegistryObject<EntityType<@NotNull CellsAmmopickupEntity>> CELLS_AMMOPICKUP =
            ENTITY_TYPES.register("cells_ammopickup", () -> build(EntityType.Builder
                            .<CellsAmmopickupEntity>of(CellsAmmopickupEntity::new, MobCategory.MISC)
                            .sized(1f, 1f)
                            .fireImmune()
                            .clientTrackingRange(256)
                            .updateInterval(1)
                    , "cells_ammopickup"));
    
    public static final RegistryObject<EntityType<@NotNull GrenadesAmmopickupEntity>> GRENADES_AMMOPICKUP =
            ENTITY_TYPES.register("grenades_ammopickup", () -> build(EntityType.Builder
                            .<GrenadesAmmopickupEntity>of(GrenadesAmmopickupEntity::new, MobCategory.MISC)
                            .sized(1f, 1f)
                            .fireImmune()
                            .clientTrackingRange(256)
                            .updateInterval(1)
                    , "grenades_ammopickup"));
    
    public static final RegistryObject<EntityType<@NotNull RocketsAmmopickupEntity>> ROCKETS_AMMOPICKUP =
            ENTITY_TYPES.register("rockets_ammopickup", () -> build(EntityType.Builder
                            .<RocketsAmmopickupEntity>of(RocketsAmmopickupEntity::new, MobCategory.MISC)
                            .sized(1f, 1f)
                            .fireImmune()
                            .clientTrackingRange(256)
                            .updateInterval(1)
                    , "rockets_ammopickup"));


    public static final RegistryObject<EntityType<@NotNull NailProjectileEntity>> NAIL_PROJECTILE =
            ENTITY_TYPES.register("nail_projectile", () -> build(EntityType.Builder
                            .<NailProjectileEntity>of(NailProjectileEntity::new, MobCategory.MISC)
                            .sized(0.15f, 0.15f)
                            .fireImmune()
                            .clientTrackingRange(256)
                            .updateInterval(1)
                    , "nail_projectile"));

    public static final RegistryObject<EntityType<@NotNull SuperNailProjectileEntity>> SUPER_NAIL_PROJECTILE =
            ENTITY_TYPES.register("super_nail_projectile", () -> build(EntityType.Builder
                            .<SuperNailProjectileEntity>of(SuperNailProjectileEntity::new, MobCategory.MISC)
                            .sized(0.15f, 0.15f)
                            .fireImmune()
                            .clientTrackingRange(256)
                            .updateInterval(1)
                    , "super_nail_projectile"));

    public static final RegistryObject<EntityType<@NotNull RocketProjectileEntity>> ROCKET_PROJECTILE =
            ENTITY_TYPES.register("rocket_projectile", () -> build(EntityType.Builder
                            .<RocketProjectileEntity>of(RocketProjectileEntity::new, MobCategory.MISC)
                            .sized(0.5f, 0.5f)
                            .fireImmune()
                            .clientTrackingRange(256)
                            .updateInterval(1)
                    , "rocket_projectile"));

    public static final RegistryObject<EntityType<@NotNull GrenadeProjectileEntity>> GRENADE_PROJECTILE =
            ENTITY_TYPES.register("grenade_projectile", () -> build(EntityType.Builder
                            .<GrenadeProjectileEntity>of(GrenadeProjectileEntity::new, MobCategory.MISC)
                            .sized(0.5f, 0.5f)
                            .fireImmune()
                            .clientTrackingRange(256)
                            .updateInterval(1)
                    , "grenade_projectile"));

    public static final RegistryObject<EntityType<@NotNull QuadDamagePowerupEntity>> QUAD_DAMAGE_POWERUP =
            ENTITY_TYPES.register("quad_damage_powerup", () -> build(EntityType.Builder
                            .<QuadDamagePowerupEntity>of(QuadDamagePowerupEntity::new, MobCategory.MISC)
                            .sized(1.5f, 2f)
                            .fireImmune()
                            .clientTrackingRange(256)
                            .updateInterval(1)
                    , "quad_damage_powerup"));

    public static final RegistryObject<EntityType<@NotNull PentagramPowerupEntity>> PENTAGRAM_POWERUP =
            ENTITY_TYPES.register("pentagram_powerup", () -> build(EntityType.Builder
                            .<PentagramPowerupEntity>of(PentagramPowerupEntity::new, MobCategory.MISC)
                            .sized(1.5f, 2f)
                            .fireImmune()
                            .clientTrackingRange(256)
                            .updateInterval(1)
                    , "pentagram_powerup"));

    public static final RegistryObject<EntityType<@NotNull RingofshadowsPowerupEntity>> RING_POWERUP =
            ENTITY_TYPES.register("ring_powerup", () -> build(EntityType.Builder
                            .<RingofshadowsPowerupEntity>of(RingofshadowsPowerupEntity::new, MobCategory.MISC)
                            .sized(1.5f, 2f)
                            .fireImmune()
                            .clientTrackingRange(256)
                            .updateInterval(1)
                    , "ring_powerup"));

    public static final RegistryObject<EntityType<@NotNull BiosuitPowerupEntity>> BIOSUIT_POWERUP =
            ENTITY_TYPES.register("biosuit_powerup", () -> build(EntityType.Builder
                            .<BiosuitPowerupEntity>of(BiosuitPowerupEntity::new, MobCategory.MISC)
                            .sized(1.5f, 2f)
                            .fireImmune()
                            .clientTrackingRange(256)
                            .updateInterval(1)
                    , "biosuit_powerup"));

    public static void register(BusGroup eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
