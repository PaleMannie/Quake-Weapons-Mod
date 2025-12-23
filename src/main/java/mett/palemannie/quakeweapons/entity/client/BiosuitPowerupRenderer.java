package mett.palemannie.quakeweapons.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.entity.custom.BiosuitPowerupEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class BiosuitPowerupRenderer extends EntityRenderer<BiosuitPowerupEntity, EntityRenderState> {

    private static final ResourceLocation BIOSUIT_LOCATION = ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID, "textures/entity/biosuit_powerup/biosuit_powerup.png");
    private final BiosuitPowerupModel model;

    public BiosuitPowerupRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new BiosuitPowerupModel(context.bakeLayer(BiosuitPowerupModel.BIOSUIT_LAYER));
    }

    float bobbingSpeed = 0.05f;
    float bobbingHeight = 0.2f;
    float rotationSpeed = 5f;

    public void render(EntityRenderState state, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {

        poseStack.pushPose();

        poseStack.translate(0.0F, 2f, 0.0F);
        poseStack.scale(1f, 1f, 1f);
        poseStack.mulPose(Axis.XP.rotationDegrees(180f));

        float ageInTicks = state.ageInTicks;

        double bob = Math.sin(ageInTicks * bobbingSpeed) * bobbingHeight;
        poseStack.translate(0.0D, 0.25D + bob, 0.0D);

        float rotation = (ageInTicks * rotationSpeed) % 360;
        poseStack.mulPose(Axis.YN.rotationDegrees(rotation));

        VertexConsumer $$6 = bufferSource.getBuffer(this.model.renderType(BIOSUIT_LOCATION));
        this.model.renderToBuffer(poseStack, $$6, packedLight, OverlayTexture.NO_OVERLAY);

        VertexConsumer $$5 = bufferSource.getBuffer(RenderType.eyes(BIOSUIT_LOCATION));
        this.model.renderToBuffer(poseStack, $$6, packedLight, OverlayTexture.NO_OVERLAY);

        poseStack.popPose();

        super.render(state, poseStack, bufferSource, packedLight);
    }

    @Override
    public boolean shouldRender(BiosuitPowerupEntity pLivingEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        return true;
    }

    @Override
    public EntityRenderState createRenderState() {
        return new EntityRenderState();
    }

    @Override
    public void extractRenderState(BiosuitPowerupEntity pEntity, EntityRenderState pReusedState, float pPartialTick) {
        super.extractRenderState(pEntity, pReusedState, pPartialTick);
    }

    /*@Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull BiosuitPowerupEntity spit) { return BIOSUIT_LOCATION; }*/

}