package mett.palemannie.quakeweapons.item.client;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.QuakeWeaponsConfig;
import mett.palemannie.quakeweapons.item.custom.ShotgunItem;
import net.minecraft.resources.Identifier;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class ShotgunModel extends GeoModel<ShotgunItem> {

    private static final Identifier DEFAULT_MODEL = Identifier.fromNamespaceAndPath(QuakeWeapons.MODID, "shotgun");
    private static final Identifier ALT_MODEL = Identifier.fromNamespaceAndPath(QuakeWeapons.MODID, "shotgun_alt");

    private static final Identifier DEFAULT_TEXTURE = Identifier.fromNamespaceAndPath(QuakeWeapons.MODID, "textures/item/shotgun.png");
    private static final Identifier ALT_TEXTURE = Identifier.fromNamespaceAndPath(QuakeWeapons.MODID, "textures/item/shotgun_alt.png");

    private static final Identifier DEFAULT_ANIM = Identifier.fromNamespaceAndPath(QuakeWeapons.MODID, "shotgun");
    private static final Identifier ALT_ANIM = Identifier.fromNamespaceAndPath(QuakeWeapons.MODID, "shotgun_alt");

    @Override
    public Identifier getModelResource(GeoRenderState geoRenderState) {
        return QuakeWeaponsConfig.COMMON.enableAltModels.get() ? ALT_MODEL : DEFAULT_MODEL;
    }

    @Override
    public Identifier getTextureResource(GeoRenderState geoRenderState) {
        return QuakeWeaponsConfig.COMMON.enableAltModels.get() ? ALT_TEXTURE : DEFAULT_TEXTURE;
    }

    @Override
    public Identifier getAnimationResource(ShotgunItem animatable) {
        return QuakeWeaponsConfig.COMMON.enableAltModels.get() ? ALT_ANIM : DEFAULT_ANIM;
    }
}
