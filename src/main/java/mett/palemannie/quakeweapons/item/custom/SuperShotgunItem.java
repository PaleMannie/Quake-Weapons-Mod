package mett.palemannie.quakeweapons.item.custom;

import com.mojang.blaze3d.vertex.PoseStack;
import mett.palemannie.quakeweapons.item.ModItems;
import mett.palemannie.quakeweapons.item.client.SuperShotgunRenderer;
import mett.palemannie.quakeweapons.util.ServerPlayHandler;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.renderer.GeoItemRenderer;

import java.util.function.Consumer;

public class SuperShotgunItem extends AbstractWeapon {
    public SuperShotgunItem(Properties properties) {
        super(properties, 14, 12, 2, "super_shotgun.animations");
    }

    @Override public Item getAmmoItem() { return ModItems.SHELL.get(); }

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private SuperShotgunRenderer renderer;

            @Override
            public GeoItemRenderer<SuperShotgunItem> getGeoItemRenderer() {
                if (this.renderer == null)
                    this.renderer = new SuperShotgunRenderer();

                return this.renderer;
            }
        });
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {

            //Keeps the item in the bow holding position when it's not used
            @Override
            public HumanoidModel.ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
                if (!itemStack.isEmpty()) {
                    if (entityLiving.getItemInHand(hand) == itemStack) {
                        return HumanoidModel.ArmPose.BOW_AND_ARROW;
                    }
                }
                return HumanoidModel.ArmPose.EMPTY;
            }

            @Override
            public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm, ItemStack itemInHand, float partialTick, float equipProcess, float swingProcess) {

                if (itemInHand.getItem() instanceof AbstractWeapon) {

                    int side = arm == HumanoidArm.RIGHT ? 1 : -1;
                    poseStack.translate(side * 0.56f, -0.52f, -0.72f);

                    return true;
                }

                return false;
            }
        });
    }

    @Override
    protected void fireWeapon(ServerLevel level, ServerPlayer player, ItemStack stack, int useTicks) {
        ServerPlayHandler.handleSuperShotgunShoot(player);
        sendRecoil(player, 3f, 0f, player.getRandom().nextBoolean() ? 0.33f : -0.33f);
    }
}
