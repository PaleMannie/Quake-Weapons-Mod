package mett.palemannie.quakeweapons.net.packets;

import mett.palemannie.quakeweapons.util.ServerPlayHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class C2SQuadDamageUsePacket {
    public C2SQuadDamageUsePacket(){
    }
    public C2SQuadDamageUsePacket(FriendlyByteBuf buf){
    }
    public void toBytes(FriendlyByteBuf buf){
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier){

        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(()-> {

            ServerPlayer player = context.getSender();
            ServerPlayHandler.playQuadDamageUseSound(player);

        });
        return true;
    }
}
