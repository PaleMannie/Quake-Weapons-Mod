package mett.palemannie.quakeweapons.mixin;

import mett.palemannie.quakeweapons.util.IQWInvisRenderStateExtension;
import net.minecraft.client.renderer.entity.HorseRenderer;
import net.minecraft.client.renderer.entity.state.HorseRenderState;
import net.minecraft.world.entity.animal.horse.Horse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HorseRenderer.class)
public abstract class HorseRendererMixin {

    @Inject(
            method = "extractRenderState(Lnet/minecraft/world/entity/animal/horse/Horse;Lnet/minecraft/client/renderer/entity/state/HorseRenderState;F)V",
            at = @At("TAIL")
    )
    private void qw_fillInvisState(Horse horse, HorseRenderState state, float pPartialTick, CallbackInfo ci) {

        boolean invis = horse.getTags().contains("QWInvis");

        ((IQWInvisRenderStateExtension) state).qw_setInvisible(invis);
    }
}