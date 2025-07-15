package mett.palemannie.quakeweapons.util;

import mett.palemannie.quakeweapons.entity.custom.NailProjectileEntity;
import mett.palemannie.quakeweapons.entity.custom.SuperNailProjectileEntity;
import mett.palemannie.quakeweapons.item.custom.NailGunItem;
import mett.palemannie.quakeweapons.sound.ModSounds;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class ServerPlayHandler {

    public static void handleAxeShoot(ServerPlayer player){
    }

    public static void handleThunderboltShoot(ServerPlayer player){
    }

    public static void handleRocketLauncherShoot(ServerPlayer player){
    }

    public static void handleGrenadeLauncherShoot(ServerPlayer player){
    }

    public static void handleSuperShotgunShoot(ServerPlayer player){
    }

    public static void handleShotgunShoot(ServerPlayer player){
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
        projectile.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 0.8F, 0.0F);

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
        boolean rightSide = NailGunItem.rightSide;
        double offset = rightSide ? 0.3 : -0.3;
        double forwardOffset = 0.4;

        Vec3 look = player.getLookAngle();
        Vec3 right = look.cross(new Vec3(0, 1, 0)).normalize();

        double spawnX = player.getX() + right.x * offset + look.x * forwardOffset;
        double spawnY = player.getEyeY() - 0.1 + right.y * offset + look.y * forwardOffset;
        double spawnZ = player.getZ() + right.z * offset + look.z * forwardOffset;

        NailProjectileEntity projectile = new NailProjectileEntity(sevel, player);
        projectile.setPos(spawnX, spawnY, spawnZ);
        projectile.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 0.8F, 0.0F);

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
