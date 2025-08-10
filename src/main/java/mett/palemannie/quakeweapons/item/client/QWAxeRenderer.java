package mett.palemannie.quakeweapons.item.client;

import mett.palemannie.quakeweapons.item.custom.QWAxeItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class QWAxeRenderer extends GeoItemRenderer<QWAxeItem> {
    public QWAxeRenderer() { super(new QWAxeModel());
    }
}
