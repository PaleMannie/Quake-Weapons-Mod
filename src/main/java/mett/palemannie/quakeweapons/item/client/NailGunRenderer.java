package mett.palemannie.quakeweapons.item.client;

import mett.palemannie.quakeweapons.item.custom.NailGunItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class NailGunRenderer extends GeoItemRenderer<NailGunItem> {
    public NailGunRenderer() {
        super(new NailGunModel());
    }
}