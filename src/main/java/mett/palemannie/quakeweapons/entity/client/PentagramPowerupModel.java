package mett.palemannie.quakeweapons.entity.client;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.entity.custom.PentagramPowerupEntity;
import net.minecraft.resources.Identifier;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class PentagramPowerupModel extends GeoModel<PentagramPowerupEntity> {


	@Override
	public Identifier getModelResource(GeoRenderState geoRenderState) {
		return Identifier.fromNamespaceAndPath(QuakeWeapons.MODID, "pentagram");
	}

	@Override
	public Identifier getTextureResource(GeoRenderState geoRenderState) {
		return Identifier.fromNamespaceAndPath(QuakeWeapons.MODID, "textures/entity/pentagram/pentagram.png");
	}

	@Override
	public Identifier getAnimationResource(PentagramPowerupEntity biosuitPowerupEntity) {
		return Identifier.fromNamespaceAndPath(QuakeWeapons.MODID, "pentring");
	}
}
