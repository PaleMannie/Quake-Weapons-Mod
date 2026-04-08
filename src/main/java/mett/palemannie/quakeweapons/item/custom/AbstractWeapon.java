package mett.palemannie.quakeweapons.item.custom;

import mett.palemannie.quakeweapons.QuakeWeaponsConfig;
import mett.palemannie.quakeweapons.effect.ModEffects;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
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
            enableAltModels = QuakeWeaponsConfig.COMMON.enableAltModels.get();

            System.out.println("[QuakeWeapons] Grenade Launcher alternative model config reloaded:");
            System.out.println(" enableAltModel=" + enableAltModels);

        } catch (Exception e) {

            System.err.println("[QuakeWeapons] Failed to load config values, using defaults!");
            enableAltModels = false;
        }

        System.out.println("[QuakeWeapons] Config values after load: enableAltModel:"
                + QuakeWeaponsConfig.COMMON.enableAltModels.get());
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

    /// Anti-slowdown methods
    private boolean doAntiSlow(Player player) {

        boolean squakeEnabled = player.getPersistentData().getBoolean("QWSquakeEnabled").orElse(false);
        return (!HAS_SQUAKE) || (!squakeEnabled);
    }

    private void applyAntiFirstTickDash(Player player, boolean diagonal) {

        var ms = player.getAttribute(Attributes.MOVEMENT_SPEED);
        var we = player.getAttribute(Attributes.WATER_MOVEMENT_EFFICIENCY);
        if (ms != null) ms.setBaseValue(diagonal ? 0.707106781186d : 0.1);
        if (we != null) we.setBaseValue(0.25d);
    }

    private void applyAntiSlowSpeed(Player player, boolean diagonal) {

        var ms = player.getAttribute(Attributes.MOVEMENT_SPEED);
        var we = player.getAttribute(Attributes.WATER_MOVEMENT_EFFICIENCY);
        if (ms != null) ms.setBaseValue(diagonal ? 0.353553390593d : 0.5d);
        if (we != null) we.setBaseValue(0.25d);
    }

    private void applySquakeAntiSlowSpeed(Player player, boolean diagonal) {

        var ms = player.getAttribute(Attributes.MOVEMENT_SPEED);
        var we = player.getAttribute(Attributes.WATER_MOVEMENT_EFFICIENCY);
        if (ms != null) ms.setBaseValue(diagonal ? 0.353553390593d/2d : 0.25d);
        if (we != null) we.setBaseValue(0.25d);
    }

    private void resetMovement(Player player) {

        var ms = player.getAttribute(Attributes.MOVEMENT_SPEED);
        var we = player.getAttribute(Attributes.WATER_MOVEMENT_EFFICIENCY);
        if (ms != null) ms.setBaseValue(0.1d);
        if (we != null) we.setBaseValue(0.0d);
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
        boolean diagonal = player.getPersistentData().getBoolean("QWDiagonal").orElse(false);
        applyAntiFirstTickDash(player, diagonal);

        if (usedHand != InteractionHand.MAIN_HAND) return InteractionResult.FAIL;

        setCurrentHand(usedHand, player);

        if (!level.isClientSide()) {

            if (doAntiSlow(player)) {

                applyAntiSlowSpeed(player, diagonal);
            } else {

                applySquakeAntiSlowSpeed(player, diagonal);
            }
        }

        return InteractionResult.PASS;
    }

    protected abstract void executeWeaponFire(Level level, LivingEntity user, ItemStack stack, int pRemainingUseDuration);

    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int remaining) {
        super.onUseTick(level, entity, stack, remaining);

        if (!level.isClientSide() && entity instanceof Player player) {
            if (doAntiSlow(player)) {
                boolean diagonal = player.getPersistentData().getBoolean("QWDiagonal").orElse(false);
                applyAntiSlowSpeed(player, diagonal);
            } else {

                boolean diagonal = player.getPersistentData().getBoolean("QWDiagonal").orElse(false);
                applySquakeAntiSlowSpeed(player, diagonal);
            }
        }

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

        /// resets the anti-use-slowdown
        if (entity instanceof Player player && !level.isClientSide()) {
            resetMovement(player);
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

        resetMovement((Player) entity);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity entity) {

        resetMovement((Player) entity);

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
