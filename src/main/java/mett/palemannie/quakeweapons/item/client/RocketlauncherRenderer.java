package mett.palemannie.quakeweapons.item.client;

import mett.palemannie.quakeweapons.item.custom.RocketlauncherItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class RocketlauncherRenderer extends GeoItemRenderer<RocketlauncherItem> {
    public RocketlauncherRenderer() {
        super(new RocketlauncherModel());
    }
}
