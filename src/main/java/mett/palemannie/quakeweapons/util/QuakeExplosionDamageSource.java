package mett.palemannie.quakeweapons.util;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

/** Keeps explosion attribution while distinguishing kills from self-inflicted deaths. */
public final class QuakeExplosionDamageSource extends DamageSource {
    public QuakeExplosionDamageSource(DamageSource source) {
        super(source.typeHolder(), source.getDirectEntity(), source.getEntity(), source.sourcePositionRaw());
    }

    @Override
    public Component getLocalizedDeathMessage(LivingEntity victim) {
        String key = "death.attack." + getMsgId();
        Entity owner = getEntity();
        if (owner != null && owner != victim) {
            return Component.translatable(key + ".player", victim.getDisplayName(), owner.getDisplayName());
        }
        return Component.translatable(key, victim.getDisplayName());
    }
}
