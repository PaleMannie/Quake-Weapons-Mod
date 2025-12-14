package mett.palemannie.quakeweapons.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.entity.custom.BiosuitPowerupEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class BiosuitPowerupModel extends EntityModel<EntityRenderState> {


	public static final ModelLayerLocation BIOSUIT_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "biosuit_powerup"), "main");

	private final ModelPart shoes;
	private final ModelPart body;
	private final ModelPart arms;
	private final ModelPart head;
	private final ModelPart backpack;

	public BiosuitPowerupModel(ModelPart root) {

		super(root);
        this.shoes = root.getChild("shoes");
		this.body = root.getChild("body");
		this.arms = root.getChild("arms");
		this.head = root.getChild("head");
		this.backpack = root.getChild("backpack");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition shoes = partdefinition.addOrReplaceChild("shoes", CubeListBuilder.create().texOffs(0, 27).addBox(-3.5F, -6.9F, -6.0F, 7.0F, 7.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -24.0F, -10.5F, 7.0F, 5.0F, 21.0F, new CubeDeformation(0.0F))
				.texOffs(0, 50).addBox(-3.0F, -23.8F, -5.0F, 6.0F, 12.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(57, 0).addBox(-2.5F, -12.0F, -5.0F, 5.0F, 12.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition arms = partdefinition.addOrReplaceChild("arms", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -23.9F, -10.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(59, 37).addBox(-2.5F, -13.9F, -10.5F, 5.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(34, 61).addBox(-2.5F, -13.9F, 5.5F, 5.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(36, 0).addBox(-2.0F, -23.9F, 6.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(48, 23).addBox(-6.5F, -23.0F, -4.5F, 2.0F, 4.0F, 9.0F, new CubeDeformation(0.0F))
				.texOffs(29, 103).addBox(-5.0F, -23.025F, -5.0F, 10.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(30, 38).addBox(-4.5F, -32.0F, -4.5F, 9.0F, 12.0F, 9.0F, new CubeDeformation(0.0F))
				.texOffs(29, 103).addBox(-5.0F, -32.025F, -5.0F, 10.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(27, 30).addBox(-3.0F, -33.0F, -3.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition backpack = partdefinition.addOrReplaceChild("backpack", CubeListBuilder.create().texOffs(13, 0).addBox(2.75F, -18.0F, -4.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(58, 60).addBox(3.0F, -21.0F, -3.0F, 2.0F, 7.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(0, 17).addBox(2.75F, -18.0F, 3.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	/*@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {

		shoes.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		arms.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		head.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		backpack.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
	}

	@Override
	public void setupAnim(BiosuitPowerupEntity biosuitPowerupEntity, float v, float v1, float v2, float v3, float v4) {

	}*/
}
