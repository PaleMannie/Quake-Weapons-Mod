package mett.palemannie.quakeweapons.util;

import mett.palemannie.quakeweapons.QuakeWeaponsConfig;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.phys.AABB;

public final class WeaponAggroHandler {

    private WeaponAggroHandler() {}

    public static void onWeaponShot(ServerPlayer player) {

        ServerLevel level = player.level();

        double radius = QuakeWeaponsConfig.SERVER.weaponAggroRange.get();
        double radiusSqr = radius * radius;

        AABB area = player.getBoundingBox().inflate(radius);

        for (Monster monster : level.getEntitiesOfClass(Monster.class, area, monster ->
                monster.isAlive()
                        && !monster.isSpectator()
                        && monster.distanceToSqr(player) <= radiusSqr
        )) {
            monster.setTarget(player);
            monster.setAggressive(true);
        }
    }
}
