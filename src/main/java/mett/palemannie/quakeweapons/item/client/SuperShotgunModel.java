package mett.palemannie.quakeweapons.item.client;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.item.custom.SuperShotgunItem;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

public class SuperShotgunModel extends GeoModel<SuperShotgunItem> {

    @Override
    public ResourceLocation getModelResource(SuperShotgunItem superShotgunItem, @Nullable GeoRenderer<SuperShotgunItem> geoRenderer) {
        return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "geo/super_shotgun.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SuperShotgunItem superShotgunItem, @Nullable GeoRenderer<SuperShotgunItem> geoRenderer) {
        return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "textures/item/super_shotgun.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SuperShotgunItem animatable) {

        return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "animations/super_shotgun.animation.json");
    }
}
