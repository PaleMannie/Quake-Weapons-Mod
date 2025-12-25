package mett.palemannie.quakeweapons.entity.client;

import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.entity.custom.PentagramPowerupEntity;
import mett.palemannie.quakeweapons.entity.custom.RingofshadowsPowerupEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class RingPowerupModel extends GeoModel<RingofshadowsPowerupEntity> {


	@Override
	public ResourceLocation getModelResource(GeoRenderState geoRenderState) {
		return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "ring");
	}

	@Override
	public ResourceLocation getTextureResource(GeoRenderState geoRenderState) {
		return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "textures/entity/ring/ring.png");
	}

	@Override
	public ResourceLocation getAnimationResource(RingofshadowsPowerupEntity ring) {
		return ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "pentring");
	}
}
