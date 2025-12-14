package mett.palemannie.quakeweapons.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;
import net.minecraft.client.renderer.entity.layers.HorseArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.HorseRenderState;
import net.minecraft.world.entity.animal.horse.Horse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HorseArmorLayer.class)
public abstract class HorseArmorLayerMixin extends RenderLayer<HorseRenderState, HorseModel> {

    /// Horse armour should go invisible too while under ring of shadows effect

    @Unique
    private final HorseModel _adultModel;
    @Unique
    private final HorseModel _babyModel;
    @Unique
    private final EquipmentLayerRenderer _equipmentRenderer;

    public HorseArmorLayerMixin(RenderLayerParent<HorseRenderState, HorseModel> pRenderer, EntityModelSet pEntityModels, EquipmentLayerRenderer pEquipmentRenderer) {
        super(pRenderer);
        this._equipmentRenderer = pEquipmentRenderer;
        this._adultModel = new HorseModel(pEntityModels.bakeLayer(ModelLayers.HORSE_ARMOR));
        this._babyModel = new HorseModel(pEntityModels.bakeLayer(ModelLayers.HORSE_BABY_ARMOR));
    }

    /*@Inject(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/animal/horse/Horse;FFFFFF)V",
    at = @At("HEAD"), cancellable = true)
    private void qw$hideHorseArmor(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, Horse pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch, CallbackInfo ci){

        if(pLivingEntity.getPersistentData().getBoolean("QWInvis")){

            ci.cancel();
        }
    }*/

    @Inject(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/renderer/entity/state/HorseRenderState;FF)V",
            at = @At("HEAD"), cancellable = true)
    private void qw_hideArmourWhenInvisible(PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, HorseRenderState pRenderState, float pYRot, float pXRot, CallbackInfo ci){


    }

}
