package mett.palemannie.quakeweapons.item.custom;

import mett.palemannie.quakeweapons.item.client.GrenadelauncherRenderer;
import mett.palemannie.quakeweapons.item.client.QWAxeRenderer;
import mett.palemannie.quakeweapons.util.ServerPlayHandler;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.animatable.processing.AnimationController;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.renderer.GeoItemRenderer;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class QWAxeItem extends AbstractWeapon{

    public QWAxeItem(Properties pProperties) {
        super(pProperties);
        this.cooldown = 8;
    }

    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    private static final RawAnimation SHOOT_ANIM = RawAnimation.begin().then("qwaxe.animations.shooting", Animation.LoopType.LOOP);
    private static final RawAnimation IDLE_ANIM = RawAnimation.begin().then("qwaxe.animations.idle", Animation.LoopType.LOOP);

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

        controllerRegistrar.add(new AnimationController<>("controller", 0, state -> PlayState.CONTINUE)
                .triggerableAnim("shooting", SHOOT_ANIM));

        controllerRegistrar.add(new AnimationController<>("controller3", 0, state -> PlayState.CONTINUE)
                .triggerableAnim("idle", IDLE_ANIM));
    }

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private QWAxeRenderer renderer;

            @Override
            @Nullable
            public GeoItemRenderer<QWAxeItem> getGeoItemRenderer() {
                if (this.renderer == null)
                    this.renderer = new QWAxeRenderer();

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

    private boolean consumeAmmo(Player player) {

        return player.isUsingItem();
    }

    @Override
    protected void executeWeaponFire(Level level, LivingEntity user, ItemStack stack, int pRemainingUseDuration) {

        if(user instanceof ServerPlayer serverPlayer){

            if(pRemainingUseDuration % 10 == 0){

                if (consumeAmmo((Player)user)) {

                    if (level instanceof ServerLevel serverLevel) {

                        stopIdleAnimation(user, serverLevel, stack);
                        startShootingAnimation(user, serverLevel, stack); }

                        ServerPlayHandler.handleAxeShoot(serverPlayer);

                } else {

                    if (level instanceof ServerLevel serverLevel) {

                        startIdleAnimation(user, serverLevel, stack);
                        stopShootingAnimation(user, serverLevel, stack);
                    }
                }
            }
        }
    }
}
