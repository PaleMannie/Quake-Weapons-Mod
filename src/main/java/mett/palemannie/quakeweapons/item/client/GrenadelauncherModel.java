package mett.palemannie.quakeweapons.item.client;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.item.custom.GrenadelauncherItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class GrenadelauncherModel extends GeoModel<GrenadelauncherItem> {


    @Override
    public ResourceLocation getModelResource(GeoRenderState geoRenderState) {
        return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "grenadelauncher");
    }

    @Override
    public ResourceLocation getTextureResource(GeoRenderState geoRenderState) {
        return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "textures/item/grenadelauncher.png");
    }

    @Override
    public ResourceLocation getAnimationResource(GrenadelauncherItem animatable) {

        return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "grenadelauncher");
    }
}
