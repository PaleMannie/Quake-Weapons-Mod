package mett.palemannie.quakeweapons.item.custom;

import mett.palemannie.quakeweapons.item.ModItems;
import mett.palemannie.quakeweapons.item.client.GrenadelauncherRenderer;
import mett.palemannie.quakeweapons.util.ServerPlayHandler;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.animatable.processing.AnimationController;
import software.bernie.geckolib.animation.Animation;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.renderer.GeoItemRenderer;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class TestWeaponItem extends AbstractTestWeapon {

    public TestWeaponItem(Properties props, int fireDelayTicks, int ammoPerShot) {
        super(props, fireDelayTicks, ammoPerShot);
    }

    @Override
    protected void fireServer(Player player, ItemStack stack) {

        consumeAmmo(player);
        ServerPlayHandler.handleGrenadeLauncherShoot((ServerPlayer) player);
    }

    private boolean consumeAmmo(Player player) {
        if (player.isCreative()) return true;

        for (ItemStack stack : player.getInventory().getNonEquipmentItems()) {
            if (stack.is(ModItems.GRENADE.get())) {
                stack.shrink(1);
                return true;
            }
        }
        return false;
    }

    private static final RawAnimation SHOOT_ANIM = RawAnimation.begin().then("grenadelauncher.animations.shooting", Animation.LoopType.LOOP);
    private static final RawAnimation AMMOEMPTY_ANIM = RawAnimation.begin().then("grenadelauncher.animations.ammoempty", Animation.LoopType.LOOP);
    private static final RawAnimation IDLE_ANIM = RawAnimation.begin().then("grenadelauncher.animations.idle", Animation.LoopType.LOOP);

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

        controllerRegistrar.add(new AnimationController<>("controller", 0, state -> PlayState.CONTINUE)
                .triggerableAnim("shooting", SHOOT_ANIM));

        controllerRegistrar.add(new AnimationController<>("controller2", 0, state -> PlayState.CONTINUE)
                .triggerableAnim("ammoempty", AMMOEMPTY_ANIM));

        controllerRegistrar.add(new AnimationController<>("controller3", 0, state -> PlayState.CONTINUE)
                .triggerableAnim("idle", IDLE_ANIM));
    }

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private GrenadelauncherRenderer renderer;

            @Override
            @Nullable
            public GeoItemRenderer<GrenadelauncherItem> getGeoItemRenderer() {
                if (this.renderer == null)
                    this.renderer = new GrenadelauncherRenderer();

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
        });
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() { return this.cache; }
}
