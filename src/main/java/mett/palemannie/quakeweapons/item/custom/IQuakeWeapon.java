package mett.palemannie.quakeweapons.item.custom;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface IQuakeWeapon {
    void onQuakeFire(Player player, ItemStack stack);
}