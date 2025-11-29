package mett.palemannie.quakeweapons.item.client;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.item.custom.RocketlauncherItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class RocketlauncherModel extends GeoModel<RocketlauncherItem> {

    @Override
    public ResourceLocation getModelResource(RocketlauncherItem animatable) {

        return new ResourceLocation(QuakeWeapons.MODID, "geo/rocketlauncher.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(RocketlauncherItem animatable) {

        return new ResourceLocation(QuakeWeapons.MODID, "textures/item/rocketlauncher.png");
    }

    @Override
    public ResourceLocation getAnimationResource(RocketlauncherItem animatable) {

        return new ResourceLocation(QuakeWeapons.MODID, "animations/rocketlauncher.animations.json");
    }
}
