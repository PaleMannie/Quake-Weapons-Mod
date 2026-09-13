package mett.palemannie.quakeweapons.entity;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.entity.custom.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, QuakeWeapons.MODID);

    public static final RegistryObject<EntityType<ShellsAmmoPickupEntity>> SHELLS_AMMOPICKUP =
            ENTITY_TYPES.register("shells_ammopickup", () -> EntityType.Builder.<ShellsAmmoPickupEntity>of(ShellsAmmoPickupEntity::new, MobCategory.MISC)
                    .sized(1.0f, 1.0f).fireImmune().clientTrackingRange(256).updateInterval(1)
                    .build("shells_ammopickup"));
    
    public static final RegistryObject<EntityType<NailsAmmoPickupEntity>> NAILS_AMMOPICKUP =
            ENTITY_TYPES.register("nails_ammopickup", () -> EntityType.Builder.<NailsAmmoPickupEntity>of(NailsAmmoPickupEntity::new, MobCategory.MISC)
                    .sized(1.0f, 1.0f).fireImmune().clientTrackingRange(256).updateInterval(1)
                    .build("nails_ammopickup"));
    
    public static final RegistryObject<EntityType<CellsAmmoPickupEntity>> CELLS_AMMOPICKUP =
            ENTITY_TYPES.register("cells_ammopickup", () -> EntityType.Builder.<CellsAmmoPickupEntity>of(CellsAmmoPickupEntity::new, MobCategory.MISC)
                    .sized(1.0f, 1.0f).fireImmune().clientTrackingRange(256).updateInterval(1)
                    .build("cells_ammopickup"));
    
    public static final RegistryObject<EntityType<GrenadesAmmoPickupEntity>> GRENADES_AMMOPICKUP =
            ENTITY_TYPES.register("grenades_ammopickup", () -> EntityType.Builder.<GrenadesAmmoPickupEntity>of(GrenadesAmmoPickupEntity::new, MobCategory.MISC)
                    .sized(1.0f, 1.0f).fireImmune().clientTrackingRange(256).updateInterval(1)
                    .build("grenades_ammopickup"));
    
    public static final RegistryObject<EntityType<RocketsAmmoPickupEntity>> ROCKETS_AMMOPICKUP =
            ENTITY_TYPES.register("rockets_ammopickup", () -> EntityType.Builder.<RocketsAmmoPickupEntity>of(RocketsAmmoPickupEntity::new, MobCategory.MISC)
                    .sized(1.0f, 1.0f).fireImmune().clientTrackingRange(256).updateInterval(1).
                    build("rockets_ammopickup"));


    public static final RegistryObject<EntityType<NailProjectileEntity>> NAIL_PROJECTILE =
            ENTITY_TYPES.register("nail_projectile", () -> EntityType.Builder.<NailProjectileEntity>of(NailProjectileEntity::new, MobCategory.MISC)
                    .sized(0.15f, 0.15f).fireImmune().clientTrackingRange(256).updateInterval(1)
                    .build("nail_projectile"));

    public static final RegistryObject<EntityType<SuperNailProjectileEntity>> SUPER_NAIL_PROJECTILE =
            ENTITY_TYPES.register("super_nail_projectile", () -> EntityType.Builder.<SuperNailProjectileEntity>of(SuperNailProjectileEntity::new, MobCategory.MISC)
                    .sized(0.15f, 0.15f).fireImmune().clientTrackingRange(256).updateInterval(1)
                    .build("super_nail_projectile"));

    public static final RegistryObject<EntityType<MuzzleflashEntity>> MUZZLE_FLASH =
            ENTITY_TYPES.register("muzzleflash", () -> EntityType.Builder.<MuzzleflashEntity>of(MuzzleflashEntity::new, MobCategory.MISC)
                    .sized(0.01f, 0.01f).fireImmune()
                    .build("muzzleflash"));

    public static final RegistryObject<EntityType<RocketProjectileEntity>> ROCKET_PROJECTILE =
            ENTITY_TYPES.register("rocket_projectile", () -> EntityType.Builder.<RocketProjectileEntity>of(RocketProjectileEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).fireImmune().clientTrackingRange(256).updateInterval(1)
                    .build("rocket_projectile"));

    public static final RegistryObject<EntityType<GrenadeProjectileEntity>> GRENADE_PROJECTILE =
            ENTITY_TYPES.register("grenade_projectile", () -> EntityType.Builder.<GrenadeProjectileEntity>of(GrenadeProjectileEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).fireImmune().clientTrackingRange(256).updateInterval(1)
                    .build("grenade_projectile"));

    public static final RegistryObject<EntityType<QuadDamagePowerupEntity>> QUAD_DAMAGE_POWERUP =
            ENTITY_TYPES.register("quad_damage_powerup", () -> EntityType.Builder.<QuadDamagePowerupEntity>of(QuadDamagePowerupEntity::new, MobCategory.MISC)
                    .sized(1.5f, 2f).fireImmune().clientTrackingRange(256).updateInterval(1)
                    .build("quad_damage_powerup"));

    public static final RegistryObject<EntityType<PentagramPowerupEntity>> PENTAGRAM_POWERUP =
            ENTITY_TYPES.register("pentagram_powerup", () -> EntityType.Builder.<PentagramPowerupEntity>of(PentagramPowerupEntity::new, MobCategory.MISC)
                    .sized(1.5f, 2f).fireImmune().clientTrackingRange(256).updateInterval(1)
                    .build("pentagram_powerup"));

    public static final RegistryObject<EntityType<RingofshadowsPowerupEntity>> RING_POWERUP =
            ENTITY_TYPES.register("ring_powerup", () -> EntityType.Builder.<RingofshadowsPowerupEntity>of(RingofshadowsPowerupEntity::new, MobCategory.MISC)
                    .sized(1.5f, 2f).fireImmune().clientTrackingRange(256).updateInterval(1)
                    .build("ring_powerup"));

    public static final RegistryObject<EntityType<BiosuitPowerupEntity>> BIOSUIT_POWERUP =
            ENTITY_TYPES.register("biosuit_powerup", () -> EntityType.Builder.<BiosuitPowerupEntity>of(BiosuitPowerupEntity::new, MobCategory.MISC)
                    .sized(1.5f, 2f).fireImmune().clientTrackingRange(256).updateInterval(1)
                    .build("biosuit_powerup"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
