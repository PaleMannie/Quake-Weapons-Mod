package mett.palemannie.quakeweapons.entity.custom;

import mett.palemannie.quakeweapons.effect.ModEffects;
import mett.palemannie.quakeweapons.item.ModItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.animatable.processing.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class QuadDamagePowerupEntity extends AbstractPowerupEntity{

    public QuadDamagePowerupEntity(EntityType<? extends QuadDamagePowerupEntity> type, Level level) {
        super(type, level);
    }

    @Override
    protected void onPickup(Player player) {
        player.addEffect(new MobEffectInstance(ModEffects.QUAD_DAMAGE.getHolder().get(), getPowerupDuration()));
    }

    @Override
    protected Item getPowerupItem() {
        return ModItems.QUAD_DAMAGE_POWERUP.get();
    }

    @Override
    public boolean hurtServer(ServerLevel pLevel, DamageSource pDamageSource, float pAmount) {
        return false;
    }

    private static final RawAnimation HOVER = RawAnimation.begin().thenLoop("quad.animation.hover");

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

        controllerRegistrar.add(new AnimationController<>("hover", 0, state -> state.setAndContinue(HOVER)));
    }

    protected final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() { return this.cache; }
}
