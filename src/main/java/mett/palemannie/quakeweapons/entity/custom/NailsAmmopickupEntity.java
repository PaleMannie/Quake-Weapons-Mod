package mett.palemannie.quakeweapons.entity.custom;

import mett.palemannie.quakeweapons.item.ModItems;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class NailsAmmopickupEntity extends AbstractAmmoPickupEntity {

    public NailsAmmopickupEntity(EntityType<?> type, Level level) { super(type, level); }

    @Override protected Item ammoItem() { return ModItems.NAIL.get(); }
    @Override protected int amount() { return 50; }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {}
    @Override
    public boolean hurtServer(ServerLevel serverLevel, DamageSource damageSource, float v) {return false;}
    @Override
    protected void readAdditionalSaveData(ValueInput valueInput) {}
    @Override
    protected void addAdditionalSaveData(ValueOutput valueOutput) {}
}
