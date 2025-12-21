package mett.palemannie.quakeweapons.entity;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.entity.custom.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, QuakeWeapons.MODID);


    public static final RegistryObject<EntityType<NailProjectileEntity>> NAIL_PROJECTILE =
            ENTITY_TYPES.register("nail_projectile", () -> build(EntityType.Builder.<NailProjectileEntity>of(NailProjectileEntity::new, MobCategory.MISC)
                    .sized(0.15f, 0.15f)
                    .fireImmune().clientTrackingRange(256)
                    .updateInterval(1)
                    , "nail_projectile"));

    public static final RegistryObject<EntityType<SuperNailProjectileEntity>> SUPER_NAIL_PROJECTILE =
            ENTITY_TYPES.register("super_nail_projectile", () -> build(EntityType.Builder.<SuperNailProjectileEntity>of(SuperNailProjectileEntity::new, MobCategory.MISC)
                    .sized(0.15f, 0.15f)
                    .fireImmune().clientTrackingRange(256)
                    .updateInterval(1)
                    , "super_nail_projectile"));

    public static final RegistryObject<EntityType<MuzzleflashEntity>> MUZZLE_FLASH =
            ENTITY_TYPES.register("muzzleflash", () ->build(EntityType.Builder.<MuzzleflashEntity>of(MuzzleflashEntity::new, MobCategory.MISC)
                    .sized(0.01f, 0.01f)
                    .fireImmune()
                    , "muzzleflash"));

    public static final RegistryObject<EntityType<RocketProjectileEntity>> ROCKET_PROJECTILE =
            ENTITY_TYPES.register("rocket_projectile", () -> build(EntityType.Builder.<RocketProjectileEntity>of(RocketProjectileEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f)
                    .fireImmune().clientTrackingRange(256)
                    .updateInterval(1)
                    , "rocket_projectile"));

    public static final RegistryObject<EntityType<GrenadeProjectileEntity>> GRENADE_PROJECTILE =
            ENTITY_TYPES.register("grenade_projectile", () -> build(EntityType.Builder.<GrenadeProjectileEntity>of(GrenadeProjectileEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f)
                    .fireImmune().clientTrackingRange(256)
                    .updateInterval(1)
                    , "grenade_projectile"));

    public static final RegistryObject<EntityType<QuadDamagePowerupEntity>> QUAD_DAMAGE_POWERUP =
            ENTITY_TYPES.register("quad_damage_powerup", () -> build(EntityType.Builder.<QuadDamagePowerupEntity>of(QuadDamagePowerupEntity::new, MobCategory.MISC)
                    .sized(1.5f, 2f)
                    .setShouldReceiveVelocityUpdates(false)
                    .fireImmune().clientTrackingRange(256)
                    .setTrackingRange(256)
                    .updateInterval(20)
                    , "quad_damage_powerup"));

    public static final RegistryObject<EntityType<PentagramPowerupEntity>> PENTAGRAM_POWERUP =
            ENTITY_TYPES.register("pentagram_powerup", () -> build(EntityType.Builder.<PentagramPowerupEntity>of(PentagramPowerupEntity::new, MobCategory.MISC)
                    .sized(1.5f, 2f)
                    .fireImmune()
                    .clientTrackingRange(256)
                    .updateInterval(1)
                    , "pentagram_powerup"));

    public static final RegistryObject<EntityType<RingofshadowsPowerupEntity>> RING_POWERUP =
            ENTITY_TYPES.register("ring_powerup", () -> build(EntityType.Builder.<RingofshadowsPowerupEntity>of(RingofshadowsPowerupEntity::new, MobCategory.MISC)
                    .sized(1.5f, 2f)
                    .fireImmune()
                    .clientTrackingRange(256)
                    .updateInterval(1)
                    , "ring_powerup"));

    public static final RegistryObject<EntityType<BiosuitPowerupEntity>> BIOSUIT_POWERUP =
            ENTITY_TYPES.register("biosuit_powerup", () -> build(EntityType.Builder.<BiosuitPowerupEntity>of(BiosuitPowerupEntity::new, MobCategory.MISC)
                    .sized(1.5f, 2f)
                    .fireImmune()
                    .clientTrackingRange(256)
                    .updateInterval(1)
                    , "biosuit_powerup"));

    private static <T extends Entity> EntityType<T> build(EntityType.Builder<T> builder, String type) {
        ResourceKey<EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, type));
        return builder.build(key);
    }

    public static void register(BusGroup eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
