package mett.palemannie.quakeweapons.item.client;

import mett.palemannie.quakeweapons.item.custom.SuperNailgunItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class SuperNailGunRenderer extends GeoItemRenderer<SuperNailgunItem> {
    public SuperNailGunRenderer() {
        super(new SuperNailGunModel());
    }
}
