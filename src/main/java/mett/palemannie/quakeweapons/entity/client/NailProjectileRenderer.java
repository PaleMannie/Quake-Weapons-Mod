package mett.palemannie.quakeweapons.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.entity.custom.NailProjectileEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LlamaSpitRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class NailProjectileRenderer extends EntityRenderer<NailProjectileEntity, LlamaSpitRenderState> {

    private static final ResourceLocation NAIL_LOCATION = ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID,"textures/entity/nail_projectile/nail_projectile.png");
    private final NailProjectileModel model;

    public NailProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new NailProjectileModel(context.bakeLayer(NailProjectileModel.NAIL_LAYER));
    }

    @Override
    public void submit(LlamaSpitRenderState state, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {

        poseStack.pushPose();

        poseStack.translate(0f, 0.1f, 0f);

        poseStack.mulPose(Axis.YP.rotationDegrees(state.yRot));
        poseStack.mulPose(Axis.XP.rotationDegrees(-state.xRot + 180f));
        poseStack.mulPose(Axis.ZP.rotationDegrees(180f));

        this.model.setupAnim(state);

        /*VertexConsumer vertexconsumer = pBufferSource.getBuffer(this.model.renderType(NAIL_LOCATION));
        this.model.renderToBuffer(poseStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY);*/

        nodeCollector.submitModel(this.model, state, poseStack, this.model.renderType(NAIL_LOCATION), state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor, (ModelFeatureRenderer.CrumblingOverlay) null);


        poseStack.popPose();
        super.submit(state, poseStack, nodeCollector, cameraRenderState);
    }

    @Override
    public boolean shouldRender(NailProjectileEntity pLivingEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        return true;
    }

    @Override
    public LlamaSpitRenderState createRenderState() {
        return new LlamaSpitRenderState();
    }

    public void extractRenderState(NailProjectileEntity pEntity, LlamaSpitRenderState renderState, float pPartialTick) {
        super.extractRenderState(pEntity, renderState, pPartialTick);
        renderState.xRot = pEntity.getXRot(pPartialTick);
        renderState.yRot = pEntity.getYRot(pPartialTick);
    }
}