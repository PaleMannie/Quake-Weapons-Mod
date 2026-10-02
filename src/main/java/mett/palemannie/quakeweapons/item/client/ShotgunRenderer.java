package mett.palemannie.quakeweapons.item.client;

import mett.palemannie.quakeweapons.item.custom.ShotgunItem;
import com.geckolib.renderer.GeoItemRenderer;

public class ShotgunRenderer extends GeoItemRenderer<ShotgunItem> {
    public ShotgunRenderer() {
        super(new ShotgunModel());
    }
}
