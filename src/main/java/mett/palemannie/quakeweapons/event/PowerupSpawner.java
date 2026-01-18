package mett.palemannie.quakeweapons.event;


import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.QuakeWeaponsConfig;
import mett.palemannie.quakeweapons.entity.ModEntities;
import mett.palemannie.quakeweapons.entity.custom.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

import java.util.function.Consumer;

@Mod.EventBusSubscriber(modid = QuakeWeapons.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PowerupSpawner {

    private static int spawnInterval = 600;
    private static int spawnAttempts = 3;
    private static int searchRadius = 5;
    private static boolean debugEnabled = false;
    private static boolean powerupSpawningEnabled = true;

    private static long tickCounter = 0;

    @SubscribeEvent
    public static void onConfigReload(ModConfigEvent event) {

        if (event.getConfig().getSpec() == QuakeWeaponsConfig.SERVER_SPEC) {
            reloadConfigValues();
        }
    }

    public static void reloadConfigValues() {

        try {
            spawnInterval = QuakeWeaponsConfig.SERVER.powerupSpawnInterval.get();
            spawnAttempts = QuakeWeaponsConfig.SERVER.powerupSpawnAttempts.get();
            searchRadius = QuakeWeaponsConfig.SERVER.powerupSpawnSearchRadius.get();
            debugEnabled = QuakeWeaponsConfig.SERVER.powerupDebug.get();
            powerupSpawningEnabled = QuakeWeaponsConfig.SERVER.enablePowerups.get();

            System.out.println("[QuakeWeapons] PowerupSpawner config reloaded:");
            System.out.println(" interval=" + spawnInterval + " | attempts=" + spawnAttempts + " | radius=" + searchRadius + " | debug=" + debugEnabled
            + " | enablePowerups=" + powerupSpawningEnabled);

        } catch (Exception e) {
            System.err.println("[QuakeWeapons] Failed to load config values, using defaults!");
            spawnInterval = 600;
            spawnAttempts = 3;
            searchRadius = 5;
            debugEnabled = false;
            powerupSpawningEnabled = true;
        }

        System.out.println("[QuakeWeapons] Config values after load: powerupSpawnInterval:"
                + QuakeWeaponsConfig.SERVER.powerupSpawnInterval.get() + ", powerupSpawnAttempts:"
                + QuakeWeaponsConfig.SERVER.powerupSpawnAttempts.get() + ", powerupSpawnSearchRadius:"
                + QuakeWeaponsConfig.SERVER.powerupSpawnSearchRadius.get() + ", enablePowerups:"
                + QuakeWeaponsConfig.SERVER.enablePowerups.get() + ", powerupDebug;"
                + QuakeWeaponsConfig.SERVER.powerupDebug.get());
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.LevelTickEvent event) {

        if (!powerupSpawningEnabled) {
            if (debugEnabled) {
                System.err.println("POWERUP SPAWNING DISABLED. DISABLE DEBUG MODE IN SERVER CONFIG OR ENABLE POWERUP SPAWNING");
            }
            return;
        }

        if (/*event.phase != TickEvent.Phase.END || */event.level().isClientSide()) return;

        int interval = spawnInterval;
        int attempts = spawnAttempts;
        ServerLevel level = (ServerLevel) event.level();
        long gameTime = level.getGameTime();


        if ((gameTime % interval) != 0L) {
            return;
        }

        for (ServerPlayer player : level.players()) {
            for (int i = 0; i < attempts; i++) {
                trySpawnNearPlayer(level, player);
            }
        }
    }

    private static void trySpawnNearPlayer(ServerLevel level, ServerPlayer player) {

        RandomSource random = level.random;
        int x = player.blockPosition().getX() + Mth.nextInt(random, -128, 128);
        int z = player.blockPosition().getZ() + Mth.nextInt(random, -128, 128);
        int y = Mth.nextInt(random, -59, 314);
        BlockPos candidate = new BlockPos(x, y, z);

        if (tryFindSpawnPos(level, candidate, searchRadius, searchRadius, pos -> {
            AbstractPowerupEntity entity = randomPowerup(level);
            entity.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
            level.addFreshEntity(entity);
            debug(level, "§aSpawned " + entity.getType().toShortString() + " at " + pos);
        })) return;

        debug(level, "§cNo valid spawn near " + candidate);
    }

    private static boolean tryFindSpawnPos(ServerLevel level, BlockPos center, int radius, int yRadius, Consumer<BlockPos> onFound) {

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                for (int dy = -yRadius; dy <= yRadius; dy++) {
                    BlockPos pos = center.offset(dx, dy, dz);
                    BlockState above = level.getBlockState(pos);
                    BlockState below = level.getBlockState(pos.below());
                    if (above.isAir() && (below.isSolid() || below.isFaceSturdy(level, pos.below(), Direction.UP))) {
                        onFound.accept(pos);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static AbstractPowerupEntity randomPowerup(ServerLevel level) {

        return switch (level.random.nextInt(4)) {
            case 0 -> new QuadDamagePowerupEntity(ModEntities.QUAD_DAMAGE_POWERUP.get(), level);
            case 1 -> new PentagramPowerupEntity(ModEntities.PENTAGRAM_POWERUP.get(), level);
            case 2 -> new BiosuitPowerupEntity(ModEntities.BIOSUIT_POWERUP.get(), level);
            default -> new RingofshadowsPowerupEntity(ModEntities.RING_POWERUP.get(), level);
        };
    }

    private static void debug(ServerLevel level, String msg) {

        if (!debugEnabled) return;
        Component comp = Component.literal("§d[PowerupSpawner]§r " + msg);
        for (ServerPlayer sp : level.players()) sp.sendSystemMessage(comp);
        System.out.println("[PowerupSpawner] " + msg.replaceAll("§.", ""));
    }
}