package mett.palemannie.quakeweapons.event;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.effect.ModEffects;
import mett.palemannie.quakeweapons.sound.ModSounds;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = QuakeWeapons.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {

    @SubscribeEvent
    public static void onQuadDamageHurt(LivingHurtEvent event) {

        /// Quad Damage apply damage
        if (event.getSource().getEntity() instanceof LivingEntity attacker) {
            if(attacker.hasEffect(ModEffects.QUAD_DAMAGE.get())) {

                event.setAmount(event.getAmount() * 4.0F);
            }
        }

        /// Pentagram apply invulnerability
        if (event.getEntity().hasEffect(ModEffects.INVULNERABILITY.get())) {

            event.setCanceled(true);
            event.getEntity().level().playSound(null, event.getEntity().blockPosition(), ModSounds.PENTAGRAM_USE.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
        }

        /// Biosuit apply poison and wither immunities & Fire resistance
        if (event.getEntity().hasEffect(ModEffects.BIOSUIT.get())) {

            DamageSource src = event.getSource();

            if (src.is(DamageTypes.MAGIC) || src.is(DamageTypes.WITHER)) {
                event.setCanceled(true);
            }

            if(src.is(DamageTypes.IN_FIRE) || src.is(DamageTypes.ON_FIRE) || src.is(DamageTypes.UNATTRIBUTED_FIREBALL)
                    || src.is(DamageTypes.FIREBALL) || src.is(DamageTypes.LAVA)){

                event.setAmount(event.getAmount() / 2f);
            }
        }
    }

    /// Effect pickup sounds
    @SubscribeEvent
    public static void onEffectGotten(MobEffectEvent.Added event){

        Entity entity = event.getEntity();

        if(event.getEffectInstance().getEffect().equals(ModEffects.QUAD_DAMAGE.get())){

            entity.level().playSound(null, entity.blockPosition(), ModSounds.QUAD_DAMAGE_PICKUP.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
        }

        if(event.getEffectInstance().getEffect().equals(ModEffects.INVULNERABILITY.get())){

            entity.level().playSound(null, entity.blockPosition(), ModSounds.PENTAGRAM_PICKUP.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
        }

        if(event.getEffectInstance().getEffect().equals(ModEffects.QW_INVIS.get())){

            entity.level().playSound(null, entity.blockPosition(), ModSounds.RING_PICKUP.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
        }

        if(event.getEffectInstance().getEffect().equals(ModEffects.BIOSUIT.get())){

            entity.level().playSound(null, entity.blockPosition(), ModSounds.BIOSUIT_PICKUP.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
        }
    }

    ///Quad Damage play use sound when attacking with anything or using any item
    ///additional Invis cancelation when under Ring of Shadows effect

    @SubscribeEvent
    public static void onQuadDamageAttack1(PlayerInteractEvent.LeftClickEmpty event){

        if(event.getEntity().hasEffect(ModEffects.QUAD_DAMAGE.get()) && event.getLevel().isClientSide){

            event.getEntity().playSound(ModSounds.QUAD_DAMAGE_USE.get(), 1f, 1f);
        }

        if(event.getEntity().hasEffect(ModEffects.QW_INVIS.get())){

            event.getEntity().removeEffect(ModEffects.QW_INVIS.get());
            event.getEntity().setInvisible(false);
        }
    }

    @SubscribeEvent
    public static void onQuadDamageAttack2(PlayerInteractEvent.LeftClickBlock event){

        if(event.getEntity().hasEffect(ModEffects.QUAD_DAMAGE.get()) && event.getLevel().isClientSide){

            event.getEntity().playSound(ModSounds.QUAD_DAMAGE_USE.get(), 1f, 1f);
        }

        if(event.getEntity().hasEffect(ModEffects.QW_INVIS.get())){

            event.getEntity().removeEffect(ModEffects.QW_INVIS.get());
            event.getEntity().setInvisible(false);
        }
    }

    @SubscribeEvent
    public static void onQuadDamageAttack3(PlayerInteractEvent.EntityInteract event){

        if(event.getEntity().hasEffect(ModEffects.QUAD_DAMAGE.get()) && event.getLevel().isClientSide){

            event.getEntity().playSound(ModSounds.QUAD_DAMAGE_USE.get(), 1f, 1f);
        }
    }

    @SubscribeEvent
    public static void onQuadDamageAttack4(AttackEntityEvent event){

        if(event.getEntity().hasEffect(ModEffects.QUAD_DAMAGE.get()) && event.getEntity().level().isClientSide()){

            event.getEntity().playSound(ModSounds.QUAD_DAMAGE_USE.get(), 1f, 1f);
        }

        if(event.getEntity().hasEffect(ModEffects.QW_INVIS.get())){

            event.getEntity().removeEffect(ModEffects.QW_INVIS.get());
            event.getEntity().setInvisible(false);
        }
    }

    @SubscribeEvent
    public static void onQuadDamageAttack5(ArrowLooseEvent event){

        if(event.getEntity().hasEffect(ModEffects.QUAD_DAMAGE.get()) && event.getLevel().isClientSide()){

            event.getEntity().playSound(ModSounds.QUAD_DAMAGE_USE.get(), 1f, 1f);
        }

        if(event.getEntity().hasEffect(ModEffects.QW_INVIS.get())){

            event.getEntity().removeEffect(ModEffects.QW_INVIS.get());
        }
    }

    @SubscribeEvent
    public static void onQuadDamageAttack6(PlayerInteractEvent.RightClickItem event){

        if(event.getEntity().hasEffect(ModEffects.QUAD_DAMAGE.get()) && event.getLevel().isClientSide()){

            event.getEntity().playSound(ModSounds.QUAD_DAMAGE_USE.get(), 1f, 1f);
        }
    }


    @SubscribeEvent
    public static void onRenderPlayer(RenderLivingEvent.Pre<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> event) {

        LivingEntity entity = event.getEntity();
        if (entity.hasEffect(ModEffects.QW_INVIS.get())) {

            event.setCanceled(true);
        }
    }
}