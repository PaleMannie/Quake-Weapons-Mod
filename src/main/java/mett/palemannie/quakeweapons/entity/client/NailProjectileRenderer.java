package mett.palemannie.quakeweapons.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.entity.custom.NailProjectileEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class NailProjectileRenderer extends EntityRenderer<NailProjectileEntity> {

    private static final ResourceLocation NAIL_LOCATION = ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID,"textures/entity/nail_projectile/nail_projectile.png");
    private final NailProjectileModel<NailProjectileEntity> model;

    public NailProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new NailProjectileModel<>(context.bakeLayer(NailProjectileModel.NAIL_LAYER));
    }

    public void render(NailProjectileEntity nailEntity, float v1, float v2, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {

        poseStack.pushPose();

        poseStack.translate(0.0F, 0.05f, 0.0F);

        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(v2, nailEntity.yRotO, nailEntity.getYRot()) + 180f));
        poseStack.mulPose(Axis.XP.rotationDegrees(Mth.lerp(v2, nailEntity.xRotO, nailEntity.getXRot()) ));

        poseStack.scale(0.75F, 0.75F, 0.75F);

        this.model.setupAnim(nailEntity, v2, 0.0F, -0.1F, 0.0F, 0.0F);
        VertexConsumer $$6 = bufferSource.getBuffer(this.model.renderType(NAIL_LOCATION));
        this.model.renderToBuffer(poseStack, $$6, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();

        super.render(nailEntity, v1, v2, poseStack, bufferSource, packedLight);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull NailProjectileEntity spit) { return NAIL_LOCATION; }

}