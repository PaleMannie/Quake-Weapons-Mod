package mett.palemannie.quakeweapons.item.custom;

import mett.palemannie.quakeweapons.QuakeWeaponsConfig;
import mett.palemannie.quakeweapons.item.ModItems;
import mett.palemannie.quakeweapons.item.client.GrenadelauncherRenderer;
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

public class GrenadelauncherItem extends AbstractWeapon{

    public GrenadelauncherItem(Properties pProperties) {
        super(pProperties);
        this.cooldown = 10;
    }

    private static boolean enableAltModel = false;

    public static void reloadConfigValues() {

        try {
            enableAltModel = QuakeWeaponsConfig.COMMON.enableAltModel.get();

            System.out.println("[QuakeWeapons] Grenade Launcher alternative model config reloaded:");
            System.out.println(" enableAltModel=" + enableAltModel);

        } catch (Exception e) {
            System.err.println("[QuakeWeapons] Failed to load config values, using defaults!");
            enableAltModel = false;
        }

        System.out.println("[QuakeWeapons] Config values after load: enableAltModel:"
                + QuakeWeaponsConfig.COMMON.enableAltModel.get());
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() { return this.cache; }

    private static String getAnimPrefix() {
        return enableAltModel
                ? "grenadelauncher_alt.animations"
                : "grenadelauncher.animations";
    }

    private RawAnimation getShootAnim() {
        return RawAnimation.begin().then(getAnimPrefix() + ".shooting", Animation.LoopType.LOOP);
    }

    private RawAnimation getAmmoEmptyAnim() {
        return RawAnimation.begin().then(getAnimPrefix() + ".ammoempty", Animation.LoopType.LOOP);
    }

    private RawAnimation getIdleAnim() {
        return RawAnimation.begin().then(getAnimPrefix() + ".idle", Animation.LoopType.LOOP);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>("controller", 0, state -> PlayState.CONTINUE)
                .triggerableAnim("shooting", getShootAnim()));

        controllerRegistrar.add(new AnimationController<>("controller2", 0, state -> PlayState.CONTINUE)
                .triggerableAnim("ammoempty", getAmmoEmptyAnim()));

        controllerRegistrar.add(new AnimationController<>("controller3", 0, state -> PlayState.CONTINUE)
                .triggerableAnim("idle", getIdleAnim()));
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
