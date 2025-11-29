package mett.palemannie.quakeweapons.item.client;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.item.custom.ThunderboltItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ThunderboltModel extends GeoModel<ThunderboltItem> {

    @Override
    public ResourceLocation getModelResource(ThunderboltItem animatable) {

        return new ResourceLocation(QuakeWeapons.MODID, "geo/thunderbolt.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ThunderboltItem animatable) {

        return new ResourceLocation(QuakeWeapons.MODID, "textures/item/thunderbolt.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ThunderboltItem animatable) {

        return new ResourceLocation(QuakeWeapons.MODID, "animations/thunderbolt.animations.json");
    }
}
