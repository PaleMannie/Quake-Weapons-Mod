package mett.palemannie.quakeweapons.item.client;

import mett.palemannie.quakeweapons.item.custom.GrenadelauncherItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class GrenadelauncherRenderer extends GeoItemRenderer<GrenadelauncherItem> {
    public GrenadelauncherRenderer() {
        super(new GrenadelauncherModel());
    }
}
