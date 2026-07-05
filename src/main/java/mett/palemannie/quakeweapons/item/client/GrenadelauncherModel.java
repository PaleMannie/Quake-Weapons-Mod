package mett.palemannie.quakeweapons.item.client;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.QuakeWeaponsConfig;
import mett.palemannie.quakeweapons.item.custom.GrenadelauncherItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class GrenadelauncherModel extends GeoModel<GrenadelauncherItem> {

    private static final ResourceLocation DEFAULT_MODEL = ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "geo/grenadelauncher.geo.json");
    private static final ResourceLocation ALT_MODEL = ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "geo/grenadelauncher_alt.geo.json");

    private static final ResourceLocation DEFAULT_TEXTURE = ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "textures/item/grenadelauncher.png");
    private static final ResourceLocation ALT_TEXTURE = ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "textures/item/grenadelauncher_alt.png");

    private static final ResourceLocation DEFAULT_ANIM = ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "animations/grenadelauncher.animations.json");
    private static final ResourceLocation ALT_ANIM = ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "animations/grenadelauncher_alt.animations.json");

    @Override
    public ResourceLocation getModelResource(GrenadelauncherItem animatable) {

        return QuakeWeaponsConfig.COMMON.enableEnhancedModels.get() ? ALT_MODEL : DEFAULT_MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(GrenadelauncherItem animatable) {

        return QuakeWeaponsConfig.COMMON.enableEnhancedModels.get() ? ALT_TEXTURE : DEFAULT_TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationResource(GrenadelauncherItem animatable) {

        return QuakeWeaponsConfig.COMMON.enableEnhancedModels.get() ? ALT_ANIM : DEFAULT_ANIM;
    }
}
