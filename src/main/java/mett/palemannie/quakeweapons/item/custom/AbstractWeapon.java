package mett.palemannie.quakeweapons.item.custom;

import mett.palemannie.quakeweapons.QuakeWeaponsConfig;
import mett.palemannie.quakeweapons.effect.ModEffects;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
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
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.animatable.processing.AnimationController;
import software.bernie.geckolib.animation.Animation;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

/*
 *   Credit goes to byteManiaks MCQuake3 - semi-appropriated to Forge
 *   https://github.com/bytemaniak/mcquake3
 */

public abstract class AbstractWeapon extends Item implements GeoItem {

    protected final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    /// Squake check
    private static final boolean HAS_SQUAKE = ModList.get().isLoaded("squakeport_1_21_6");

    public AbstractWeapon(Properties pProperties) {
        super(pProperties);

        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }




    private static boolean enableAltModels = false;

    /**
     * Optional: Call once after config load / on reload.
     * Benutzt Reflection, damit AbstractWeapon keine harte Abhängigkeit auf die Config-Klasse braucht.
     */
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

    /** Override in subclasses if you want custom behavior per weapon. */
    protected boolean isAltModelEnabled() {
        return enableAltModels;
    }

    /** Must be provided by each weapon. Example: "nailgun.animations" */
    protected abstract String baseAnimPrefix();

    /** Optional. Example: "nailgun_alt.animations". Return null/"" if no alt. */
    protected String altAnimPrefix() { return null; }

    protected final String animPrefix() {
        if (isAltModelEnabled()) {
            String alt = altAnimPrefix();
            if (alt != null && !alt.isBlank()) return alt;
        }
        return baseAnimPrefix();
    }

    protected final RawAnimation animShoot() {
        return RawAnimation.begin().then(animPrefix() + ".shooting", Animation.LoopType.LOOP);
    }

    protected final RawAnimation animAmmoEmpty() {
        return RawAnimation.begin().then(animPrefix() + ".ammoempty", Animation.LoopType.LOOP);
    }

    protected final RawAnimation animIdle() {
        return RawAnimation.begin().then(animPrefix() + ".idle", Animation.LoopType.LOOP);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>("controller", 0, state -> PlayState.CONTINUE)
                .triggerableAnim("shooting", animShoot()));

        controllerRegistrar.add(new AnimationController<>("controller2", 0, state -> PlayState.CONTINUE)
                .triggerableAnim("ammoempty", animAmmoEmpty()));

        controllerRegistrar.add(new AnimationController<>("controller3", 0, state -> PlayState.CONTINUE)
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
    public void onUseTick(Level level, LivingEntity ent, ItemStack stack, int remaining) {
        super.onUseTick(level, ent, stack, remaining);

        if (!level.isClientSide() && ent instanceof Player player) {
            if (doAntiSlow(player)) {
                boolean diagonal = player.getPersistentData().getBoolean("QWDiagonal").orElse(false);
                applyAntiSlowSpeed(player, diagonal);
            } else {

                boolean diagonal = player.getPersistentData().getBoolean("QWDiagonal").orElse(false);
                applySquakeAntiSlowSpeed(player, diagonal);
            }
        }

        executeWeaponFire(level, ent, stack, remaining);
    }

    @Override
    public boolean releaseUsing(ItemStack stack, Level level, LivingEntity entity, int pTimeCharged) {
        super.releaseUsing(stack, level, entity, pTimeCharged);

        /// apply cooldown
        if(entity instanceof Player) ((Player) entity).getCooldowns().addCooldown(stack, cooldown);

        /// animations
        if (level instanceof ServerLevel serverLevel){
            stopShootingAnimation(entity, serverLevel, stack);
            stopAmmoEmptyAnimation(entity, serverLevel, stack);
            startIdleAnimation(entity, serverLevel, stack);
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
    public boolean onDroppedByPlayer(ItemStack stack, Player player) {

        if (!player.level().isClientSide()) {

            resetMovement(player);

            if (player.level() instanceof ServerLevel sl) {

                stopShootingAnimation(player, sl, stack);
                stopAmmoEmptyAnimation(player, sl, stack);
                stopIdleAnimation(player, sl, stack);
            }
        }

        return super.onDroppedByPlayer(stack, player);
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

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, @Nullable EquipmentSlot slot, int slotIndex) {

        if (!(entity instanceof Player player)) return;
        if (level.isClientSide) return;

        if (slot != EquipmentSlot.MAINHAND) {
            stopAmmoEmptyAnimation(player, (ServerLevel) level, stack);
            stopShootingAnimation(player, (ServerLevel) level, stack);
            startIdleAnimation(player, (ServerLevel) level, stack);
            return;
        }

        if (!player.getAbilities().mayBuild) return;
    }

    /// Animation starters and stoppers
    public void startShootingAnimation(LivingEntity pLivingEntity, ServerLevel serverLevel, ItemStack stack){

        triggerAnim(pLivingEntity, GeoItem.getOrAssignId(stack, serverLevel), "controller", "shooting");
    }

    public void stopShootingAnimation(LivingEntity pLivingEntity, ServerLevel serverLevel, ItemStack stack){

        stopTriggeredAnim(pLivingEntity, GeoItem.getOrAssignId(stack, serverLevel), "controller", "shooting");
    }

    public void startAmmoEmptyAnimation(LivingEntity pLivingEntity, ServerLevel serverLevel, ItemStack stack){

        triggerAnim(pLivingEntity, GeoItem.getOrAssignId(stack, serverLevel), "controller2", "ammoempty");
    }

    public void stopAmmoEmptyAnimation(LivingEntity pLivingEntity, ServerLevel serverLevel, ItemStack stack){

        stopTriggeredAnim(pLivingEntity, GeoItem.getOrAssignId(stack, serverLevel), "controller2", "ammoempty");
    }

    public void startIdleAnimation(LivingEntity pLivingEntity, ServerLevel serverLevel, ItemStack stack){

        triggerAnim(pLivingEntity, GeoItem.getOrAssignId(stack, serverLevel), "controller3", "idle");
    }

    public void stopIdleAnimation(LivingEntity pLivingEntity, ServerLevel serverLevel, ItemStack stack){

        stopTriggeredAnim(pLivingEntity, GeoItem.getOrAssignId(stack, serverLevel), "controller3", "idle");
    }
}
