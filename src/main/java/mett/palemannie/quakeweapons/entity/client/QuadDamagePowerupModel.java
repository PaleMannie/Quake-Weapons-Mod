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

		PartDefinition bb_main = partdefinition.addOrReplaceChild("quad_damage_powerup", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 2.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(30, 43).addBox(-1.0F, 6.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(18, 43).addBox(-1.5F, 10.0F, -1.5F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-18.0F, 17.0F, -2.5F, 36.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(36, 19).addBox(-2.0F, 14.0F, -2.0F, 4.0F, 13.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(36, 10).addBox(-3.0F, 27.0F, -3.0F, 6.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(18, 10).addBox(16.0F, 22.0F, -2.5F, 4.0F, 28.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(0, 10).addBox(-20.0F, 22.0F, -2.5F, 4.0F, 28.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(0, 43).addBox(12.0F, 48.0F, -2.5F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(36, 36).addBox(-16.0F, 48.0F, -2.5F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		quad_damage_powerup.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}
