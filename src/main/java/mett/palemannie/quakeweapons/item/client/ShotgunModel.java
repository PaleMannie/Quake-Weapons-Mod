package mett.palemannie.quakeweapons.item.client;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.item.custom.ShotgunItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ShotgunModel extends GeoModel<ShotgunItem> {

    @Override
    public ResourceLocation getModelResource(ShotgunItem animatable) {

        return new ResourceLocation(QuakeWeapons.MODID, "geo/shotgun.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ShotgunItem animatable) {

        return new ResourceLocation(QuakeWeapons.MODID, "textures/item/shotgun.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ShotgunItem animatable) {

        return new ResourceLocation(QuakeWeapons.MODID, "animations/shotgun.animations.json");
    }
}
