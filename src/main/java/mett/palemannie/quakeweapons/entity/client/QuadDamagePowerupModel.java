package mett.palemannie.quakeweapons.entity.client;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.entity.custom.QuadDamagePowerupEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class QuadDamagePowerupModel extends GeoModel<QuadDamagePowerupEntity> {


	@Override
	public ResourceLocation getModelResource(GeoRenderState geoRenderState) {
		return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "quad");
	}

	@Override
	public ResourceLocation getTextureResource(GeoRenderState geoRenderState) {
		return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "textures/entity/quad/quad.png");
	}

	@Override
	public ResourceLocation getAnimationResource(QuadDamagePowerupEntity quad) {
		return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "quad");
	}
}
