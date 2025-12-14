package mett.palemannie.quakeweapons.entity.client;

import mett.palemannie.quakeweapons.QuakeWeapons;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.resources.ResourceLocation;

public class MuzzleflashModel extends EntityModel<EntityRenderState> {

    public static final ModelLayerLocation FLASH_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "muzzleflash"), "main");
    private static final String MAIN = "main";
    private final ModelPart root;

    public MuzzleflashModel(ModelPart root) {
        super(root);
        this.root = root;
    }

    public static LayerDefinition createBodyLayer() {

        MeshDefinition meshdefinition = new MeshDefinition();

        return LayerDefinition.create(meshdefinition, 16, 16);
    }

    /*@Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int red, int green, int blue) {

        root.render(poseStack, vertexConsumer, red, green, blue);
    }

    @Override
    public ModelPart root() { return this.root; }

    public ModelPart getRoot() {
        return this.root;
    }

    @Override
    public void setupAnim(MuzzleflashEntity t, float v, float v1, float v2, float v3, float v4) {
    }*/
}