package mett.palemannie.quakeweapons.compat;

import net.minecraftforge.fml.ModList;

import java.lang.reflect.Method;

public final class SquakeCompat {
    private SquakeCompat() {}

    /// Achieving compatibility with Squake Ported
    private static final String SQUAKE_MODID = "squakeport_1_21_6";
    private static final String CONFIG_CLASS = "mett.palemannie.squakeport_1_21_6.ModConfig";

    public static boolean isSquakeMovementEnabledClient() {

        if (!ModList.get().isLoaded(SQUAKE_MODID)) return false;

        try {

            Class<?> clazz = Class.forName(CONFIG_CLASS);
            Method m = clazz.getMethod("isEnabled");
            return (boolean) m.invoke(null);

        } catch (Throwable t) {

            return false;
        }
    }
}
