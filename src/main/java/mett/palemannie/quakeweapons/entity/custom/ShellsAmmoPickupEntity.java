package mett.palemannie.quakeweapons.entity.custom;

import mett.palemannie.quakeweapons.item.ModItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class ShellsAmmoPickupEntity extends AbstractAmmoPickupEntity {

    public ShellsAmmoPickupEntity(EntityType<?> type, Level level) { super(type, level); }
    @Override protected Item ammoItem() { return ModItems.SHELL.get(); }
    @Override protected int amount() { return 10; }
}
