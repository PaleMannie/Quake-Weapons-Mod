package mett.palemannie.quakeweapons.entity.client;

import mett.palemannie.quakeweapons.entity.custom.RingofshadowsPowerupEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class RingPowerupRenderer<R extends LivingEntityRenderState & GeoRenderState> extends GeoEntityRenderer<RingofshadowsPowerupEntity, R> {
    public RingPowerupRenderer(EntityRendererProvider.Context context) {
        super(context, new RingPowerupModel());

        this.addRenderLayer(new AutoGlowingGeoLayer<>(this));
        this.withScale(2f);
    }
}