package mett.palemannie.quakeweapons.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.entity.custom.PentagramPowerupEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class PentagramPowerupRenderer extends EntityRenderer<PentagramPowerupEntity, EntityRenderState> {

    private static final ResourceLocation PENTAGRAM_LOCATION = ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID,"textures/entity/pentagram_powerup/pentagram_powerup.png");
    private final PentagramPowerupModel model;

    public PentagramPowerupRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new PentagramPowerupModel(context.bakeLayer(PentagramPowerupModel.PENTAGRAM_LAYER));
    }

    float bobbingSpeed = 0.05f;
    float bobbingHeight = 0.1f;
    float rotationSpeed = 5f;

    public void render(EntityRenderState state, float v1, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {

        poseStack.pushPose();

        poseStack.translate(0.0F, 0.0f, 0.0F);
        poseStack.scale(2f, 2f, 2f);

        float ageInTicks = state.ageInTicks + partialTicks;

        double bob = Math.sin(ageInTicks * bobbingSpeed) * bobbingHeight;
        poseStack.translate(0.0D, 0.25D + bob, 0.0D);

        float rotation = (ageInTicks * rotationSpeed) % 360;
        poseStack.mulPose(Axis.YP.rotationDegrees(rotation));

        VertexConsumer $$6 = bufferSource.getBuffer(this.model.renderType(PENTAGRAM_LOCATION));
        this.model.renderToBuffer(poseStack, $$6, packedLight, OverlayTexture.NO_OVERLAY);

        VertexConsumer $$5 = bufferSource.getBuffer(RenderType.eyes(PENTAGRAM_LOCATION));
        this.model.renderToBuffer(poseStack, $$6, packedLight, OverlayTexture.NO_OVERLAY);

        poseStack.popPose();

        super.render(state, poseStack, bufferSource, packedLight);
    }

    @Override
    public boolean shouldRender(PentagramPowerupEntity pLivingEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        return true;
    }

    @Override
    public EntityRenderState createRenderState() {
        return new EntityRenderState();
    }

    @Override
    public void extractRenderState(PentagramPowerupEntity pEntity, EntityRenderState pReusedState, float pPartialTick) {
        super.extractRenderState(pEntity, pReusedState, pPartialTick);
    }

    /*@Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull PentagramPowerupEntity spit) { return PENTAGRAM_LOCATION; }*/

}