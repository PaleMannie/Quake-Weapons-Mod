package mett.palemannie.quakeweapons.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import mett.palemannie.quakeweapons.util.IQWInvisRenderStateExtension;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.ArmedEntityRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandLayer.class)
public abstract class ItemInHandLayerMixin<S extends ArmedEntityRenderState, M extends EntityModel<S> & ArmedModel> extends RenderLayer<S, M> {

    public ItemInHandLayerMixin(RenderLayerParent<S, M> pRenderer, ItemRenderer pItemRenderer) {
        super(pRenderer);
    }

    /*@Inject(
            method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void quake$hideHeldItems(PoseStack poseStack, MultiBufferSource buffer, int packedLight,
                                     T entity, float limbSwing, float limbSwingAmount, float partialTicks,
                                     float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        if (entity.getPersistentData().getBoolean("QWInvis")) {

            ci.cancel(); // Waffen & Items blockieren
        }
    }*/

    @Inject(
            method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/renderer/entity/state/ArmedEntityRenderState;FF)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void qw_hideHeldItemsWhenInvisible(
            PoseStack stack, MultiBufferSource source, int packedLicht, S state, float partialTick, float p_117209_, CallbackInfo ci
    ) {
        if (((IQWInvisRenderStateExtension) state).qw_isInvisible()) {
            ci.cancel(); // NICHT rendern
        }
    }
}
