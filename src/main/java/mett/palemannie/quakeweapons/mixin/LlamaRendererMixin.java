package mett.palemannie.quakeweapons.mixin;

import mett.palemannie.quakeweapons.util.IQWInvisRenderStateExtension;
import net.minecraft.client.renderer.entity.LlamaRenderer;
import net.minecraft.client.renderer.entity.state.LlamaRenderState;
import net.minecraft.world.entity.animal.horse.Llama;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LlamaRenderer.class)
public abstract class LlamaRendererMixin {

    @Inject(
            method = "extractRenderState(Lnet/minecraft/world/entity/animal/horse/Llama;Lnet/minecraft/client/renderer/entity/state/LlamaRenderState;F)V",
            at = @At("TAIL")
    )
    private void qw_fillInvisState(Llama llama, LlamaRenderState state, float pPartialTick, CallbackInfo ci) {

        boolean invis = llama.getTags().contains("QWInvis");

        ((IQWInvisRenderStateExtension) state).qw_setInvisible(invis);
    }
}