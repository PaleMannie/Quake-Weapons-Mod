package mett.palemannie.quakeweapons.item.custom;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.core.animation.Animation;

import java.util.Map;
import java.util.WeakHashMap;

/** Keeps the firing loop running while held and stops it as soon as firing ends. */
public abstract class AbstractNailgunWeapon extends AbstractWeapon {
    private final Map<ServerPlayer, ItemStack> activeAnimations = new WeakHashMap<>();

    protected AbstractNailgunWeapon(Properties properties, int ammoCost, String animationBase) {
        super(properties, 2, 1, ammoCost, animationBase);
    }

    @Override
    protected Animation.LoopType shootingLoopType() { return Animation.LoopType.LOOP; }

    @Override
    protected void onSuccessfulFire(ServerLevel level, ServerPlayer player, ItemStack stack) {
        if (activeAnimations.get(player) == stack) return;
        hardStopTriggeredAnimations(player, level, stack);
        activeAnimations.put(player, stack);
        super.onSuccessfulFire(level, player, stack);
    }

    @Override
    protected void onAmmoEmpty(ServerLevel level, ServerPlayer player, ItemStack stack) {
        hardStopTriggeredAnimations(player, level, stack);
        super.onAmmoEmpty(level, player, stack);
    }

    @Override
    protected void afterShooting(ItemStack stack, Level level, LivingEntity user, int timeCharged) {
        if (level instanceof ServerLevel serverLevel) {
            hardStopTriggeredAnimations(user, serverLevel, stack);
        }
    }

    @Override
    public void hardStopTriggeredAnimations(LivingEntity user, ServerLevel level, ItemStack stack) {
        if (user instanceof ServerPlayer player && activeAnimations.get(player) == stack) {
            activeAnimations.remove(player);
        }
        super.hardStopTriggeredAnimations(user, level, stack);
    }
}
