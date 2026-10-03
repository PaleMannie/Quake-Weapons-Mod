package mett.palemannie.quakeweapons.client;

import com.mojang.blaze3d.vertex.PoseStack;
import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.item.ModItems;
import mett.palemannie.quakeweapons.item.client.*;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

import java.util.function.Supplier;

@EventBusSubscriber(modid = QuakeWeapons.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class WeaponClientExtensions {
    private WeaponClientExtensions() {}

    @SubscribeEvent
    public static void register(RegisterClientExtensionsEvent event) {
        event.registerItem(extension(GrenadelauncherRenderer::new), ModItems.GRENADELAUNCHER.get());
        event.registerItem(extension(NailGunRenderer::new), ModItems.NAILGUN.get());
        event.registerItem(extension(QWAxeRenderer::new), ModItems.QWAXE.get());
        event.registerItem(extension(RocketlauncherRenderer::new), ModItems.ROCKETLAUNCHER.get());
        event.registerItem(extension(ShotgunRenderer::new), ModItems.SHOTGUN.get());
        event.registerItem(extension(SuperNailGunRenderer::new), ModItems.SUPER_NAILGUN.get());
        event.registerItem(extension(SuperShotgunRenderer::new), ModItems.SUPER_SHOTGUN.get());
        event.registerItem(extension(ThunderboltRenderer::new), ModItems.THUNDERBOLT.get());
    }

    private static IClientItemExtensions extension(Supplier<BlockEntityWithoutLevelRenderer> factory) {
        return new IClientItemExtensions() {
            private BlockEntityWithoutLevelRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (renderer == null) renderer = factory.get();
                return renderer;
            }

            @Override
            public HumanoidModel.ArmPose getArmPose(LivingEntity entity, InteractionHand hand, ItemStack stack) {
                return !stack.isEmpty() && entity.getItemInHand(hand) == stack
                        ? HumanoidModel.ArmPose.BOW_AND_ARROW : HumanoidModel.ArmPose.EMPTY;
            }

            @Override
            public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm,
                                                   ItemStack stack, float partialTick, float equipProcess, float swingProcess) {
                int side = arm == HumanoidArm.RIGHT ? 1 : -1;
                poseStack.translate(side * 0.56f, -0.52f, -0.72f);
                return true;
            }
        };
    }
}
