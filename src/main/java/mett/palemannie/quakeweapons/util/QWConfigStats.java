package mett.palemannie.quakeweapons.util;

import mett.palemannie.quakeweapons.QuakeWeaponsConfig;
import mett.palemannie.quakeweapons.effect.ModEffects;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;

public class QWConfigStats {

    private static final Identifier Q2W_QUAD_DAMAGE =
            Identifier.fromNamespaceAndPath("q2w", "quad_damage_effect");

    public static float AxeDamage = QuakeWeaponsConfig.COMMON.axeDamage.get().floatValue();
    public static float ShotgunDamage = QuakeWeaponsConfig.COMMON.shotgunDamage.get().floatValue();
    public static float SuperShotgunDamage = QuakeWeaponsConfig.COMMON.superShotgunDamage.get().floatValue();
    public static float NailgunDamage = QuakeWeaponsConfig.COMMON.nailgunDamage.get().floatValue();
    public static float SuperNailgunDamage = QuakeWeaponsConfig.COMMON.superNailgunDamage.get().floatValue();
    public static float ThunderboltDamage = QuakeWeaponsConfig.COMMON.thunderboltDamage.get().floatValue();
    public static float RocketlauncherDamage = QuakeWeaponsConfig.COMMON.rocketlauncherDamage.get().floatValue();
    public static float RocketlauncherRadius = QuakeWeaponsConfig.COMMON.rocketlauncherRadius.get().floatValue();
    public static float GrenadelauncherDamage = QuakeWeaponsConfig.COMMON.grenadelauncherDamage.get().floatValue();
    public static float GrenadelauncherRadius = QuakeWeaponsConfig.COMMON.grenadelauncherRadius.get().floatValue();

    public static boolean hasQuadDamage(@Nullable Entity attacker) {
        if (!(attacker instanceof LivingEntity livingEntity)) return false;

        return livingEntity.hasEffect(ModEffects.QUAD_DAMAGE.getHolder().orElseThrow())
                || ForgeRegistries.MOB_EFFECTS.getHolder(Q2W_QUAD_DAMAGE)
                        .map(livingEntity::hasEffect)
                        .orElse(false);
    }

    public static float applyQuadDamage(float baseDamage, @Nullable Entity attacker) {
        return hasQuadDamage(attacker) ? baseDamage * 4.0F : baseDamage;
    }
}
