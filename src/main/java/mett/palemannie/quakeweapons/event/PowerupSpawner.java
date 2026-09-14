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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.function.Consumer;

@Mod.EventBusSubscriber(modid = QuakeWeapons.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class PowerupSpawner {
    private PowerupSpawner() {}

    private static final int TYPE_COUNT = 9;
    private static final double NEARBY_RADIUS = 128d;
    private static final String AUTO_SPAWN_TAG = "QWAutomaticPickup";
    private static final Map<ServerLevel, List<Integer>> BAGS = new WeakHashMap<>();

    private static int spawnInterval = 600;
    private static int spawnAttempts = 3;
    private static int searchRadius = 5;
    private static int maxNearbyPowerups = 4;
    private static boolean debugEnabled;
    private static boolean powerupSpawningEnabled = true;

    public static void reloadConfigValues() {
        try {
            spawnInterval = QuakeWeaponsConfig.SERVER.powerupSpawnInterval.get();
            spawnAttempts = QuakeWeaponsConfig.SERVER.powerupSpawnAttempts.get();
            searchRadius = QuakeWeaponsConfig.SERVER.powerupSpawnSearchRadius.get();
            maxNearbyPowerups = QuakeWeaponsConfig.SERVER.maxNearbyPowerups.get();
            debugEnabled = QuakeWeaponsConfig.SERVER.powerupDebug.get();
            powerupSpawningEnabled = QuakeWeaponsConfig.SERVER.enablePowerups.get();
            QuakeWeapons.LOGGER.info("PowerupSpawner: interval={}, attempts={}, searchRadius={}, maxNearby={}, enabled={}",
                    spawnInterval, spawnAttempts, searchRadius, maxNearbyPowerups, powerupSpawningEnabled);
        } catch (Exception exception) {
            QuakeWeapons.LOGGER.error("Failed to load PowerupSpawner config; using defaults", exception);
            spawnInterval = 600;
            spawnAttempts = 3;
            searchRadius = 5;
            maxNearbyPowerups = 4;
            debugEnabled = false;
            powerupSpawningEnabled = true;
        }
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.LevelTickEvent event) {
        if (event.phase != TickEvent.Phase.END || !(event.level instanceof ServerLevel level)) return;
        if (!powerupSpawningEnabled || level.getGameTime() % spawnInterval != 0L) return;

        for (ServerPlayer player : level.players()) {
            for (int attempt = 0; attempt < spawnAttempts; attempt++) {
                trySpawnNearPlayer(level, player);
            }
        }
    }

    private static void trySpawnNearPlayer(ServerLevel level, ServerPlayer player) {
        if (nearbyLimitReached(level, player)) {
            debug(level, "Nearby automatic pickup limit reached for " + player.getScoreboardName());
            return;
        }

        RandomSource random = level.random;
        int x = player.blockPosition().getX() + Mth.nextInt(random, -128, 128);
        int z = player.blockPosition().getZ() + Mth.nextInt(random, -128, 128);
        int minY = Math.max(level.getMinBuildHeight() + 1, player.blockPosition().getY() - 64);
        int maxY = Math.min(level.getMaxBuildHeight() - 1, player.blockPosition().getY() + 64);
        int y = Mth.nextInt(random, minY, maxY);
        BlockPos candidate = new BlockPos(x, y, z);

        if (tryFindSpawnPos(level, player, candidate, searchRadius, pos -> {
            Vec3 spawnPosition = Vec3.atBottomCenterOf(pos);
            for (ServerPlayer nearby : level.players()) {
                if (nearby.distanceToSqr(spawnPosition) <= NEARBY_RADIUS * NEARBY_RADIUS
                        && nearbyLimitReached(level, nearby)) return;
            }

            Entity entity = randomPickup(level);
            entity.getPersistentData().putBoolean(AUTO_SPAWN_TAG, true);
            entity.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 0, 0);
            if (level.addFreshEntity(entity)) {
                List<Integer> bag = getBag(level);
                bag.remove(bag.size() - 1);
                debug(level, "§aSpawned " + entity.getType().toShortString() + " at " + pos);
            } else {
                debug(level, "§cSpawn rejected for " + entity.getType().toShortString() + " at " + pos);
            }
        })) return;
        debug(level, "§cNo valid spawn near " + candidate);
    }

    private static boolean nearbyLimitReached(ServerLevel level, ServerPlayer player) {
        return level.getEntities(player, player.getBoundingBox().inflate(NEARBY_RADIUS),
                entity -> !entity.isRemoved()
                        && entity.getPersistentData().getBoolean(AUTO_SPAWN_TAG)
                        && entity.distanceToSqr(player) <= NEARBY_RADIUS * NEARBY_RADIUS)
                .size() >= maxNearbyPowerups;
    }

    private static boolean tryFindSpawnPos(ServerLevel level, ServerPlayer player, BlockPos center,
                                           int radius, Consumer<BlockPos> onFound) {
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                for (int dy = -radius; dy <= radius; dy++) {
                    BlockPos pos = center.offset(dx, dy, dz);
                    if (player.distanceToSqr(Vec3.atBottomCenterOf(pos)) > NEARBY_RADIUS * NEARBY_RADIUS) continue;
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

    private static List<Integer> getBag(ServerLevel level) {
        List<Integer> bag = BAGS.computeIfAbsent(level, ignored -> new ArrayList<>(TYPE_COUNT));
        if (bag.isEmpty()) {
            for (int i = 0; i < TYPE_COUNT; i++) bag.add(i);
            for (int i = bag.size() - 1; i > 0; i--) {
                int j = level.random.nextInt(i + 1);
                int value = bag.get(i);
                bag.set(i, bag.get(j));
                bag.set(j, value);
            }
        }
        return bag;
    }

    private static Entity randomPickup(ServerLevel level) {
        List<Integer> bag = getBag(level);
        return switch (bag.get(bag.size() - 1)) {
            case 0 -> new QuadDamagePowerupEntity(ModEntities.QUAD_DAMAGE_POWERUP.get(), level);
            case 1 -> new PentagramPowerupEntity(ModEntities.PENTAGRAM_POWERUP.get(), level);
            case 2 -> new BiosuitPowerupEntity(ModEntities.BIOSUIT_POWERUP.get(), level);
            case 3 -> new RingofshadowsPowerupEntity(ModEntities.RING_POWERUP.get(), level);
            case 4 -> new ShellsAmmopickupEntity(ModEntities.SHELLS_AMMOPICKUP.get(), level);
            case 5 -> new NailsAmmopickupEntity(ModEntities.NAILS_AMMOPICKUP.get(), level);
            case 6 -> new CellsAmmopickupEntity(ModEntities.CELLS_AMMOPICKUP.get(), level);
            case 7 -> new GrenadesAmmopickupEntity(ModEntities.GRENADES_AMMOPICKUP.get(), level);
            case 8 -> new GrenadesAmmopickupEntity(ModEntities.MEGAHEALTH_PICKUP.get(), level);
            default -> new RocketsAmmopickupEntity(ModEntities.ROCKETS_AMMOPICKUP.get(), level);
        };
    }

    private static void debug(ServerLevel level, String message) {
        if (!debugEnabled) return;
        Component text = Component.literal("§d[PowerupSpawner]§r " + message);
        for (ServerPlayer player : level.players()) player.sendSystemMessage(text);
    }
}
