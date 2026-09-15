package mett.palemannie.quakeweapons;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class QuakeWeaponsConfig
{
    public static final ForgeConfigSpec COMMON_SPEC;
    public static final Common COMMON;

    static {
        final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        COMMON = new Common(builder);
        COMMON_SPEC = builder.build();
    }

    public static class Common {
        public final ForgeConfigSpec.BooleanValue enableMuzzleFlash;
        public final ForgeConfigSpec.BooleanValue enableRocketTrailLight;
        public final ForgeConfigSpec.BooleanValue enableThunderboltTracer;
        public final ForgeConfigSpec.BooleanValue enableGore;
        public final ForgeConfigSpec.BooleanValue enableEnhancedModels;
        public final ForgeConfigSpec.DoubleValue axeDamage;
        public final ForgeConfigSpec.DoubleValue shotgunDamage;
        public final ForgeConfigSpec.DoubleValue superShotgunDamage;
        public final ForgeConfigSpec.DoubleValue nailgunDamage;
        public final ForgeConfigSpec.DoubleValue superNailgunDamage;
        public final ForgeConfigSpec.DoubleValue thunderboltDamage;
        public final ForgeConfigSpec.DoubleValue rocketlauncherDamage;
        public final ForgeConfigSpec.DoubleValue rocketlauncherRadius;
        public final ForgeConfigSpec.DoubleValue grenadelauncherDamage;
        public final ForgeConfigSpec.DoubleValue grenadelauncherRadius;
        public final ForgeConfigSpec.DoubleValue explosionSelfDamageMultiplier;


        public Common(ForgeConfigSpec.Builder builder) {
            builder.push("Effects");

            enableEnhancedModels = builder
                    .comment("Use alternate weapon models, textures, and animations where available. Some alternate assets are unfinished.")
                    .define("enableEnhancedModels", false);

            enableMuzzleFlash = builder.comment("Show muzzle flashes and brief dynamic light when firing supported weapons. Experimental: flashing light may affect photosensitive players.")
                    .define("enableMuzzleFlash", false);

            enableRocketTrailLight = builder.comment("Place short-lived light blocks along flying rockets. Experimental: repeated light changes may affect photosensitive players.")
                    .define("enableRocketTrailLight", false);

            enableThunderboltTracer = builder.comment("Show electric-spark tracer particles along Thunderbolt shots.")
                    .define("enableThunderboltTracer", false);

            enableGore = builder.comment("Show additional gore particles when Quake weapons hit living entities.")
                    .define("enableThunderboltTracer", false);

            builder.pop();
            builder.push("Weapon damage");

            axeDamage = builder
                    .comment("Base damage dealt by one Axe hit. Two damage points equal one heart.")
                    .defineInRange("axeDamage", 10.0, 0.0, Float.MAX_VALUE);

            shotgunDamage = builder
                    .comment("Base damage per Shotgun pellet; each shot fires 6 pellets. Two damage points equal one heart.")
                    .defineInRange("shotgunDamage", 2.0, 0.0, Float.MAX_VALUE);

            superShotgunDamage = builder
                    .comment("Base damage per Super Shotgun pellet; each shot fires 14 pellets. Two damage points equal one heart.")
                    .defineInRange("superShotgunDamage", 2.0, 0.0, Float.MAX_VALUE);

            nailgunDamage = builder
                    .comment("Base damage dealt by one Nailgun projectile. Two damage points equal one heart.")
                    .defineInRange("nailgunDamage", 2.0, 0.0, Float.MAX_VALUE);

            superNailgunDamage = builder
                    .comment("Base damage dealt by one Super Nailgun projectile. Two damage points equal one heart.")
                    .defineInRange("superNailgunDamage", 4.0, 0.0, Float.MAX_VALUE);

            rocketlauncherDamage = builder
                    .comment("Maximum base Rocket Launcher splash damage. Quake 1 linear falloff and cover checks. Independent of radius and Quake impulse. Two damage points equal one heart.")
                    .defineInRange("rocketlauncherDamage", 28, 0.0, Float.MAX_VALUE);

            rocketlauncherRadius = builder
                    .comment("Rocket Launcher splash cutoff in blocks. Quake 1 falloff retains one third of maximum damage at the edge, then stops.")
                    .defineInRange("rocketlauncherRadius", 4.0, 0.0, Float.MAX_VALUE);

            grenadelauncherDamage = builder
                    .comment("Maximum base Grenade Launcher splash damage. Quake 1 linear falloff and cover checks. Independent of radius and Quake impulse. Two damage points equal one heart.")
                    .defineInRange("grenadelauncherDamage", 28, 0.0, Float.MAX_VALUE);

            grenadelauncherRadius = builder
                    .comment("Grenade Launcher splash cutoff in blocks. Quake 1 falloff retains one third of maximum damage at the edge, then stops.")
                    .defineInRange("grenadelauncherRadius", 5.0, 0.0, Float.MAX_VALUE);

            thunderboltDamage = builder
                    .comment("Base damage dealt by one Thunderbolt shot. Two damage points equal one heart.")
                    .defineInRange("thunderboltDamage", 6.0, 0.0, Float.MAX_VALUE);

            explosionSelfDamageMultiplier = builder
                    .comment("Self splash damage multiplier for Rocket Launcher and Grenade Launcher explosions. 0 = no self damage, 0.5 = half, 1 = full damage. Does not affect Quake knockback or damage to other entities.")
                    .defineInRange("explosionSelfDamageMultiplier", 0.5, 0.0, 1.0);

            builder.pop();
        }
    }

    public static final ForgeConfigSpec SERVER_SPEC;
    public static final Server SERVER;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        SERVER = new Server(builder);
        SERVER_SPEC = builder.build();
    }

    public static class Server {

        public final ForgeConfigSpec.IntValue weaponAggroRange;
        public final ForgeConfigSpec.IntValue powerupSpawnInterval;
        public final ForgeConfigSpec.IntValue powerupSpawnAttempts;
        public final ForgeConfigSpec.IntValue maxNearbyPowerups;
        public final ForgeConfigSpec.IntValue powerupSpawnSearchRadius;
        public final ForgeConfigSpec.IntValue powerupEffectDuration;
        public final ForgeConfigSpec.IntValue powerupLifetime;
        public final ForgeConfigSpec.BooleanValue powerupDebug;
        public final ForgeConfigSpec.BooleanValue enablePowerups;

        public Server(ForgeConfigSpec.Builder builder) {

            builder.push("Weapon aggro values");
            weaponAggroRange = builder
                    .comment("Range in blocks within which firing a Quake weapon alerts monsters. Set to 0 to disable.")
                    .defineInRange("weaponAggroRange", 24, 0, 256);
            builder.pop();

            builder.push("Powerup Spawner values");

            enablePowerups = builder
                    .comment("Automatically spawn Quake powerups and ammo pickups near players. Manually placed pickups are unaffected.")
                    .define("enablePowerups", true);

            powerupDebug = builder
                    .comment("Send powerup spawn results and nearby-limit messages to players in the affected dimension.")
                    .define("powerupDebug", false);

            powerupEffectDuration = builder
                    .comment("Duration of a collected powerup effect, in ticks (20 ticks = 1 second). Does not affect ammo pickups.")
                    .defineInRange("powerupEffectDuration", 600, 1, Integer.MAX_VALUE-1);

            powerupLifetime = builder
                    .comment("Time before uncollected powerups and ammo pickups despawn, in ticks (20 ticks = 1 second).")
                    .defineInRange("powerupLifetime", 6000, 1, Integer.MAX_VALUE-1);

            powerupSpawnInterval = builder
                    .comment("Ticks between automatic spawn rounds in each dimension (20 ticks = 1 second). Each round makes the configured number of attempts per player.")
                    .defineInRange("powerupSpawnInterval", 600, 20, Integer.MAX_VALUE-1);

            powerupSpawnAttempts = builder
                    .comment("Spawn attempts per player in each spawn round. Higher values can create more pickups and increase server work.")
                    .defineInRange("powerupSpawnAttempts", 5, 1, 512);

            powerupSpawnSearchRadius = builder
                    .comment("Search radius around each candidate position for a suitable spawn block. Higher values increase server work.")
                    .defineInRange("powerupSpawnSearchRadius", 5, 1, 512);

            maxNearbyPowerups = builder
                    .comment("Maximum automatically spawned Quake powerups and ammo pickups within 128 blocks of a player. Manually placed pickups do not count.")
                    .defineInRange("maxNearbyPowerups", 4, 1, Integer.MAX_VALUE);

            builder.pop();
        }
    }

    public static void registerConfigs() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, COMMON_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SERVER_SPEC);
    }
}
