package mett.palemannie.quakeweapons.event;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.effect.ModEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.DeltaTracker;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class EffectOverlayRenderClientEvent {
    public static void onRenderOverlay(GuiGraphics graphics, DeltaTracker deltaTracker) {

        /// Color added to GUI while on Quake effects

        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;

        /// Quad Damage
        if (player != null && player.hasEffect(ModEffects.QUAD_DAMAGE.getHolder().orElseThrow())) {

            MobEffectInstance inst = player.getEffect(ModEffects.QUAD_DAMAGE.getHolder().orElseThrow());
            if (inst == null) return;

            int remaining = inst.getDuration();
            long gameTime = mc.level.getGameTime();

            float r, g, b, alpha;
            r = 0.24F;
            g = 0.44F;
            b = 0.95F;

            if (remaining > 60) {
                alpha = 0.2F;

            } else {
                alpha = 0.04F + 0.2F * (0.5F * (1.0F + Mth.sin((gameTime % 20) / 20.0F * Mth.TWO_PI)));
            }

            int screenW = mc.getWindow().getGuiScaledWidth();
            int screenH = mc.getWindow().getGuiScaledHeight();

            int color = ((int)(alpha * 255) << 24) |
                    ((int)(r * 255) << 16) |
                    ((int)(g * 255) << 8) |
                    (int)(b * 255);

            graphics.fill(0, 0, screenW, screenH, color);
        }

        /// Pentagram of Protection
        if (player != null && player.hasEffect(ModEffects.INVULNERABILITY.getHolder().orElseThrow())) {

            MobEffectInstance inst = player.getEffect(ModEffects.INVULNERABILITY.getHolder().orElseThrow());
            if (inst == null) return;

            int remaining = inst.getDuration();
            long gameTime = mc.level.getGameTime();

            float r, g, b, alpha;
            r = 1f;
            g = 0.84f;
            b = 0f;

            if (remaining > 60) {

                alpha = 0.2F;
            } else {

                alpha = 0.04F + 0.2F * (0.5F * (1.0F + Mth.sin((gameTime % 20) / 20.0F * Mth.TWO_PI)));
            }

            int screenW = mc.getWindow().getGuiScaledWidth();
            int screenH = mc.getWindow().getGuiScaledHeight();

            int color = ((int)(alpha * 255) << 24) |
                    ((int)(r * 255) << 16) |
                    ((int)(g * 255) << 8) |
                    (int)(b * 255);

            graphics.fill(0, 0, screenW, screenH, color);
        }

        ///Ring of Shadows
        if (player != null && player.hasEffect(ModEffects.QW_INVIS.getHolder().orElseThrow())) {

            MobEffectInstance inst = player.getEffect(ModEffects.QW_INVIS.getHolder().orElseThrow());
            if (inst == null) return;

            int remaining = inst.getDuration();
            long gameTime = mc.level.getGameTime();

            float r, g, b, alpha;
            r = 0.3f;
            g = 0.1f;
            b = 0.3f;

            if (remaining > 60) {

                alpha = 0.2F;
            } else {

                alpha = 0.04F + 0.2F * (0.5F * (1.0F + Mth.sin((gameTime % 20) / 20.0F * Mth.TWO_PI)));
            }

            int screenW = mc.getWindow().getGuiScaledWidth();
            int screenH = mc.getWindow().getGuiScaledHeight();

            int color = ((int)(alpha * 255) << 24) |
                    ((int)(r * 255) << 16) |
                    ((int)(g * 255) << 8) |
                    (int)(b * 255);

            graphics.fill(0, 0, screenW, screenH, color);
        }

        ///Biosuit
        if (player != null && player.hasEffect(ModEffects.BIOSUIT.getHolder().orElseThrow())) {

            MobEffectInstance inst = player.getEffect(ModEffects.BIOSUIT.getHolder().orElseThrow());
            if (inst == null) return;

            int remaining = inst.getDuration();
            long gameTime = mc.level.getGameTime();

            float r, g, b, alpha;
            r = 0f;
            g = 1f;
            b = 0.5f;

            if (remaining > 60) {

                alpha = 0.2F;
            } else {

                alpha = 0.04F + 0.2F * (0.5F * (1.0F + Mth.sin((gameTime % 20) / 20.0F * Mth.TWO_PI)));
            }

            int screenW = mc.getWindow().getGuiScaledWidth();
            int screenH = mc.getWindow().getGuiScaledHeight();

            int color = ((int)(alpha * 255) << 24) |
                    ((int)(r * 255) << 16) |
                    ((int)(g * 255) << 8) |
                    (int)(b * 255);

            graphics.fill(0, 0, screenW, screenH, color);
        }
    }
}
