package mett.palemannie.quakeweapons.item.custom;

import com.mojang.blaze3d.vertex.PoseStack;
import mett.palemannie.quakeweapons.effect.ModEffects;
import mett.palemannie.quakeweapons.item.ModItems;
import mett.palemannie.quakeweapons.item.client.ThunderboltRenderer;
import mett.palemannie.quakeweapons.sound.ModSounds;
import mett.palemannie.quakeweapons.util.WeaponKnockback;
import mett.palemannie.quakeweapons.util.ModDamageTypes;
import mett.palemannie.quakeweapons.util.QWConfigStats;
import mett.palemannie.quakeweapons.util.ServerPlayHandler;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.object.LoopType;
import software.bernie.geckolib.animation.object.PlayState;
import software.bernie.geckolib.renderer.GeoItemRenderer;

import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.function.Consumer;

public class ThunderboltItem extends AbstractWeapon {
    private static final String CONTROLLER = "thunderbolt_controller";
    private enum FiringState { SHOOTING, EMPTY }
    private static final Map<ServerPlayer, FiringState> FIRING_STATES = new WeakHashMap<>();
    private static final RawAnimation SHOOT_ANIM = RawAnimation.begin().then("thunderbolt.animations.shooting", LoopType.LOOP);
    private static final RawAnimation AMMOEMPTY_ANIM = RawAnimation.begin().then("thunderbolt.animations.ammoempty", LoopType.PLAY_ONCE);
    private static final RawAnimation IDLE_ANIM = RawAnimation.begin().then("thunderbolt.animations.idle", LoopType.LOOP);

    public ThunderboltItem(Properties properties) {
        super(properties, 2, 1, 1, "thunderbolt.animations");
    }

    @Override public Item getAmmoItem() { return ModItems.CELL.get(); }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(CONTROLLER, 0, state -> state.setAndContinue(IDLE_ANIM))
                .triggerableAnim("shooting", SHOOT_ANIM)
                .triggerableAnim("ammoempty", AMMOEMPTY_ANIM));
    }

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private ThunderboltRenderer renderer;

            @Override
            public GeoItemRenderer<ThunderboltItem> getGeoItemRenderer() {
                if (this.renderer == null)
                    this.renderer = new ThunderboltRenderer();

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

            @Override
            public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm, ItemStack itemInHand, float partialTick, float equipProcess, float swingProcess) {

                if (itemInHand.getItem() instanceof AbstractWeapon) {

                    int side = arm == HumanoidArm.RIGHT ? 1 : -1;
                    poseStack.translate(side * 0.56f, -0.52f, -0.72f);

                    return true;
                }

                return false;
            }
        });
    }

    private void triggerWaterDischarge(ServerLevel level, Player player) {

        if(level.isClientSide()) return;

        int cellCount = countAmmoCells(player);
        double radius = cellCount/2d;

        Vec3 dischargePos = player.position();

        AABB area = new AABB(dischargePos.x - radius, dischargePos.y - radius, dischargePos.z - radius,
                dischargePos.x + radius, dischargePos.y + radius, dischargePos.z + radius);

        List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class, area,
                e -> e.isAlive() && e != player);

        removeAllAmmoCells(player);

        for (LivingEntity target : targets) {
            boolean targetInWater = target.isInLiquid();
            boolean hasLOS = hasLineOfSight(level, dischargePos, target);

            if (targetInWater || hasLOS) {

                //minimal player hit so the correct death message appears
                WeaponKnockback.hurt(target, level.damageSources().playerAttack(player), Float.MIN_VALUE, level);
                //the real damage
                WeaponKnockback.hurt(target, level.damageSources().source(ModDamageTypes.THUNDERBOLT_DISCHARGE, null, null),
                        QWConfigStats.applyQuadDamage((cellCount * 0.66f) * QWConfigStats.ThunderboltDamage, player), level);

                //particles and sound
                level.playSound(null, target.blockPosition(), ModSounds.THUNDERBOLT_LOOP.get(), SoundSource.PLAYERS, 0.5f, 0.5f);
                level.sendParticles(ParticleTypes.ELECTRIC_SPARK,
                        target.getX(), target.getY() + target.getBbHeight() / 2, target.getZ(),
                        12, 0.2, 0.5, 0.2, 0.05);
            }
        }

        //player self discharge damage and particles and sound
        WeaponKnockback.hurt(player, level.damageSources().source(ModDamageTypes.THUNDERBOLT_DISCHARGE, player, player), (cellCount * 0.5f) * QWConfigStats.ThunderboltDamage, level);
        level.playSound(null, player.blockPosition(), ModSounds.THUNDERBOLT_LOOP.get(), SoundSource.PLAYERS, 0.5f, 0.5f);
        level.sendParticles(ParticleTypes.ELECTRIC_SPARK,
                player.getX(), player.getY() + player.getBbHeight() / 2, player.getZ(),
                12, 0.2, 0.5, 0.2, 0.05);
    }

    private boolean hasLineOfSight(Level level, Vec3 from, LivingEntity target) {
        Vec3 to = target.position().add(0, target.getBbHeight() / 2, 0);
        BlockHitResult hit = level.clip(new ClipContext(from, to, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, target));
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
        if (player.isInLiquid()) triggerWaterDischarge(level, player);
        if (FIRING_STATES.put(player, FiringState.SHOOTING) != FiringState.SHOOTING) {
            long id = GeoItem.getOrAssignId(stack, level);
            stopTriggeredAnim(player, id, CONTROLLER, "ammoempty");
            triggerAnim(player, id, CONTROLLER, "shooting");
        }
    }

    @Override
    protected void onAmmoEmpty(ServerLevel level, ServerPlayer player, ItemStack stack) {
        ServerPlayHandler.playAmmoEmptySound(player);
        if (FIRING_STATES.put(player, FiringState.EMPTY) != FiringState.EMPTY) {
            long id = GeoItem.getOrAssignId(stack, level);
            stopTriggeredAnim(player, id, CONTROLLER, "shooting");
            triggerAnim(player, id, CONTROLLER, "ammoempty");
        }
    }

    @Override
    protected void afterShooting(ItemStack stack, Level level, LivingEntity user, int timeCharged) {
        if (level instanceof ServerLevel serverLevel) hardStopTriggeredAnimations(user, serverLevel, stack);
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerLevel level, Entity entity, EquipmentSlot slot) {
        super.inventoryTick(stack, level, entity, slot);

        boolean selected = slot == net.minecraft.world.entity.EquipmentSlot.MAINHAND;

        if (selected && entity instanceof ServerPlayer player && !player.isUsingItem()
                && FIRING_STATES.containsKey(player)) {
            hardStopTriggeredAnimations(player, level, stack);
        }
    }

    @Override
    public void hardStopTriggeredAnimations(LivingEntity user, ServerLevel level, ItemStack stack) {
        if (user instanceof ServerPlayer player) FIRING_STATES.remove(player);
        long id = GeoItem.getOrAssignId(stack, level);
        stopTriggeredAnim(user, id, CONTROLLER, "shooting");
        stopTriggeredAnim(user, id, CONTROLLER, "ammoempty");
    }

    @Override
    protected void fireWeapon(ServerLevel level, ServerPlayer player, ItemStack stack, int useTicks) {
        ServerPlayHandler.handleThunderboltShoot(player, useTicks);
        sendRecoil(player, 0.25f, 0f, player.getRandom().nextBoolean() ? 0.25f : -0.25f);
    }
}
