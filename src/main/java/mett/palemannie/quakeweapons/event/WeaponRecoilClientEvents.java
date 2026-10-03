package mett.palemannie.quakeweapons.event;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.client.ClientWeaponRecoil;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.ViewportEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = QuakeWeapons.MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class WeaponRecoilClientEvents {
    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        ClientWeaponRecoil.clientTick();
    }

    @SubscribeEvent
    public static void onComputeCameraAngles(ViewportEvent.ComputeCameraAngles event) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null || minecraft.options.getCameraType() != CameraType.FIRST_PERSON) return;
        float partialTick = (float) event.getPartialTick();
        event.setPitch(event.getPitch() - ClientWeaponRecoil.pitch(partialTick));
        event.setRoll(event.getRoll() + ClientWeaponRecoil.roll(partialTick));
        event.setYaw(event.getYaw() + ClientWeaponRecoil.yaw(partialTick));
    }
}
