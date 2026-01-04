package mett.palemannie.quakeweapons.mixin;

import mett.palemannie.quakeweapons.item.custom.AbstractWeapon;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin {

    @Inject(method = "aiStep", at = @At("HEAD"))
    private void quake$fixDiagonalMovement(CallbackInfo ci) {
        LocalPlayer player = (LocalPlayer)(Object)this;

        if (!(player.getUseItem().getItem() instanceof AbstractWeapon))
            return;

        boolean forward = player.input.keyPresses.forward();
        boolean backward = player.input.keyPresses.backward();
        boolean left  = player.input.keyPresses.left();
        boolean right  = player.input.keyPresses.right();

        if ((forward || backward) && (left || right)) {

            player.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.3535f);

        } else if(forward || backward || left || right){

            player.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.5f);
        } else player.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.1f);
    }
}