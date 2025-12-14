package mett.palemannie.quakeweapons.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mett.palemannie.quakeweapons.item.custom.AbstractWeapon;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/*
 *   All credit goes to byteManiaks MCQuake3 - appropriated to Forge
 *   https://github.com/bytemaniak/mcquake3
 */


@Mixin(ItemInHandRenderer.class)
public class ItemInHandRendererMixin {

    /*@WrapOperation(method = "renderArmWithItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getUseAnimation()Lnet/minecraft/world/item/UseAnim;"))
    // When shooting a gun, don't play the bow animation as it looks out of place
    private UseAnim cancelFirstPersonBowAnimation(ItemStack instance, Operation<UseAnim> original) {
        if (instance.getItem() instanceof AbstractWeapon) return UseAnim.NONE;
        return original.call(instance);
    }*/

    /**
     * @author PaleMannie
     * @reason lol
     */
    /*@Overwrite
    private void renderArmWithItem(AbstractClientPlayer pPlayer,
                                   float pPartialTicks,
                                   float pPitch,
                                   InteractionHand pHand,
                                   float pSwingProgress,
                                   ItemStack pStack,
                                   float pEquippedProgress,
                                   PoseStack pPoseStack,
                                   MultiBufferSource pBuffer,
                                   int pCombinedLight){
        if (!pPlayer.isScoping()) {
            boolean flag = pHand == InteractionHand.MAIN_HAND;
            HumanoidArm humanoidarm = flag ? pPlayer.getMainArm() : pPlayer.getMainArm().getOpposite();
            pPoseStack.pushPose();

            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            if(pStack.getItem() instanceof AbstractWeapon){
                ItemInHandRenderer.applyItemArmTransform(pPoseStack, humanoidarm, pEquippedProgress);}
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            else if (pStack.isEmpty()) {
                if (flag && !pPlayer.isInvisible()) {
                    ItemInHandRenderer.renderPlayerArm(pPoseStack, pBuffer, pCombinedLight, pEquippedProgress, pSwingProgress, humanoidarm);
                }
            } else if (pStack.has(DataComponents.MAP_ID)) {
                if (flag && ItemInHandRenderer.offHandItem.isEmpty()) {
                    ItemInHandRenderer.renderTwoHandedMap(pPoseStack, pBuffer, pCombinedLight, pPitch, pEquippedProgress, pSwingProgress);
                } else {
                    ItemInHandRenderer.renderOneHandedMap(pPoseStack, pBuffer, pCombinedLight, pEquippedProgress, humanoidarm, pSwingProgress, pStack);
                }
            } else if (pStack.getItem() instanceof CrossbowItem) {
                boolean flag1 = CrossbowItem.isCharged(pStack);
                boolean flag2 = humanoidarm == HumanoidArm.RIGHT;
                int i = flag2 ? 1 : -1;
                if (pPlayer.isUsingItem() && pPlayer.getUseItemRemainingTicks() > 0 && pPlayer.getUsedItemHand() == pHand) {
                    ItemInHandRenderer.applyItemArmTransform(pPoseStack, humanoidarm, pEquippedProgress);
                    pPoseStack.translate((float)i * -0.4785682F, -0.094387F, 0.05731531F);
                    pPoseStack.mulPose(Axis.XP.rotationDegrees(-11.935F));
                    pPoseStack.mulPose(Axis.YP.rotationDegrees((float)i * 65.3F));
                    pPoseStack.mulPose(Axis.ZP.rotationDegrees((float)i * -9.785F));
                    float f9 = (float)pStack.getUseDuration(pPlayer) - ((float)pPlayer.getUseItemRemainingTicks() - pPartialTicks + 1.0F);
                    float f13 = f9 / (float)CrossbowItem.getChargeDuration(pStack, pPlayer);
                    if (f13 > 1.0F) {
                        f13 = 1.0F;
                    }

                    if (f13 > 0.1F) {
                        float f16 = Mth.sin((f9 - 0.1F) * 1.3F);
                        float f3 = f13 - 0.1F;
                        float f4 = f16 * f3;
                        pPoseStack.translate(f4 * 0.0F, f4 * 0.004F, f4 * 0.0F);
                    }

                    pPoseStack.translate(f13 * 0.0F, f13 * 0.0F, f13 * 0.04F);
                    pPoseStack.scale(1.0F, 1.0F, 1.0F + f13 * 0.2F);
                    pPoseStack.mulPose(Axis.YN.rotationDegrees((float)i * 45.0F));
                } else {
                    float f = -0.4F * Mth.sin(Mth.sqrt(pSwingProgress) * (float) Math.PI);
                    float f1 = 0.2F * Mth.sin(Mth.sqrt(pSwingProgress) * (float) (Math.PI * 2));
                    float f2 = -0.2F * Mth.sin(pSwingProgress * (float) Math.PI);
                    pPoseStack.translate((float)i * f, f1, f2);
                    ItemInHandRenderer.applyItemArmTransform(pPoseStack, humanoidarm, pEquippedProgress);
                    ItemInHandRenderer.applyItemArmAttackTransform(pPoseStack, humanoidarm, pSwingProgress);
                    if (flag1 && pSwingProgress < 0.001F && flag) {
                        pPoseStack.translate((float)i * -0.641864F, 0.0F, 0.0F);
                        pPoseStack.mulPose(Axis.YP.rotationDegrees((float)i * 10.0F));
                    }
                }

                ItemInHandRenderer.renderItem(
                        pPlayer,
                        pStack,
                        flag2 ? ItemDisplayContext.FIRST_PERSON_RIGHT_HAND : ItemDisplayContext.FIRST_PERSON_LEFT_HAND,
                        !flag2,
                        pPoseStack,
                        pBuffer,
                        pCombinedLight
                );
            } else {
                boolean flag3 = humanoidarm == HumanoidArm.RIGHT;
                if (!net.minecraftforge.client.extensions.common.IClientItemExtensions.of(pStack).applyForgeHandTransform(pPoseStack, minecraft.player, humanoidarm, pStack, pPartialTicks, pEquippedProgress, pSwingProgress)) // FORGE: Allow items to define custom arm animation
                    if (pPlayer.isUsingItem() && pPlayer.getUseItemRemainingTicks() > 0 && pPlayer.getUsedItemHand() == pHand) {
                        int k = flag3 ? 1 : -1;
                        switch (pStack.getUseAnimation()) {
                            case NONE:
                                ItemInHandRenderer.applyItemArmTransform(pPoseStack, humanoidarm, pEquippedProgress);
                                break;
                            case EAT:
                            case DRINK:
                                ItemInHandRenderer.applyEatTransform(pPoseStack, pPartialTicks, humanoidarm, pStack, pPlayer);
                                ItemInHandRenderer.applyItemArmTransform(pPoseStack, humanoidarm, pEquippedProgress);
                                break;
                            case BLOCK:
                                ItemInHandRenderer.applyItemArmTransform(pPoseStack, humanoidarm, pEquippedProgress);
                                break;
                            case BOW:
                                ItemInHandRenderer.applyItemArmTransform(pPoseStack, humanoidarm, pEquippedProgress);
                                pPoseStack.translate((float)k * -0.2785682F, 0.18344387F, 0.15731531F);
                                pPoseStack.mulPose(Axis.XP.rotationDegrees(-13.935F));
                                pPoseStack.mulPose(Axis.YP.rotationDegrees((float)k * 35.3F));
                                pPoseStack.mulPose(Axis.ZP.rotationDegrees((float)k * -9.785F));
                                float f8 = (float)pStack.getUseDuration(pPlayer) - ((float)pPlayer.getUseItemRemainingTicks() - pPartialTicks + 1.0F);
                                float f12 = f8 / 20.0F;
                                f12 = (f12 * f12 + f12 * 2.0F) / 3.0F;
                                if (f12 > 1.0F) {
                                    f12 = 1.0F;
                                }

                                if (f12 > 0.1F) {
                                    float f15 = Mth.sin((f8 - 0.1F) * 1.3F);
                                    float f18 = f12 - 0.1F;
                                    float f20 = f15 * f18;
                                    pPoseStack.translate(f20 * 0.0F, f20 * 0.004F, f20 * 0.0F);
                                }

                                pPoseStack.translate(f12 * 0.0F, f12 * 0.0F, f12 * 0.04F);
                                pPoseStack.scale(1.0F, 1.0F, 1.0F + f12 * 0.2F);
                                pPoseStack.mulPose(Axis.YN.rotationDegrees((float)k * 45.0F));
                                break;
                            case SPEAR:
                                ItemInHandRenderer.applyItemArmTransform(pPoseStack, humanoidarm, pEquippedProgress);
                                pPoseStack.translate((float)k * -0.5F, 0.7F, 0.1F);
                                pPoseStack.mulPose(Axis.XP.rotationDegrees(-55.0F));
                                pPoseStack.mulPose(Axis.YP.rotationDegrees((float)k * 35.3F));
                                pPoseStack.mulPose(Axis.ZP.rotationDegrees((float)k * -9.785F));
                                float f7 = (float)pStack.getUseDuration(pPlayer) - ((float)pPlayer.getUseItemRemainingTicks() - pPartialTicks + 1.0F);
                                float f11 = f7 / 10.0F;
                                if (f11 > 1.0F) {
                                    f11 = 1.0F;
                                }

                                if (f11 > 0.1F) {
                                    float f14 = Mth.sin((f7 - 0.1F) * 1.3F);
                                    float f17 = f11 - 0.1F;
                                    float f19 = f14 * f17;
                                    pPoseStack.translate(f19 * 0.0F, f19 * 0.004F, f19 * 0.0F);
                                }

                                pPoseStack.translate(0.0F, 0.0F, f11 * 0.2F);
                                pPoseStack.scale(1.0F, 1.0F, 1.0F + f11 * 0.2F);
                                pPoseStack.mulPose(Axis.YN.rotationDegrees((float)k * 45.0F));
                                break;
                            case BRUSH:
                                ItemInHandRenderer.applyBrushTransform(pPoseStack, pPartialTicks, humanoidarm, pStack, pPlayer, pEquippedProgress);
                        }
                    } else if (pPlayer.isAutoSpinAttack()) {
                        ItemInHandRenderer.applyItemArmTransform(pPoseStack, humanoidarm, pEquippedProgress);
                        int j = flag3 ? 1 : -1;
                        pPoseStack.translate((float)j * -0.4F, 0.8F, 0.3F);
                        pPoseStack.mulPose(Axis.YP.rotationDegrees((float)j * 65.0F));
                        pPoseStack.mulPose(Axis.ZP.rotationDegrees((float)j * -85.0F));
                    } else {
                        float f5 = -0.4F * Mth.sin(Mth.sqrt(pSwingProgress) * (float) Math.PI);
                        float f6 = 0.2F * Mth.sin(Mth.sqrt(pSwingProgress) * (float) (Math.PI * 2));
                        float f10 = -0.2F * Mth.sin(pSwingProgress * (float) Math.PI);
                        int l = flag3 ? 1 : -1;
                        pPoseStack.translate((float)l * f5, f6, f10);
                        ItemInHandRenderer.applyItemArmTransform(pPoseStack, humanoidarm, pEquippedProgress);
                        ItemInHandRenderer.applyItemArmAttackTransform(pPoseStack, humanoidarm, pSwingProgress);
                    }

                this.renderItem(
                        pPlayer,
                        pStack,
                        flag3 ? ItemDisplayContext.FIRST_PERSON_RIGHT_HAND : ItemDisplayContext.FIRST_PERSON_LEFT_HAND,
                        !flag3,
                        pPoseStack,
                        pBuffer,
                        pCombinedLight
                );
            }

            pPoseStack.popPose();
        }



    }*/
}
