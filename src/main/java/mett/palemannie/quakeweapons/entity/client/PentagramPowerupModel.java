package mett.palemannie.quakeweapons.entity.client;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.entity.custom.PentagramPowerupEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class PentagramPowerupModel extends GeoModel<PentagramPowerupEntity> {


	@Override
	public ResourceLocation getModelResource(GeoRenderState geoRenderState) {
		return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "pentagram");
	}

	@Override
	public ResourceLocation getTextureResource(GeoRenderState geoRenderState) {
		return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "textures/entity/pentagram/pentagram.png");
	}

	@Override
	public ResourceLocation getAnimationResource(PentagramPowerupEntity biosuitPowerupEntity) {
		return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "pentring");
	}
}
