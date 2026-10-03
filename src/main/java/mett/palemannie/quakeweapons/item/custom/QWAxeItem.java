package mett.palemannie.quakeweapons.item.custom;

import mett.palemannie.quakeweapons.util.ServerPlayHandler;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class QWAxeItem extends AbstractWeapon {
    public QWAxeItem(Properties properties) {
        super(properties, 10, 8, 0, "qwaxe.animations");
    }

    @Override
    protected boolean causesShotAggro() { return false; }

    @Override
    protected void fireWeapon(ServerLevel level, ServerPlayer player, ItemStack stack, int useTicks) {
        ServerPlayHandler.handleAxeShoot(player);
    }
}
