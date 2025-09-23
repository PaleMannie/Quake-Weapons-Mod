package mett.palemannie.quakeweapons.event;


import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.entity.ModEntities;
import mett.palemannie.quakeweapons.entity.custom.*;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.commands.PlaceCommand;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = QuakeWeapons.MODID)
public class PowerupSpawner {

    private static final double SPAWN_CHANCE = 0.001D; // Feinjustieren! Klein = selten 0.00001D
    private static final int MAX_ATTEMPTS = 3;           // pro Tick max. Versuche

    @SubscribeEvent
    public static void onWorldTick(TickEvent.LevelTickEvent event) {
        if (event.level.isClientSide || event.phase != TickEvent.Phase.END) return;
        ServerLevel level = (ServerLevel) event.level;

        // Nur Overworld? → Bedingung hier rein
        // if (!level.dimension().equals(Level.OVERWORLD)) return;

        for (int i = 0; i < MAX_ATTEMPTS; i++) {
            if (level.random.nextDouble() < SPAWN_CHANCE) {
                trySpawnPowerup(level);
            }
        }
    }

    private static void trySpawnPowerup(ServerLevel level) {

        ServerPlayer player = level.getRandomPlayer();

        if(player == null) return;

        int x = Mth.nextInt(level.random, -255, 255);
        int z = Mth.nextInt(level.random, -255, 255);

        BlockPos topPos = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, new BlockPos(player.getBlockX() + x, 0,player.getBlockZ() + z));

        if (!level.getBlockState(topPos.below()).isSolid()) return;

        AbstractPowerupEntity entity = randomPowerup(level);
        entity.moveTo(topPos.getX() + 0.5, topPos.getY() + 1, topPos.getZ() + 0.5, 0, 0);
        level.addFreshEntity(entity);

        // 🔹 Debug-Meldung ins Chat an alle Spieler
        String name = entity.getType().toShortString(); // oder dein eigenes Label
        Component msg = Component.literal("§d[Powerup-Spawn]§r " + name + " bei " + topPos.getX() + " " + topPos.getY() + " " + topPos.getZ());
        for (ServerPlayer sp : level.players()) {
            sp.sendSystemMessage(msg);
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
