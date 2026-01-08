package mett.palemannie.quakeweapons.mixin;

import mett.palemannie.quakeweapons.item.custom.AbstractWeapon;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin {

    /*@Inject(
            method = "attack",
            at = @At("HEAD"),
            cancellable = true
    )
    private void coolbow$leftClickCharge(Entity pTarget, CallbackInfo ci) {
        Player player = (Player) (Object) this;

        ItemStack stack = player.getMainHandItem();
        if (!(stack.getItem() instanceof AbstractWeapon)) return;

        // Angriff verhindern
        ci.cancel();
        System.out.println("PLAYER ATTACK MIXIN FIRED");
        // Bogen spannen
        if (!player.isUsingItem()) {
            player.startUsingItem(InteractionHand.MAIN_HAND);
        }
    }*/
}