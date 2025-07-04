package mett.palemannie.quakeweapons.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {
    public static final String KEY_CATEGORY_QUAKEWEAPONS = "quakeweapons.key.category.title";
    public static final String KEY_SHOOT = "quakeweapons.key.shoot";

    public static final KeyMapping SHOOT_QUAKE_WEAPONS = new KeyMapping(KEY_SHOOT, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_MOUSE_BUTTON_8, KEY_CATEGORY_QUAKEWEAPONS);
}
