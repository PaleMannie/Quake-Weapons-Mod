package mett.palemannie.quakeweapons.mixin;

import mett.palemannie.quakeweapons.item.custom.AbstractWeapon;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {

    @ModifyReturnValue(
            method = "itemUseSpeedMultiplier",
            at = @At("RETURN")
    )
    private float quakeweapons$noUseSlowForWeapons(float original) {
        LocalPlayer self = (LocalPlayer) (Object) this;
        ItemStack stack = self.getUseItem();

        if (stack.getItem() instanceof AbstractWeapon) {
            return 1.0F;
        }

        return original;
    }

    @ModifyReturnValue(
            method = "isSlowDueToUsingItem",
            at = @At("RETURN")
    )
    private boolean quakeweapons$allowSprintWhileUsingWeapons(boolean original) {
        LocalPlayer self = (LocalPlayer) (Object) this;
        ItemStack stack = self.getUseItem();

        if (stack.getItem() instanceof AbstractWeapon) {
            return false;
        }

        return original;
    }
}