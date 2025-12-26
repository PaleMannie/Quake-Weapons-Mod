package mett.palemannie.quakeweapons.entity.client;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.entity.custom.BiosuitPowerupEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class BiosuitPowerupModel extends GeoModel<BiosuitPowerupEntity> {


	@Override
	public ResourceLocation getModelResource(GeoRenderState geoRenderState) {
		return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "biosuit");
	}

	@Override
	public ResourceLocation getTextureResource(GeoRenderState geoRenderState) {
		return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "textures/entity/biosuit/biosuit.png");
	}

	@Override
	public ResourceLocation getAnimationResource(BiosuitPowerupEntity biosuitPowerupEntity) {
		return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "biosuit");
	}
}
