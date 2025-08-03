package mett.palemannie.quakeweapons.item.client;

import mett.palemannie.quakeweapons.item.custom.ShotGunItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class ShotgunRenderer extends GeoItemRenderer<ShotGunItem> {
    public ShotgunRenderer() {
        super(new ShotgunModel());
    }
}
