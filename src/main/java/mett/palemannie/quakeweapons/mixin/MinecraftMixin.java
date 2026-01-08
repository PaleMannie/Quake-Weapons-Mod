package mett.palemannie.quakeweapons.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import mett.palemannie.quakeweapons.item.custom.AbstractWeapon;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

/*
*   All credit goes to byteManiaks MCQuake3 - appropriated to Forge
*   https://github.com/bytemaniak/mcquake3
 */

@Mixin(Minecraft.class)
public class MinecraftMixin {

    /*@WrapOperation(method = "Lnet/minecraft/client/Minecraft;handleKeybinds()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;startAttack()Z"))
    // Replaces the attack action with the use action when firing Quake weapons
    private boolean doQuakeWeaponAttack(Minecraft instance, Operation<Boolean> original) {
        if (instance.player.getMainHandItem().getItem() instanceof AbstractWeapon) {
            instance.startUseItem();
            return false;
        }

        return original.call(instance);
    }

    @WrapOperation(method = "Lnet/minecraft/client/Minecraft;handleKeybinds()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/KeyMapping;isDown()Z"))
    // Keeps the Quake weapons firing. Replace right click check with left click check when Quake weapons are fired to ensure that.
    private boolean isQuakeWeaponFired(KeyMapping key, Operation<Boolean> original) {
        Minecraft instance = Minecraft.getInstance();
        if (key.equals(instance.options.keyUse) &&
                instance.player.getUseItem().getItem() instanceof AbstractWeapon &&
                instance.options.keyAttack.isDown())
            return true;

        return original.call(key);
    }*/

    @Shadow public LocalPlayer player;

    @Shadow protected  boolean startAttack(){
        return false;
    }
    @Shadow protected  void startUseItem(){};
    @Shadow public int rightClickDelay;

    // 1) Linksklick-Click: startAttack() -> startUseItem() für CoolBow
    @Redirect(
            method = "handleKeybinds()V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;startAttack()Z")
    )
    private boolean coolbow$attackBecomesUse(Minecraft self) {
        if (player == null) return self.startAttack();

        ItemStack stack = player.getMainHandItem();
        if (stack.getItem() instanceof AbstractWeapon) {
            self.startUseItem();
            return false; // "kein Angriff"
        }
        return self.startAttack();
    }

    // 2) Gedrückt halten: continueAttack(...) unterbinden, sonst schlägt/mined er weiter
    @Inject(
            method = "handleKeybinds()V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;continueAttack(Z)V"),
            cancellable = true
    )
    private void coolbow$blockContinueAttack(CallbackInfo ci) {
        if (player == null) return;
        ItemStack stack = player.getMainHandItem();
        if (stack.getItem() instanceof AbstractWeapon && ((Minecraft)(Object)this).options.keyAttack.isDown()) {
            // Halten von Linksklick soll "Use halten" bedeuten.
            if (!player.isUsingItem()) {
                this.startUseItem();
            }
            ci.cancel(); // verhindert block breaking / melee hold-attack
        }
    }

    // 3) Release-Logik: Vanilla released bei !keyUse.isDown()
    //    Wir lassen keyUse.isDown() für CoolBow so tun, als wäre keyAttack.isDown()
    @Redirect(
            method = "handleKeybinds()V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/KeyMapping;isDown()Z", ordinal = 0)
    )
    private boolean coolbow$useIsDownUsesAttack(net.minecraft.client.KeyMapping keyUse) {
        // ordinal=0 trifft in deiner Methode auf: if (!this.options.keyUse.isDown()) releaseUsingItem(...)
        // :contentReference[oaicite:2]{index=2}
        Minecraft mc = (Minecraft)(Object)this;
        if (player != null && player.isUsingItem()) {
            ItemStack using = player.getUseItem();
            if (using.getItem() instanceof AbstractWeapon) {
                return mc.options.keyAttack.isDown();
            }
        }
        // Originalzustand (ohne Rekursion über isDown()):
        return ((KeyMappingAccessor)(Object)keyUse).coolbow$isDown();
    }
}
