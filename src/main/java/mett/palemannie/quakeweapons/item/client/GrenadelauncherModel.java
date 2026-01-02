package mett.palemannie.quakeweapons.item.client;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.QuakeWeaponsConfig;
import mett.palemannie.quakeweapons.item.custom.GrenadelauncherItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class GrenadelauncherModel extends GeoModel<GrenadelauncherItem> {

    private static final ResourceLocation DEFAULT_MODEL = ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "grenadelauncher");
    private static final ResourceLocation ALT_MODEL = ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "grenadelauncher_alt");

    private static final ResourceLocation DEFAULT_TEXTURE = ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "textures/item/grenadelauncher.png");
    private static final ResourceLocation ALT_TEXTURE = ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "textures/item/grenadelauncher_alt.png");

    private static final ResourceLocation DEFAULT_ANIM = ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "grenadelauncher");
    private static final ResourceLocation ALT_ANIM = ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "grenadelauncher_alt");

    @Override
    public ResourceLocation getModelResource(GeoRenderState geoRenderState) {
        return QuakeWeaponsConfig.COMMON.enableAltModel.get() ? ALT_MODEL : DEFAULT_MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(GeoRenderState geoRenderState) {
        return QuakeWeaponsConfig.COMMON.enableAltModel.get() ? ALT_TEXTURE : DEFAULT_TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationResource(GrenadelauncherItem animatable) {

        return QuakeWeaponsConfig.COMMON.enableAltModel.get() ? ALT_ANIM : DEFAULT_ANIM;
    }
}
