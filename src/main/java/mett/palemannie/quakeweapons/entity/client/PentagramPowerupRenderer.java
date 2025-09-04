package mett.palemannie.quakeweapons.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.entity.custom.PentagramPowerupEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class PentagramPowerupRenderer extends EntityRenderer<PentagramPowerupEntity> {

    private static final ResourceLocation PENTAGRAM_LOCATION = ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID,"textures/entity/pentagram_powerup/pentagram_powerup.png");
    private final PentagramPowerupModel<PentagramPowerupEntity> model;

    public PentagramPowerupRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new PentagramPowerupModel<>(context.bakeLayer(PentagramPowerupModel.PENTAGRAM_LAYER));
    }

    public void render(PentagramPowerupEntity rocketEntity, float v1, float v2, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {

        poseStack.pushPose();

        poseStack.translate(0.0F, 0.05f, 0.0F);

        poseStack.mulPose(Axis.YP.rotationDegrees(0f));
        poseStack.mulPose(Axis.XP.rotationDegrees(0f));
        poseStack.mulPose(Axis.ZP.rotationDegrees(0f));

        poseStack.scale(1f, 1f, 1f);

        VertexConsumer $$6 = bufferSource.getBuffer(this.model.renderType(PENTAGRAM_LOCATION));
        this.model.renderToBuffer(poseStack, $$6, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        poseStack.popPose();

        super.render(rocketEntity, v1, v2, poseStack, bufferSource, packedLight);
    }

    @Override
    public boolean shouldRender(PentagramPowerupEntity pLivingEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        return true;
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull PentagramPowerupEntity spit) { return PENTAGRAM_LOCATION; }

}