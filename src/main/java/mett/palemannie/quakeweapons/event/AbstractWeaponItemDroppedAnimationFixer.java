package mett.palemannie.quakeweapons.event;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.QuakeWeaponsConfig;
import mett.palemannie.quakeweapons.item.custom.AbstractWeapon;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = QuakeWeapons.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class AbstractWeaponItemDroppedAnimationFixer {

    private static double scanRadius = 20d;
    private static long scanInterval = 5;

    private static long tickCounter = 0;

    /// Reads the latest values from the server config
    public static void reloadConfigValues() {
        try {
            scanRadius = QuakeWeaponsConfig.SERVER.animationDroppedFixerSearchRadius.get();
            scanInterval = QuakeWeaponsConfig.SERVER.animationDroppedFixerSearchInterval.get();
            System.out.println("[QuakeWeapons] Dropped Weapon Fixer Config reloaded: radius=" + scanRadius + ", interval=" + scanInterval);
        } catch (Exception e) {
            System.err.println("[QuakeWeapons] Failed to load dropped weapon fixer config values, using defaults!");
            scanRadius = 20d;
            scanInterval = 5;
        }
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.LevelTickEvent event) {

        ///This event fixes the weapon being stuck in the shooting animation when dropping while shooting a Quake-Weapon

        if (event.phase != TickEvent.Phase.START || event.level.isClientSide) return;
        ServerLevel serverLevel = (ServerLevel) event.level;

        tickCounter++;
        if (tickCounter % scanInterval != 0) return;

        for (ServerPlayer player : serverLevel.players()) {

            AABB scanBox = player.getBoundingBox().inflate(scanRadius);
            List<ItemEntity> nearbyItems = serverLevel.getEntitiesOfClass(ItemEntity.class, scanBox, e -> true);

            for (ItemEntity itemEntity : nearbyItems) {
                ItemStack stack = itemEntity.getItem();

                if (stack.getItem() instanceof AbstractWeapon weapon) {
                    if(serverLevel.getRandomPlayer() != null) {
                        weapon.stopShootingAnimation(serverLevel.getRandomPlayer(), serverLevel, stack);
                        weapon.stopAmmoEmptyAnimation(serverLevel.getRandomPlayer(), serverLevel, stack);
                        weapon.stopIdleAnimation(serverLevel.getRandomPlayer(), serverLevel, stack);
                    }
                }
            }
        }
    }
}