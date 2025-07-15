package mett.palemannie.quakeweapons.item.custom;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HitscanTest extends Item {

    public HitscanTest(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        pPlayer.startUsingItem(pUsedHand);
        pLevel.playSound(null, pPlayer.blockPosition(), SoundEvents.PIG_AMBIENT, SoundSource.NEUTRAL, 1f, 1f);
        return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));
    }

    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int remainingUseDuration) {

        if (!(entity instanceof Player player)) return;

        /*if (!consumeAmmo(player)) {
            player.releaseUsingItem();
            return;
        }*/

        double maxDistance = 20.0;
        Vec3 start = player.getEyePosition();
        Vec3 look = player.getLookAngle();

        for (double i = 0.0; i < maxDistance; i += 0.25) {
            Vec3 point = start.add(look.scale(i));

            // Schaden an allen Entities an dieser Position
            AABB box = new AABB(point.subtract(0.125, 0.125, 0.125), point.add(0.125, 0.125, 0.125));
            List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, box);
            for (LivingEntity target : entities) {
                if (target != player && target.isAlive()) {
                    target.hurt(level.damageSources().playerAttack(player), 6F);
                }
            }

            // Blitz Partikel
            level.addParticle(ParticleTypes.ELECTRIC_SPARK, point.x, point.y-0.5f, point.z, 0, 0, 0);
        }
        level.playSound(null, player.blockPosition(), SoundEvents.STONE_BUTTON_CLICK_OFF, SoundSource.NEUTRAL, 1f, 1f);
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
        super.releaseUsing(pStack, pLevel, pLivingEntity, pTimeCharged);

        pLevel.playSound(null, pLivingEntity.blockPosition(), SoundEvents.PIG_DEATH, SoundSource.NEUTRAL, 1f, 1f);
    }

    @Override
    public @NotNull UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.NONE;
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 2000000000;
    }
}
