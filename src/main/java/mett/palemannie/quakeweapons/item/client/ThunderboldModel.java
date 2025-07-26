package mett.palemannie.quakeweapons.item.client;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.item.custom.NailGunItem;
import mett.palemannie.quakeweapons.item.custom.ThunderboltItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ThunderboldModel extends GeoModel<ThunderboltItem> {

    @Override
    public ResourceLocation getModelResource(ThunderboltItem animatable) {

        return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "geo/thunderbolt.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ThunderboltItem animatable) {

        return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "textures/item/thunderbolt.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ThunderboltItem animatable) {

        return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "animations/thunderbolt.animations.json");
    }
}
