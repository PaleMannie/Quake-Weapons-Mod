package mett.palemannie.quakeweapons.item.client;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.item.custom.SuperNailgunItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class SuperNailGunModel extends GeoModel<SuperNailgunItem> {

    @Override
    public ResourceLocation getModelResource(GeoRenderState geoRenderState) {
        return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "geo/super_nailgun.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GeoRenderState geoRenderState) {
        return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "textures/item/super_nailgun.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SuperNailgunItem animatable) {

        return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "animations/super_nailgun.animation.json");
    }
}
