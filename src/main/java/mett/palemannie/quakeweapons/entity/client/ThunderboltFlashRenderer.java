package mett.palemannie.quakeweapons.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.entity.custom.HitscanMuzzleflashEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class ThunderboltFlashRenderer extends EntityRenderer<HitscanMuzzleflashEntity> {

    private static final ResourceLocation FLASH_LOCATION = ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID,"textures/entity/thunderbolt_flash/thunderbolt_flash.png");
    private final ThunderboltFlashModel<HitscanMuzzleflashEntity> model;

    public ThunderboltFlashRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new ThunderboltFlashModel<>(context.bakeLayer(ThunderboltFlashModel.FLASH_LAYER));
    }

    public void render(HitscanMuzzleflashEntity nailEntity, float v1, float v2, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {


    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull HitscanMuzzleflashEntity spit) { return FLASH_LOCATION; }

}