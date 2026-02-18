package mett.palemannie.quakeweapons.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.entity.custom.MuzzleflashEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.resources.Identifier;

public class MuzzleflashRenderer extends EntityRenderer<MuzzleflashEntity, EntityRenderState> {

    private static final Identifier FLASH_LOCATION = Identifier.fromNamespaceAndPath(QuakeWeapons.MODID,"textures/entity/muzzleflash/muzzleflash.png");
    private final MuzzleflashModel model;

    public MuzzleflashRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new MuzzleflashModel(context.bakeLayer(MuzzleflashModel.FLASH_LAYER));
    }

    @Override
    public EntityRenderState createRenderState() {
        return new EntityRenderState();
    }

    @Override
    public void submit(EntityRenderState state, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
        super.submit(state, poseStack, nodeCollector, cameraRenderState);
    }
}