package mett.palemannie.quakeweapons.effect;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.effect.custom.QuadDamageEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS
            = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, QuakeWeapons.MODID);

    public static final RegistryObject<MobEffect> QUAD_DAMAGE = MOB_EFFECTS.register("quad_damage", ()-> new QuadDamageEffect(MobEffectCategory.BENEFICIAL, 4034242));
    public static final RegistryObject<MobEffect> INVULNERABILITY = MOB_EFFECTS.register("invulnerability", ()-> new QuadDamageEffect(MobEffectCategory.BENEFICIAL, 11181238));
    public static final RegistryObject<MobEffect> QW_INVIS = MOB_EFFECTS.register("qw_invis", ()-> new QuadDamageEffect(MobEffectCategory.BENEFICIAL, 11181238));
    public static final RegistryObject<MobEffect> BIOSUIT = MOB_EFFECTS.register("biosuit", ()-> new QuadDamageEffect(MobEffectCategory.BENEFICIAL, 11181238));

    public static void register(IEventBus eventBus){
        MOB_EFFECTS.register(eventBus);
    }
}
