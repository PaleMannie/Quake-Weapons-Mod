package mett.palemannie.quakeweapons.item.custom;

import mett.palemannie.quakeweapons.effect.ModEffects;
import mett.palemannie.quakeweapons.item.ModItems;
import mett.palemannie.quakeweapons.sound.ModSounds;
import mett.palemannie.quakeweapons.util.WeaponKnockback;
import mett.palemannie.quakeweapons.util.ModDamageTypes;
import mett.palemannie.quakeweapons.util.QWConfigStats;
import mett.palemannie.quakeweapons.util.ServerPlayHandler;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.Animation;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.PlayState;

import java.util.List;

public class ThunderboltItem extends AbstractWeapon {
    private static final RawAnimation SHOOT_ANIM = RawAnimation.begin().then("thunderbolt.animations.shooting", Animation.LoopType.LOOP);
    private static final RawAnimation AMMOEMPTY_ANIM = RawAnimation.begin().then("thunderbolt.animations.ammoempty", Animation.LoopType.LOOP);
    private static final RawAnimation IDLE_ANIM = RawAnimation.begin().then("thunderbolt.animations.idle", Animation.LoopType.LOOP);

    public ThunderboltItem(Properties properties) {
        super(properties, 2, 1, 1, "thunderbolt.animations");
    }

    @Override public net.minecraft.world.item.Item getAmmoItem() { return ModItems.CELL.get(); }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, state -> PlayState.CONTINUE)
                .triggerableAnim("shooting", SHOOT_ANIM));
        controllers.add(new AnimationController<>(this, "controller2", 0, state -> PlayState.CONTINUE)
                .triggerableAnim("ammoempty", AMMOEMPTY_ANIM));
        controllers.add(new AnimationController<>(this, "controller3", 0, state -> PlayState.CONTINUE)
                .triggerableAnim("idle", IDLE_ANIM));
    }

    private void triggerWaterDischarge(ServerLevel level, Player player) {

        if(level.isClientSide) return;

        int cellCount = countAmmoCells(player);
        double radius = cellCount/2d;

        Vec3 dischargePos = player.position();

        AABB area = new AABB(dischargePos.x - radius, dischargePos.y - radius, dischargePos.z - radius,
                dischargePos.x + radius, dischargePos.y + radius, dischargePos.z + radius);

        List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class, area,
                e -> e.isAlive() && e != player);

        removeAllAmmoCells(player);

        for (LivingEntity target : targets) {
            boolean targetInWater = target.isInWaterOrBubble();
            boolean hasLOS = hasLineOfSight(level, dischargePos, target);

            if (targetInWater || hasLOS) {

                //minimal player hit so the correct death message appears
                WeaponKnockback.hurt(target, level.damageSources().playerAttack(player), Float.MIN_VALUE);
                //the real damage
                WeaponKnockback.hurt(target, level.damageSources().source(ModDamageTypes.THUNDERBOLT_DISCHARGE, null, null),
                        player.hasEffect(ModEffects.QUAD_DAMAGE) ? ((cellCount * 0.66f) * QWConfigStats.ThunderboltDamage * 4)
                                : ((cellCount * 0.66f) * QWConfigStats.ThunderboltDamage));

                //particles and sound
                level.playSound(null, target.blockPosition(), ModSounds.THUNDERBOLT_LOOP.get(), SoundSource.PLAYERS, 0.5f, 0.5f);
                level.sendParticles(ParticleTypes.ELECTRIC_SPARK,
                        target.getX(), target.getY() + target.getBbHeight() / 2, target.getZ(),
                        12, 0.2, 0.5, 0.2, 0.05);
            }
        }

        //player self discharge damage and particles and sound
        WeaponKnockback.hurt(player, level.damageSources().source(ModDamageTypes.THUNDERBOLT_DISCHARGE, player, player), (cellCount * 0.5f) * QWConfigStats.ThunderboltDamage);
        level.playSound(null, player.blockPosition(), ModSounds.THUNDERBOLT_LOOP.get(), SoundSource.PLAYERS, 0.5f, 0.5f);
        level.sendParticles(ParticleTypes.ELECTRIC_SPARK,
                player.getX(), player.getY() + player.getBbHeight() / 2, player.getZ(),
                12, 0.2, 0.5, 0.2, 0.05);
    }

    private boolean hasLineOfSight(Level level, Vec3 from, LivingEntity target) {
        Vec3 to = target.position().add(0, target.getBbHeight() / 2, 0);
        BlockHitResult hit = level.clip(new ClipContext(from, to, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, CollisionContext.empty()));
        return hit.getType() == HitResult.Type.MISS;
    }

    private int countAmmoCells(Player player) {
        int total = 0;
        for (ItemStack stack : player.getInventory().items) {
            if (stack.is(ModItems.CELL.get())) {
                total += stack.getCount();
            }
        }
        return total;
    }

    private void removeAllAmmoCells(Player player) {
        for (ItemStack stack : player.getInventory().items) {
            if (stack.is(ModItems.CELL.get())) {
                stack.setCount(0);
            }
        }
    }

    @Override
    protected void onSuccessfulFire(ServerLevel level, ServerPlayer player, ItemStack stack) {
        if (player.isInWaterOrBubble()) triggerWaterDischarge(level, player);
        long id = GeoItem.getOrAssignId(stack, level);
        stopTriggeredAnim(player, id, "controller2", "ammoempty");
        stopTriggeredAnim(player, id, "controller3", "idle");
        triggerAnim(player, id, "controller", "shooting");
    }

    @Override
    protected void onAmmoEmpty(ServerLevel level, ServerPlayer player, ItemStack stack) {
        ServerPlayHandler.playAmmoEmptySound(player);
        long id = GeoItem.getOrAssignId(stack, level);
        stopTriggeredAnim(player, id, "controller", "shooting");
        stopTriggeredAnim(player, id, "controller3", "idle");
        triggerAnim(player, id, "controller2", "ammoempty");
    }

    @Override
    protected void afterShooting(ItemStack stack, Level level, LivingEntity user, int timeCharged) {
        if (level instanceof ServerLevel serverLevel) hardStopTriggeredAnimations(user, serverLevel, stack);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, level, entity, slot, selected);
        if (selected && level instanceof ServerLevel serverLevel && entity instanceof Player player
                && !player.isUsingItem()) {
            triggerAnim(player, GeoItem.getOrAssignId(stack, serverLevel), "controller3", "idle");
        }
    }

    @Override
    public void hardStopTriggeredAnimations(LivingEntity user, ServerLevel level, ItemStack stack) {
        long id = GeoItem.getOrAssignId(stack, level);
        stopTriggeredAnim(user, id, "controller", "shooting");
        stopTriggeredAnim(user, id, "controller2", "ammoempty");
        triggerAnim(user, id, "controller3", "idle");
    }

    @Override
    protected void fireWeapon(ServerLevel level, ServerPlayer player, ItemStack stack, int useTicks) {
        ServerPlayHandler.handleThunderboltShoot(player, useTicks);
        sendRecoil(player, 0.25f, 0f, player.getRandom().nextBoolean() ? 0.25f : -0.25f);
    }
}
