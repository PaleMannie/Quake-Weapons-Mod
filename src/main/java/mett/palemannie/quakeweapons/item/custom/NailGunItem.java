package mett.palemannie.quakeweapons.item.custom;

import mett.palemannie.quakeweapons.item.client.NailGunRenderer;
import mett.palemannie.quakeweapons.sound.ModSounds;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
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

public class NailGunItem extends AbstractWeapon {

    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    private static final RawAnimation SHOOT_ANIM = RawAnimation.begin().then("animation.nailgun.shooting", Animation.LoopType.LOOP);

    int ammoConsumption;
    int fireRate;
    Item ammoType;

    public NailGunItem(Properties pProperties) {
        super(pProperties);

    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, state -> PlayState.CONTINUE)
                .triggerableAnim("shooting", SHOOT_ANIM));
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private NailGunRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if(this.renderer == null) {
                    this.renderer = new NailGunRenderer();
                }

                return this.renderer;
            }

            //keeps the item in the bow holding position when it's not used
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
    protected void executeWeaponFire(Level pLevel, LivingEntity pLivingEntity, ItemStack stack, int pRemainingUseDuration) {

        if(pRemainingUseDuration % 2 == 0) pLevel.playSound(pLivingEntity, pLivingEntity.getOnPos(), ModSounds.NAILGUN_SHOOT.get(), SoundSource.NEUTRAL, 1f, 1f);
    }
}