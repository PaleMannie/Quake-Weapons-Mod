package mett.palemannie.quakeweapons.item.custom;

import mett.palemannie.quakeweapons.item.ModItems;
import mett.palemannie.quakeweapons.util.ServerPlayHandler;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class SuperNailgunItem extends AbstractNailgunWeapon {
    public SuperNailgunItem(Properties properties) {
        super(properties, 2, "super_nailgun.animations");
    }

    @Override public net.minecraft.world.item.Item getAmmoItem() { return ModItems.NAIL.get(); }

    @Override
    protected void fireWeapon(ServerLevel level, ServerPlayer player, ItemStack stack, int useTicks) {
        ServerPlayHandler.handleSuperNailgunShoot(player);
        sendRecoil(player, 0.25f, 0f, player.getRandom().nextBoolean() ? 0.25f : -0.25f);
    }
}
