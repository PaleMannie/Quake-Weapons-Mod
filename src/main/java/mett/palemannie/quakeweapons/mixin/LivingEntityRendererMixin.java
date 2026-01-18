package mett.palemannie.quakeweapons.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin {

    /// A much simpler way to get everyone invisible on command

    @Inject(method = "render(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At("HEAD"), cancellable = true)
    private void hidePlayerModel(LivingEntityRenderState state, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, CallbackInfo ci) {

        if (state.isInvisibleToPlayer) {
            ci.cancel();
        }
    }

    @Inject(method = "extractRenderState(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;F)V", at = @At("RETURN"))
    private void onExtractRenderState(LivingEntity entity, LivingEntityRenderState state, float partialTick, CallbackInfo ci) {

        if (entity.getPersistentData().getBoolean("QWInvis").orElse(false)) {

            state.isInvisibleToPlayer = true;
            state.isInvisible = true;
        } else {

            state.isInvisibleToPlayer = false;
        }
    }
}