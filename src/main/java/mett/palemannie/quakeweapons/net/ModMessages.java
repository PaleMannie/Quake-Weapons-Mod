package mett.palemannie.quakeweapons.net;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.net.packets.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModMessages {

    public static SimpleChannel INSTANCE;
    private static int PacketID = 0;
    private static int id(){
        return PacketID++;
    }

    public static void register(){
        SimpleChannel net = NetworkRegistry.ChannelBuilder.named(ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "messages"))
                .networkProtocolVersion(()-> "1.0").clientAcceptedVersions(s -> true).serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(S2CInvisPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .encoder(S2CInvisPacket::encode)
                .decoder(S2CInvisPacket::decode)
                .consumerMainThread(S2CInvisPacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message){
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player){
        INSTANCE.send(PacketDistributor.PLAYER.with(()->player), message);
    }
}
