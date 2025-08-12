package mett.palemannie.quakeweapons.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.entity.custom.GrenadeProjectileEntity;
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

public class GrenadeProjectileRenderer extends EntityRenderer<GrenadeProjectileEntity> {

    private static final ResourceLocation GRENADE_LOCATION = ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID,"textures/entity/grenade_projectile/grenade_projectile.png");
    private static final ResourceLocation GRENADE_EMISSIVE_LOCATION = ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID,"textures/entity/grenade_projectile/grenade_projectile_glow.png");

    private final GrenadeProjectileModel<GrenadeProjectileEntity> model;

    public GrenadeProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new GrenadeProjectileModel<>(context.bakeLayer(GrenadeProjectileModel.GRENADE_LAYER));
    }

    public void render(GrenadeProjectileEntity grenadeEntity, float v1, float v2, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {

        poseStack.pushPose();

        poseStack.translate(0.0F, 0.05f, 0.0F);

        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(v2, grenadeEntity.yRotO, grenadeEntity.getYRot()) + 180f));
        poseStack.mulPose(Axis.XP.rotationDegrees(Mth.lerp(v2, grenadeEntity.xRotO, grenadeEntity.getXRot()) ));
        poseStack.mulPose(Axis.ZP.rotationDegrees(45f));

        poseStack.scale(0.125F, 0.125F, 0.125F);

        VertexConsumer normal = bufferSource.getBuffer(RenderType.entityCutoutNoCull(GRENADE_LOCATION));
        this.model.renderToBuffer(poseStack, normal, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);

        VertexConsumer emissive = bufferSource.getBuffer(RenderType.eyes(GRENADE_EMISSIVE_LOCATION));
        this.model.renderToBuffer(poseStack, emissive, 0xF000F0, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        poseStack.popPose();

        super.render(grenadeEntity, v1, v2, poseStack, bufferSource, packedLight);
    }

    @Override
    public boolean shouldRender(GrenadeProjectileEntity pLivingEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        return true;
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull GrenadeProjectileEntity spit) { return GRENADE_LOCATION; }

}