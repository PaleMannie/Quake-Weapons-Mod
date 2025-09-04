package mett.palemannie.quakeweapons.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.entity.custom.QuadDamagePowerupEntity;
import mett.palemannie.quakeweapons.entity.custom.RocketProjectileEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class QuaddamagePowerupRenderer extends EntityRenderer<QuadDamagePowerupEntity> {

    private static final ResourceLocation QUAD_LOCATION = ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID,"textures/entity/quad_damage_powerup/quad_damage_powerup.png");
    private final QuadDamagePowerupModel<QuadDamagePowerupEntity> model;

    public QuaddamagePowerupRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new QuadDamagePowerupModel<>(context.bakeLayer(QuadDamagePowerupModel.QUAD_LAYER));
    }

    public void render(QuadDamagePowerupEntity rocketEntity, float v1, float v2, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {

        poseStack.pushPose();

        poseStack.translate(0.0F, 0.05f, 0.0F);

        poseStack.mulPose(Axis.YP.rotationDegrees(0f));
        poseStack.mulPose(Axis.XP.rotationDegrees(0f));
        poseStack.mulPose(Axis.ZP.rotationDegrees(0f));

        poseStack.scale(1f, 1f, 1f);

        VertexConsumer $$6 = bufferSource.getBuffer(this.model.renderType(QUAD_LOCATION));
        this.model.renderToBuffer(poseStack, $$6, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        poseStack.popPose();

        super.render(rocketEntity, v1, v2, poseStack, bufferSource, packedLight);
    }

    @Override
    public boolean shouldRender(QuadDamagePowerupEntity pLivingEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        return true;
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull QuadDamagePowerupEntity spit) { return QUAD_LOCATION; }

}