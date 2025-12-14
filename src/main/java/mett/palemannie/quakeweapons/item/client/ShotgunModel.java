package mett.palemannie.quakeweapons.item.client;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.item.custom.ShotgunItem;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

public class ShotgunModel extends GeoModel<ShotgunItem> {

    @Override
    public ResourceLocation getModelResource(ShotgunItem shotgunItem, @Nullable GeoRenderer<ShotgunItem> geoRenderer) {
        return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "geo/shotgun.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ShotgunItem shotgunItem, @Nullable GeoRenderer<ShotgunItem> geoRenderer) {
        return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "textures/item/shotgun.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ShotgunItem animatable) {

        return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "animations/shotgun.animation.json");
    }
}
