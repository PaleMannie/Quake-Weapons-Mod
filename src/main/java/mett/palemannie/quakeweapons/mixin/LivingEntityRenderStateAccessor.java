package mett.palemannie.quakeweapons.mixin;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LivingEntityRenderState.class)
public interface LivingEntityRenderStateAccessor {

    @Accessor("isInvisibleToPlayer")
    boolean isInvisibleToPlayer();

    @Accessor("isInvisibleToPlayer")
    void setInvisibleToPlayer(boolean value);
}