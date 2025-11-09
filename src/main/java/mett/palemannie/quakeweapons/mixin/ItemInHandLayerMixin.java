package mett.palemannie.quakeweapons.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandLayer.class)
public abstract class ItemInHandLayerMixin<T extends LivingEntity, M extends HumanoidModel<T>>
        extends RenderLayer<T, M> {

    public ItemInHandLayerMixin(LivingEntityRenderer<T, M> renderer) {
        super(renderer);
    }

    @Inject(
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
    }
}