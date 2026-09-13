package mett.palemannie.quakeweapons.util;

import java.util.HashMap;
import java.util.Map;

public final class WeaponRefireClock<K> {
    private final Map<K, Long> lastAttempts = new HashMap<>();

    public boolean tryFire(K weapon, long currentTick, int intervalTicks) {
        Long last = lastAttempts.get(weapon);
        if (last != null && currentTick >= last && currentTick - last < intervalTicks) return false;
        lastAttempts.put(weapon, currentTick);
        return true;
    }
}
