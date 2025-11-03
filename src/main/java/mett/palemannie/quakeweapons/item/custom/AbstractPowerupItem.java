package mett.palemannie.quakeweapons.item.custom;

import mett.palemannie.quakeweapons.QuakeWeaponsConfig;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public abstract class AbstractPowerupItem extends Item {

    public AbstractPowerupItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide) {
            // Wir lesen den Config-Wert LAZY zur Laufzeit
            int duration = QuakeWeaponsConfig.SERVER.powerupEffectDuration.get();


            // Das eigentliche Verhalten wird an die Subklasse delegiert
            onPowerupUse(level, player, stack, duration);

            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    /**
     * Subklassen implementieren hier ihr spezielles Powerup-Verhalten.
     */
    protected abstract void onPowerupUse(Level level, Player player, ItemStack stack, int duration);

    /**
     * Zugriffsmethode für die Config-Werte (später falls woanders gebraucht)
     */
    protected int getPowerupDuration() {
        return QuakeWeaponsConfig.SERVER.powerupEffectDuration.get();
    }
}
