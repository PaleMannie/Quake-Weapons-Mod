package mett.palemannie.quakeweapons;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.apache.commons.lang3.tuple.Pair;

@Mod.EventBusSubscriber(modid = QuakeWeapons.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class QuakeWeaponsConfig
{
    public static final ForgeConfigSpec COMMON_SPEC;
    public static final Common COMMON;

    static {
        Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();
    }

    public static class Common {
        public final ForgeConfigSpec.BooleanValue enableMuzzleFlash;
        public final ForgeConfigSpec.BooleanValue quadDamageOnly;
        public final ForgeConfigSpec.BooleanValue enableThunderboltTracer;
        public final ForgeConfigSpec.DoubleValue axeDamage;
        public final ForgeConfigSpec.DoubleValue shotgunDamage;
        public final ForgeConfigSpec.DoubleValue superShotgunDamage;
        public final ForgeConfigSpec.DoubleValue nailgunDamage;
        public final ForgeConfigSpec.DoubleValue superNailgunDamage;
        public final ForgeConfigSpec.DoubleValue thunderboltDamage;
        public final ForgeConfigSpec.DoubleValue rocketlauncherDamage;
        public final ForgeConfigSpec.DoubleValue grenadelauncherDamage;

        public Common(ForgeConfigSpec.Builder builder) {
            builder.push("Spitting Image");

            enableMuzzleFlash = builder.comment("[EXPERIMENTAL: EPILEPSY WARNING] Enables/Disables muzzle flash when shooting").define("enableMuzzleFlash", false);

            quadDamageOnly = builder.comment("Enables/Disables permanent quad damage effect").define("quadDamageOnly", false);

            enableThunderboltTracer = builder.comment("Enables/Disables Thunderbolt hitscan tracers").define("enableThunderboltTracer", false);

            axeDamage = builder
                    .comment("How much damage the Quake axe deals")
                    .defineInRange("axeDamage", 1.0, 0.0, Float.MAX_VALUE);

            shotgunDamage = builder
                    .comment("How much damage the Shotgun deals per pellet (6 pellets per shot)")
                    .defineInRange("shotgunDamage", 2.0, 0.0, Float.MAX_VALUE);

            superShotgunDamage = builder
                    .comment("How much damage the Double Barreled Shotgun deals per pellet (14 pellets per shot)")
                    .defineInRange("superShotgunDamage", 2.0, 0.0, Float.MAX_VALUE);

            nailgunDamage = builder
                    .comment("How much damage the Nailgun deals per shot")
                    .defineInRange("nailgunDamage", 2.0, 0.0, Float.MAX_VALUE);

            superNailgunDamage = builder
                    .comment("How much damage the Super Nailgun deals per shot")
                    .defineInRange("superNailgunDamage", 4.0, 0.0, Float.MAX_VALUE);

            thunderboltDamage = builder
                    .comment("How much damage the Thunderbolt deals per shot")
                    .defineInRange("thunderboltDamage", 6.0, 0.0, Float.MAX_VALUE);

            rocketlauncherDamage = builder
                    .comment("How much damage the Rocket Launcher deals per shot")
                    .defineInRange("rocketlauncherDamage", 6.0, 0.0, Float.MAX_VALUE);

            grenadelauncherDamage = builder
                    .comment("How much damage the Grenade Launcher deals per shot")
                    .defineInRange("grenadelauncherDamage", 6.0, 0.0, Float.MAX_VALUE);

            builder.pop();
        }
    }
}
