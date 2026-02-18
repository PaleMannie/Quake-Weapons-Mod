package mett.palemannie.quakeweapons.entity.client;

import mett.palemannie.quakeweapons.entity.custom.QuadDamagePowerupEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.renderer.layer.builtin.AutoGlowingGeoLayer;

public class QuaddamagePowerupRenderer<R extends LivingEntityRenderState & GeoRenderState> extends GeoEntityRenderer<QuadDamagePowerupEntity, R> {
    public QuaddamagePowerupRenderer(EntityRendererProvider.Context context) {
        super(context, new QuadDamagePowerupModel());

        this.addRenderLayer(new AutoGlowingGeoLayer<>(this));
        this.withScale(0.5f);
    }

    private void addRenderLayer(AutoGlowingGeoLayer<QuadDamagePowerupEntity, Void,R> biosuitPowerupEntityVoidRAutoGlowingGeoLayer) {
    }
}