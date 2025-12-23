package mett.palemannie.quakeweapons.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.entity.custom.NailProjectileEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.LlamaSpitRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class NailProjectileRenderer extends EntityRenderer<NailProjectileEntity, LlamaSpitRenderState> {

    private static final ResourceLocation NAIL_LOCATION = ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID,"textures/entity/nail_projectile/nail_projectile.png");
    private final NailProjectileModel model;

    public NailProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new NailProjectileModel(context.bakeLayer(NailProjectileModel.NAIL_LAYER));
    }

    public void render(LlamaSpitRenderState pRenderState, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight) {

        pPoseStack.pushPose();

        pPoseStack.translate(0f, 0.1f, 0f);

        pPoseStack.mulPose(Axis.YP.rotationDegrees(pRenderState.yRot));
        pPoseStack.mulPose(Axis.XP.rotationDegrees(-pRenderState.xRot));

        this.model.setupAnim(pRenderState);
        VertexConsumer vertexconsumer = pBufferSource.getBuffer(this.model.renderType(NAIL_LOCATION));
        this.model.renderToBuffer(pPoseStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY);
        pPoseStack.popPose();

        super.render(pRenderState, pPoseStack, pBufferSource, pPackedLight);
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

    /*@Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull NailProjectileEntity spit) { return NAIL_LOCATION; }*/

}