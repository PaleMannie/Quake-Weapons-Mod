package mett.palemannie.quakeweapons.event;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.item.custom.AbstractWeapon;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = QuakeWeapons.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class AbstractWeaponItemDroppedAnimationFixer {
    private AbstractWeaponItemDroppedAnimationFixer() {}

    @SubscribeEvent
    public static void onItemToss(ItemTossEvent event) {
        if (!(event.getPlayer().level() instanceof ServerLevel level)) return;
        ItemStack stack = event.getEntity().getItem();
        if (stack.getItem() instanceof AbstractWeapon weapon) {
            weapon.hardStopTriggeredAnimations(event.getPlayer(), level, stack);
        }
    }
}
