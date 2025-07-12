package mett.palemannie.quakeweapons.net;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.net.packets.C2SAmmoEmptyPacket;
import mett.palemannie.quakeweapons.net.packets.C2SNailPacket;
import mett.palemannie.quakeweapons.net.packets.C2SSuperNailPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModMessages {

    private static SimpleChannel INSTANCE;
    private static int PacketID = 0;
    private static int id(){
        return PacketID++;
    }

    public static void register(){
        SimpleChannel net = NetworkRegistry.ChannelBuilder.named(ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "messages"))
                .networkProtocolVersion(()-> "1.0").clientAcceptedVersions(s -> true).serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(C2SNailPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(C2SNailPacket::new)
                .encoder(C2SNailPacket::toBytes)
                .consumerMainThread(C2SNailPacket::handle)
                .add();

        net.messageBuilder(C2SAmmoEmptyPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(C2SAmmoEmptyPacket::new)
                .encoder(C2SAmmoEmptyPacket::toBytes)
                .consumerMainThread(C2SAmmoEmptyPacket::handle)
                .add();

        net.messageBuilder(C2SSuperNailPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(C2SSuperNailPacket::new)
                .encoder(C2SSuperNailPacket::toBytes)
                .consumerMainThread(C2SSuperNailPacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message){
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player){
        INSTANCE.send(PacketDistributor.PLAYER.with(()->player), message);
    }
}
