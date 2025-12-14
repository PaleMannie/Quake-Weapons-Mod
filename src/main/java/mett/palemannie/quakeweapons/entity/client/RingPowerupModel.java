package mett.palemannie.quakeweapons.entity.client;

import mett.palemannie.quakeweapons.QuakeWeapons;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.resources.ResourceLocation;

public class RingPowerupModel extends EntityModel<EntityRenderState>{
	public static final ModelLayerLocation RING_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "ring_powerup"), "main");
	private final ModelPart ring_powerup;

	public RingPowerupModel(ModelPart root) {
        super(root);
        this.ring_powerup = root.getChild("ring_powerup");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bb_main = partdefinition.addOrReplaceChild("ring_powerup", CubeListBuilder.create(), PartPose.offset(0.0F, 14.0F, 0.0F));
		PartDefinition cube_r1 = bb_main.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -4.825F, -0.5F, 2.0F, 0.5F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 6).addBox(0.0F, -0.5F, -0.5F, 2.0F, 0.5F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -1.5F, -0.532F, -0.2071F, -0.3361F));
		PartDefinition cube_r2 = bb_main.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 2).addBox(-2.0F, -4.825F, -0.525F, 2.0F, 0.5F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(5, 5).addBox(-2.0F, -0.5F, -0.525F, 2.0F, 0.5F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -1.5F, -0.532F, 0.2071F, 0.3361F));
		PartDefinition cube_r3 = bb_main.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(5, 7).addBox(1.425F, -3.4F, -0.525F, 2.0F, 0.5F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 4).addBox(1.425F, 0.9F, -0.525F, 2.0F, 0.5F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -1.5F, -0.2391F, -0.5194F, -1.1143F));
		PartDefinition cube_r4 = bb_main.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(5, 1).addBox(-3.425F, -3.4F, -0.55F, 2.0F, 0.5F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(5, 3).addBox(-3.4F, 0.9F, -0.55F, 2.0F, 0.5F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -1.5F, -0.2391F, 0.5194F, 1.1143F));

		return LayerDefinition.create(meshdefinition, 16, 16);
	}

	/*@Override
	public void setupAnim(RingofshadowsPowerupEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int red, int green, int blue) {

		ring_powerup.render(poseStack, vertexConsumer, red, green, blue);
	}*/
}
