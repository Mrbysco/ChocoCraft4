package net.chococraft.fabric.mixins;

import net.chococraft.fabric.client.layer.ChocodisguiseArmorLayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>> extends EntityRenderer<T> implements RenderLayerParent<T, M> {

	@Shadow
	@Final
	protected List<RenderLayer<T, M>> layers;

	protected LivingEntityRendererMixin(EntityRendererProvider.Context context) {
		super(context);
	}

	@Inject(method = "addLayer(Lnet/minecraft/client/renderer/entity/layers/RenderLayer;)Z",
			at = @At("RETURN"))
	public final void chococraft_addLayer(RenderLayer<T, M> renderLayer, CallbackInfoReturnable<Boolean> cir) {
		if (renderLayer instanceof HumanoidArmorLayer<?, ?, ?>) {
			LivingEntityRenderer entityRenderer = (LivingEntityRenderer) (Object) this;
			this.layers.add(new ChocodisguiseArmorLayer<>(entityRenderer, Minecraft.getInstance().getEntityModels()));
		}
	}

}
