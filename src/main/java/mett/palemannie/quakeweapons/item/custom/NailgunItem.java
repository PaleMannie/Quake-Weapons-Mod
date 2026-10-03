package mett.palemannie.quakeweapons.item.custom;

import mett.palemannie.quakeweapons.item.ModItems;
import mett.palemannie.quakeweapons.util.ServerPlayHandler;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class NailgunItem extends AbstractNailgunWeapon {
    public NailgunItem(Properties properties) {
        super(properties, 1, "animation.nailgun");
    }

    @Override public net.minecraft.world.item.Item getAmmoItem() { return ModItems.NAIL.get(); }

    public static boolean rightSide = false;

    @Override
    protected void afterShooting(ItemStack stack, Level level, LivingEntity user, int timeCharged) {
        super.afterShooting(stack, level, user, timeCharged);
        rightSide = false;
    }

    @Override
    protected void fireWeapon(ServerLevel level, ServerPlayer player, ItemStack stack, int useTicks) {
        rightSide = !rightSide;
        ServerPlayHandler.handleNailgunShoot(player);
        sendRecoil(player, 0.25f, 0f, player.getRandom().nextBoolean() ? 0.25f : -0.25f);
    }
}
