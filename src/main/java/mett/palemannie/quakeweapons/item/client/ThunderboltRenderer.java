package mett.palemannie.quakeweapons.item.client;

import mett.palemannie.quakeweapons.item.custom.ThunderboltItem;
import com.geckolib.renderer.GeoItemRenderer;

public class ThunderboltRenderer extends GeoItemRenderer<ThunderboltItem> {
    public ThunderboltRenderer() {
        super(new ThunderboltModel());
    }
}
