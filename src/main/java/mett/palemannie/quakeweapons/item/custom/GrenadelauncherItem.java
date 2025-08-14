package mett.palemannie.quakeweapons.item.custom;

import mett.palemannie.quakeweapons.item.ModItems;
import mett.palemannie.quakeweapons.item.client.GrenadelauncherRenderer;
import mett.palemannie.quakeweapons.net.ModMessages;
import mett.palemannie.quakeweapons.net.packets.C2SAmmoEmptyPacket;
import mett.palemannie.quakeweapons.util.ServerPlayHandler;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import java.util.function.Consumer;

public class GrenadelauncherItem extends AbstractWeapon{

    public GrenadelauncherItem(Properties pProperties) {
        super(pProperties);
        this.cooldown = 11;
    }

    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    private static final RawAnimation SHOOT_ANIM = RawAnimation.begin().then("grenadelauncher.animations.shooting", Animation.LoopType.LOOP);
    private static final RawAnimation AMMOEMPTY_ANIM = RawAnimation.begin().then("grenadelauncher.animations.ammoempty", Animation.LoopType.LOOP);
    private static final RawAnimation IDLE_ANIM = RawAnimation.begin().then("grenadelauncher.animations.idle", Animation.LoopType.LOOP);

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, state -> PlayState.CONTINUE)
                .triggerableAnim("shooting", SHOOT_ANIM));

        controllerRegistrar.add(new AnimationController<>(this, "controller2", 0, state -> PlayState.CONTINUE)
                .triggerableAnim("ammoempty", AMMOEMPTY_ANIM));

        controllerRegistrar.add(new AnimationController<>(this, "controller3", 0, state -> PlayState.CONTINUE)
                .triggerableAnim("idle", IDLE_ANIM));
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private GrenadelauncherRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if(this.renderer == null) {
                    this.renderer = new GrenadelauncherRenderer();
                }

                return this.renderer;
            }

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
        if (player.isCreative()) return true;

        for (ItemStack stack : player.getInventory().items) {
            if (stack.is(ModItems.GRENADE.get())) {
                stack.shrink(1);
                return true;
            }
        }
        return false;
    }

    @Override
    protected void executeWeaponFire(Level level, LivingEntity user, ItemStack stack, int pRemainingUseDuration) {

        if(user instanceof ServerPlayer serverPlayer){


            if((pRemainingUseDuration - 8) % 12 == 0){


                if (consumeAmmo((Player)user)) {

                    if (level instanceof ServerLevel serverLevel) {

                        stopAmmoEmptyAnimation(user, serverLevel, stack);
                        stopIdleAnimation(user, serverLevel, stack);
                        startShootingAnimation(user, serverLevel, stack); }

                        ServerPlayHandler.handleGrenadeLauncherShoot(serverPlayer);
                } else {

                    ServerPlayHandler.playAmmoEmptySound(serverPlayer);
                    stopShootingAnimation(user, level.getServer().overworld(), stack);
                    stopIdleAnimation(user, level.getServer().overworld(), stack);
                    startAmmoEmptyAnimation(user, level.getServer().overworld(), stack);
                }
            }
        }
    }
}
