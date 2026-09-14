package mett.palemannie.quakeweapons.gui;

import mett.palemannie.quakeweapons.item.custom.AbstractWeapon;
import mett.palemannie.quakeweapons.item.custom.WeaponCompatibility;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public final class AmmoHudOverlay {

    private AmmoHudOverlay() {}

    public static final IGuiOverlay HUD = (gui, graphics, partialTick, width, height) -> {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.options.hideGui || minecraft.player == null || minecraft.player.isSpectator()) return;
        if (!(minecraft.player.getMainHandItem().getItem() instanceof AbstractWeapon weapon)) return;
        Item ammo = weapon.getAmmoItem();
        if (ammo == null) return;

        int total = 0;
        for (int slot = 0; slot < minecraft.player.getInventory().getContainerSize(); slot++) {
            ItemStack stack = minecraft.player.getInventory().getItem(slot);
            if (WeaponCompatibility.isAmmo(stack, ammo)) total += stack.getCount();
        }

        int x = width / 2 - 91;
        int y = minecraft.player.isCreative() ? height - gui.leftHeight
                : minecraft.player.getArmorValue() > 0 ? height - gui.leftHeight - 18
                : height - gui.leftHeight - 8;
        graphics.renderItem(ammo.getDefaultInstance(), x, y);
        drawOutlinedString(graphics, minecraft.font, Integer.toString(total), x + 20, y + 4);
    };

    private static void drawOutlinedString(GuiGraphics graphics, Font font, String text, int x, int y) {
        graphics.drawString(font, text, x - 1, y, 0x000000, false);
        graphics.drawString(font, text, x + 1, y, 0x000000, false);
        graphics.drawString(font, text, x, y - 1, 0x000000, false);
        graphics.drawString(font, text, x, y + 1, 0x000000, false);
        graphics.drawString(font, text, x, y, 0xFFFFFF, false);
    }
}
