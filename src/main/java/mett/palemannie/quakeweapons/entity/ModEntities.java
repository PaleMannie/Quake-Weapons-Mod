package mett.palemannie.quakeweapons.entity;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.entity.custom.NailProjectileEntity;
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


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
