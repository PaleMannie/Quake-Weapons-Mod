package mett.palemannie.quakeweapons.item.client;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.item.custom.ThunderboltItem;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

public class ThunderboltModel extends GeoModel<ThunderboltItem> {

    @Override
    public ResourceLocation getModelResource(ThunderboltItem thunderboltItem, @Nullable GeoRenderer<ThunderboltItem> geoRenderer) {
        return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "geo/thunderbolt.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ThunderboltItem thunderboltItem, @Nullable GeoRenderer<ThunderboltItem> geoRenderer) {
        return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "textures/item/thunderbolt.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ThunderboltItem animatable) {

        return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "animations/thunderbolt.animation.json");
    }
}
