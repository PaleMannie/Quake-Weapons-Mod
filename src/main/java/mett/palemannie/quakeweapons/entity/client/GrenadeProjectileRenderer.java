package mett.palemannie.quakeweapons.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.entity.custom.GrenadeProjectileEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;

public class GrenadeProjectileRenderer extends EntityRenderer<GrenadeProjectileEntity, GrenadeRenderState> {

    private static final Identifier GRENADE_LOCATION = Identifier.fromNamespaceAndPath(QuakeWeapons.MODID,"textures/entity/grenade_projectile/grenade_projectile.png");
    private static final Identifier GRENADE_EMISSIVE_LOCATION = Identifier.fromNamespaceAndPath(QuakeWeapons.MODID,"textures/entity/grenade_projectile/grenade_projectile_glow.png");

    private final GrenadeProjectileModel model;

    public GrenadeProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new GrenadeProjectileModel(context.bakeLayer(GrenadeProjectileModel.GRENADE_LAYER));
    }

    @Override
    public void submit(GrenadeRenderState state, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState pCameraRenderState) {

        poseStack.pushPose();
        poseStack.scale(0.2F, 0.2F, 0.2F);
        poseStack.translate(0.0F, 0.1f, 0.0F);

        poseStack.mulPose(Axis.XP.rotationDegrees(state.tumbleX));
        poseStack.mulPose(Axis.YP.rotationDegrees(state.tumbleY));
        poseStack.mulPose(Axis.ZP.rotationDegrees(state.tumbleZ));

        nodeCollector.submitModel(this.model, state, poseStack, this.model.renderType(GRENADE_LOCATION), state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor, (ModelFeatureRenderer.CrumblingOverlay) null);
        nodeCollector.submitModel(this.model, state, poseStack, RenderTypes.eyes(GRENADE_EMISSIVE_LOCATION), state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor, (ModelFeatureRenderer.CrumblingOverlay) null);

        poseStack.popPose();
    }

    @Override
    public boolean shouldRender(GrenadeProjectileEntity pLivingEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        return true;
    }

    @Override
    public GrenadeRenderState createRenderState() {
        return new GrenadeRenderState();
    }

    @Override
    public void extractRenderState(GrenadeProjectileEntity entity, GrenadeRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);

        /// Tumble n shiet
        RandomSource random = RandomSource.create(entity.getId());

        if (!entity.hasStopped) {
            float tumbleSpeed = -25.0F;
            float tumbleX = (entity.tickCount + partialTick) * (tumbleSpeed + (random.nextFloat() * (-tumbleSpeed * 2)));
            float tumbleY = (entity.tickCount + partialTick) * (tumbleSpeed + (random.nextFloat() * (-tumbleSpeed * 2)));
            float tumbleZ = (entity.tickCount + partialTick) * (tumbleSpeed + (random.nextFloat() * (-tumbleSpeed * 2)));

            entity.lastTumbleX = tumbleX;
            entity.lastTumbleY = tumbleY;
            entity.lastTumbleZ = tumbleZ;

            state.tumbleX = (Mth.lerp(partialTick, entity.xRotO, entity.getXRot())) + tumbleX;
            state.tumbleY = (Mth.lerp(partialTick, entity.yRotO, entity.getYRot()) + 180f) + tumbleY;
            state.tumbleZ = tumbleZ;
        } else {
            state.tumbleX = (Mth.lerp(partialTick, entity.xRotO, entity.getXRot())) + entity.lastTumbleX;
            state.tumbleY = (Mth.lerp(partialTick, entity.yRotO, entity.getYRot()) + 180f) + entity.lastTumbleY;
            state.tumbleZ = entity.lastTumbleZ;
        }

        state.hasStopped = entity.hasStopped;
    }
}