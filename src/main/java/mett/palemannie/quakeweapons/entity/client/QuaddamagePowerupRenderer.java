package mett.palemannie.quakeweapons.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import mett.palemannie.quakeweapons.QuakeWeapons;
import mett.palemannie.quakeweapons.entity.custom.QuadDamagePowerupEntity;
import mett.palemannie.quakeweapons.entity.custom.RingofshadowsPowerupEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class QuaddamagePowerupRenderer<R extends LivingEntityRenderState & GeoRenderState> extends GeoEntityRenderer<QuadDamagePowerupEntity, R> {
    public QuaddamagePowerupRenderer(EntityRendererProvider.Context context) {
        super(context, new QuadDamagePowerupModel());

        this.addRenderLayer(new AutoGlowingGeoLayer<>(this));
        this.shadowRadius = 0.25f;
        this.withScale(0.5f);
    }
}