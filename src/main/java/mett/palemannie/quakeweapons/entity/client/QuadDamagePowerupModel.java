package mett.palemannie.quakeweapons.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mett.palemannie.quakeweapons.QuakeWeapons;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class QuadDamagePowerupModel<T extends Entity> extends EntityModel<T> {

	public static final ModelLayerLocation QUAD_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "quad_damage_powerup"), "main");
	private final ModelPart quad_damage_powerup;

	public QuadDamagePowerupModel(ModelPart root) {
		this.quad_damage_powerup = root.getChild("quad_damage_powerup");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition quad_damage_powerup = partdefinition.addOrReplaceChild("quad_damage_powerup", CubeListBuilder.create().texOffs(11, 12).addBox(-0.25F, 2.5F, -0.25F, 0.5F, 1.0F, 0.5F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-0.125F, 1.5F, -0.125F, 0.25F, 1.0F, 0.25F, new CubeDeformation(0.0F))
				.texOffs(12, 9).addBox(-0.375F, 3.5F, -0.4F, 0.75F, 1.0F, 0.75F, new CubeDeformation(0.0F))
				.texOffs(8, 8).addBox(-0.5F, 4.5F, -0.5F, 1.0F, 3.5F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 4).addBox(-1.25F, 8.0F, -1.25F, 2.5F, 1.0F, 2.5F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-5.0F, 5.25F, -0.75F, 10.0F, 1.5F, 1.5F, new CubeDeformation(0.0F))
				.texOffs(0, 8).addBox(4.0F, 6.75F, -0.625F, 1.25F, 7.0F, 1.25F, new CubeDeformation(0.0F))
				.texOffs(4, 8).addBox(-5.5F, 6.75F, -0.625F, 1.25F, 7.0F, 1.25F, new CubeDeformation(0.0F))
				.texOffs(11, 7).addBox(-4.25F, 13.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(9, 4).addBox(2.0F, 13.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		quad_damage_powerup.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}
