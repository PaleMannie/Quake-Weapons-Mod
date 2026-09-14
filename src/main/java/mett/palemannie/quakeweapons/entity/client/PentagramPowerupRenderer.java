package mett.palemannie.quakeweapons.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.entity.custom.PentagramPowerupEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

public class PentagramPowerupRenderer extends EntityRenderer<PentagramPowerupEntity, EntityRenderState> {

    private static final Identifier PENTAGRAM_LOCATION = Identifier.fromNamespaceAndPath(QuakeWeapons.MODID,"textures/entity/pentagram_powerup/pentagram_powerup.png");
    private final PentagramPowerupModel model;

    public PentagramPowerupRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new PentagramPowerupModel(context.bakeLayer(PentagramPowerupModel.PENTAGRAM_LAYER));
    }

    float bobbingSpeed = 0.05f;
    float bobbingHeight = 0.1f;
    float rotationSpeed = 5f;

    @Override
    public void submit(EntityRenderState state, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraState) {

        poseStack.pushPose();

        poseStack.translate(0f, 0.75f, 0f);
        poseStack.scale(0.75f, 0.75f, 0.75f);

        float ageInTicks = state.ageInTicks;

        double bob = Math.sin(ageInTicks * bobbingSpeed) * bobbingHeight;
        poseStack.translate(0d, 0.25d + bob, 0d);

        float rotation = (ageInTicks * rotationSpeed) % 360;
        poseStack.mulPose(Axis.YP.rotationDegrees(rotation));

        nodeCollector.submitModel(this.model, state, poseStack, this.model.renderType(PENTAGRAM_LOCATION), state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor, null);
        nodeCollector.submitModel(this.model, state, poseStack, RenderTypes.eyes(PENTAGRAM_LOCATION), state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor, null);

        poseStack.popPose();

        super.submit(state, poseStack, nodeCollector, cameraState);
    }

    @Override
    public boolean shouldRender(PentagramPowerupEntity pLivingEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        return true;
    }

    @Override
    public EntityRenderState createRenderState() {
        return new EntityRenderState();
    }
}