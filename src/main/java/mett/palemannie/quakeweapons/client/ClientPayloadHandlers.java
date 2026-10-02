package mett.palemannie.quakeweapons.client;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.net.packets.*;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.network.event.RegisterClientPayloadHandlersEvent;

@EventBusSubscriber(modid = QuakeWeapons.MODID, value = Dist.CLIENT)
public final class ClientPayloadHandlers {

    private ClientPayloadHandlers() {}

    @SubscribeEvent
    public static void register(RegisterClientPayloadHandlersEvent event) {

        event.register(S2CInvisPacket.TYPE, (packet, context) -> {

            var level = Minecraft.getInstance().level;
            if (level != null && level.getEntity(packet.entityId()) instanceof LivingEntity entity) {
                entity.getPersistentData().putBoolean("QWInvis", packet.invisible());
            }
        });

        event.register(WeaponRecoilS2CPacket.TYPE, (packet, context) ->
                ClientWeaponRecoil.kick(packet.pitchKick(), packet.rollKick(), packet.yawKick()));

        event.register(ExplosionImpulseS2CPacket.TYPE, (packet, context) -> {

            var player = Minecraft.getInstance().player;
            if (player == null) return;
            player.setDeltaMovement(player.getDeltaMovement().add(packet.impulse()));
            if (packet.impulse().y > 0.0D) player.setOnGround(false);
        });
    }
}
