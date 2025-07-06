package mett.palemannie.quakeweapons.util;

import mett.palemannie.quakeweapons.QuakeWeapons;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;

public class ModDamageTypes {

    public static final ResourceKey<DamageType> register(String name){
        return ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, name));
    }

    public static final ResourceKey<DamageType> NAILGUN_DAMAGE = register("nailgun_damage");
}
