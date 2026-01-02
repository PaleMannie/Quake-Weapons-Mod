package mett.palemannie.quakeweapons.item.custom;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;

public abstract class AbstractTestWeapon extends Item implements IQuakeWeapon, GeoItem {

    /* -------------------------
       KONFIG / BALANCING
       ------------------------- */

    /** Ticks zwischen zwei Schüssen */
    protected final int fireDelayTicks;

    /** Munition pro Schuss */
    protected final int ammoPerShot;

    protected final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    protected AbstractTestWeapon(Properties props,
                             int fireDelayTicks,
                             int ammoPerShot) {
        super(props);
        this.fireDelayTicks = fireDelayTicks;
        this.ammoPerShot = ammoPerShot;
    }

    /* -------------------------
       QUake-Fire-ENTRYPOINT
       ------------------------- */

    @Override
    public final void onQuakeFire(Player player, ItemStack stack) {

        if (!player.level().isClientSide) {

            stopIdleAnimation(player, (ServerLevel) player.level(), stack);
            stopAmmoEmptyAnimation(player, (ServerLevel) player.level(), stack);
            startShootingAnimation(player, (ServerLevel) player.level(), stack);
        }

        // Cooldown aktiv → nichts tun
        if (player.getCooldowns().isOnCooldown(stack)) {
            return;
        }

        // Nur Client triggert Animation & Input

        // Server: echte Logik
        if (!player.level().isClientSide) {

            fireServer(player, stack);
        }

        // Cooldown setzen (beide Seiten!)
        player.getCooldowns().addCooldown(stack, fireDelayTicks);
    }

    /* -------------------------
       SERVER-LOGIK
       ------------------------- */

    /** Spawn Projektile, Damage, Explosion etc */
    protected abstract void fireServer(Player player, ItemStack stack);

    /* -------------------------
       CLIENT-LOGIK
       ------------------------- */

    /** Animation, Sound, Recoil */
    protected void playClientFireEffects(Player player, ItemStack stack) {
        playFireSound(player);
        triggerFireAnimation(stack, player);
    }

    protected void playFireSound(Player player) {}

    protected void triggerFireAnimation(ItemStack stack, Player player) {}

    /* -------------------------
       AMMO
       ------------------------- */

    protected void onDryFire(Player player, ItemStack stack) {
        if (player.level().isClientSide) {
            playDryFireSound(player);
            triggerDryFireAnimation(stack, player);
        }
    }

    protected void playDryFireSound(Player player) {}

    protected void triggerDryFireAnimation(ItemStack stack, Player player) {}

    /* -------------------------
       VANILLA-USE KOMPLETT DEAKTIVIERT
       ------------------------- */

    @Override
    public InteractionResult use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        return InteractionResult.PASS;
    }

    @Override
    public int getUseDuration(ItemStack pStack, LivingEntity entity) {
        return 0;
    }

    @Override
    public @NotNull ItemUseAnimation getUseAnimation(ItemStack pStack) {
        return ItemUseAnimation.NONE;
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) { return false; }

    @Override
    public boolean onDroppedByPlayer(ItemStack stack, Player player) {

        stopShootingAnimation(player, (ServerLevel) player.level(), stack);
        stopAmmoEmptyAnimation(player, (ServerLevel) player.level(), stack);
        stopIdleAnimation(player, (ServerLevel) player.level(), stack);
        return super.onDroppedByPlayer(stack, player);
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

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        return true;
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