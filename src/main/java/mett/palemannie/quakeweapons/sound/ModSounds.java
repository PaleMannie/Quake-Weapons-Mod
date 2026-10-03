package mett.palemannie.quakeweapons.sound;

import mett.palemannie.quakeweapons.QuakeWeapons;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModSounds {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, QuakeWeapons.MODID);

    public static final DeferredHolder<SoundEvent, SoundEvent> NAILGUN_SHOOT = registerSoundEvents("nailgun_shoot");
    public static final DeferredHolder<SoundEvent, SoundEvent> SUPER_NAILGUN_SHOOT = registerSoundEvents("super_nailgun_shoot");
    public static final DeferredHolder<SoundEvent, SoundEvent> NAILGUN_HIT = registerSoundEvents("nailgun_hit");
    public static final DeferredHolder<SoundEvent, SoundEvent> THUNDERBOLT_START = registerSoundEvents("thunderbolt_start");
    public static final DeferredHolder<SoundEvent, SoundEvent> THUNDERBOLT_LOOP = registerSoundEvents("thunderbolt_loop");
    public static final DeferredHolder<SoundEvent, SoundEvent> SHOTGUN_SHOOT = registerSoundEvents("shotgun_shoot");
    public static final DeferredHolder<SoundEvent, SoundEvent> SUPER_SHOTGUN_SHOOT = registerSoundEvents("super_shotgun_shoot");
    public static final DeferredHolder<SoundEvent, SoundEvent> ROCKETLAUNCHER_SHOOT = registerSoundEvents("rocketlauncher_shoot");
    public static final DeferredHolder<SoundEvent, SoundEvent> GRENADELAUNCHER_SHOOT = registerSoundEvents("grenadelauncher_shoot");
    public static final DeferredHolder<SoundEvent, SoundEvent> GRENADE_BOUNCE = registerSoundEvents("grenade_bounce");
    public static final DeferredHolder<SoundEvent, SoundEvent> EXPLOSION = registerSoundEvents("explosion");
    public static final DeferredHolder<SoundEvent, SoundEvent> AXE_HIT_AIR = registerSoundEvents("axe_hit_air");
    public static final DeferredHolder<SoundEvent, SoundEvent> AXE_HIT_SOLID = registerSoundEvents("axe_hit_solid");
    public static final DeferredHolder<SoundEvent, SoundEvent> AXE_HIT_ENTITY = registerSoundEvents("axe_hit_entity");
    public static final DeferredHolder<SoundEvent, SoundEvent> QUAD_DAMAGE_PICKUP = registerSoundEvents("quad_damage_pickup");
    public static final DeferredHolder<SoundEvent, SoundEvent> QUAD_DAMAGE_USE = registerSoundEvents("quad_damage_use");
    public static final DeferredHolder<SoundEvent, SoundEvent> QUAD_DAMAGE_EXPIRE = registerSoundEvents("quad_damage_expire");
    public static final DeferredHolder<SoundEvent, SoundEvent> PENTAGRAM_PICKUP = registerSoundEvents("pentagram_pickup");
    public static final DeferredHolder<SoundEvent, SoundEvent> PENTAGRAM_USE = registerSoundEvents("pentagram_use");
    public static final DeferredHolder<SoundEvent, SoundEvent> PENTAGRAM_EXPIRE = registerSoundEvents("pentagram_expire");
    public static final DeferredHolder<SoundEvent, SoundEvent> RING_PICKUP = registerSoundEvents("ring_pickup");
    public static final DeferredHolder<SoundEvent, SoundEvent> RING_USE = registerSoundEvents("ring_use");
    public static final DeferredHolder<SoundEvent, SoundEvent> RING_EXPIRE = registerSoundEvents("ring_expire");
    public static final DeferredHolder<SoundEvent, SoundEvent> BIOSUIT_PICKUP = registerSoundEvents("biosuit_pickup");
    public static final DeferredHolder<SoundEvent, SoundEvent> BIOSUIT_EXPIRE = registerSoundEvents("biosuit_expire");
    public static final DeferredHolder<SoundEvent, SoundEvent> MEGAHEALTH_SOUND = registerSoundEvents("megahealth_sound");
    public static final DeferredHolder<SoundEvent, SoundEvent> AMMO_PICKUP_SOUND = registerSoundEvents("ammo_pickup_sound");

    private static DeferredHolder<SoundEvent, SoundEvent> registerSoundEvents(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
