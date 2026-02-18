package mett.palemannie.quakeweapons.item.client;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.QuakeWeaponsConfig;
import mett.palemannie.quakeweapons.item.custom.QWAxeItem;
import net.minecraft.resources.Identifier;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class QWAxeModel extends GeoModel<QWAxeItem> {

    private static final Identifier DEFAULT_MODEL = Identifier.fromNamespaceAndPath(QuakeWeapons.MODID, "qwaxe");
    private static final Identifier ALT_MODEL = Identifier.fromNamespaceAndPath(QuakeWeapons.MODID, "qwaxe_alt");

    private static final Identifier DEFAULT_TEXTURE = Identifier.fromNamespaceAndPath(QuakeWeapons.MODID, "textures/item/qwaxe.png");
    private static final Identifier ALT_TEXTURE = Identifier.fromNamespaceAndPath(QuakeWeapons.MODID, "textures/item/qwaxe_alt.png");

    private static final Identifier DEFAULT_ANIM = Identifier.fromNamespaceAndPath(QuakeWeapons.MODID, "qwaxe");
    private static final Identifier ALT_ANIM = Identifier.fromNamespaceAndPath(QuakeWeapons.MODID, "qwaxe_alt");

    @Override
    public Identifier getModelResource(GeoRenderState geoRenderState) {
        return QuakeWeaponsConfig.COMMON.enableAltModels.get() ? ALT_MODEL : DEFAULT_MODEL;
    }

    @Override
    public Identifier getTextureResource(GeoRenderState geoRenderState) {
        return QuakeWeaponsConfig.COMMON.enableAltModels.get() ? ALT_TEXTURE : DEFAULT_TEXTURE;
    }

    @Override
    public Identifier getAnimationResource(QWAxeItem animatable) {
        return QuakeWeaponsConfig.COMMON.enableAltModels.get() ? ALT_ANIM : DEFAULT_ANIM;
    }
}
