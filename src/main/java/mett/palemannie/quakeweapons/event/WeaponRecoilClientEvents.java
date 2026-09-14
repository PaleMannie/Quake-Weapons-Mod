package mett.palemannie.quakeweapons.event;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.client.ClientWeaponRecoil;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = QuakeWeapons.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class WeaponRecoilClientEvents {
    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
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
