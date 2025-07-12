package mett.palemannie.quakeweapons.item.custom;

import mett.palemannie.quakeweapons.item.ModItems;
import mett.palemannie.quakeweapons.item.client.NailGunRenderer;
import mett.palemannie.quakeweapons.item.client.SuperNailGunRenderer;
import mett.palemannie.quakeweapons.net.ModMessages;
import mett.palemannie.quakeweapons.net.packets.C2SAmmoEmptyPacket;
import mett.palemannie.quakeweapons.net.packets.C2SNailPacket;
import mett.palemannie.quakeweapons.net.packets.C2SSuperNailPacket;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
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

public class SuperNailgunItem extends AbstractWeapon{

    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    private static final RawAnimation SHOOT_ANIM = RawAnimation.begin().then("super_nailgun.animations.shooting", Animation.LoopType.LOOP);

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, state -> PlayState.CONTINUE)
                .triggerableAnim("shooting", SHOOT_ANIM));
    }

    public SuperNailgunItem(Properties pProperties) {
        super(pProperties);
        this.cooldown = 1;

    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private SuperNailGunRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if(this.renderer == null) {
                    this.renderer = new SuperNailGunRenderer();
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
            if (stack.is(ModItems.NAIL.get()) && stack.getCount() >= 2) {
                stack.shrink(2);
                return true;
            }
        }
        return false;
    }

    @Override
    protected void executeWeaponFire(Level level, LivingEntity user, ItemStack stack, int pRemainingUseDuration) {

        if(user instanceof ServerPlayer serverPlayer){

            if(pRemainingUseDuration % 2 == 0){


                if (consumeAmmo((Player)user)) {

                    ModMessages.sendToServer(new C2SSuperNailPacket());

                } else {

                    ModMessages.sendToServer(new C2SAmmoEmptyPacket());
                    stopShootingAnimation(user, level.getServer().overworld());
                }
            }
        }
    }
}
