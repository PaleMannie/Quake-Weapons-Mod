package mett.palemannie.quakeweapons.item.custom;

import mett.palemannie.quakeweapons.effect.ModEffects;
import mett.palemannie.quakeweapons.net.ModMessages;
import mett.palemannie.quakeweapons.net.packets.WeaponRecoilS2CPacket;
import mett.palemannie.quakeweapons.util.ServerPlayHandler;
import mett.palemannie.quakeweapons.util.WeaponRefireClock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
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
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Map;
import java.util.WeakHashMap;

public abstract class AbstractWeapon extends Item implements GeoItem {

    private static final Map<ServerPlayer, WeaponRefireClock<AbstractWeapon>> REFIRE_CLOCKS = new WeakHashMap<>();
    private static final String CONTROLLER = "weapon_controller";
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final int fireIntervalTicks;
    private final int releaseCooldownTicks;
    private final int ammoCostPerShot;
    private final String animationBase;

    protected AbstractWeapon(Properties properties, int fireIntervalTicks, int releaseCooldownTicks,
                             int ammoCostPerShot, String animationBase) {
        super(properties);
        this.fireIntervalTicks = fireIntervalTicks;
        this.releaseCooldownTicks = releaseCooldownTicks;
        this.ammoCostPerShot = ammoCostPerShot;
        this.animationBase = animationBase;
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    @Override public AnimatableInstanceCache getAnimatableInstanceCache() { return cache; }
    @Nullable public Item getAmmoItem() { return null; }
    protected Animation.LoopType shootingLoopType() { return Animation.LoopType.PLAY_ONCE; }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        RawAnimation shoot = RawAnimation.begin().then(animationBase + ".shooting", shootingLoopType());
        RawAnimation empty = RawAnimation.begin().then(animationBase + ".ammoempty", Animation.LoopType.PLAY_ONCE);
        RawAnimation idle = RawAnimation.begin().then(animationBase + ".idle", Animation.LoopType.LOOP);
        controllers.add(new AnimationController<>(this, CONTROLLER, 0, state -> {
            state.setAndContinue(idle);
            return PlayState.CONTINUE;
        }).triggerableAnim("shoot", shoot).triggerableAnim("ammoempty", empty));
    }

    protected void setCurrentHand(InteractionHand hand, LivingEntity entity) {
        ItemStack stack = entity.getItemInHand(hand);
        if (stack.isEmpty() || entity.isUsingItem()) return;
        entity.useItem = stack;
        entity.useItemRemaining = stack.getUseDuration();
        if (!entity.level().isClientSide) {
            entity.setLivingEntityFlag(1, true);
            entity.setLivingEntityFlag(2, hand == InteractionHand.OFF_HAND);
            entity.gameEvent(GameEvent.ITEM_INTERACT_START);
        }
    }

    @Override public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) { return slotChanged; }
    @Override public @NotNull UseAnim getUseAnimation(ItemStack stack) { return UseAnim.NONE; }
    @Override public int getUseDuration(ItemStack stack) { return 2_000_000_000; }
    @Override public boolean canAttackBlock(BlockState state, Level level, BlockPos pos, Player player) { return false; }
    @Override public boolean onEntitySwing(ItemStack stack, LivingEntity entity) { return true; }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (hand != InteractionHand.MAIN_HAND || player.getCooldowns().isOnCooldown(this)) {
            return InteractionResultHolder.fail(player.getItemInHand(hand));
        }
        player.removeEffect(ModEffects.QW_INVIS.get());
        player.setInvisible(false);
        setCurrentHand(hand, player);
        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }

    @Override
    public final void onUseTick(Level level, LivingEntity user, ItemStack stack, int remainingUseDuration) {
        super.onUseTick(level, user, stack, remainingUseDuration);
        if (!(level instanceof ServerLevel serverLevel) || !(user instanceof ServerPlayer player)) return;
        if (!player.isUsingItem() || player.getUseItem() != stack || player.getCooldowns().isOnCooldown(this)) return;
        if (!REFIRE_CLOCKS.computeIfAbsent(player, ignored -> new WeaponRefireClock<>())
                .tryFire(this, player.getServer().getTickCount(), fireIntervalTicks)) return;

        int useTicks = getUseDuration(stack) - remainingUseDuration;
        if (!consumeAmmo(player, ammoCostPerShot)) {
            onAmmoEmpty(serverLevel, player, stack);
            return;
        }
        onSuccessfulFire(serverLevel, player, stack);
        fireWeapon(serverLevel, player, stack, useTicks);
    }

    protected abstract void fireWeapon(ServerLevel level, ServerPlayer player, ItemStack stack, int useTicks);

    protected void onSuccessfulFire(ServerLevel level, ServerPlayer player, ItemStack stack) {
        triggerAnim(player, GeoItem.getOrAssignId(stack, level), CONTROLLER, "shoot");
    }

    protected void onAmmoEmpty(ServerLevel level, ServerPlayer player, ItemStack stack) {
        ServerPlayHandler.playAmmoEmptySound(player);
        if (getAmmoItem() != null) triggerAnim(player, GeoItem.getOrAssignId(stack, level), CONTROLLER, "ammoempty");
    }

    protected boolean consumeAmmo(Player player, int amount) {
        Item ammo = getAmmoItem();
        if (amount <= 0 || ammo == null || player.isCreative()) return true;
        int available = 0;
        for (ItemStack stack : player.getInventory().items) {
            if (WeaponCompatibility.isAmmo(stack, ammo)) available += stack.getCount();
        }
        if (available < amount) return false;
        int remaining = amount;
        for (ItemStack stack : player.getInventory().items) {
            if (!WeaponCompatibility.isAmmo(stack, ammo)) continue;
            int taken = Math.min(remaining, stack.getCount());
            stack.shrink(taken);
            remaining -= taken;
            if (remaining == 0) break;
        }
        player.getInventory().setChanged();
        return true;
    }

    protected void sendRecoil(ServerPlayer player, float pitch, float roll, float yaw) {
        ModMessages.sendToPlayer(new WeaponRecoilS2CPacket(pitch, roll, yaw), player);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity user, int timeCharged) {
        super.releaseUsing(stack, level, user, timeCharged);
        if (user instanceof Player player && releaseCooldownTicks > 0) player.getCooldowns().addCooldown(this, releaseCooldownTicks);
        afterShooting(stack, level, user, timeCharged);
    }

    protected void afterShooting(ItemStack stack, Level level, LivingEntity user, int timeCharged) {}

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, level, entity, slot, selected);
        if (!(level instanceof ServerLevel serverLevel) || !(entity instanceof LivingEntity living)) return;
        if (stack.hasTag() && stack.getTag().getBoolean("WasDropped")) {
            hardStopTriggeredAnimations(living, serverLevel, stack);
            stack.getTag().remove("WasDropped");
        }
        if (entity instanceof Player player && !selected && !(player.isUsingItem() && player.getUseItem() == stack)) {
            hardStopTriggeredAnimations(living, serverLevel, stack);
        }
    }

    public void hardStopTriggeredAnimations(LivingEntity living, ServerLevel level, ItemStack stack) {
        long id = GeoItem.getOrAssignId(stack, level);
        stopTriggeredAnim(living, id, CONTROLLER, "shoot");
        stopTriggeredAnim(living, id, CONTROLLER, "ammoempty");
    }
}
