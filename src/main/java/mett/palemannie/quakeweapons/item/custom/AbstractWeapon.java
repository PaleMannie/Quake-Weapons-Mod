package mett.palemannie.quakeweapons.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
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

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() { return this.cache; }

    public void setCurrentHand(InteractionHand hand, LivingEntity player) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (!itemStack.isEmpty() && !player.isUsingItem()) {
            player.useItem = itemStack;
            player.useItemRemaining = itemStack.getUseDuration();
            if (!player.level().isClientSide()) {
                player.setLivingEntityFlag(1, true);
                player.setLivingEntityFlag(2, hand == InteractionHand.OFF_HAND);
                player.gameEvent(GameEvent.ITEM_INTERACT_START);
            }

        }
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 2000000000;
    }

    @Override
    public boolean canAttackBlock(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer) {
        return false;
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        setCurrentHand(pUsedHand, (LivingEntity) pPlayer);
        return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));
    }

    protected abstract void executeWeaponFire(Level level, LivingEntity user, ItemStack stack, int pRemainingUseDuration);

    @Override
    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pRemainingUseDuration) {
        super.onUseTick(pLevel, pLivingEntity, pStack, pRemainingUseDuration);

        if (pLevel instanceof ServerLevel serverLevel) {
            startShootingAnimation(pLivingEntity, serverLevel); }
        executeWeaponFire(pLevel, pLivingEntity, pStack, pRemainingUseDuration);
    }

    int cooldown;

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
        super.releaseUsing(pStack, pLevel, pLivingEntity, pTimeCharged);

        if(pLivingEntity instanceof Player) ((Player) pLivingEntity).getCooldowns().addCooldown(this, cooldown);
        if (pLevel instanceof ServerLevel serverLevel)
            stopShootingAnimation(pLivingEntity, serverLevel);
        //this ensures, that the Nailgun always starts shooting from the right barrel
        NailGunItem.rightSide = false;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        if (!selected && level instanceof ServerLevel serverLevel) {
            stopShootingAnimation((LivingEntity) entity, serverLevel);
        }
        super.inventoryTick(stack, level, entity, slot, selected);
    }

    public void startShootingAnimation(LivingEntity pLivingEntity, ServerLevel serverLevel){

        triggerAnim(pLivingEntity, GeoItem.getOrAssignId(pLivingEntity.getItemInHand(pLivingEntity.getUsedItemHand()), serverLevel), "controller", "shooting");
    }

    public void stopShootingAnimation(LivingEntity pLivingEntity, ServerLevel serverLevel){

        stopTriggeredAnim(pLivingEntity, GeoItem.getOrAssignId(pLivingEntity.getItemInHand(pLivingEntity.getUsedItemHand()), serverLevel), "controller", "shooting");
    }
}
