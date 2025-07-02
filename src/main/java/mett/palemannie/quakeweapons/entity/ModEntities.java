package mett.palemannie.quakeweapons.entity;

import mett.palemannie.quakeweapons.QuakeWeapons;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, QuakeWeapons.MODID);


    /*public static final RegistryObject<EntityType<DiceProjectileEntity>> DICE_PROJECTILE =
            ENTITY_TYPES.register("dice_projectile", () -> EntityType.Builder.<DiceProjectileEntity>of(DiceProjectileEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("dice_projectile"));*/


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
