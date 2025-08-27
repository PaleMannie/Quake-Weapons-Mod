package mett.palemannie.quakeweapons.net;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.net.packets.C2SQuadDamagePickupPacket;
import mett.palemannie.quakeweapons.net.packets.C2SQuadDamageUsePacket;
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

        net.messageBuilder(C2SQuadDamagePickupPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(C2SQuadDamagePickupPacket::new)
                .encoder(C2SQuadDamagePickupPacket::toBytes)
                .consumerMainThread(C2SQuadDamagePickupPacket::handle)
                .add();

        net.messageBuilder(C2SQuadDamageUsePacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(C2SQuadDamageUsePacket::new)
                .encoder(C2SQuadDamageUsePacket::toBytes)
                .consumerMainThread(C2SQuadDamageUsePacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message){
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player){
        INSTANCE.send(PacketDistributor.PLAYER.with(()->player), message);
    }
}
