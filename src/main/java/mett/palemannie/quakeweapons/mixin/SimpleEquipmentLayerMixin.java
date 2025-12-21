package mett.palemannie.quakeweapons.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import mett.palemannie.quakeweapons.util.IQWInvisRenderStateExtension;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.SimpleEquipmentLayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SimpleEquipmentLayer.class)
public abstract class SimpleEquipmentLayerMixin {

    @Inject(
            method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;FF)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void quakeweapons$hideHorseEquipmentIfInvisible(
            PoseStack p_392971_, MultiBufferSource p_393255_, int p_396211_, LivingEntityRenderState state, float p_392529_, float p_395422_, CallbackInfo ci
    ) {
        // QW-Invis → kein Equipment rendern
        if (((IQWInvisRenderStateExtension) state).qw_isInvisible()) {
            ci.cancel();
        }
    }
}