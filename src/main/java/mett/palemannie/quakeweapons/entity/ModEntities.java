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


    public static final RegistryObject<EntityType<NailProjectileEntity>> NAIL_PROJECTILE =
            ENTITY_TYPES.register("nail_projectile", () -> EntityType.Builder.<NailProjectileEntity>of(NailProjectileEntity::new, MobCategory.MISC)
                    .sized(0.1f, 0.1f).fireImmune().build("nail_projectile"));

    public static final RegistryObject<EntityType<SuperNailProjectileEntity>> SUPER_NAIL_PROJECTILE =
            ENTITY_TYPES.register("super_nail_projectile", () -> EntityType.Builder.<SuperNailProjectileEntity>of(SuperNailProjectileEntity::new, MobCategory.MISC)
                    .sized(0.1f, 0.1f).fireImmune().build("super_nail_projectile"));

    public static final RegistryObject<EntityType<MuzzleflashEntity>> MUZZLE_FLASH =
            ENTITY_TYPES.register("muzzleflash", () -> EntityType.Builder.<MuzzleflashEntity>of(MuzzleflashEntity::new, MobCategory.MISC)
                    .sized(0.1f, 0.1f).fireImmune().build("muzzleflash"));

    public static final RegistryObject<EntityType<RocketProjectileEntity>> ROCKET_PROJECTILE =
            ENTITY_TYPES.register("rocket_projectile", () -> EntityType.Builder.<RocketProjectileEntity>of(RocketProjectileEntity::new, MobCategory.MISC)
                    .sized(0.1f, 0.1f).fireImmune().build("rocket_projectile"));

    public static final RegistryObject<EntityType<GrenadeProjectileEntity>> GRENADE_PROJECTILE =
            ENTITY_TYPES.register("grenade_projectile", () -> EntityType.Builder.<GrenadeProjectileEntity>of(GrenadeProjectileEntity::new, MobCategory.MISC)
                    .sized(0.1f, 0.1f).fireImmune().build("grenade_projectile"));


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
