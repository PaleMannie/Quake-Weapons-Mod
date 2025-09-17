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

public class BiosuitPowerupModel<T extends Entity> extends EntityModel<T> {
	public static final ModelLayerLocation BIOSUIT_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "biosuit_powerup"), "main");
	private final ModelPart biosuit_powerup;

	public BiosuitPowerupModel(ModelPart root) {
		this.biosuit_powerup = root.getChild("biosuit_powerup");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bb_main = partdefinition.addOrReplaceChild("biosuit_powerup", CubeListBuilder.create().texOffs(40, 72).addBox(1.9F, -4.0F, -6.0F, 6.0F, 4.0F, 9.0F, new CubeDeformation(0.0F))
				.texOffs(82, 24).addBox(1.9F, -12.0F, -3.0F, 6.0F, 8.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(70, 76).addBox(1.4F, -10.0F, -3.5F, 7.0F, 4.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(60, 87).addBox(2.4F, -20.0F, -2.5F, 5.0F, 8.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(0, 60).addBox(-6.5F, -24.0F, -2.9F, 14.0F, 4.0F, 5.8F, new CubeDeformation(0.0F))
				.texOffs(42, 16).addBox(-6.9F, -28.0F, -3.9F, 14.8F, 4.0F, 7.8F, new CubeDeformation(0.0F))
				.texOffs(40, 62).addBox(-6.4F, -32.0F, -2.9F, 13.8F, 4.0F, 5.8F, new CubeDeformation(0.0F))
				.texOffs(36, 34).addBox(-8.9F, -36.0F, -3.9F, 18.8F, 4.0F, 7.8F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-15.9F, -42.0F, -4.9F, 32.8F, 6.0F, 9.8F, new CubeDeformation(0.0F))
				.texOffs(0, 34).addBox(-6.0F, -57.0F, -6.0F, 12.0F, 14.0F, 12.0F, new CubeDeformation(0.0F))
				.texOffs(74, 46).addBox(-7.4F, -10.0F, -3.5F, 7.0F, 4.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(71, 63).addBox(-6.9F, -4.0F, -6.0F, 6.0F, 4.0F, 9.0F, new CubeDeformation(0.0F))
				.texOffs(80, 10).addBox(-6.9F, -12.0F, -3.0F, 6.0F, 8.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(40, 85).addBox(-6.4F, -20.0F, -2.5F, 5.0F, 8.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(20, 70).addBox(11.5F, -36.0F, -2.5F, 5.0F, 20.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(0, 70).addBox(-15.5F, -36.0F, -2.5F, 5.0F, 20.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(35, 47).addBox(-6.25F, -58.0F, -6.25F, 12.5F, 2.0F, 12.5F, new CubeDeformation(0.0F))
				.texOffs(76, 0).addBox(-3.25F, -59.0F, -3.25F, 6.5F, 2.0F, 6.5F, new CubeDeformation(0.0F))
				.texOffs(0, 16).addBox(-7.0F, -45.0F, -7.5F, 14.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		biosuit_powerup.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}
