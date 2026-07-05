package mett.palemannie.quakeweapons.item.client;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.QuakeWeaponsConfig;
import mett.palemannie.quakeweapons.item.custom.ShotgunItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ShotgunModel extends GeoModel<ShotgunItem> {

    private static final ResourceLocation DEFAULT_MODEL = ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "geo/shotgun.geo.json");
    private static final ResourceLocation ALT_MODEL = ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "geo/shotgun_alt.geo.json");

    private static final ResourceLocation DEFAULT_TEXTURE = ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "textures/item/shotgun.png");
    private static final ResourceLocation ALT_TEXTURE = ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "textures/item/shotgun_alt.png");

    private static final ResourceLocation DEFAULT_ANIM = ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "animations/shotgun.animations.json");
    private static final ResourceLocation ALT_ANIM = ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "animations/shotgun_alt.animations.json");

    @Override
    public ResourceLocation getModelResource(ShotgunItem animatable) {

        return QuakeWeaponsConfig.COMMON.enableEnhancedModels.get() ? ALT_MODEL : DEFAULT_MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(ShotgunItem animatable) {

        return QuakeWeaponsConfig.COMMON.enableEnhancedModels.get() ? ALT_TEXTURE : DEFAULT_TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationResource(ShotgunItem animatable) {

        return QuakeWeaponsConfig.COMMON.enableEnhancedModels.get() ? ALT_ANIM : DEFAULT_ANIM;
    }
}
