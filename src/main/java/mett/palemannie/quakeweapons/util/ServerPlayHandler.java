package mett.palemannie.quakeweapons.util;

import mett.palemannie.quakeweapons.QuakeWeaponsConfig;
import mett.palemannie.quakeweapons.entity.ModEntities;
import mett.palemannie.quakeweapons.entity.custom.*;
import mett.palemannie.quakeweapons.item.custom.NailgunItem;
import mett.palemannie.quakeweapons.sound.ModSounds;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class ServerPlayHandler {

    private static boolean isMuzzleFlashEnabled(){
        return QuakeWeaponsConfig.COMMON.enableMuzzleFlash.get();
    }

    private static Vec3 getShotgunNormalizedSpreadDirection(Vec3 look, double spreadDegrees, RandomSource random) {

        double spreadRad = Math.toRadians(spreadDegrees);
        double angle = random.nextDouble() * Math.PI * 2;
        double radius = random.nextDouble() * Math.sin(spreadRad);

        Vec3 up = new Vec3(0, 1, 0);
        Vec3 right = look.cross(up).normalize();
        up = right.cross(look).normalize();

        Vec3 offset = right.scale(Math.cos(angle) * radius).add(up.scale(Math.sin(angle) * radius));

        return look.add(offset).normalize();
    }

    private static Vec3 getSuperShotgunNormalizedSpreadDirection(Vec3 forward, double spreadH, double spreadV, RandomSource random) {

        double yaw = Math.toRadians((random.nextDouble() - 0.5) * 2 * spreadH);
        double pitch = Math.toRadians((random.nextDouble() - 0.5) * 2 * spreadV);

        Vec3 up = new Vec3(0, 1, 0);
        Vec3 right = forward.cross(up).normalize();
        up = right.cross(forward).normalize();

        Vec3 dir = forward
                .add(right.scale(Math.tan(yaw)))
                .add(up.scale(Math.tan(pitch)));

        return dir.normalize();
    }

    private static void shootFromRotationNoMomentum(Projectile projectile, Player player, float xRot, float yRot, float velocity, float inaccuracy) {

        projectile.setOwner(player);
        projectile.setPos(player.getEyePosition().x, player.getEyeY() - 0.2d, player.getEyePosition().z);

        float f = -Mth.sin(yRot * ((float)Math.PI / 180F)) * Mth.cos(xRot * ((float)Math.PI / 180F));
        float f1 = -Mth.sin(xRot * ((float)Math.PI / 180F));
        float f2 =  Mth.cos(yRot * ((float)Math.PI / 180F)) * Mth.cos(xRot * ((float)Math.PI / 180F));

        projectile.setYRot(player.getYRot());
        projectile.yRotO = player.getYRot();
        projectile.setXRot(player.getXRot());
        projectile.xRotO = player.getXRot();

        projectile.shoot(f, f1, f2, velocity, inaccuracy);
    }

    public static void handleAxeShoot(ServerPlayer player){
    }

    public static void handleThunderboltShoot(ServerPlayer player, int useTime){

        Level lvl = player.level();
        ServerLevel sevel = player.serverLevel();

        ///Entity
        double forwardOffset = 0.2;

        Vec3 look1 = player.getLookAngle();
        Vec3 right = look1.cross(new Vec3(0, 0, 0)).normalize();

        double spawnX = player.getX() + right.x+ look1.x * forwardOffset;
        double spawnY = player.getEyeY() - 0.25 + right.y + look1.y * forwardOffset;
        double spawnZ = player.getZ() + right.z + look1.z * forwardOffset;

        if(isMuzzleFlashEnabled()){

            MuzzleflashEntity flash = new MuzzleflashEntity(sevel, player);
            flash.setPos(spawnX, spawnY, spawnZ);
            flash.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 0.0F, 0.0F);

            sevel.addFreshEntity(flash);
        }

        ///Hitscan
        double maxDistance = 20.0;
        Vec3 start = player.getEyePosition();
        Vec3 look = player.getLookAngle();

        Set<LivingEntity> alreadyHit = new HashSet<>();

        for (double i = 0.0; i < maxDistance; i += 0.25) {
            Vec3 point = start.add(look.scale(i));

            AABB box = new AABB(
                    point.x - 0.25, point.y - 0.25, point.z - 0.25,
                    point.x + 0.25, point.y + 0.25, point.z + 0.25
            );

            List<LivingEntity> entities = lvl.getEntitiesOfClass(LivingEntity.class, box);
            for (LivingEntity target : entities) {
                if (target != player && target.isAlive() && !alreadyHit.contains(target)) {
                    alreadyHit.add(target);

                    target.hurt(lvl.damageSources().playerAttack(player), Float.MIN_VALUE);
                    target.hurt(lvl.damageSources().source(ModDamageTypes.THUNDERBOLT_DAMAGE, null, null), WeaponDamageStats.ThunderboltDamage);
                }
            }

            if(QuakeWeaponsConfig.COMMON.enableThunderboltTracer.get()) player.getServer().overworld().sendParticles(ParticleTypes.ELECTRIC_SPARK, point.x, point.y-0.25f, point.z, 1, 0.02f, 0.02f, 0.02f, 0f);
        }

        ///Sound
        double posX = player.getX();
        double posY = player.getY();
        double posZ = player.getZ();

        if(useTime < 2) lvl.playSound(null, posX, posY, posZ, ModSounds.THUNDERBOLT_START.get(), SoundSource.PLAYERS, 1f, 1f);
        if(useTime % 12 == 0) lvl.playSound(null, posX, posY, posZ, ModSounds.THUNDERBOLT_LOOP.get(), SoundSource.PLAYERS, 1f, 1f);
    }

    public static void handleRocketLauncherShoot(ServerPlayer player){

        ServerLevel sevel = player.serverLevel();
        Level lvl = player.level();

        ///Entity
        double forwardOffset = 0.2;

        Vec3 look = player.getLookAngle();
        Vec3 right = look.cross(new Vec3(0, 0, 0)).normalize();

        double spawnX = player.getX() + right.x+ look.x * forwardOffset;
        double spawnY = player.getEyeY() - 0.2f + right.y + look.y * forwardOffset;
        double spawnZ = player.getZ() + right.z + look.z * forwardOffset;

        RocketProjectileEntity rocket = new RocketProjectileEntity(ModEntities.ROCKET_PROJECTILE.get(), sevel);

        shootFromRotationNoMomentum(rocket, player, player.getXRot(), player.getYRot(), 0.8f, 0.0f);
        sevel.addFreshEntity(rocket);

        if(isMuzzleFlashEnabled()){

            MuzzleflashEntity flash = new MuzzleflashEntity(sevel, player);
            flash.setPos(spawnX, spawnY, spawnZ);

            sevel.addFreshEntity(flash);
        }

        ///Sound
        double posX = player.getX();
        double posY = player.getY();
        double posZ = player.getZ();
        lvl.playSound(null, posX, posY, posZ, ModSounds.ROCKETLAUNCHER_SHOOT.get(), SoundSource.PLAYERS, 1f, 1f);
    }

    public static void handleGrenadeLauncherShoot(ServerPlayer player){

        ServerLevel sevel = player.serverLevel();
        Level lvl = player.level();

        ///Entity
        double forwardOffset = 0.2;

        Vec3 look = player.getLookAngle();
        Vec3 right = look.cross(new Vec3(0, 0, 0)).normalize();

        double spawnX = player.getX() + right.x+ look.x * forwardOffset;
        double spawnY = player.getEyeY() - 0.25 + right.y + look.y * forwardOffset;
        double spawnZ = player.getZ() + right.z + look.z * forwardOffset;

        GrenadeProjectileEntity grenade = new GrenadeProjectileEntity(ModEntities.GRENADE_PROJECTILE.get(), sevel);

        shootFromRotationNoMomentum(grenade, player, player.getXRot(), player.getYRot(), 0.8f, 0.0f);
        sevel.addFreshEntity(grenade);

        if(isMuzzleFlashEnabled()){

            MuzzleflashEntity flash = new MuzzleflashEntity(sevel, player);
            flash.setPos(spawnX, spawnY, spawnZ);

            sevel.addFreshEntity(flash);
        }

        ///Sound
        double posX = player.getX();
        double posY = player.getY();
        double posZ = player.getZ();
        lvl.playSound(null, posX, posY, posZ, ModSounds.GRENADELAUNCHER_SHOOT.get(), SoundSource.PLAYERS, 1f, 1f);
    }

    public static void handleSuperShotgunShoot(ServerPlayer player) {

        ServerLevel sevel = player.serverLevel();
        Vec3 eyePos = player.getEyePosition();
        Vec3 look = player.getLookAngle();

        final int PELLETS = 14;
        final double RANGE = 64.0;
        final double SPREAD_H = 11.0;
        final double SPREAD_V = 7.0;
        final float DAMAGE_PER_PELLET = WeaponDamageStats.SuperShotgunDamage;

        for (int i = 0; i < PELLETS; i++) {
            Vec3 pelletDir = getSuperShotgunNormalizedSpreadDirection(look, SPREAD_H, SPREAD_V, sevel.random);
            Vec3 endPos = eyePos.add(pelletDir.scale(RANGE));

            BlockHitResult blockHit = sevel.clip(new ClipContext(
                    eyePos, endPos,
                    ClipContext.Block.COLLIDER,
                    ClipContext.Fluid.NONE,
                    player
            ));

            EntityHitResult entityHit = ProjectileUtil.getEntityHitResult(
                    sevel, player, eyePos, endPos,
                    new AABB(eyePos, endPos).inflate(1.0),
                    e -> e instanceof LivingEntity && e != player
            );

            if (entityHit != null && (blockHit == null || entityHit.getLocation().distanceTo(eyePos) < blockHit.getLocation().distanceTo(eyePos))) {
                LivingEntity target = (LivingEntity) entityHit.getEntity();
                target.hurt(sevel.damageSources().playerAttack(player), Float.MIN_VALUE);
                target.hurt(sevel.damageSources().source(ModDamageTypes.SUPER_SHOTGUN_DAMAGE, null, null), DAMAGE_PER_PELLET);

                Vec3 hitPos = entityHit.getLocation();
                sevel.sendParticles(player, ParticleTypes.LANDING_LAVA, true, hitPos.x, hitPos.y, hitPos.z, 1, 0.5d, 0.5d, 0.5d, 0d);
                sevel.sendParticles(player, ParticleTypes.SMOKE, true, hitPos.x, hitPos.y, hitPos.z, 1, 0.5d, 0.5d, 0.5d, 0d);
            }
            else if (blockHit != null && blockHit.getType() != HitResult.Type.MISS) {
                Vec3 hitPos = blockHit.getLocation();
                sevel.sendParticles(ParticleTypes.SMOKE, hitPos.x, hitPos.y, hitPos.z,
                        1, 0.1, 0.1, 0.1, 0.0);
            }
        }

        ///Sound
        double posX = player.getX();
        double posY = player.getY();
        double posZ = player.getZ();
        sevel.playSound(null, posX, posY, posZ, ModSounds.SUPER_SHOTGUN_SHOOT.get(), SoundSource.PLAYERS, 1f, 1f);

        ///Entity
        double forwardOffset = 0.2;

        Vec3 look1 = player.getLookAngle();
        Vec3 right = look1.cross(new Vec3(0, 0, 0)).normalize();

        double spawnX = player.getX() + right.x+ look1.x * forwardOffset;
        double spawnY = player.getEyeY() - 0.25 + right.y + look1.y * forwardOffset;
        double spawnZ = player.getZ() + right.z + look1.z * forwardOffset;

        if(isMuzzleFlashEnabled()){

            MuzzleflashEntity flash = new MuzzleflashEntity(sevel, player);
            flash.setPos(spawnX, spawnY, spawnZ);

            sevel.addFreshEntity(flash);
        }
    }

    public static void handleShotgunShoot(ServerPlayer player){

        ServerLevel sevel = player.serverLevel();
        Level level = player.level();


        ///Hitscan
        final int PELLETS = 6;
        final float DAMAGE_PER_PELLET = WeaponDamageStats.ShotgunDamage;
        final double RANGE = 64.0;
        final double SPREAD_DEGREES = 10.0;

        Vec3 eyePos = player.getEyePosition();
        Vec3 look = player.getLookAngle();

        for (int i = 0; i < PELLETS; i++) {

            Vec3 pelletDir = getShotgunNormalizedSpreadDirection(look, SPREAD_DEGREES, level.random);
            Vec3 endPos = eyePos.add(pelletDir.scale(RANGE));

            BlockHitResult blockHit = level.clip(new ClipContext(
                    eyePos, endPos,
                    ClipContext.Block.COLLIDER,
                    ClipContext.Fluid.NONE,
                    player
            ));

            EntityHitResult entityHit = ProjectileUtil.getEntityHitResult(
                    level, player, eyePos, endPos,
                    new AABB(eyePos, endPos).inflate(1.0),
                    e -> e instanceof LivingEntity && e != player
            );

            if (entityHit != null && (blockHit == null || entityHit.getLocation().distanceTo(eyePos) < blockHit.getLocation().distanceTo(eyePos))) {
                LivingEntity target = (LivingEntity) entityHit.getEntity();
                target.hurt(level.damageSources().playerAttack(player), Float.MIN_VALUE);
                target.hurt(level.damageSources().source(ModDamageTypes.SHOTGUN_DAMAGE, null, null), DAMAGE_PER_PELLET);

                Vec3 hitPos = entityHit.getLocation();
                sevel.sendParticles(player, ParticleTypes.LANDING_LAVA, true, hitPos.x, hitPos.y, hitPos.z, 1, 0.5d, 0.5d, 0.5d, 0d);
                sevel.sendParticles(player, ParticleTypes.SMOKE, true, hitPos.x, hitPos.y, hitPos.z, 1, 0.5d, 0.5d, 0.5d, 0d);
            }
            // Sonst Block
            else if (blockHit != null && blockHit.getType() != HitResult.Type.MISS) {

                Vec3 hitPos = blockHit.getLocation();
                sevel.sendParticles(player, ParticleTypes.SMOKE, true, hitPos.x, hitPos.y, hitPos.z, 1, 0.1d, 0.1d, 0.1d, 0d);
            }
        }

        ///Sound
        double posX = player.getX();
        double posY = player.getY();
        double posZ = player.getZ();
        level.playSound(null, posX, posY, posZ, ModSounds.SHOTGUN_SHOOT.get(), SoundSource.PLAYERS, 1f, 1f);

        ///Entity
        double forwardOffset = 0.2;

        Vec3 look1 = player.getLookAngle();
        Vec3 right = look1.cross(new Vec3(0, 0, 0)).normalize();

        double spawnX = player.getX() + right.x+ look1.x * forwardOffset;
        double spawnY = player.getEyeY() - 0.25 + right.y + look1.y * forwardOffset;
        double spawnZ = player.getZ() + right.z + look1.z * forwardOffset;

        if(isMuzzleFlashEnabled()){

            MuzzleflashEntity flash = new MuzzleflashEntity(sevel, player);
            flash.setPos(spawnX, spawnY, spawnZ);

            sevel.addFreshEntity(flash);
        }
    }

    public static void handleSuperNailgunShoot(ServerPlayer player){

        ServerLevel sevel = player.serverLevel();
        Level lvl = player.level();

        ///Entity
        double forwardOffset = 0.2;

        Vec3 look = player.getLookAngle();
        Vec3 right = look.cross(new Vec3(0, 0, 0)).normalize();

        double spawnX = player.getX() + right.x+ look.x * forwardOffset;
        double spawnY = player.getEyeY() - 0.25 + right.y + look.y * forwardOffset;
        double spawnZ = player.getZ() + right.z + look.z * forwardOffset;

        SuperNailProjectileEntity supernail = new SuperNailProjectileEntity(ModEntities.SUPER_NAIL_PROJECTILE.get(), sevel);

        shootFromRotationNoMomentum(supernail, player, player.getXRot(), player.getYRot(), 1f, 0.0f);
        sevel.addFreshEntity(supernail);

        if(isMuzzleFlashEnabled()){

            MuzzleflashEntity flash = new MuzzleflashEntity(sevel, player);
            flash.setPos(spawnX, spawnY, spawnZ);

            sevel.addFreshEntity(flash);
        }

        ///Sound
        double posX = player.getX();
        double posY = player.getY();
        double posZ = player.getZ();
        lvl.playSound(null, posX, posY, posZ, ModSounds.SUPER_NAILGUN_SHOOT.get(), SoundSource.PLAYERS, 1f, 1f);
    }

    public static void handleNailgunShoot(ServerPlayer player){

        ServerLevel sevel = player.serverLevel();
        Level lvl = player.level();

        ///Entity
        boolean rightSide = NailgunItem.rightSide;
        double offset = rightSide ? 0.3 : -0.3;
        double forwardOffset = 0.4;

        Vec3 look = player.getLookAngle();
        Vec3 right = look.cross(new Vec3(0, 1, 0)).normalize();

        double spawnX = player.getX() + right.x * offset + look.x * forwardOffset;
        double spawnY = player.getEyeY() - 0.1 + right.y * offset + look.y * forwardOffset;
        double spawnZ = player.getZ() + right.z * offset + look.z * forwardOffset;

        NailProjectileEntity nail = new NailProjectileEntity(ModEntities.NAIL_PROJECTILE.get(), sevel);

        shootFromRotationNoMomentum(nail, player, player.getXRot(), player.getYRot(), 1f, 0.0f);
        nail.setPos(spawnX, spawnY, spawnZ);
        sevel.addFreshEntity(nail);

        if(isMuzzleFlashEnabled()){

            MuzzleflashEntity flash = new MuzzleflashEntity(sevel, player);
            flash.setPos(spawnX, spawnY, spawnZ);

            sevel.addFreshEntity(flash);
        }

        ///Sound
        double posX = player.getX();
        double posY = player.getY();
        double posZ = player.getZ();
        lvl.playSound(null, posX, posY, posZ, ModSounds.NAILGUN_SHOOT.get(), SoundSource.PLAYERS, 1f, 1f);
    }

    public static void playAmmoEmptySound(ServerPlayer player){

        Level level = player.level();
        level.playSound(null, player.blockPosition(), SoundEvents.DISPENSER_FAIL, SoundSource.NEUTRAL, 1f, 1f);
    }
}
