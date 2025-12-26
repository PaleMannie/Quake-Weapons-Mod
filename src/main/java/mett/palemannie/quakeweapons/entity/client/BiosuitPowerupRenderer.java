package mett.palemannie.quakeweapons.entity.client;

import mett.palemannie.quakeweapons.entity.custom.BiosuitPowerupEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class BiosuitPowerupRenderer<R extends LivingEntityRenderState & GeoRenderState> extends GeoEntityRenderer<BiosuitPowerupEntity, R>{
    public BiosuitPowerupRenderer(EntityRendererProvider.Context context) {
        super(context, new BiosuitPowerupModel());

        this.addRenderLayer(new AutoGlowingGeoLayer<>(this));
        this.shadowRadius = 0.5f;
    }
}