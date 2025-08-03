package mett.palemannie.quakeweapons.item.client;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.item.custom.ShotgunItem;
import mett.palemannie.quakeweapons.item.custom.SuperShotgunItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SuperShotgunModel extends GeoModel<SuperShotgunItem> {

    @Override
    public ResourceLocation getModelResource(SuperShotgunItem animatable) {

        return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "geo/super_shotgun.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SuperShotgunItem animatable) {

        return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "textures/item/super_shotgun.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SuperShotgunItem animatable) {

        return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "animations/super_shotgun.animations.json");
    }
}
