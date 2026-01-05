package mett.palemannie.quakeweapons.util;

import net.minecraft.client.player.LocalPlayer;

public class QWPlayer {

    public static boolean isMovingDiagonally(LocalPlayer player){

        return (player.input.keyPresses.forward() || player.input.keyPresses.backward())
                && (player.input.keyPresses.left() || player.input.keyPresses.right());

    }
}
