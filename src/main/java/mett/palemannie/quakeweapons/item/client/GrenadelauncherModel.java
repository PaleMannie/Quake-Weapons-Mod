package mett.palemannie.quakeweapons.item.client;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.item.custom.GrenadelauncherItem;
import mett.palemannie.quakeweapons.item.custom.RocketlauncherItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class GrenadelauncherModel extends GeoModel<GrenadelauncherItem> {

    @Override
    public ResourceLocation getModelResource(GrenadelauncherItem animatable) {

        return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "geo/grenadelauncher.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GrenadelauncherItem animatable) {

        return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "textures/item/grenadelauncher.png");
    }

    @Override
    public ResourceLocation getAnimationResource(GrenadelauncherItem animatable) {

        return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "animations/grenadelauncher.animations.json");
    }
}
