package mett.palemannie.quakeweapons.item.client;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.item.custom.ShotGunItem;
import mett.palemannie.quakeweapons.item.custom.ThunderboltItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ShotgunModel extends GeoModel<ShotGunItem> {

    @Override
    public ResourceLocation getModelResource(ShotGunItem animatable) {

        return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "geo/shotgun.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ShotGunItem animatable) {

        return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "textures/item/shotgun.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ShotGunItem animatable) {

        return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "animations/shotgun.animations.json");
    }
}
