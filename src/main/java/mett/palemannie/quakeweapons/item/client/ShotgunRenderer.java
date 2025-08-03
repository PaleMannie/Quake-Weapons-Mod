package mett.palemannie.quakeweapons.item.client;

import mett.palemannie.quakeweapons.item.custom.ShotgunItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class ShotgunRenderer extends GeoItemRenderer<ShotgunItem> {
    public ShotgunRenderer() {
        super(new ShotgunModel());
    }
}
