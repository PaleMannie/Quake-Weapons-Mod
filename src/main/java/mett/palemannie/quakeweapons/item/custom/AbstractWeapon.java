package mett.palemannie.quakeweapons.item.custom;

import mett.palemannie.quakeweapons.QuakeWeaponsConfig;
import mett.palemannie.quakeweapons.effect.ModEffects;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.fml.ModList;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.object.LoopType;
import software.bernie.geckolib.animation.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

/*
 *   Credit goes to byteManiaks MCQuake3 - semi-appropriated to Forge
 *   https://github.com/bytemaniak/mcquake3
 */

public abstract class AbstractWeapon extends Item implements GeoItem {

    protected final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    /// Squake check
    private static final boolean HAS_SQUAKE = ModList.get().isLoaded("squakeported");

    public AbstractWeapon(Properties pProperties) {
        super(pProperties);

        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    private static boolean enableAltModels = false;

    /// Alt model config check
    public static void reloadAltModelConfig() {

        try {
            enableAltModels = QuakeWeaponsConfig.COMMON.enableEnhancedModels.get();

            System.out.println("[QuakeWeapons] Grenade Launcher alternative model config reloaded:");
            System.out.println(" enableAltModel=" + enableAltModels);

        } catch (Exception e) {

            System.err.println("[QuakeWeapons] Failed to load config values, using defaults!");
            enableAltModels = false;
        }

        System.out.println("[QuakeWeapons] Config values after load: enableAltModel:"
                + QuakeWeaponsConfig.COMMON.enableEnhancedModels.get());
    }

    protected boolean isAltModelEnabled() {
        return enableAltModels;
    }

    protected abstract String baseAnimPrefix();

    protected String altAnimPrefix() { return null; }

    protected final String animPrefix() {
        if (isAltModelEnabled()) {
            String alt = altAnimPrefix();
            if (alt != null && !alt.isBlank()) return alt;
        }
        return baseAnimPrefix();
    }

    protected final RawAnimation animShoot() {
        return RawAnimation.begin().then(animPrefix() + ".shooting", shootLoopType());
    }

    protected final RawAnimation animAmmoEmpty() {
        return RawAnimation.begin().then(animPrefix() + ".ammoempty", shootLoopType());
    }

    protected final RawAnimation animIdle() {
        return RawAnimation.begin().then(animPrefix() + ".idle", LoopType.LOOP);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>("main_controller", 0, state -> PlayState.CONTINUE)
                .triggerableAnim("shooting", animShoot())
                .triggerableAnim("ammoempty", animAmmoEmpty())
                .triggerableAnim("idle", animIdle()));
    }

    int cooldown;

    public void setCurrentHand(InteractionHand hand, LivingEntity player) {

        ItemStack itemStack = player.getItemInHand(hand);
        if (!itemStack.isEmpty() && !player.isUsingItem()) {

            player.useItem = itemStack;
            player.useItemRemaining = itemStack.getUseDuration(player);
            if (!player.level().isClientSide()) {

                player.setLivingEntityFlag(1, true);
                player.setLivingEntityFlag(2, hand == InteractionHand.OFF_HAND);
                player.gameEvent(GameEvent.ITEM_INTERACT_START);
            }
        }
    }

    /// Item Properties
    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) { return false; }

    @Override
    public @NotNull ItemUseAnimation getUseAnimation(ItemStack pStack) {
        return ItemUseAnimation.NONE;
    }

    @Override
    public int getUseDuration(ItemStack pStack, LivingEntity entity) {
        return 2000000000;
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        return true;
    }

    protected boolean shouldInterruptShootAnimationOnRelease() {
        return false;
    }

    protected LoopType shootLoopType() {
        return LoopType.PLAY_ONCE;
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        return InteractionResult.PASS;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand usedHand) {

        player.removeEffect(ModEffects.QW_INVIS.getHolder().get());

        if (usedHand != InteractionHand.MAIN_HAND) return InteractionResult.FAIL;

        setCurrentHand(usedHand, player);

        return InteractionResult.PASS;
    }

    protected abstract void executeWeaponFire(Level level, LivingEntity user, ItemStack stack, int pRemainingUseDuration);

    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int remaining) {
        super.onUseTick(level, entity, stack, remaining);

        executeWeaponFire(level, entity, stack, remaining);
    }

    @Override
    public boolean releaseUsing(ItemStack stack, Level level, LivingEntity entity, int pTimeCharged) {
        super.releaseUsing(stack, level, entity, pTimeCharged);

        /// apply cooldown
        if(entity instanceof Player) ((Player) entity).getCooldowns().addCooldown(stack, cooldown);

        /// animations for automatic weapons
        if (level instanceof ServerLevel serverLevel){

            if (shouldInterruptShootAnimationOnRelease()) {

                stopShootingAnimation(entity, serverLevel, stack);
                stopAmmoEmptyAnimation(entity, serverLevel, stack);
                startIdleAnimation(entity, serverLevel, stack);
            }
        }

        /// this ensures, that the Nailgun always starts shooting from the right barrel
        if (stack.getItem() instanceof NailgunItem) {
            stack.update(DataComponents.CUSTOM_DATA, CustomData.EMPTY, oldCd -> {
                CompoundTag tag = oldCd.copyTag();
                tag.putBoolean(NailgunItem.KEY_RIGHT, true);
                return CustomData.of(tag);
            });
        }

        return false;
    }

    @Override
    public void onStopUsing(ItemStack stack, LivingEntity entity, int count) {
        super.onStopUsing(stack, entity, count);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity entity) {

        return super.finishUsingItem(pStack, pLevel, entity);
    }

    /// Animation starters and stoppers
    public void startShootingAnimation(LivingEntity livingEntity, ServerLevel serverLevel, ItemStack stack) {
        triggerAnim(livingEntity, GeoItem.getOrAssignId(stack, serverLevel), "main_controller", "shooting");
    }

    public void stopShootingAnimation(LivingEntity livingEntity, ServerLevel serverLevel, ItemStack stack) {
        stopTriggeredAnim(livingEntity, GeoItem.getOrAssignId(stack, serverLevel), "main_controller", "shooting");
    }

    public void startAmmoEmptyAnimation(LivingEntity livingEntity, ServerLevel serverLevel, ItemStack stack) {
        triggerAnim(livingEntity, GeoItem.getOrAssignId(stack, serverLevel), "main_controller", "ammoempty");
    }

    public void stopAmmoEmptyAnimation(LivingEntity livingEntity, ServerLevel serverLevel, ItemStack stack) {
        stopTriggeredAnim(livingEntity, GeoItem.getOrAssignId(stack, serverLevel), "main_controller", "ammoempty");
    }

    public void startIdleAnimation(LivingEntity livingEntity, ServerLevel serverLevel, ItemStack stack) {
        triggerAnim(livingEntity, GeoItem.getOrAssignId(stack, serverLevel), "main_controller", "idle");
    }

    public void stopIdleAnimation(LivingEntity livingEntity, ServerLevel serverLevel, ItemStack stack) {
        stopTriggeredAnim(livingEntity, GeoItem.getOrAssignId(stack, serverLevel), "main_controller", "idle");
    }
}
