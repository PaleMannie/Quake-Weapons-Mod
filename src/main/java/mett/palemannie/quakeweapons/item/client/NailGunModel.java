package mett.palemannie.quakeweapons.item.client;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.item.custom.NailgunItem;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

public class NailGunModel extends GeoModel<NailgunItem> {

    @Override
    public ResourceLocation getModelResource(NailgunItem nailgunItem, @Nullable GeoRenderer<NailgunItem> geoRenderer) {
        return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "geo/nailgun.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(NailgunItem nailgunItem, @Nullable GeoRenderer<NailgunItem> geoRenderer) {
        return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "textures/item/nailgun.png");
    }

    @Override
    public ResourceLocation getAnimationResource(NailgunItem animatable) {

        return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "animations/nailgun.animation.json");
    }
}