package mett.palemannie.quakeweapons.util;

import mett.palemannie.quakeweapons.effect.ModEffects;
import mett.palemannie.quakeweapons.sound.ModSounds;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public final class PowerupSoundRegistry {

    private static final Map<Identifier, Map<PowerupSoundEventType, SoundEvent>> SOUNDS = new HashMap<>();

    public static void init() {

        register(ModEffects.BIOSUIT.getId(), ModSounds.BIOSUIT_PICKUP.get(), ModSounds.BIOSUIT_EXPIRE.get());
        register(ModEffects.QUAD_DAMAGE.getId(), ModSounds.QUAD_DAMAGE_PICKUP.get(), ModSounds.QUAD_DAMAGE_EXPIRE.get());
        register(ModEffects.INVULNERABILITY.getId(), ModSounds.PENTAGRAM_PICKUP.get(), ModSounds.PENTAGRAM_EXPIRE.get());
        register(ModEffects.QW_INVIS.getId(), ModSounds.RING_PICKUP.get(), ModSounds.RING_EXPIRE.get());
    }

    private static void register(Identifier effect, SoundEvent add, SoundEvent expire) {

        Map<PowerupSoundEventType, SoundEvent> map = new EnumMap<>(PowerupSoundEventType.class);
        map.put(PowerupSoundEventType.ADD, add);
        map.put(PowerupSoundEventType.EXPIRING, expire);
        SOUNDS.put(effect, map);
    }

    @Nullable
    public static SoundEvent getSound(Identifier effect, PowerupSoundEventType type) {

        Map<PowerupSoundEventType, SoundEvent> map = SOUNDS.get(effect);
        return map != null ? map.get(type) : null;
    }
}