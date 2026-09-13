package mett.palemannie.quakeweapons.item.custom;

import mett.palemannie.quakeweapons.item.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public final class WeaponCompatibility {
    private WeaponCompatibility() {}

    public static boolean isAmmo(ItemStack stack, Item localAmmo) {
        if (stack.is(localAmmo)) return true;
        String q2wName = localAmmo == ModItems.SHELL.get() ? "shell"
                : localAmmo == ModItems.GRENADE.get() ? "grenade"
                : localAmmo == ModItems.ROCKET.get() ? "rocket" : null;
        return q2wName != null && ForgeRegistries.ITEMS.getKey(stack.getItem())
                .equals(ResourceLocation.fromNamespaceAndPath("q2w", q2wName));
    }
}
