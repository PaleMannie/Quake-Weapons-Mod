package mett.palemannie.quakeweapons.util;

import mett.palemannie.quakeweapons.QuakeWeaponsConfig;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.phys.AABB;

public final class WeaponAggroHandler {

    private WeaponAggroHandler() {}

    public static void onWeaponShot(ServerPlayer player) {
        int range = QuakeWeaponsConfig.SERVER.weaponAggroRange.get();
        if (range <= 0) return;

        double radiusSqr = (double) range * range;
        AABB area = player.getBoundingBox().inflate(range);
        for (Monster monster : player.serverLevel().getEntitiesOfClass(Monster.class, area, monster ->
                monster.isAlive()
                        && !monster.isSpectator()
                        && monster.distanceToSqr(player) <= radiusSqr)) {
            monster.setTarget(player);
            monster.setAggressive(true);
        }
    }
}
