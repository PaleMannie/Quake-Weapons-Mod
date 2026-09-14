package mett.palemannie.quakeweapons.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.entity.custom.GrenadeProjectileEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;

public class GrenadeProjectileRenderer extends EntityRenderer<GrenadeProjectileEntity, ProjectileRenderState> {

    private static final Identifier GRENADE_LOCATION = Identifier.fromNamespaceAndPath(QuakeWeapons.MODID,"textures/entity/grenade_projectile/grenade_projectile.png");
    private static final Identifier GRENADE_EMISSIVE_LOCATION = Identifier.fromNamespaceAndPath(QuakeWeapons.MODID,"textures/entity/grenade_projectile/grenade_projectile_glow.png");

    private final GrenadeProjectileModel model;

    public GrenadeProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new GrenadeProjectileModel(context.bakeLayer(GrenadeProjectileModel.GRENADE_LAYER));
    }

    @Override
    public void submit(ProjectileRenderState state, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraState) {

        poseStack.pushPose();
        poseStack.scale(0.2f, 0.2f, 0.2f);
        poseStack.translate(0f, 0.1f, 0f);

        poseStack.mulPose(Axis.XP.rotationDegrees(state.xRot));
        poseStack.mulPose(Axis.YP.rotationDegrees(state.yRot));
        poseStack.mulPose(Axis.ZP.rotationDegrees(state.zRot));

        nodeCollector.submitModel(this.model, state, poseStack, RenderTypes.entityCutoutNoCull(GRENADE_LOCATION), state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor, null);
        nodeCollector.submitModel(this.model, state, poseStack, RenderTypes.eyes(GRENADE_EMISSIVE_LOCATION), state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor, null);
        poseStack.popPose();

        super.submit(state, poseStack, nodeCollector, cameraState);
    }

    @Override
    public boolean shouldRender(GrenadeProjectileEntity pLivingEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        return true;
    }

    @Override
    public ProjectileRenderState createRenderState() {
        return new ProjectileRenderState();
    }

    @Override
    public void extractRenderState(GrenadeProjectileEntity entity, ProjectileRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        if (!entity.hasStopped) {
            RandomSource random = RandomSource.create(entity.getId());
            float tumbleSpeed = -25f;
            entity.lastTumbleX = state.ageInTicks * (tumbleSpeed + random.nextFloat() * (-tumbleSpeed * 2));
            entity.lastTumbleY = state.ageInTicks * (tumbleSpeed + random.nextFloat() * (-tumbleSpeed * 2));
            entity.lastTumbleZ = state.ageInTicks * (tumbleSpeed + random.nextFloat() * (-tumbleSpeed * 2));
        }
        state.xRot = Mth.lerp(partialTick, entity.xRotO, entity.getXRot()) + entity.lastTumbleX;
        state.yRot = Mth.lerp(partialTick, entity.yRotO, entity.getYRot()) + 180f + entity.lastTumbleY;
        state.zRot = entity.lastTumbleZ;
    }
}