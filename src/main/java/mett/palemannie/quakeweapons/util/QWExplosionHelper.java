package mett.palemannie.quakeweapons.util;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

/** Distance falloff, partial cover and blast knockback based on Q2W's explosion handler. */
public final class QWExplosionHelper {
    private QWExplosionHelper() {}

    public static void grenadeExplosion(ServerLevel level, Entity projectile, @Nullable Entity owner, Vec3 center, Entity quadapplier) {
        DamageSource source = level.damageSources().source(ModDamageTypes.GRENADELAUNCHER_DAMAGE, projectile, owner);
        radiusDamage(level, projectile, owner, center, QWConfigStats.GrenadelauncherDamage,
                QWConfigStats.GrenadelauncherRadius, source, quadapplier);
    }

    public static void rocketExplosion(ServerLevel level, @Nullable Entity projectile, @Nullable Entity owner, Vec3 center, Entity quadapplier) {
        DamageSource source = level.damageSources().source(ModDamageTypes.ROCKETLAUNCHER_DAMAGE, projectile, owner);
        radiusDamage(level, projectile, owner, center, QWConfigStats.RocketlauncherDamage,
                QWConfigStats.RocketlauncherRadius, source, quadapplier);
    }

    public static void radiusDamage(ServerLevel level, @Nullable Entity inflictor, @Nullable Entity owner,
                                    Vec3 center, float maxDamage, double radius, DamageSource source, Entity quadapplier) {
        if (radius <= 0 || maxDamage <= 0) return;
        AABB area = new AABB(center, center).inflate(radius);
        for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, area, LivingEntity::isAlive)) {
            if (target == inflictor) continue;
            Vec3 closest = closestPointOnBox(target.getBoundingBox(), center);
            double distance = closest.distanceTo(center);
            if (distance > radius) continue;

            double falloff = Mth.clamp(1d - distance / radius, 0d, 1d);
            float damage = (float) (maxDamage * falloff);
            if (!hasLooseLineOfSight(level, center, target)) damage *= 0.35f;
            if (damage <= 0f) continue;

            target.hurtServer(level, source, QWConfigStats.applyQuadDamage(damage, quadapplier));
            applyKnockback(target, center, falloff, target == owner && target instanceof Player);
        }
    }

    private static Vec3 closestPointOnBox(AABB box, Vec3 point) {
        return new Vec3(Mth.clamp(point.x, box.minX, box.maxX),
                Mth.clamp(point.y, box.minY, box.maxY), Mth.clamp(point.z, box.minZ, box.maxZ));
    }

    private static boolean hasLooseLineOfSight(ServerLevel level, Vec3 center, LivingEntity target) {
        return clearPath(level, center, target.getBoundingBox().getCenter(), target)
                || clearPath(level, center, target.getEyePosition(), target)
                || clearPath(level, center, target.position().add(0, 0.15, 0), target);
    }

    private static boolean clearPath(ServerLevel level, Vec3 from, Vec3 to, Entity entity) {
        BlockHitResult hit = level.clip(new ClipContext(from, to, ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE, entity));
        return hit.getType() == HitResult.Type.MISS;
    }

    private static void applyKnockback(LivingEntity target, Vec3 center, double falloff, boolean selfBlast) {
        Vec3 direction = target.getBoundingBox().getCenter().subtract(center);
        direction = direction.lengthSqr() < 1e-7d ? new Vec3(0, 1, 0) : direction.normalize();
        double strength = (selfBlast ? 1.35d : 0.85d) * falloff;
        double lift = selfBlast
                ? Math.max(0.35d * falloff, direction.y * strength + 0.3d * falloff)
                : Math.max(0.18d, direction.y * strength + 0.15d);
        target.push(direction.x * strength, lift, direction.z * strength);
        if (selfBlast) target.setOnGround(false);
        target.hurtMarked = true;
    }
}
