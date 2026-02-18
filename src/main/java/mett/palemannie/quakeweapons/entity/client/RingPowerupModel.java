package mett.palemannie.quakeweapons.entity.client;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.entity.custom.RingofshadowsPowerupEntity;
import net.minecraft.resources.Identifier;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class RingPowerupModel extends GeoModel<RingofshadowsPowerupEntity> {


	@Override
	public Identifier getModelResource(GeoRenderState geoRenderState) {
		return Identifier.fromNamespaceAndPath(QuakeWeapons.MODID, "ring");
	}

	@Override
	public Identifier getTextureResource(GeoRenderState geoRenderState) {
		return Identifier.fromNamespaceAndPath(QuakeWeapons.MODID, "textures/entity/ring/ring.png");
	}

	@Override
	public Identifier getAnimationResource(RingofshadowsPowerupEntity ring) {
		return Identifier.fromNamespaceAndPath(QuakeWeapons.MODID, "pentring");
	}
}
