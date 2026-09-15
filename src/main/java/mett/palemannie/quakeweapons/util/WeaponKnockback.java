package mett.palemannie.quakeweapons.util;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public final class WeaponKnockback {
    private WeaponKnockback() {}

    public static void hurt(LivingEntity target, DamageSource source, float damage, ServerLevel level) {
        Vec3 before = target.getDeltaMovement();
        target.hurtServer(level, source, damage);
        // Scale only the movement change caused by this hit, including its vertical kick.
        target.setDeltaMovement(before.add(target.getDeltaMovement().subtract(before).scale(0.25D)));
    }
}
