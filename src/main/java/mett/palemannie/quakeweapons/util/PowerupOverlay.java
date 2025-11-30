package mett.palemannie.quakeweapons.util;

import com.mojang.blaze3d.systems.RenderSystem;
import mett.palemannie.quakeweapons.effect.ModEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.client.extensions.IForgeGuiGraphics;

public class PowerupOverlay implements IForgeGuiGraphics {

    public void render(Minecraft mc, GuiGraphics g, float partialTick, int width, int height) {

        if (mc.player == null) return;

        // --- QUAD DAMAGE ---
        if (mc.player.hasEffect(ModEffects.QUAD_DAMAGE.getHolder().get())) {
            renderTint(g, width, height, 0x0000FF, mc.player.getEffect(ModEffects.QUAD_DAMAGE.getHolder().get()).getDuration());
        }

        // --- INVULNERABILITY ---
        if (mc.player.hasEffect(ModEffects.INVULNERABILITY.getHolder().get())) {
            renderTint(g, width, height, 0xFFFF55, mc.player.getEffect(ModEffects.INVULNERABILITY.getHolder().get()).getDuration());
        }

        // --- BIOSUIT ---
        if (mc.player.hasEffect(ModEffects.BIOSUIT.getHolder().get())) {
            renderTint(g, width, height, 0x00FF55, mc.player.getEffect(ModEffects.BIOSUIT.getHolder().get()).getDuration());
        }

        // --- SHADOWS ---
        if (mc.player.hasEffect(ModEffects.QW_INVIS.getHolder().get())) {
            renderTint(g, width, height, 0x301030, mc.player.getEffect(ModEffects.QW_INVIS.getHolder().get()).getDuration());
        }
    }


    private void renderTint(GuiGraphics g, int width, int height, int rgb, int duration) {

        int alpha = 80;

        // Pulsieren
        if (duration < 80) {
            float pulse = (float)(Math.sin(System.currentTimeMillis() / 200.0) * 0.5 + 0.5);
            alpha = (int)(pulse * 160);
        }

        int color = (alpha << 24) | rgb;

        RenderSystem.enableBlend();
        g.fill(0, 0, width, height, color);
    }
}
