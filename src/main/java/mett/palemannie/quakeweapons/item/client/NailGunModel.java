package mett.palemannie.quakeweapons.item.client;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.QuakeWeaponsConfig;
import mett.palemannie.quakeweapons.item.custom.NailgunItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class NailGunModel extends GeoModel<NailgunItem> {

    private static final ResourceLocation DEFAULT_MODEL = ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "geo/nailgun.geo.json");
    private static final ResourceLocation ALT_MODEL = ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "geo/nailgun_alt.geo.json");

    private static final ResourceLocation DEFAULT_TEXTURE = ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "textures/item/nailgun.png");
    private static final ResourceLocation ALT_TEXTURE = ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "textures/item/nailgun_alt.png");

    private static final ResourceLocation DEFAULT_ANIM = ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "animations/nailgun.animations.json");
    private static final ResourceLocation ALT_ANIM = ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "animations/nailgun_alt.animations.json");

    @Override
    public ResourceLocation getModelResource(NailgunItem animatable) {

        return QuakeWeaponsConfig.COMMON.enableEnhancedModels.get() ? ALT_MODEL : DEFAULT_MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(NailgunItem animatable) {

        return QuakeWeaponsConfig.COMMON.enableEnhancedModels.get() ? ALT_TEXTURE : DEFAULT_TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationResource(NailgunItem animatable) {

        return QuakeWeaponsConfig.COMMON.enableEnhancedModels.get() ? ALT_ANIM : DEFAULT_ANIM;
    }
}