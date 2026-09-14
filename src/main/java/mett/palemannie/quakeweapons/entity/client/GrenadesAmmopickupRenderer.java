package mett.palemannie.quakeweapons.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.entity.custom.GrenadesAmmopickupEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class GrenadesAmmopickupRenderer extends EntityRenderer<GrenadesAmmopickupEntity> {

    private static final ResourceLocation GRENADES_AMMOPICKUP_LOCATION = ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID,"textures/entity/ammopickups/grenades_ammopickup.png");
    private final GrenadesAmmopickupModel<GrenadesAmmopickupEntity> model;

    public GrenadesAmmopickupRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new GrenadesAmmopickupModel<>(context.bakeLayer(GrenadesAmmopickupModel.GRENADES_AMMOPICKUP_LAYER));
    }

    float bobbingSpeed = 0.05f;
    float bobbingHeight = 0.1f;
    float rotationSpeed = 5f;

    public void render(GrenadesAmmopickupEntity nailEntity, float v1, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {

        poseStack.pushPose();

        poseStack.translate(0.0F, 2f, 0.0F);
        poseStack.scale(1f, 1f, 1f);

        poseStack.mulPose(Axis.XP.rotationDegrees(Mth.lerp(partialTicks, nailEntity.xRotO, nailEntity.getXRot()) + 180f));

        float ageInTicks = nailEntity.tickCount + partialTicks;

        double bob = Math.sin(ageInTicks * bobbingSpeed) * bobbingHeight;
        poseStack.translate(0.0D, 0.25D + bob, 0.0D);

        float rotation = (ageInTicks * rotationSpeed) % 360;
        poseStack.mulPose(Axis.YP.rotationDegrees(rotation));

        VertexConsumer $$6 = bufferSource.getBuffer(this.model.renderType(GRENADES_AMMOPICKUP_LOCATION));
        this.model.renderToBuffer(poseStack, $$6, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        VertexConsumer $$5 = bufferSource.getBuffer(RenderType.eyes(GRENADES_AMMOPICKUP_LOCATION));
        this.model.renderToBuffer(poseStack, $$6, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        poseStack.popPose();

        super.render(nailEntity, v1, partialTicks, poseStack, bufferSource, packedLight);
    }

    @Override
    public boolean shouldRender(GrenadesAmmopickupEntity pLivingEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        return true;
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull GrenadesAmmopickupEntity spit) { return GRENADES_AMMOPICKUP_LOCATION; }

}