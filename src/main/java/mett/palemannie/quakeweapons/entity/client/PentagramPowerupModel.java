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

		PartDefinition pentagram_powerup = partdefinition.addOrReplaceChild("pentagram_powerup", CubeListBuilder.create().texOffs(4, 17).addBox(-0.25F, 2.5F, -0.25F, 0.5F, 1.0F, 0.5F, new CubeDeformation(0.0F))
				.texOffs(8, 17).addBox(-0.125F, 1.5F, -0.125F, 0.25F, 1.0F, 0.25F, new CubeDeformation(0.0F))
				.texOffs(0, 17).addBox(-0.375F, 3.5F, -0.4F, 0.75F, 1.0F, 0.75F, new CubeDeformation(0.0F))
				.texOffs(16, 12).addBox(-0.5F, 4.5F, -0.5F, 1.0F, 3.5F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 8).addBox(-1.25F, 8.0F, -1.25F, 2.5F, 1.0F, 2.5F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r1 = pentagram_powerup.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 12).addBox(0.685F, -2.4215F, -1.0125F, 2.0F, 0.9F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.3238F, 8.8012F, 0.0125F, 3.1416F, 0.0F, -0.7897F));

		PartDefinition cube_r2 = pentagram_powerup.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(12, 8).addBox(-1.1872F, -1.7498F, -0.9875F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.3238F, 8.8012F, 0.0125F, 3.1416F, 0.0F, -1.3111F));

		PartDefinition cube_r3 = pentagram_powerup.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 4).addBox(-2.572F, -2.0975F, -1.0125F, 3.0F, 1.5F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.3238F, 8.8012F, 0.0125F, 3.1416F, 0.0F, -2.0907F));

		PartDefinition cube_r4 = pentagram_powerup.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0582F, -3.3506F, -0.9875F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.3238F, 8.8012F, 0.0125F, 3.1416F, 0.0F, -2.9656F));

		PartDefinition cube_r5 = pentagram_powerup.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(8, 12).addBox(3.425F, -4.35F, -1.0F, 2.0F, 0.9F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 5.5F, 0.0F, 0.0F, 0.0F, -2.3562F));

		PartDefinition cube_r6 = pentagram_powerup.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(22, 8).addBox(2.15F, -2.05F, -0.975F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 5.5F, 0.0F, 0.0F, 0.0F, -1.8326F));

		PartDefinition cube_r7 = pentagram_powerup.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(10, 4).addBox(0.0F, 0.05F, -1.0F, 3.0F, 1.5F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 5.5F, 0.0F, 0.0F, 0.0F, -1.0472F));

		PartDefinition cube_r8 = pentagram_powerup.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(10, 0).addBox(-3.05F, 0.0F, -0.975F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 5.5F, 0.0F, 0.0F, 0.0F, -0.1745F));

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
