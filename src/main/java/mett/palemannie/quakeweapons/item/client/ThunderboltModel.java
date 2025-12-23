package mett.palemannie.quakeweapons.item.client;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.item.custom.ThunderboltItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class ThunderboltModel extends GeoModel<ThunderboltItem> {

    @Override
    public ResourceLocation getModelResource(GeoRenderState geoRenderState) {
        return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "thunderbolt");
    }

    @Override
    public ResourceLocation getTextureResource(GeoRenderState geoRenderState) {
        return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "textures/item/thunderbolt.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ThunderboltItem animatable) {

        return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "thunderbolt");
    }
}
