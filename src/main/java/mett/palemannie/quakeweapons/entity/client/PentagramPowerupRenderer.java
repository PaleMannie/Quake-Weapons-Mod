package mett.palemannie.quakeweapons.entity.client;

import mett.palemannie.quakeweapons.entity.custom.PentagramPowerupEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.renderer.layer.builtin.AutoGlowingGeoLayer;

public class PentagramPowerupRenderer<R extends LivingEntityRenderState & GeoRenderState> extends GeoEntityRenderer<PentagramPowerupEntity, R> {
    public PentagramPowerupRenderer(EntityRendererProvider.Context context) {
        super(context, new PentagramPowerupModel());

        this.addRenderLayer(new AutoGlowingGeoLayer<>(this));
        this.withScale(2f);
    }

    private void addRenderLayer(AutoGlowingGeoLayer<PentagramPowerupEntity, Void,R> biosuitPowerupEntityVoidRAutoGlowingGeoLayer) {
    }
}