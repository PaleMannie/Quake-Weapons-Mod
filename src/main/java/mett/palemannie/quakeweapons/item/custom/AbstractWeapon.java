package mett.palemannie.quakeweapons.item.custom;

import mett.palemannie.quakeweapons.effect.ModEffects;
import mett.palemannie.quakeweapons.util.QWPlayer;
import net.minecraft.client.player.LocalPlayer;
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
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;

/*
 *   Major credit goes to byteManiaks MCQuake3 - semi-appropriated to Forge
 *   https://github.com/bytemaniak/mcquake3
 */

public abstract class AbstractWeapon extends Item implements GeoItem {

    protected final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public AbstractWeapon(Properties pProperties) {
        super(pProperties);

        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

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
    public InteractionResult use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {

        pPlayer.removeEffect(ModEffects.QW_INVIS.getHolder().get());
        pPlayer.setInvisible(false);

        if (pUsedHand != InteractionHand.MAIN_HAND) {
            return InteractionResult.FAIL;
        } else {
            setCurrentHand(pUsedHand, (LivingEntity) pPlayer);
            return InteractionResult.PASS;
        }
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        return InteractionResult.PASS;
    }

    protected abstract void executeWeaponFire(Level level, LivingEntity user, ItemStack stack, int pRemainingUseDuration);

    @Override
    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pRemainingUseDuration) {
        super.onUseTick(pLevel, pLivingEntity, pStack, pRemainingUseDuration);

        /// anti use-slowdown
        if(pLivingEntity instanceof LocalPlayer player && pLevel.isClientSide()) {

            if(QWPlayer.isMovingDiagonally(player)){

                player.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.35355d); }
        } else {pLivingEntity.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.5d);}

        pLivingEntity.getAttribute(Attributes.WATER_MOVEMENT_EFFICIENCY).setBaseValue(0.25d);
        executeWeaponFire(pLevel, pLivingEntity, pStack, pRemainingUseDuration);

        if(pLivingEntity.isDeadOrDying()){

            pLivingEntity.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.1d);
            pLivingEntity.getAttribute(Attributes.WATER_MOVEMENT_EFFICIENCY).setBaseValue(0.0d);
        }
    }

    int cooldown;

    @Override
    public boolean releaseUsing(ItemStack stack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
        super.releaseUsing(stack, pLevel, pLivingEntity, pTimeCharged);

        if(pLivingEntity instanceof Player) ((Player) pLivingEntity).getCooldowns().addCooldown(stack, cooldown);
        if (pLevel instanceof ServerLevel serverLevel){
            stopShootingAnimation(pLivingEntity, serverLevel, stack);
            stopAmmoEmptyAnimation(pLivingEntity, serverLevel, stack);
            startIdleAnimation(pLivingEntity, serverLevel, stack);
        }

        /// resets the anti-use-slowdown
        pLivingEntity.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.1d);
        pLivingEntity.getAttribute(Attributes.WATER_MOVEMENT_EFFICIENCY).setBaseValue(0.0d);

        /// this ensures, that the Nailgun always starts shooting from the right barrel
        NailgunItem.rightSide = false;
        return false;
    }

    @Override
    public boolean onDroppedByPlayer(ItemStack stack, Player player) {

        player.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.1d);
        player.getAttribute(Attributes.WATER_MOVEMENT_EFFICIENCY).setBaseValue(0.0d);

        stopShootingAnimation(player, (ServerLevel) player.level(), stack);
        stopAmmoEmptyAnimation(player, (ServerLevel) player.level(), stack);
        stopIdleAnimation(player, (ServerLevel) player.level(), stack);

        return super.onDroppedByPlayer(stack, player);
    }

    @Override
    public void onStopUsing(ItemStack stack, LivingEntity entity, int count) {
        super.onStopUsing(stack, entity, count);

        entity.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.1d);
        entity.getAttribute(Attributes.WATER_MOVEMENT_EFFICIENCY).setBaseValue(0.0d);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {

        pLivingEntity.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.1d);
        pLivingEntity.getAttribute(Attributes.WATER_MOVEMENT_EFFICIENCY).setBaseValue(0.0d);
        return super.finishUsingItem(pStack, pLevel, pLivingEntity);
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

    public void startShootingAnimation(LivingEntity pLivingEntity, ServerLevel serverLevel, ItemStack stack){

        triggerAnim(pLivingEntity, GeoItem.getId(stack), "controller", "shooting");
    }

    public void stopShootingAnimation(LivingEntity pLivingEntity, ServerLevel serverLevel, ItemStack stack){

        stopTriggeredAnim(pLivingEntity, GeoItem.getId(stack), "controller", "shooting");
    }

    public void startAmmoEmptyAnimation(LivingEntity pLivingEntity, ServerLevel serverLevel, ItemStack stack){

        triggerAnim(pLivingEntity, GeoItem.getId(stack), "controller2", "ammoempty");
    }

    public void stopAmmoEmptyAnimation(LivingEntity pLivingEntity, ServerLevel serverLevel, ItemStack stack){

        stopTriggeredAnim(pLivingEntity, GeoItem.getId(stack), "controller2", "ammoempty");
    }

    public void startIdleAnimation(LivingEntity pLivingEntity, ServerLevel serverLevel, ItemStack stack){

        triggerAnim(pLivingEntity, GeoItem.getId(stack), "controller3", "idle");
    }

    public void stopIdleAnimation(LivingEntity pLivingEntity, ServerLevel serverLevel, ItemStack stack){

        stopTriggeredAnim(pLivingEntity, GeoItem.getId(stack), "controller3", "idle");
    }
}
