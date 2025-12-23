package mett.palemannie.quakeweapons.item.client;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.item.custom.SuperShotgunItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class SuperShotgunModel extends GeoModel<SuperShotgunItem> {

    @Override
    public ResourceLocation getModelResource(GeoRenderState geoRenderState) {
        return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "super_shotgun");
    }

    @Override
    public ResourceLocation getTextureResource(GeoRenderState geoRenderState) {
        return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "textures/item/super_shotgun.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SuperShotgunItem animatable) {

        return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "super_shotgun");
    }
}
