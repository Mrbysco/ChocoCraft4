package net.chococraft.client.renderer.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.chococraft.Chococraft;
import net.chococraft.client.renderer.states.ChocoboRenderState;
import net.chococraft.common.entity.AbstractChocobo;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

public class LayerPlumage extends RenderLayer<ChocoboRenderState, EntityModel<ChocoboRenderState>> {

	private final ResourceLocation PLUMAGE = ResourceLocation.fromNamespaceAndPath(Chococraft.MOD_ID, "textures/entities/chocobos/plumage.png");

	public LayerPlumage(RenderLayerParent<ChocoboRenderState, EntityModel<ChocoboRenderState>> rendererIn) {
		super(rendererIn);
	}

	@Override
	public void render(PoseStack poseStack, MultiBufferSource bufferSource, int i, ChocoboRenderState renderState, float f, float g) {
		if (!renderState.isInvisible && renderState.isMale && !renderState.isBaby) {
			renderColoredCutoutModel(this.getParentModel(), PLUMAGE, poseStack, bufferSource, i, renderState, -1);
		}
	}
}
