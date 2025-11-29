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
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class SuperNailProjectileRenderer extends EntityRenderer<SuperNailProjectileEntity> {

    private static final ResourceLocation SUPER_NAIL_LOCATION = new ResourceLocation(QuakeWeapons.MODID,"textures/entity/super_nail_projectile/super_nail_projectile.png");
    private final SuperNailProjectileModel<SuperNailProjectileEntity> model;

    public SuperNailProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new SuperNailProjectileModel<>(context.bakeLayer(SuperNailProjectileModel.SUPER_NAIL_LAYER));
    }

    public void render(SuperNailProjectileEntity nailEntity, float v1, float v2, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {

        poseStack.pushPose();

        poseStack.translate(0.0F, 0.05f, 0.0F);

        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(v2, nailEntity.yRotO, nailEntity.getYRot()) + 180f));
        poseStack.mulPose(Axis.XP.rotationDegrees(Mth.lerp(v2, nailEntity.xRotO, nailEntity.getXRot()) ));

        poseStack.scale(0.75F, 0.75F, 0.75F);

        this.model.setupAnim(nailEntity, v2, 0.0F, -0.1F, 0.0F, 0.0F);
        VertexConsumer $$6 = bufferSource.getBuffer(this.model.renderType(SUPER_NAIL_LOCATION));
        this.model.renderToBuffer(poseStack, $$6, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();

        super.render(nailEntity, v1, v2, poseStack, bufferSource, packedLight);
    }

    @Override
    public boolean shouldRender(SuperNailProjectileEntity pLivingEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        return true;
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull SuperNailProjectileEntity spit) { return SUPER_NAIL_LOCATION; }

}