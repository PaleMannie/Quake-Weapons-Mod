package mett.palemannie.quakeweapons.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.entity.custom.SuperNailProjectileEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

public class SuperNailProjectileRenderer extends EntityRenderer<SuperNailProjectileEntity, ProjectileRenderState> {

    private static final Identifier SUPER_NAIL_LOCATION = Identifier.fromNamespaceAndPath(QuakeWeapons.MODID,"textures/entity/super_nail_projectile/super_nail_projectile.png");
    private final SuperNailProjectileModel model;

    public SuperNailProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new SuperNailProjectileModel(context.bakeLayer(SuperNailProjectileModel.SUPER_NAIL_LAYER));
    }

    @Override
    public void submit(ProjectileRenderState state, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {

        poseStack.pushPose();

        poseStack.translate(0f, 0.1f, 0f);

        poseStack.mulPose(Axis.YP.rotationDegrees(state.yRot));
        poseStack.mulPose(Axis.XP.rotationDegrees(-state.xRot + 180f));
        poseStack.mulPose(Axis.ZP.rotationDegrees(180f));

        this.model.setupAnim(state);

        nodeCollector.submitModel(this.model, state, poseStack, this.model.renderType(SUPER_NAIL_LOCATION), state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor, (ModelFeatureRenderer.CrumblingOverlay) null);


        poseStack.popPose();
        super.submit(state, poseStack, nodeCollector, cameraRenderState);
    }

    @Override
    public boolean shouldRender(SuperNailProjectileEntity pLivingEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        return true;
    }

    @Override
    public ProjectileRenderState createRenderState() {
        return new ProjectileRenderState();
    }

    public void extractRenderState(SuperNailProjectileEntity pEntity, ProjectileRenderState renderState, float pPartialTick) {
        super.extractRenderState(pEntity, renderState, pPartialTick);
        renderState.xRot = pEntity.getXRot(pPartialTick);
        renderState.yRot = pEntity.getYRot(pPartialTick);
    }
}