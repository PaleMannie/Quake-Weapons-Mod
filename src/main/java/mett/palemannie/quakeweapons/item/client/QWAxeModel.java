package mett.palemannie.quakeweapons.item.client;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.item.custom.QWAxeItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class QWAxeModel extends GeoModel<QWAxeItem> {

    @Override
    public ResourceLocation getModelResource(QWAxeItem animatable) {

        return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "geo/qwaxe.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(QWAxeItem animatable) {

        return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "textures/item/qwaxe.png");
    }

    @Override
    public ResourceLocation getAnimationResource(QWAxeItem animatable) {

        return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "animations/qwaxe.animation.json");
    }
}
