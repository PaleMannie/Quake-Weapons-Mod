package mett.palemannie.quakeweapons.entity.client;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.entity.custom.BiosuitPowerupEntity;
import net.minecraft.resources.Identifier;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class BiosuitPowerupModel extends GeoModel<BiosuitPowerupEntity> {


	@Override
	public Identifier getModelResource(GeoRenderState geoRenderState) {
		return Identifier.fromNamespaceAndPath(QuakeWeapons.MODID, "biosuit");
	}

	@Override
	public Identifier getTextureResource(GeoRenderState geoRenderState) {
		return Identifier.fromNamespaceAndPath(QuakeWeapons.MODID, "textures/entity/biosuit/biosuit.png");
	}

	@Override
	public Identifier getAnimationResource(BiosuitPowerupEntity biosuitPowerupEntity) {
		return Identifier.fromNamespaceAndPath(QuakeWeapons.MODID, "biosuit");
	}
}
