package mett.palemannie.quakeweapons.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.entity.custom.RocketProjectileEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LlamaSpitRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

public class RocketProjectileRenderer extends EntityRenderer<RocketProjectileEntity, LlamaSpitRenderState> {

    private static final Identifier ROCKET_LOCATION = Identifier.fromNamespaceAndPath(QuakeWeapons.MODID,"textures/entity/rocket_projectile/rocket_projectile.png");
    private static final Identifier ROCKET_EMISSIVE_LOCATION = Identifier.fromNamespaceAndPath(QuakeWeapons.MODID,"textures/entity/rocket_projectile/rocket_projectile_glow.png");
    private final RocketProjectileModel model;

    public RocketProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new RocketProjectileModel(context.bakeLayer(RocketProjectileModel.ROCKET_LAYER));
    }

    @Override
    public void submit(LlamaSpitRenderState state, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {

        poseStack.pushPose();

        poseStack.scale(0.25F, 0.25F, 0.25F);
        poseStack.translate(0.0F, 0.25f, 0.0F);

        poseStack.mulPose(Axis.YP.rotationDegrees(state.yRot));
        poseStack.mulPose(Axis.XP.rotationDegrees(-state.xRot + 180f));

        nodeCollector.submitModel(this.model, state, poseStack, this.model.renderType(ROCKET_LOCATION), state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor, (ModelFeatureRenderer.CrumblingOverlay) null);
        nodeCollector.submitModel(this.model, state, poseStack, RenderTypes.eyes(ROCKET_EMISSIVE_LOCATION), state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor, (ModelFeatureRenderer.CrumblingOverlay) null);

        poseStack.popPose();

        super.submit(state, poseStack, nodeCollector, cameraRenderState);
    }

    @Override
    public boolean shouldRender(RocketProjectileEntity pLivingEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        return true;
    }

    @Override
    public LlamaSpitRenderState createRenderState() {
        return new LlamaSpitRenderState();
    }

    public void extractRenderState(RocketProjectileEntity pEntity, LlamaSpitRenderState renderState, float pPartialTick) {
        super.extractRenderState(pEntity, renderState, pPartialTick);
        renderState.xRot = pEntity.getXRot(pPartialTick);
        renderState.yRot = pEntity.getYRot(pPartialTick);
    }
}