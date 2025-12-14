package mett.palemannie.quakeweapons.mixin;

import mett.palemannie.quakeweapons.util.IQWInvisRenderStateExtension;
import net.minecraft.client.renderer.entity.state.LlamaRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(LlamaRenderState.class)
public class LlamaRenderStateMixin implements IQWInvisRenderStateExtension {

    @Unique
    public boolean qw$quakeInvisible = false;

    @Override
    public void qw_setInvisible(boolean invis) {
        this.qw$quakeInvisible = invis;
    }

    @Override
    public boolean qw_isInvisible() {
        return this.qw$quakeInvisible;
    }
}
