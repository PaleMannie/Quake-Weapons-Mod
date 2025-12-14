package mett.palemannie.quakeweapons.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.entity.custom.MuzzleflashEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class MuzzleflashRenderer extends EntityRenderer<MuzzleflashEntity, EntityRenderState> {

    private static final ResourceLocation FLASH_LOCATION = ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID,"textures/entity/muzzleflash/muzzleflash.png");
    private final MuzzleflashModel model;

    public MuzzleflashRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new MuzzleflashModel(context.bakeLayer(MuzzleflashModel.FLASH_LAYER));
    }

    @Override
    public EntityRenderState createRenderState() {
        return new EntityRenderState();
    }

    public void render(MuzzleflashEntity nailEntity, float v1, float v2, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
    }

    /*@Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull MuzzleflashEntity spit) { return FLASH_LOCATION; }*/

}