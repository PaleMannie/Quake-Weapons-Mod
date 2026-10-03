package mett.palemannie.quakeweapons.effect;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.effect.custom.BiosuitEffect;
import mett.palemannie.quakeweapons.effect.custom.InvulnerabilityEffect;
import mett.palemannie.quakeweapons.effect.custom.QWInvisEffect;
import mett.palemannie.quakeweapons.effect.custom.QuadDamageEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModEffects {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS
            = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, QuakeWeapons.MODID);

    public static final DeferredHolder<MobEffect, MobEffect> QUAD_DAMAGE = MOB_EFFECTS.register("quad_damage", ()-> new QuadDamageEffect(MobEffectCategory.BENEFICIAL, 4034242));
    public static final DeferredHolder<MobEffect, MobEffect> INVULNERABILITY = MOB_EFFECTS.register("invulnerability", ()-> new InvulnerabilityEffect(MobEffectCategory.BENEFICIAL, 16765184));
    public static final DeferredHolder<MobEffect, MobEffect> QW_INVIS = MOB_EFFECTS.register("qw_invis", ()-> new QWInvisEffect(MobEffectCategory.BENEFICIAL, 5051981));
    public static final DeferredHolder<MobEffect, MobEffect> BIOSUIT = MOB_EFFECTS.register("biosuit", ()-> new BiosuitEffect(MobEffectCategory.BENEFICIAL, 65408));

    public static void register(IEventBus eventBus){
        MOB_EFFECTS.register(eventBus);
    }
}
