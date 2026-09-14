package mett.palemannie.quakeweapons.mixin;

import mett.palemannie.quakeweapons.effect.ModEffects;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityInvisibilityMixin {

    @Inject(method = "updateInvisibilityStatus", at = @At("TAIL"))
    private void includeQWInvisibility(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (entity.hasEffect(ModEffects.QW_INVIS.getHolder().orElseThrow())) {
            entity.setInvisible(true);
        }
    }
}
