package mett.palemannie.quakeweapons.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.entity.custom.ThunderboltFlashEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class ThunderboltFlashRenderer extends EntityRenderer<ThunderboltFlashEntity> {

    private static final ResourceLocation FLASH_LOCATION = ResourceLocation.fromNamespaceAndPath(QuakeWeapons.MODID,"textures/entity/thunderbolt_flash/thunderbolt_flash.png");
    private final ThunderboltFlashModel<ThunderboltFlashEntity> model;

    public ThunderboltFlashRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new ThunderboltFlashModel<>(context.bakeLayer(ThunderboltFlashModel.FLASH_LAYER));
    }

    public void render(ThunderboltFlashEntity nailEntity, float v1, float v2, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {


    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull ThunderboltFlashEntity spit) { return FLASH_LOCATION; }

}