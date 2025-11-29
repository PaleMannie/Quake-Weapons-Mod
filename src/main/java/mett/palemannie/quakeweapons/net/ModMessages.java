package mett.palemannie.quakeweapons.net;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.net.packets.S2CInvisPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.SimpleChannel;

public class ModMessages {

    private static int PacketID = 0;
    private static int id(){
        return PacketID++;
    }
    final static int version = 1;

    public static final SimpleChannel INSTANCE = ChannelBuilder.named(ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "messages"))
            .networkProtocolVersion(version)
            .clientAcceptedVersions(((status, version1) -> true))
            .serverAcceptedVersions(((status, version1) -> true))
            .simpleChannel();

    public static void register(){

        INSTANCE.messageBuilder(S2CInvisPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(S2CInvisPacket::decode)
                .encoder(S2CInvisPacket::encode)
                .consumerMainThread(S2CInvisPacket::handle)
                .add();
    }

    public static void sendToServer(Object message){
        INSTANCE.send(message, PacketDistributor.SERVER.noArg());
    }

    public static <MSG> void sendToTrackingEntityAndSelf(MSG message, LivingEntity entity) {
        INSTANCE.send(message, PacketDistributor.TRACKING_ENTITY_AND_SELF.with(entity));
    }
}
