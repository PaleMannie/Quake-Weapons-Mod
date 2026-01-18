package mett.palemannie.quakeweapons.net.packets;

import mett.palemannie.quakeweapons.util.PowerupSoundEventType;
import mett.palemannie.quakeweapons.util.PowerupSoundRegistry;
import mett.palemannie.quakeweapons.util.QWPowerupSoundInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
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

        ctx.enqueueWork(() -> handleClient(packet));
        ctx.setPacketHandled(true);
    }

    @OnlyIn(Dist.CLIENT)
    private static void handleClient(S2CPowerupSoundPacket packet) {

        Minecraft mc = Minecraft.getInstance();
        Level level = mc.level;
        if (level == null) return;

        if (packet.entityId != mc.player.getId()) return;

        Entity entity = level.getEntity(packet.entityId);
        if (!(entity instanceof LivingEntity living)) return;

        SoundEvent sound = PowerupSoundRegistry.getSound(packet.effectId, packet.type);
        if (sound != null) {
            QWPowerupSoundInstance instance = new QWPowerupSoundInstance(mc.player, sound);
            mc.getSoundManager().play(instance);
        }
    }
}