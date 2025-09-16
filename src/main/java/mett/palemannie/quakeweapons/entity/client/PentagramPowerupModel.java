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

public class PentagramPowerupModel<T extends Entity> extends EntityModel<T> {
	public static final ModelLayerLocation PENTAGRAM_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "pentagram_powerup"), "main");
	private final ModelPart pentagram_powerup;

	public PentagramPowerupModel(ModelPart root) {
		this.pentagram_powerup = root.getChild("pentagram_powerup");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bb_main = partdefinition.addOrReplaceChild("pentagram_powerup", CubeListBuilder.create(), PartPose.offset(0.0F, 10.0F, 0.0F));

		PartDefinition cube_r1 = bb_main.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 2).addBox(-1.0F, -5.8F, -0.2F, 8.0F, 0.5F, 0.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -10.5F, 0.0F, 0.0F, 0.0F, 2.5307F));

		PartDefinition cube_r2 = bb_main.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 4).addBox(-4.0F, -4.4F, -0.3F, 8.0F, 0.5F, 0.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -10.5F, 0.0F, 0.0F, 0.0F, -3.1416F));

		PartDefinition cube_r3 = bb_main.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 0).addBox(-7.25F, -5.8F, -0.275F, 8.5F, 0.5F, 0.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -10.5F, 0.0F, 0.0F, 0.0F, -2.5307F));

		PartDefinition cube_r4 = bb_main.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 14).addBox(0.0F, -9.0F, -0.225F, 0.5F, 8.0F, 0.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -10.5F, 0.0F, 0.0F, 0.0F, 2.8362F));

		PartDefinition cube_r5 = bb_main.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(4, 14).addBox(-0.5F, -9.0F, -0.25F, 0.5F, 8.0F, 0.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -10.5F, 0.0F, 0.0F, 0.0F, -2.8362F));

		PartDefinition cube_r6 = bb_main.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 8).addBox(2.85F, 1.825F, -0.475F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 6).addBox(2.85F, -6.85F, -0.475F, 4.025F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -10.5F, 0.0F, 0.0F, 0.0F, 1.9635F));

		PartDefinition cube_r7 = bb_main.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(9, 7).addBox(-6.85F, -6.85F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(9, 13).addBox(-6.85F, 1.825F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -10.5F, 0.0F, 0.0F, 0.0F, -1.9635F));

		PartDefinition cube_r8 = bb_main.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(9, 9).addBox(-4.0F, -9.7F, -0.525F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(9, 11).addBox(-4.0F, -1.0F, -0.525F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -10.5F, 0.0F, 0.0F, 0.0F, -2.7489F));

		PartDefinition cube_r9 = bb_main.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(0, 10).addBox(0.025F, -9.7F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 12).addBox(0.0F, -1.0F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -10.5F, 0.0F, 0.0F, 0.0F, 2.7489F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		pentagram_powerup.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}
