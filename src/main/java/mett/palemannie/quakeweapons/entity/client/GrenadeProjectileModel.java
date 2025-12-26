package mett.palemannie.quakeweapons.entity.client;

import mett.palemannie.quakeweapons.QuakeWeapons;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.resources.ResourceLocation;

public class GrenadeProjectileModel extends EntityModel<EntityRenderState> {

    public static final ModelLayerLocation GRENADE_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "grenade_projectile"), "main");
    private final ModelPart grenade;

    public GrenadeProjectileModel(ModelPart root) {
        super(root);
        this.grenade = root.getChild("grenade");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition grenade = partdefinition.addOrReplaceChild("grenade", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, 2.0F, 0.0F, 8.0F, 8.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(0, 24).addBox(-6.5F, 1.5F, -6.0F, 9.0F, 9.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(30, 24).addBox(-6.0F, 2.0F, -7.0F, 8.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(30, 33).addBox(-5.0F, 3.0F, -8.0F, 6.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 39).addBox(-4.0F, 4.0F, -9.0F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 39).addBox(-3.0F, 5.0F, -10.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -6.0F, -5.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }
}