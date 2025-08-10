package mett.palemannie.quakeweapons.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.entity.custom.GrenadeProjectileEntity;
import mett.palemannie.quakeweapons.entity.custom.RocketProjectileEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class GrenadeProjectileRenderer extends EntityRenderer<GrenadeProjectileEntity> {

    private static final ResourceLocation GRENADE_LOCATION = ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID,"textures/entity/grenade_projectile/grenade_projectile.png");
    private final GrenadeProjectileModel<GrenadeProjectileEntity> model;

    public GrenadeProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new GrenadeProjectileModel<>(context.bakeLayer(GrenadeProjectileModel.GRENADE_LAYER));
    }

    public void render(GrenadeProjectileEntity nailEntity, float v1, float v2, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {


    }
    
    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull GrenadeProjectileEntity spit) { return GRENADE_LOCATION; }

}