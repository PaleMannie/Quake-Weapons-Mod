package mett.palemannie.quakeweapons.event;


import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.QuakeWeaponsConfig;
import mett.palemannie.quakeweapons.entity.ModEntities;
import mett.palemannie.quakeweapons.entity.custom.*;
import mett.palemannie.quakeweapons.util.QWConfigStats;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.function.Consumer;

@Mod.EventBusSubscriber(modid = QuakeWeapons.MODID)
public class PowerupSpawner {

    private static final int SPAWN_INTERVAL = 600;
    private static final int ATTEMPTS_PER_PLAYER = 5;
    private static final int SEARCH_RADIUS = 5;

    private static int tickCounter = 0;

    @SubscribeEvent
    public static void onWorldTick(TickEvent.LevelTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.level.isClientSide) return;
        ServerLevel level = (ServerLevel) event.level;

        tickCounter++;
        if (tickCounter % SPAWN_INTERVAL != 0) return;

        for (ServerPlayer player : level.players()) {
            for (int i = 0; i < ATTEMPTS_PER_PLAYER; i++) {
                trySpawnNearPlayer(level, player);
            }
        }
    }

    private static void trySpawnNearPlayer(ServerLevel level, ServerPlayer player) {
        RandomSource random = level.random;

        // Vanilla: Zufällige Position im 8-Chunk-Radius um Spieler
        int x = player.blockPosition().getX() + Mth.nextInt(random, -128, 128);
        int z = player.blockPosition().getZ() + Mth.nextInt(random, -128, 128);
        int y = Mth.nextInt(random, level.getMinBuildHeight() + 5, level.getMaxBuildHeight() - 5);

        BlockPos candidate = new BlockPos(x, y, z);

        // Suche im Umkreis nach einer brauchbaren Stelle
        if (tryFindSpawnPos(level, candidate, SEARCH_RADIUS, pos -> {
            AbstractPowerupEntity entity = randomPowerup(level);
            entity.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 0, 0);
            level.addFreshEntity(entity);

            debug(level, "§aSpawned " + entity.getType().toShortString() + " at " + pos);
        })) {
            return;
        } else {
            debug(level, "§cNo valid spawn near " + candidate);
        }
    }

    private static boolean tryFindSpawnPos(ServerLevel level, BlockPos center, int radius, Consumer<BlockPos> onFound) {
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                for (int dy = -radius; dy <= radius; dy++) {
                    BlockPos pos = center.offset(dx, dy, dz);
                    if (level.getBlockState(pos).isAir() && level.getBlockState(pos.below()).isSolid()) {
                        onFound.accept(pos);
                        return true;
                    }
                }
            }
        }
        return false;
    }


    private static void debug(ServerLevel level, String msg) {

        if(QuakeWeaponsConfig.SERVER.powerupDebug.get()) {

            Component comp = Component.literal("§d[Powerup Debug]§r " + msg);
            for (ServerPlayer sp : level.players()) {
                sp.sendSystemMessage(comp);
            }
        }
    }

    private static AbstractPowerupEntity randomPowerup(ServerLevel level) {

        int roll = level.random.nextInt(4); // 4 Powerups
        return switch (roll) {
            case 0 -> new QuadDamagePowerupEntity(ModEntities.QUAD_DAMAGE_POWERUP.get(), level);
            case 1 -> new PentagramPowerupEntity(ModEntities.PENTAGRAM_POWERUP.get(), level);
            case 2 -> new BiosuitPowerupEntity(ModEntities.BIOSUIT_POWERUP.get(), level);
            default -> new RingofshadowsPowerupEntity(ModEntities.RING_POWERUP.get(), level);
        };
    }
}
