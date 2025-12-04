package mett.palemannie.quakeweapons.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.entity.custom.SuperNailProjectileEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class SuperNailProjectileModel extends EntityModel<SuperNailProjectileEntity> {

    public static final ModelLayerLocation SUPER_NAIL_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "super_nail_projectile"), "main");
    private static final String MAIN = "main";
    private final ModelPart supernail;

    public SuperNailProjectileModel(ModelPart root) {
        this.supernail = root;
    }

    public static LayerDefinition createBodyLayer() {

        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partDefinition = meshdefinition.getRoot();

        PartDefinition bb_main = partDefinition.addOrReplaceChild("bb_main", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition cube_r1 = bb_main.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(-4, 8).addBox(-4.0F, 0.0F, 0.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -2.0F, 0.3367F, 0.741F, -1.8605F));
        PartDefinition cube_r2 = bb_main.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(-4, 4).addBox(-4.0F, 0.0F, 0.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -2.0F, 0.3367F, 0.741F, 2.3283F));
        PartDefinition cube_r3 = bb_main.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(-4, 0).addBox(-4.0F, 0.0F, 0.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -2.0F, -0.3465F, 0.7703F, 2.8972F));
        PartDefinition cube_r4 = bb_main.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 12).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 3.1416F));

        return LayerDefinition.create(meshdefinition, 16, 16);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int red, int green, int blue) {

        supernail.render(poseStack, vertexConsumer, red, green, blue);
    }

    /*@Override
    public ModelPart root() { return this.supernail; }*/

    @Override
    public void setupAnim(SuperNailProjectileEntity t, float v, float v1, float v2, float v3, float v4) {

    }
}