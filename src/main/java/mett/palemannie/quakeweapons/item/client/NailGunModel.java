package mett.palemannie.quakeweapons.item.client;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.item.custom.NailgunItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class NailGunModel extends GeoModel<NailgunItem> {

    @Override
    public ResourceLocation getModelResource(NailgunItem animatable) {

        return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "geo/nailgun.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(NailgunItem animatable) {

        return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "textures/item/nailgun.png");
    }

    @Override
    public ResourceLocation getAnimationResource(NailgunItem animatable) {

        return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "animations/nailgun.animations.json");
    }
}