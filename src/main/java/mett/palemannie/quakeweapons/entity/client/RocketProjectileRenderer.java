package mett.palemannie.quakeweapons.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.entity.custom.RocketProjectileEntity;
import mett.palemannie.quakeweapons.entity.custom.SuperNailProjectileEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class RocketProjectileRenderer extends EntityRenderer<RocketProjectileEntity> {

    private static final ResourceLocation ROCKET_LOCATION = ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID,"textures/entity/rocket_projectile/rocket_projectile.png");
    private final RocketProjectileModel<RocketProjectileEntity> model;

    public RocketProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new RocketProjectileModel<>(context.bakeLayer(RocketProjectileModel.ROCKET_LAYER));
    }

    public void render(RocketProjectileEntity nailEntity, float v1, float v2, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {


    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull RocketProjectileEntity spit) { return ROCKET_LOCATION; }

}