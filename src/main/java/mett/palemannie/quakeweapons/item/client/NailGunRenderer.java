package mett.palemannie.quakeweapons.item.client;

import mett.palemannie.quakeweapons.item.custom.NailgunItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class NailGunRenderer extends GeoItemRenderer<NailgunItem> {
    public NailGunRenderer() {
        super(new NailGunModel());
    }
}