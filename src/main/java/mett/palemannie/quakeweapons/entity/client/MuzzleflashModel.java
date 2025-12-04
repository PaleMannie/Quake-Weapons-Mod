package mett.palemannie.quakeweapons.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.entity.custom.MuzzleflashEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class MuzzleflashModel extends EntityModel<MuzzleflashEntity> {

    public static final ModelLayerLocation FLASH_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "muzzleflash"), "main");
    private static final String MAIN = "main";
    private final ModelPart root;

    public MuzzleflashModel(ModelPart root) {
        this.root = root;
    }

    public static LayerDefinition createBodyLayer() {

        MeshDefinition meshdefinition = new MeshDefinition();

        return LayerDefinition.create(meshdefinition, 16, 16);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int red, int green, int blue) {

        root.render(poseStack, vertexConsumer, red, green, blue);
    }

    /*@Override
    public ModelPart root() { return this.root; }*/

    public ModelPart getRoot() {
        return this.root;
    }

    @Override
    public void setupAnim(MuzzleflashEntity t, float v, float v1, float v2, float v3, float v4) {

    }
}