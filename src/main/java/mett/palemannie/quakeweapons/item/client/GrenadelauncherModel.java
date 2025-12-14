package mett.palemannie.quakeweapons.item.client;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.item.custom.GrenadelauncherItem;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

public class GrenadelauncherModel extends GeoModel<GrenadelauncherItem> {

    @Override
    public ResourceLocation getModelResource(GrenadelauncherItem grenadelauncherItem, @Nullable GeoRenderer<GrenadelauncherItem> geoRenderer) {
        return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "geo/grenadelauncher.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GrenadelauncherItem grenadelauncherItem, @Nullable GeoRenderer<GrenadelauncherItem> geoRenderer) {
        return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "textures/item/grenadelauncher.png");
    }

    @Override
    public ResourceLocation getAnimationResource(GrenadelauncherItem animatable) {

        return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "animations/grenadelauncher.animation.json");
    }
}
