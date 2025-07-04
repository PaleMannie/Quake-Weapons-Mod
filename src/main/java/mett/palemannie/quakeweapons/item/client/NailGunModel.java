package mett.palemannie.quakeweapons.item.client;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.item.custom.NailGunItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class NailGunModel extends GeoModel<NailGunItem> {

    @Override
    public ResourceLocation getModelResource(NailGunItem animatable) {

        return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "geo/nailgun.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(NailGunItem animatable) {

        return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "textures/item/nailgun.png");
    }

    @Override
    public ResourceLocation getAnimationResource(NailGunItem animatable) {

        return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "animations/nailgun.animations.json");
    }
}