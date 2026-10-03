package mett.palemannie.quakeweapons.event;

import mett.palemannie.quakeweapons.effect.ModEffects;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;

public final class EffectOverlayRenderClientEvent {
    private EffectOverlayRenderClientEvent() {}

    public static void render(GuiGraphics graphics, DeltaTracker deltaTracker) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null || minecraft.level == null || minecraft.options.hideGui) return;

        long gameTime = minecraft.level.getGameTime();
        renderEffect(graphics, minecraft.player.getEffect(ModEffects.QUAD_DAMAGE), gameTime, 0x3D70F2);
        renderEffect(graphics, minecraft.player.getEffect(ModEffects.INVULNERABILITY), gameTime, 0xFFD600);
        renderEffect(graphics, minecraft.player.getEffect(ModEffects.QW_INVIS), gameTime, 0x4D194D);
        renderEffect(graphics, minecraft.player.getEffect(ModEffects.BIOSUIT), gameTime, 0x00FF80);
    }

    private static void renderEffect(GuiGraphics graphics, MobEffectInstance effect, long gameTime, int rgb) {
        if (effect == null) return;

        // One layer per frame: the old Forge callback drew the tint repeatedly for each HUD overlay.
        float alpha = 0.12F;
        if (!effect.isInfiniteDuration() && effect.getDuration() <= 60) {
            alpha += 0.08F * (0.5F + 0.5F * Mth.sin((gameTime % 20) / 20.0F * Mth.TWO_PI));
        }
        int color = (Math.round(alpha * 255) << 24) | rgb;
        // Screen tint must blend independently of the depth left by the world and HUD.
        graphics.fill(RenderType.guiOverlay(), 0, 0, graphics.guiWidth(), graphics.guiHeight(), color);
    }
}
