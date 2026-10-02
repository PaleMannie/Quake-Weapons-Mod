package mett.palemannie.quakeweapons.item.client;

import mett.palemannie.quakeweapons.item.custom.SuperShotgunItem;
import com.geckolib.renderer.GeoItemRenderer;

public class SuperShotgunRenderer extends GeoItemRenderer<SuperShotgunItem> {
    public SuperShotgunRenderer() {
        super(new SuperShotgunModel());
    }
}
