package mett.palemannie.quakeweapons.item.custom;

import mett.palemannie.quakeweapons.item.ModItems;
import mett.palemannie.quakeweapons.util.ServerPlayHandler;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class ShotgunItem extends AbstractWeapon {
    public ShotgunItem(Properties properties) {
        super(properties, 10, 8, 1, "shotgun.animations");
    }

    @Override public net.minecraft.world.item.Item getAmmoItem() { return ModItems.SHELL.get(); }

    @Override
    protected void fireWeapon(ServerLevel level, ServerPlayer player, ItemStack stack, int useTicks) {
        ServerPlayHandler.handleShotgunShoot(player);
        sendRecoil(player, 2f, 0f, player.getRandom().nextBoolean() ? 0.33f : -0.33f);
    }
}
