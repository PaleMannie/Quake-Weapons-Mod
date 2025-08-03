package mett.palemannie.quakeweapons.item.client;

import mett.palemannie.quakeweapons.item.custom.ShotgunItem;
import mett.palemannie.quakeweapons.item.custom.SuperShotgunItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class SuperShotgunRenderer extends GeoItemRenderer<SuperShotgunItem> {
    public SuperShotgunRenderer() {
        super(new SuperShotgunModel());
    }
}
