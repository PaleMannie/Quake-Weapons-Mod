package mett.palemannie.quakeweapons.item.custom;

import mett.palemannie.quakeweapons.item.ModItems;
import mett.palemannie.quakeweapons.util.ServerPlayHandler;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class GrenadelauncherItem extends AbstractWeapon {
    public GrenadelauncherItem(Properties properties) {
        super(properties, 12, 10, 1, "grenadelauncher.animations");
    }

    @Override public net.minecraft.world.item.Item getAmmoItem() { return ModItems.GRENADE.get(); }

    @Override
    protected void fireWeapon(ServerLevel level, ServerPlayer player, ItemStack stack, int useTicks) {
        ServerPlayHandler.handleGrenadeLauncherShoot(player);
        sendRecoil(player, 4f, 0f, player.getRandom().nextBoolean() ? 0.33f : -0.33f);
    }
}
