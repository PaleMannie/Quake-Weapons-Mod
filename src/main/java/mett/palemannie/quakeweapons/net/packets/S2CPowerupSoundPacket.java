package mett.palemannie.quakeweapons.net.packets;

import mett.palemannie.quakeweapons.util.PowerupSoundEventType;
import mett.palemannie.quakeweapons.util.PowerupSoundRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.network.CustomPayloadEvent;

public class S2CPowerupSoundPacket {

    private final int entityId;
    private final ResourceLocation effectId;
    private final PowerupSoundEventType type;

    public S2CPowerupSoundPacket(int entityId, ResourceLocation effectId, PowerupSoundEventType type) {
        this.entityId = entityId;
        this.effectId = effectId;
        this.type = type;
    }

    public static void encode(S2CPowerupSoundPacket packet, FriendlyByteBuf buf) {
        buf.writeVarInt(packet.entityId);
        buf.writeResourceLocation(packet.effectId);
        buf.writeEnum(packet.type);
    }

    public static S2CPowerupSoundPacket decode(FriendlyByteBuf buf) {
        return new S2CPowerupSoundPacket(
                buf.readVarInt(),
                buf.readResourceLocation(),
                buf.readEnum(PowerupSoundEventType.class)
        );
    }

    public static void handle(S2CPowerupSoundPacket packet, CustomPayloadEvent.Context ctx) {

        ctx.enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();
            Level level = mc.level;
            if (level == null) return;

            Entity entity = level.getEntity(packet.entityId);
            if (!(entity instanceof LivingEntity living)) return;

            SoundEvent sound = PowerupSoundRegistry.getSound(
                    packet.effectId,
                    packet.type
            );

            if (sound != null) {
                level.playLocalSound(
                        living.getX(),
                        living.getY(),
                        living.getZ(),
                        sound,
                        SoundSource.PLAYERS,
                        1.0F,
                        1.0F,
                        false
                );
            }
        });

        ctx.setPacketHandled(true);
    }
}