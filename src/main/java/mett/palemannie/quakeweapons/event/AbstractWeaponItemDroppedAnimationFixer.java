package mett.palemannie.quakeweapons.event;

import mett.palemannie.quakeweapons.item.custom.AbstractWeapon;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class AbstractWeaponItemDroppedAnimationFixer {

    @SubscribeEvent
    public static void onWorldTick(TickEvent.LevelTickEvent event) {

        ///This event fixes the weapon being stuck in the shooting animation when dropping while shooting a Quake-Weapon

        if (event.phase != TickEvent.Phase.END) return;
        if (!(event.level instanceof ServerLevel serverLevel)) return;


        for (ItemEntity itemEntity : serverLevel.getEntitiesOfClass(ItemEntity.class, new AABB(
                serverLevel.getMinBuildHeight(), 0, serverLevel.getMinBuildHeight(),
                serverLevel.getMaxBuildHeight(), serverLevel.getMaxBuildHeight(), serverLevel.getMaxBuildHeight()))) {

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