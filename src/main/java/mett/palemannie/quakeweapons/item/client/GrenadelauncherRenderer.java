package mett.palemannie.quakeweapons.item.client;

import mett.palemannie.quakeweapons.item.custom.GrenadelauncherItem;
import com.geckolib.renderer.GeoItemRenderer;

public class GrenadelauncherRenderer extends GeoItemRenderer<GrenadelauncherItem> {
    public GrenadelauncherRenderer() {
        super(new GrenadelauncherModel());
    }
}
