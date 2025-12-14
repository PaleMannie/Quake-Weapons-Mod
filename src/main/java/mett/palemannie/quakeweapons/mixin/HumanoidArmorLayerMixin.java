package mett.palemannie.quakeweapons.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import mett.palemannie.quakeweapons.util.IQWInvisRenderStateExtension;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidArmorLayer.class)
public abstract class HumanoidArmorLayerMixin<S extends HumanoidRenderState, M extends HumanoidModel<S>, A extends HumanoidModel<S>> extends RenderLayer<S, M> {

    public HumanoidArmorLayerMixin(RenderLayerParent<S, M> renderer, A pInnerModel, A pOuterModel, A pInnerModelBaby, A pOuterModelBaby, EquipmentLayerRenderer pEquipmentRenderer) {
        super(renderer);
    }

    /*@Inject(
            method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void quake$hideArmor(PoseStack poseStack, MultiBufferSource buffer, int packedLight,
                                 T entity, float limbSwing, float limbSwingAmount, float partialTicks,
                                 float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        if (entity.getPersistentData().getBoolean("QWInvis")) {

            ci.cancel();
        }
    }*/

    @Inject(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/renderer/entity/state/HumanoidRenderState;FF)V",
    at = @At("HEAD"), cancellable = true)
    private void qw$render(PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, S state, float pYRot, float pXRot, CallbackInfo ci){

        if (((IQWInvisRenderStateExtension) state).qw_isInvisible()) {
            ci.cancel(); // komplette Rüstung NICHT rendern
        }
    }
}
