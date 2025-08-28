package mett.palemannie.quakeweapons.effect;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.effect.custom.BiosuitEffect;
import mett.palemannie.quakeweapons.effect.custom.InvulnerabilityEffect;
import mett.palemannie.quakeweapons.effect.custom.QWInvisEffect;
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
    public static final RegistryObject<MobEffect> INVULNERABILITY = MOB_EFFECTS.register("invulnerability", ()-> new InvulnerabilityEffect(MobEffectCategory.BENEFICIAL, 16765184));
    public static final RegistryObject<MobEffect> QW_INVIS = MOB_EFFECTS.register("qw_invis", ()-> new QWInvisEffect(MobEffectCategory.BENEFICIAL, 5051981));
    public static final RegistryObject<MobEffect> BIOSUIT = MOB_EFFECTS.register("biosuit", ()-> new BiosuitEffect(MobEffectCategory.BENEFICIAL, 65408));

    public static void register(IEventBus eventBus){
        MOB_EFFECTS.register(eventBus);
    }
}
