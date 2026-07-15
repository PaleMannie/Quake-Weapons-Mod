package mett.palemannie.quakeweapons.item.custom;

import mett.palemannie.quakeweapons.item.ModItems;
import mett.palemannie.quakeweapons.item.client.SuperNailGunRenderer;
import mett.palemannie.quakeweapons.util.ServerPlayHandler;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.object.LoopType;
import software.bernie.geckolib.renderer.GeoItemRenderer;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class SuperNailgunItem extends AbstractWeapon{

    public SuperNailgunItem(Properties pProperties) {

        super(pProperties);
        this.cooldown = 1;
    }

    @Override
    protected boolean shouldInterruptShootAnimationOnRelease() {
        return true;
    }

    @Override
    protected LoopType shootLoopType() {
        return LoopType.LOOP;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() { return this.cache; }

    @Override
    protected String baseAnimPrefix() { return "super_nailgun.animations"; }

    @Override
    protected String altAnimPrefix() { return "super_nailgun_alt.animations"; }

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private SuperNailGunRenderer renderer;

            @Override
            @Nullable
            public GeoItemRenderer<SuperNailgunItem> getGeoItemRenderer() {
                if (this.renderer == null)
                    this.renderer = new SuperNailGunRenderer();

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
        });
    }

    private boolean consumeAmmo(Player player, boolean confirmConsumption) {
        if (player.isCreative()) return true;

        for (ItemStack stack : player.getInventory().getNonEquipmentItems()) {
            if (stack.is(ModItems.NAIL.get()) && stack.getCount() >= 2) {
                if(confirmConsumption) { stack.shrink(2); }
                return true;
            }
        }
        return false;
    }

    @Override
    protected void executeWeaponFire(Level level, LivingEntity user, ItemStack stack, int pRemainingUseDuration) {

        if(user instanceof ServerPlayer serverPlayer){

            if(pRemainingUseDuration % 16 == 0) {

                if (consumeAmmo((Player)user, false)) {

                    if (level instanceof ServerLevel serverLevel) {

                        startShootingAnimation(user, serverLevel, stack);
                    }
                } else {

                    ServerPlayHandler.playAmmoEmptySound(serverPlayer);
                    startAmmoEmptyAnimation(user, level.getServer().overworld(), stack);
                }
            }

            if(pRemainingUseDuration % 2 == 0){

                if (consumeAmmo((Player)user, true)) {

                    ServerPlayHandler.handleSuperNailgunShoot(serverPlayer);

                }
            }
        }
    }
}
