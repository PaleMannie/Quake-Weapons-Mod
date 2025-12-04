package mett.palemannie.quakeweapons.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.entity.custom.RocketProjectileEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class RocketProjectileModel extends EntityModel<RocketProjectileEntity> {

	public static final ModelLayerLocation ROCKET_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "rocket_projectile"), "main");
	private final ModelPart rocket;

	public RocketProjectileModel(ModelPart root) {
		this.rocket = root.getChild("rocket");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition rocket = partdefinition.addOrReplaceChild("rocket", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, 0.0F, -7.0F, 4.0F, 4.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(0, 20).addBox(-0.5F, 4.0F, 4.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(18, 20).addBox(2.0F, 1.5F, 4.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(0, 29).addBox(-0.5F, -1.0F, 4.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(18, 29).addBox(-3.0F, 1.5F, 4.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(36, 20).addBox(-1.0F, 1.0F, -8.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(RocketProjectileEntity t, float v, float v1, float v2, float v3, float v4) {
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int red, int green, int blue) {

		rocket.render(poseStack, vertexConsumer, red, green, blue);
	}
}