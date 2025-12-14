package mett.palemannie.quakeweapons.item.client;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.item.custom.RocketlauncherItem;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

public class RocketlauncherModel extends GeoModel<RocketlauncherItem> {

    @Override
    public ResourceLocation getModelResource(RocketlauncherItem rocketlauncherItem, @Nullable GeoRenderer<RocketlauncherItem> geoRenderer) {
        return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "geo/rocketlauncher.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(RocketlauncherItem rocketlauncherItem, @Nullable GeoRenderer<RocketlauncherItem> geoRenderer) {
        return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "textures/item/rocketlauncher.png");
    }

    @Override
    public ResourceLocation getAnimationResource(RocketlauncherItem animatable) {

        return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "animations/rocketlauncher.animation.json");
    }
}
