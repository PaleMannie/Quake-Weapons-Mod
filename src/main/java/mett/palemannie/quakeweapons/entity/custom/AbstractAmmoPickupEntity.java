package mett.palemannie.quakeweapons.entity.custom;

import mett.palemannie.quakeweapons.QuakeWeaponsConfig;
import mett.palemannie.quakeweapons.sound.ModSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public abstract class AbstractAmmoPickupEntity extends Entity {
    protected AbstractAmmoPickupEntity(EntityType<?> type, Level level) {
        super(type, level);
        this.noPhysics = true;
    }

    protected abstract Item ammoItem();
    protected abstract int amount();

    @Override
    public void tick() {
        super.tick();
        if (tickCount > QuakeWeaponsConfig.SERVER.powerupLifetime.get()) {
            discard();
            return;
        }
        if (level().isClientSide()) return;
        for (Player player : level().getEntitiesOfClass(Player.class, getBoundingBox().inflate(0.5))) {
            ItemStack remaining = new ItemStack(ammoItem(), amount());
            player.getInventory().add(remaining);
            if (!remaining.isEmpty()) {
                ItemEntity dropped = new ItemEntity(level(), getX(), getY() + 1, getZ(), remaining.copy());
                dropped.setDefaultPickUpDelay();
                level().addFreshEntity(dropped);
            }
            level().playSound(null, blockPosition(), ModSounds.AMMO_PICKUP_SOUND.get(), SoundSource.PLAYERS, 1, 1);
            discard();
            break;
        }
    }

    @Override public boolean isPickable() { return true; }
}
