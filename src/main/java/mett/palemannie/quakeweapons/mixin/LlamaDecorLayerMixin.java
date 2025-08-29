package mett.palemannie.quakeweapons.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.LlamaModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.LlamaDecorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.animal.horse.Llama;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LlamaDecorLayer.class)
public abstract class LlamaDecorLayerMixin extends RenderLayer<Llama, LlamaModel<Llama>> {

    public LlamaDecorLayerMixin(RenderLayerParent<Llama, LlamaModel<Llama>> pRenderer) {
        super(pRenderer);
    }

    @Inject(method = "Lnet/minecraft/client/renderer/entity/layers/LlamaDecorLayer;render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/animal/horse/Llama;FFFFFF)V",
            at = @At("HEAD"), cancellable = true)
    private void qw$hideLlamaDecor(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, Llama pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch, CallbackInfo ci){

        if(pLivingEntity.getPersistentData().getBoolean("QWInvis")){
            ci.cancel();
        }
    }
}
