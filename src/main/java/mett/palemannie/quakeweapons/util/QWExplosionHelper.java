package mett.palemannie.quakeweapons.util;

import mett.palemannie.quakeweapons.QuakeWeaponsConfig;
import mett.palemannie.quakeweapons.effect.ModEffects;
import mett.palemannie.quakeweapons.sound.ModSounds;
import mett.palemannie.quakeweapons.net.ModMessages;
import mett.palemannie.quakeweapons.net.packets.ExplosionImpulseS2CPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

/**
 * Quake 1 splash and impulse adapted to Minecraft, with independent damage/radius settings.
 * Reference: https://github.com/id-Software/quake-rerelease-qc/blob/main/quakec/combat.qc
 */
public final class QWExplosionHelper {

    private static final double QUAKE_SPLASH_DAMAGE = 120.0D;
    private static final double QUAKE_SPLASH_RADIUS = QUAKE_SPLASH_DAMAGE + 40.0D;
    // 32 Quake units per block; convert units/second to Minecraft blocks/tick.
    private static final double IMPULSE_PER_POINT = 8.0D / (32.0D * 20.0D);
    private static final double TRACE_OFFSET = 15.0D / 32.0D;

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

        level.playSound(null, center.x, center.y, center.z,
                ModSounds.EXPLOSION.get(), SoundSource.PLAYERS, 2.0F, 1.0F);

        if (!Double.isFinite(radius) || radius <= 0 || !Float.isFinite(maxDamage)) return;

        float quad = QWConfigStats.applyQuadDamage(1.0F, quadapplier);

        // ModEvents already applies this mod's Quad to health damage from the source owner.
        boolean quadHandledByEvent = source.getEntity() instanceof LivingEntity attacker
                && attacker.hasEffect(ModEffects.QUAD_DAMAGE.getHolder().get());

        float damageScale = Math.max(0.0F, maxDamage) * (quadHandledByEvent ? 1.0F : quad);
        double selfDamageMultiplier = QuakeWeaponsConfig.COMMON.explosionSelfDamageMultiplier.get();
        AABB area = new AABB(center, center).inflate(radius);

        for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, area, LivingEntity::isAlive)) {
            if (target == inflictor || target.isSpectator()) continue;
            if (target instanceof Player player && player.getAbilities().flying) continue;
            double distance = target.getBoundingBox().getCenter().distanceTo(center);
            if (distance > radius || !canDamage(level, center, target)) continue;

            // Quake's 120 - distance/2 within 160 units, rescaled to the configured radius.
            // This leaves 1/3 damage at the cutoff, followed by a hard stop.
            double falloff = 1.0D - 0.5D * QUAKE_SPLASH_RADIUS * (distance / radius) / QUAKE_SPLASH_DAMAGE;
            double damageMultiplier = target == owner ? selfDamageMultiplier : 1.0D;
            Vec3 velocity = target.getDeltaMovement();

            if (damageScale > 0.0F && damageMultiplier > 0.0D) {
                target.hurtServer(level, source, (float) (damageScale * falloff * damageMultiplier));
            }

            // Keep Quake's half-strength self impulse independent of the health-damage config.
            // Damage type no_knockback tags also prevent the extra Minecraft projectile hit kick.
            double impulsePoints = QUAKE_SPLASH_DAMAGE * falloff * (target == owner ? 0.5D : 1.0D);
            // Reduce only the added blast impulse, preserving the mob's existing movement.
            double knockbackMultiplier = target instanceof Mob ? 0.1D : 1.0D;
            Vec3 impulse = quakeOrigin(target).subtract(center).normalize()
                    .scale(impulsePoints * quad * IMPULSE_PER_POINT * knockbackMultiplier);
            target.setDeltaMovement(velocity.add(impulse));
            if (impulse.y > 0.0D) target.setOnGround(false);
            if (target instanceof ServerPlayer player) {
                // Server velocity does not contain all locally predicted/Squake movement.
                // Send only the impulse; hurtMarked would send an absolute velocity afterward.
                ModMessages.sendToPlayer(new ExplosionImpulseS2CPacket(impulse), player);
            } else {
                target.hurtMarked = true;
            }
        }
    }

    private static Vec3 quakeOrigin(LivingEntity target) {
        // Quake player origin: 24 units above the bottom of a 56-unit box, scaled to the pose.
        return target.position().add(0.0D, target.getBbHeight() * (24.0D / 56.0D), 0.0D);
    }

    private static boolean canDamage(ServerLevel level, Vec3 center, LivingEntity target) {
        Vec3 origin = quakeOrigin(target);
        if (clearPath(level, center, origin, target)) return true;
        for (int x = -1; x <= 1; x += 2) {
            for (int z = -1; z <= 1; z += 2) {
                if (clearPath(level, center, origin.add(x * TRACE_OFFSET, 0.0D, z * TRACE_OFFSET), target)) return true;
            }
        }
        return false;
    }

    private static boolean clearPath(ServerLevel level, Vec3 from, Vec3 to, LivingEntity target) {
        return level.clip(new ClipContext(from, to, ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE, target)).getType() == HitResult.Type.MISS;
    }
}
