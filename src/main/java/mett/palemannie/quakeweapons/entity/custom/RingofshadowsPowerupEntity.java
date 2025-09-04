package mett.palemannie.quakeweapons.entity.custom;

import mett.palemannie.quakeweapons.effect.ModEffects;
import mett.palemannie.quakeweapons.item.ModItems;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class RingofshadowsPowerupEntity extends AbstractPowerupEntity{

    public RingofshadowsPowerupEntity(EntityType<? extends RingofshadowsPowerupEntity> type, Level level) {
        super(type, level);
    }

    @Override
    protected void onPickup(Player player) {
        player.addEffect(new MobEffectInstance(ModEffects.QW_INVIS.get(), 20 * 30, 0, false, false));
    }

    @Override
    protected Item getPowerupItem() {
        return ModItems.RING_POWERUP.get();
    }
}
