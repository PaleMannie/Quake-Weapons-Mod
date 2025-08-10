package mett.palemannie.quakeweapons.util;

import mett.palemannie.quakeweapons.entity.custom.NailProjectileEntity;
import mett.palemannie.quakeweapons.entity.custom.SuperNailProjectileEntity;
import mett.palemannie.quakeweapons.entity.custom.HitscanMuzzleflashEntity;
import mett.palemannie.quakeweapons.item.custom.NailgunItem;
import mett.palemannie.quakeweapons.sound.ModSounds;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class ServerPlayHandler {

    private static Vec3 getSpreadDirection(Vec3 look, double spreadDegrees, RandomSource random) {

        double spreadRad = Math.toRadians(spreadDegrees);
        double angle = random.nextDouble() * Math.PI * 2;
        double radius = random.nextDouble() * Math.sin(spreadRad);

        Vec3 up = new Vec3(0, 1, 0);
        Vec3 right = look.cross(up).normalize();
        up = right.cross(look).normalize();

        Vec3 offset = right.scale(Math.cos(angle) * radius).add(up.scale(Math.sin(angle) * radius));

        return look.add(offset).normalize();
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

        HitscanMuzzleflashEntity projectile = new HitscanMuzzleflashEntity(sevel, player);
        projectile.setPos(spawnX, spawnY, spawnZ);
        projectile.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 0.0F, 0.0F);

        sevel.addFreshEntity(projectile);

        ///Hitscan
        double maxDistance = 20.0;
        Vec3 start = player.getEyePosition();
        Vec3 look = player.getLookAngle();

        // Speichert Gegner, die schon getroffen wurden
        Set<LivingEntity> alreadyHit = new HashSet<>();

        for (double i = 0.0; i < maxDistance; i += 0.25) {
            Vec3 point = start.add(look.scale(i));

            // Erstelle AABB um Punkt herum
            AABB box = new AABB(
                    point.x - 0.25, point.y - 0.25, point.z - 0.25,
                    point.x + 0.25, point.y + 0.25, point.z + 0.25
            );

            // Finde Gegner im Bereich
            List<LivingEntity> entities = lvl.getEntitiesOfClass(LivingEntity.class, box);
            for (LivingEntity target : entities) {
                if (target != player && target.isAlive() && !alreadyHit.contains(target)) {
                    alreadyHit.add(target); // Markiere als getroffen

                    // Wende Schaden an
                    target.hurt(lvl.damageSources().playerAttack(player), Float.MIN_VALUE);
                    target.hurt(lvl.damageSources().source(ModDamageTypes.THUNDERBOLT_DAMAGE, null, null), 6F);
                }
            }

            player.getServer().overworld().sendParticles(ParticleTypes.ELECTRIC_SPARK, point.x, point.y-0.25f, point.z, 1, 0.02f, 0.02f, 0.02f, 0f);
        }

        ///Sound
        double posX = player.getX();
        double posY = player.getY();
        double posZ = player.getZ();

        if(useTime < 2) lvl.playSound(null, posX, posY, posZ, ModSounds.THUNDERBOLT_START.get(), SoundSource.PLAYERS, 1f, 1f);
        if(useTime % 12 == 0) lvl.playSound(null, posX, posY, posZ, ModSounds.THUNDERBOLT_LOOP.get(), SoundSource.PLAYERS, 1f, 1f);
    }

    public static void handleRocketLauncherShoot(ServerPlayer player){
    }

    public static void handleGrenadeLauncherShoot(ServerPlayer player){
    }

    public static void handleSuperShotgunShoot(ServerPlayer player, int useTime){


    }

    public static void handleShotgunShoot(ServerPlayer player, int useTime){
        ServerLevel sevel = player.serverLevel();
        Random rdm = new Random();
        Level level = player.level();


        ///Hitscan
        final int PELLETS = 6;
        final float DAMAGE_PER_PELLET = 2.0F;
        final double RANGE = 64.0;
        final double SPREAD_DEGREES = 10.0;

        Vec3 eyePos = player.getEyePosition();
        Vec3 look = player.getLookAngle();

        for (int i = 0; i < PELLETS; i++) {

            double yawOffset = (level.random.nextDouble() - 0.5) * 2 * SPREAD_DEGREES;
            double pitchOffset = (level.random.nextDouble() - 0.5) * 2 * SPREAD_DEGREES;

            Vec3 pelletDir = getSpreadDirection(look, SPREAD_DEGREES, level.random);
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

        HitscanMuzzleflashEntity projectile = new HitscanMuzzleflashEntity(sevel, player);
        projectile.setPos(spawnX, spawnY, spawnZ);
        projectile.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 0.0F, 0.0F);

        sevel.addFreshEntity(projectile);
    }

    public static void handleSuperNailgunShoot(ServerPlayer player){

        ServerLevel sevel = player.serverLevel();
        Random rdm = new Random();
        Level lvl = player.level();

        ///Entity
        double forwardOffset = 0.2;

        Vec3 look = player.getLookAngle();
        Vec3 right = look.cross(new Vec3(0, 0, 0)).normalize();

        double spawnX = player.getX() + right.x+ look.x * forwardOffset;
        double spawnY = player.getEyeY() - 0.25 + right.y + look.y * forwardOffset;
        double spawnZ = player.getZ() + right.z + look.z * forwardOffset;

        SuperNailProjectileEntity projectile = new SuperNailProjectileEntity(sevel, player);
        projectile.setPos(spawnX, spawnY, spawnZ);
        projectile.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1F, 0.0F);

        sevel.addFreshEntity(projectile);

        ///Sound
        double posX = player.getX();
        double posY = player.getY();
        double posZ = player.getZ();
        lvl.playSound(null, posX, posY, posZ, ModSounds.SUPER_NAILGUN_SHOOT.get(), SoundSource.PLAYERS, 1f, 1f);
    }

    public static void handleNailgunShoot(ServerPlayer player){

        ServerLevel sevel = player.serverLevel();
        Random rdm = new Random();
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

        NailProjectileEntity projectile = new NailProjectileEntity(sevel, player);
        projectile.setPos(spawnX, spawnY, spawnZ);
        projectile.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1F, 0.0F);

        sevel.addFreshEntity(projectile);

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
