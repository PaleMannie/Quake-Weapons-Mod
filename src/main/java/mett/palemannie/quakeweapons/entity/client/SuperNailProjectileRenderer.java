package mett.palemannie.quakeweapons.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.entity.custom.SuperNailProjectileEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LlamaSpitRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class SuperNailProjectileRenderer extends EntityRenderer<SuperNailProjectileEntity, LlamaSpitRenderState> {

    private static final ResourceLocation SUPER_NAIL_LOCATION = ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID,"textures/entity/super_nail_projectile/super_nail_projectile.png");
    private final SuperNailProjectileModel model;

    public SuperNailProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new SuperNailProjectileModel(context.bakeLayer(SuperNailProjectileModel.SUPER_NAIL_LAYER));
    }

    public void render(LlamaSpitRenderState pRenderState, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight) {

        pPoseStack.pushPose();

        pPoseStack.translate(0f, 0.1f, 0f);

        pPoseStack.mulPose(Axis.YP.rotationDegrees(pRenderState.yRot));
        pPoseStack.mulPose(Axis.XP.rotationDegrees(-pRenderState.xRot + 180f));
        pPoseStack.mulPose(Axis.ZP.rotationDegrees(180f));

        VertexConsumer vertexconsumer = pBufferSource.getBuffer(this.model.renderType(SUPER_NAIL_LOCATION));
        this.model.renderToBuffer(pPoseStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY);
        pPoseStack.popPose();

        super.render(pRenderState, pPoseStack, pBufferSource, pPackedLight);
    }

    @Override
    public boolean shouldRender(SuperNailProjectileEntity pLivingEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        return true;
    }

    @Override
    public LlamaSpitRenderState createRenderState() {
        return new LlamaSpitRenderState();
    }

    public void extractRenderState(SuperNailProjectileEntity pEntity, LlamaSpitRenderState renderState, float pPartialTick) {
        super.extractRenderState(pEntity, renderState, pPartialTick);
        renderState.xRot = pEntity.getXRot(pPartialTick);
        renderState.yRot = pEntity.getYRot(pPartialTick);
    }
}