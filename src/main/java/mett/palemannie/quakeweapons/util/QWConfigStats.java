package mett.palemannie.quakeweapons.util;

import mett.palemannie.quakeweapons.QuakeWeaponsConfig;
import mett.palemannie.quakeweapons.effect.ModEffects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

public class QWConfigStats {

    public static float applyQuadDamage(float baseDamage, @Nullable Entity attacker) {
        if (!(attacker instanceof LivingEntity living)) return baseDamage;
        var q2wQuad = ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("q2w", "quad_damage_effect"));
        boolean quad = living.hasEffect(ModEffects.QUAD_DAMAGE.get())
                || (q2wQuad != null && living.hasEffect(q2wQuad));
        return quad ? baseDamage * 4.0F : baseDamage;
    }

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
}
