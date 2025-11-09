package mett.palemannie.quakeweapons.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.entity.custom.GrenadeProjectileEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.NotNull;

public class GrenadeProjectileRenderer extends EntityRenderer<GrenadeProjectileEntity> {

    private static final ResourceLocation GRENADE_LOCATION = ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID,"textures/entity/grenade_projectile/grenade_projectile.png");
    private static final ResourceLocation GRENADE_EMISSIVE_LOCATION = ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID,"textures/entity/grenade_projectile/grenade_projectile_glow.png");

    private final GrenadeProjectileModel<GrenadeProjectileEntity> model;

    public GrenadeProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new GrenadeProjectileModel<>(context.bakeLayer(GrenadeProjectileModel.GRENADE_LAYER));
    }

    public void render(GrenadeProjectileEntity grenadeEntity, float v1, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {

        poseStack.pushPose();
        poseStack.scale(0.2F, 0.2F, 0.2F);
        poseStack.translate(0.0F, 0.1f, 0.0F);

        RandomSource random = RandomSource.create(grenadeEntity.getId());

        if (!grenadeEntity.hasStopped) {

            float tumbleSpeed = -25.0F;
            float tumbleX = (grenadeEntity.tickCount + partialTicks) * (tumbleSpeed + (random.nextFloat() * (-tumbleSpeed * 2)));
            float tumbleY = (grenadeEntity.tickCount + partialTicks) * (tumbleSpeed + (random.nextFloat() * (-tumbleSpeed * 2)));
            float tumbleZ = (grenadeEntity.tickCount + partialTicks) * (tumbleSpeed + (random.nextFloat() * (-tumbleSpeed * 2)));

            grenadeEntity.lastTumbleX = tumbleX;
            grenadeEntity.lastTumbleY = tumbleY;
            grenadeEntity.lastTumbleZ = tumbleZ;

            poseStack.mulPose(Axis.XP.rotationDegrees((Mth.lerp(partialTicks, grenadeEntity.xRotO, grenadeEntity.getXRot())) + tumbleX));
            poseStack.mulPose(Axis.YP.rotationDegrees((Mth.lerp(partialTicks, grenadeEntity.yRotO, grenadeEntity.getYRot()) + 180f) + tumbleY));
            poseStack.mulPose(Axis.ZP.rotationDegrees(tumbleZ));

        } else {

            poseStack.mulPose(Axis.XP.rotationDegrees((Mth.lerp(partialTicks, grenadeEntity.xRotO, grenadeEntity.getXRot())) + grenadeEntity.lastTumbleX));
            poseStack.mulPose(Axis.YP.rotationDegrees((Mth.lerp(partialTicks, grenadeEntity.yRotO, grenadeEntity.getYRot()) + 180f) + grenadeEntity.lastTumbleY));
            poseStack.mulPose(Axis.ZP.rotationDegrees(grenadeEntity.lastTumbleZ));
        }

        VertexConsumer normal = bufferSource.getBuffer(RenderType.entityCutoutNoCull(GRENADE_LOCATION));
        this.model.renderToBuffer(poseStack, normal, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);

        VertexConsumer emissive = bufferSource.getBuffer(RenderType.eyes(GRENADE_EMISSIVE_LOCATION));
        this.model.renderToBuffer(poseStack, emissive, 0xF000F0, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        poseStack.popPose();

        super.render(grenadeEntity, v1, partialTicks, poseStack, bufferSource, packedLight);
    }

    @Override
    public boolean shouldRender(GrenadeProjectileEntity pLivingEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        return true;
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull GrenadeProjectileEntity spit) { return GRENADE_LOCATION; }

}