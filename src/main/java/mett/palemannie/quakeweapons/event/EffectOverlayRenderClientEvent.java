package mett.palemannie.quakeweapons.event;

import mett.palemannie.quakeweapons.effect.ModEffects;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.effect.MobEffectInstance;

public final class EffectOverlayRenderClientEvent {
    private static final int[] QUAD = {61, 102, 204, 100};
    private static final int[] PENTAGRAM = {255, 214, 0, 100};
    private static final int[] RING = {77, 26, 77, 100};
    private static final int[] BIOSUIT = {0, 255, 128, 100};

    private EffectOverlayRenderClientEvent() {
    }

    public static void onRenderOverlay(GuiGraphics graphics, DeltaTracker deltaTracker) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        if (player == null) return;

        float partialTick = deltaTracker.getGameTimeDeltaPartialTick(false);
        int[] mixedColor = new int[4];

        mixEffect(mixedColor, player.getEffect(ModEffects.QUAD_DAMAGE.getHolder().orElseThrow()), QUAD, partialTick);
        mixEffect(mixedColor, player.getEffect(ModEffects.INVULNERABILITY.getHolder().orElseThrow()), PENTAGRAM, partialTick);
        mixEffect(mixedColor, player.getEffect(ModEffects.QW_INVIS.getHolder().orElseThrow()), RING, partialTick);
        mixEffect(mixedColor, player.getEffect(ModEffects.BIOSUIT.getHolder().orElseThrow()), BIOSUIT, partialTick);

        int alpha = mixedColor[3];
        if (alpha == 0) return;

        int color = (alpha << 24)
                | (Math.min(255, mixedColor[0]) << 16)
                | (Math.min(255, mixedColor[1]) << 8)
                | Math.min(255, mixedColor[2]);
        graphics.fill(0, 0, graphics.guiWidth(), graphics.guiHeight(), color);
    }

    private static void mixEffect(int[] mixedColor, MobEffectInstance effect, int[] effectColor, float partialTick) {
        if (effect == null) return;

        int alpha = (int) (effectColor[3] * getPulseMultiplier(effect, partialTick));
        mixedColor[0] += effectColor[0] * alpha / 255;
        mixedColor[1] += effectColor[1] * alpha / 255;
        mixedColor[2] += effectColor[2] * alpha / 255;
        mixedColor[3] = Math.max(mixedColor[3], alpha);
    }

    private static float getPulseMultiplier(MobEffectInstance effect, float partialTick) {
        float remaining = effect.getDuration() + partialTick;
        if (remaining > 60.0F) return 1.0F;

        float progress = 1.0F - remaining / 60.0F;
        float angle = progress * (float) Math.PI * 6.0F;
        float normalized = ((float) Math.sin(angle) + 1.0F) / 2.0F;
        return 0.3F + 0.7F * normalized;
    }
}
