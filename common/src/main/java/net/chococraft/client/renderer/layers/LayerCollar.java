package net.chococraft.client.renderer.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.chococraft.Chococraft;
import net.chococraft.client.renderer.states.ChocoboRenderState;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

public class LayerCollar extends RenderLayer<ChocoboRenderState, EntityModel<ChocoboRenderState>> {
	private final ResourceLocation COLLAR_CHOCOBO = ResourceLocation.fromNamespaceAndPath(Chococraft.MOD_ID, "textures/entities/chocobos/collar.png");
	private final ResourceLocation COLLAR_CHICOBO = ResourceLocation.fromNamespaceAndPath(Chococraft.MOD_ID, "textures/entities/chicobos/collar.png");

	public LayerCollar(RenderLayerParent<ChocoboRenderState, EntityModel<ChocoboRenderState>> rendererIn) {
		super(rendererIn);
	}

	@Override
	public void render(PoseStack poseStack, MultiBufferSource bufferSource, int i, ChocoboRenderState renderState, float f, float g) {
		if (renderState.isTame && !renderState.isInvisible) {
			renderColoredCutoutModel(this.getParentModel(), renderState.isBaby ? COLLAR_CHICOBO : COLLAR_CHOCOBO, poseStack, bufferSource, i, renderState, -1);
		}
	}
}
