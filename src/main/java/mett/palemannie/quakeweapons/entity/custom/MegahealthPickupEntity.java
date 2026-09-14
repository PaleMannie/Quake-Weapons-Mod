package mett.palemannie.quakeweapons.entity.custom;

import mett.palemannie.quakeweapons.QuakeWeaponsConfig;
import mett.palemannie.quakeweapons.sound.ModSounds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class MegahealthPickupEntity extends Entity {

    public MegahealthPickupEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        noPhysics = true;
    }

    @Override
    public void tick() {

        super.tick();

        if (tickCount > QuakeWeaponsConfig.SERVER.powerupLifetime.get()) {
            discard();
            return;
        }

        if (level().isClientSide) return;

        for (Player player : level().getEntitiesOfClass(Player.class, getBoundingBox().inflate(0.5))) {

            player.addEffect(new MobEffectInstance(MobEffects.HEAL, 1, 10, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 6000, 4, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.SATURATION, 10, 10, false, false));

            level().playSound(null, blockPosition(), ModSounds.MEGAHEALTH_SOUND.get(), SoundSource.PLAYERS, 1, 1);
            discard();
            break;
        }
    }

    @Override
    protected void defineSynchedData() {}

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {}

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {}
}
