package mett.palemannie.quakeweapons.item.client;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.item.custom.RocketlauncherItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class RocketlauncherModel extends GeoModel<RocketlauncherItem> {

    @Override
    public ResourceLocation getModelResource(GeoRenderState geoRenderState) {
        return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "rocketlauncher");
    }

    @Override
    public ResourceLocation getTextureResource(GeoRenderState geoRenderState) {
        return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "textures/item/rocketlauncher.png");
    }

    @Override
    public ResourceLocation getAnimationResource(RocketlauncherItem animatable) {

        return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "rocketlauncher");
    }
}
