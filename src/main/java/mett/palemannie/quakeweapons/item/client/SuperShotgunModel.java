package mett.palemannie.quakeweapons.item.client;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.QuakeWeaponsConfig;
import mett.palemannie.quakeweapons.item.custom.SuperShotgunItem;
import net.minecraft.resources.Identifier;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class SuperShotgunModel extends GeoModel<SuperShotgunItem> {

    private static final Identifier DEFAULT_MODEL = Identifier.fromNamespaceAndPath(QuakeWeapons.MODID, "super_shotgun");
    private static final Identifier ALT_MODEL = Identifier.fromNamespaceAndPath(QuakeWeapons.MODID, "super_shotgun_alt");

    private static final Identifier DEFAULT_TEXTURE = Identifier.fromNamespaceAndPath(QuakeWeapons.MODID, "textures/item/super_shotgun.png");
    private static final Identifier ALT_TEXTURE = Identifier.fromNamespaceAndPath(QuakeWeapons.MODID, "textures/item/super_shotgun_alt.png");

    private static final Identifier DEFAULT_ANIM = Identifier.fromNamespaceAndPath(QuakeWeapons.MODID, "super_shotgun");
    private static final Identifier ALT_ANIM = Identifier.fromNamespaceAndPath(QuakeWeapons.MODID, "super_shotgun_alt");

    @Override
    public Identifier getModelResource(GeoRenderState geoRenderState) {
        return QuakeWeaponsConfig.COMMON.enableEnhancedModels.get() ? ALT_MODEL : DEFAULT_MODEL;
    }

    @Override
    public Identifier getTextureResource(GeoRenderState geoRenderState) {
        return QuakeWeaponsConfig.COMMON.enableEnhancedModels.get() ? ALT_TEXTURE : DEFAULT_TEXTURE;
    }

    @Override
    public Identifier getAnimationResource(SuperShotgunItem animatable) {
        return QuakeWeaponsConfig.COMMON.enableEnhancedModels.get() ? ALT_ANIM : DEFAULT_ANIM;
    }
}
