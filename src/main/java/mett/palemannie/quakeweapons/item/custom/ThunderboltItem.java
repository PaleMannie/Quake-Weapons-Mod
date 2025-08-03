package mett.palemannie.quakeweapons.item.custom;

import mett.palemannie.quakeweapons.item.ModItems;
import mett.palemannie.quakeweapons.item.client.ThunderboltRenderer;
import mett.palemannie.quakeweapons.net.ModMessages;
import mett.palemannie.quakeweapons.net.packets.C2SAmmoEmptyPacket;
import mett.palemannie.quakeweapons.sound.ModSounds;
import mett.palemannie.quakeweapons.util.ModDamageTypes;
import mett.palemannie.quakeweapons.util.ServerPlayHandler;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

import java.util.List;
import java.util.function.Consumer;

public class ThunderboltItem extends AbstractWeapon{

    public ThunderboltItem(Properties pProperties) {
        super(pProperties);
        this.cooldown = 1;
    }

    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    private static final RawAnimation SHOOT_ANIM = RawAnimation.begin().then("thunderbolt.animations.shooting", Animation.LoopType.LOOP);
    private static final RawAnimation AMMOEMPTY_ANIM = RawAnimation.begin().then("thunderbolt.animations.ammoempty", Animation.LoopType.LOOP);
    private static final RawAnimation IDLE_ANIM = RawAnimation.begin().then("thunderbolt.animations.idle", Animation.LoopType.LOOP);

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, state -> PlayState.CONTINUE)
                .triggerableAnim("shooting", SHOOT_ANIM));

        controllerRegistrar.add(new AnimationController<>(this, "controller2", 0, state -> PlayState.CONTINUE)
                .triggerableAnim("ammoempty", AMMOEMPTY_ANIM));

        controllerRegistrar.add(new AnimationController<>(this, "controller3", 0, state -> PlayState.CONTINUE)
                .triggerableAnim("idle", IDLE_ANIM));
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private ThunderboltRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if(this.renderer == null) {
                    this.renderer = new ThunderboltRenderer();
                }

                return this.renderer;
            }

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

        for (ItemStack stack : player.getInventory().items) {
            if (stack.is(ModItems.CELL.get())) {
                stack.shrink(1);
                return true;
            }
        }
        return false;
    }

    public int drainAllCellsAndDischarge(Player player) {
        int totalCells = 0;

        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack slot = player.getInventory().getItem(i);
            if (slot.getItem() == ModItems.CELL.get()) {
                totalCells += slot.getCount();
                player.getInventory().setItem(i, ItemStack.EMPTY);
            }
        }

        return totalCells;
    }

    public void dischargeInWater(Player player, Level level, float baseDamage, int radius) {

        AABB area = new AABB(player.blockPosition()).inflate(radius);

        List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class, area, entity ->
                entity != player && entity.isInWaterOrBubble() && entity.isAlive());

        for (LivingEntity target : targets) {

            target.hurt(level.damageSources().playerAttack(player), Float.MIN_VALUE);
            target.hurt(level.damageSources().source(ModDamageTypes.THUNDERBOLT_DAMAGE, null, null), baseDamage * 6);
            level.playSound(null, target.blockPosition(), ModSounds.THUNDERBOLT_LOOP.get(), SoundSource.PLAYERS, 0.25f, 0.5f);
            ((ServerLevel) level).sendParticles(ParticleTypes.ELECTRIC_SPARK,
                    target.getX(), target.getY(), target.getZ(),
                    8, 0.3, 0.3, 0.3, 0.01);
        }

        player.hurt(level.damageSources().source(ModDamageTypes.THUNDERBOLT_DAMAGE, null, null), baseDamage * 3.5f);
    }



    @Override
    protected void executeWeaponFire(Level level, LivingEntity user, ItemStack stack, int pRemainingUseDuration) {

        if(user instanceof ServerPlayer serverPlayer){


            if(pRemainingUseDuration % 2 == 0){


                if (consumeAmmo((Player)user)) {

                    if(user.isInWaterOrBubble()){

                        int ammocount = drainAllCellsAndDischarge((Player) user);

                        drainAllCellsAndDischarge((Player)user);
                        dischargeInWater((Player)user, level, (float) ammocount, 32);
                    }

                    if (level instanceof ServerLevel serverLevel) {

                        stopAmmoEmptyAnimation(user, serverLevel, stack);
                        stopIdleAnimation(user, serverLevel, stack);
                        startShootingAnimation(user, serverLevel, stack); }

                        ServerPlayHandler.handleThunderboltShoot(serverPlayer, this.getUseDuration(stack)-pRemainingUseDuration);
                } else {

                    ModMessages.sendToServer(new C2SAmmoEmptyPacket());
                    stopShootingAnimation(user, level.getServer().overworld(), stack);
                    stopIdleAnimation(user, level.getServer().overworld(), stack);
                    startAmmoEmptyAnimation(user, level.getServer().overworld(), stack);
                }
            }
        }
    }
}
