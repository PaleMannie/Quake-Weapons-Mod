package mett.palemannie.quakeweapons.item.client;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.QuakeWeaponsConfig;
import mett.palemannie.quakeweapons.item.custom.QWAxeItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class QWAxeModel extends GeoModel<QWAxeItem> {

    private static final ResourceLocation DEFAULT_MODEL = ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "geo/qwaxe.geo.json");
    private static final ResourceLocation ALT_MODEL = ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "geo/qwaxe_alt.geo.json");

    private static final ResourceLocation DEFAULT_TEXTURE = ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "textures/item/qwaxe.png");
    private static final ResourceLocation ALT_TEXTURE = ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "textures/item/qwaxe_alt.png");

    private static final ResourceLocation DEFAULT_ANIM = ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "animations/qwaxe.animations.json");
    private static final ResourceLocation ALT_ANIM = ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "animations/qwaxe_alt.animations.json");


    @Override
    public ResourceLocation getModelResource(QWAxeItem animatable) {

        return QuakeWeaponsConfig.COMMON.enableEnhancedModels.get() ? ALT_MODEL : DEFAULT_MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(QWAxeItem animatable) {

        return QuakeWeaponsConfig.COMMON.enableEnhancedModels.get() ? ALT_TEXTURE : DEFAULT_TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationResource(QWAxeItem animatable) {

        return QuakeWeaponsConfig.COMMON.enableEnhancedModels.get() ? ALT_ANIM : DEFAULT_ANIM;
    }
}
