package mett.palemannie.quakeweapons.client;

import net.minecraft.util.Mth;

public final class ClientWeaponRecoil {
    private ClientWeaponRecoil() {}
    private static float previousPitch, pitch;
    private static float previousRoll, roll;
    private static float previousYaw, yaw;

    public static void kick(float pitchAmount, float rollAmount, float yawAmount) {
        pitch = Mth.clamp(pitch + pitchAmount, -100, 100);
        roll = Mth.clamp(roll + rollAmount, -100, 100);
        yaw = Mth.clamp(yaw + yawAmount, -100, 100);
    }

    public static void clientTick() {
        previousPitch = pitch;
        previousRoll = roll;
        previousYaw = yaw;
        pitch = Math.abs(pitch *= 0.72f) < 0.02f ? 0 : pitch;
        roll = Math.abs(roll *= 0.68f) < 0.02f ? 0 : roll;
        yaw = Math.abs(yaw *= 0.7f) < 0.02f ? 0 : yaw;
    }

    public static float pitch(float partialTick) { return Mth.lerp(partialTick, previousPitch, pitch); }
    public static float roll(float partialTick) { return Mth.lerp(partialTick, previousRoll, roll); }
    public static float yaw(float partialTick) { return Mth.lerp(partialTick, previousYaw, yaw); }
}
