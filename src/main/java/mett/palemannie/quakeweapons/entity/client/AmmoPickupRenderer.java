package mett.palemannie.quakeweapons.entity.client;

import mett.palemannie.quakeweapons.entity.custom.AbstractAmmoPickupEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

/** Temporary renderer until the ammo pickup models and textures are supplied. */
public class AmmoPickupRenderer<T extends AbstractAmmoPickupEntity> extends EntityRenderer<T> {
    public AmmoPickupRenderer(EntityRendererProvider.Context context) { super(context); }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return ResourceLocation.fromNamespaceAndPath("minecraft", "textures/item/barrier.png");
    }
}
