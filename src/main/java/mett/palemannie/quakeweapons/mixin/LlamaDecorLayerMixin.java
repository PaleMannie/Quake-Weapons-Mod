package mett.palemannie.quakeweapons.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import mett.palemannie.quakeweapons.util.IQWInvisRenderStateExtension;
import net.minecraft.client.model.LlamaModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;
import net.minecraft.client.renderer.entity.layers.LlamaDecorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.LlamaRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LlamaDecorLayer.class)
public abstract class LlamaDecorLayerMixin extends RenderLayer<LlamaRenderState, LlamaModel> {

    public LlamaDecorLayerMixin(RenderLayerParent<LlamaRenderState, LlamaModel> pRenderer, EntityModelSet pModels, EquipmentLayerRenderer pEquipmentRenderer) {
        super(pRenderer);
    }

    /*@Inject(method = "Lnet/minecraft/client/renderer/entity/layers/LlamaDecorLayer;render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/renderer/entity/state/LlamaRenderState;FF)V",
            at = @At("HEAD"), cancellable = true)
    private void qw$hideLlamaDecor(PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, LlamaRenderState state, float pYRot, float pXRot, CallbackInfo ci){

        if(pLivingEntity.getPersistentData().getBoolean("QWInvis")){
            ci.cancel();
        }

    }*/

    @Inject(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/renderer/entity/state/LlamaRenderState;FF)V", at = @At("HEAD"), cancellable = true)
    private void qw_hideDecorWhenInvisible(
            PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, LlamaRenderState llamaState, float pYRot, float pXRot, CallbackInfo ci
    ) {
        if (((IQWInvisRenderStateExtension) llamaState).qw_isInvisible()) {
            ci.cancel();
        }
    }
}
