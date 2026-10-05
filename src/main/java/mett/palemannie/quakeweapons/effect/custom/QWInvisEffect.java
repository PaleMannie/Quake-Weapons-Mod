package mett.palemannie.quakeweapons.effect.custom;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.effect.ModEffects;
import mett.palemannie.quakeweapons.net.ModMessages;
import mett.palemannie.quakeweapons.net.packets.S2CInvisPacket;
import mett.palemannie.quakeweapons.sound.ModSounds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.core.Holder;
import net.minecraft.network.protocol.game.ClientboundSoundEntityPacket;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;

public class QWInvisEffect extends MobEffect {

    public QWInvisEffect(MobEffectCategory pCategory, int pColor) {
        super(MobEffectCategory.BENEFICIAL, 0x4D194D);
        // Hide the wearer from the locator bar until this effect is removed.
        addAttributeModifier(
                Attributes.WAYPOINT_TRANSMIT_RANGE,
                Identifier.fromNamespaceAndPath(QuakeWeapons.MODID, "effect.ring_waypoint_transmit_range"),
                -1.0D,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
    }

    @Override
    public void onEffectAdded(LivingEntity entity, int pAmplifier) {

        Level level = entity.level();

        if (!level.isClientSide()) {

            entity.setInvisible(true);
            ModMessages.sendToTrackingEntityAndSelf(new S2CInvisPacket(entity.getId(), true), entity);
        }
    }

    @Override
    public boolean applyEffectTick(ServerLevel sevel, LivingEntity entity, int amplifier) {
        if (entity instanceof ServerPlayer player) {
            player.connection.send(new ClientboundSoundEntityPacket(Holder.direct(ModSounds.RING_EXPIRE.get()),
                    SoundSource.PLAYERS, player, 1.0F, 1.0F, player.getRandom().nextLong()));
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int pAmplifier) {
        return duration == 60;
    }

    ///Assuring that upon breaking invis you get visible again by removing "QWInvis"
    @Override
    public void removeAttributeModifiers(AttributeMap map) { super.removeAttributeModifiers(map); }
}
