package mett.palemannie.quakeweapons.entity.client;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.entity.custom.QuadDamagePowerupEntity;
import net.minecraft.resources.Identifier;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class QuadDamagePowerupModel extends GeoModel<QuadDamagePowerupEntity> {


	@Override
	public Identifier getModelResource(GeoRenderState geoRenderState) {
		return Identifier.fromNamespaceAndPath(QuakeWeapons.MODID, "quad");
	}

	@Override
	public Identifier getTextureResource(GeoRenderState geoRenderState) {
		return Identifier.fromNamespaceAndPath(QuakeWeapons.MODID, "textures/entity/quad/quad.png");
	}

	@Override
	public Identifier getAnimationResource(QuadDamagePowerupEntity quad) {
		return Identifier.fromNamespaceAndPath(QuakeWeapons.MODID, "quad");
	}
}
