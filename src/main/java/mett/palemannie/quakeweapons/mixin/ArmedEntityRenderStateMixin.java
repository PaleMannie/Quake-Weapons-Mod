package mett.palemannie.quakeweapons.mixin;

import mett.palemannie.quakeweapons.util.IQWInvisRenderStateExtension;
import net.minecraft.client.renderer.entity.state.ArmedEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ArmedEntityRenderState.class)
public class ArmedEntityRenderStateMixin implements IQWInvisRenderStateExtension {

    @Unique
    private boolean qw$quakeInvisible;

    @Override
    public void qw_setInvisible(boolean invis) {
        this.qw$quakeInvisible = invis;
    }

    @Override
    public boolean qw_isInvisible() {
        return this.qw$quakeInvisible;
    }
}