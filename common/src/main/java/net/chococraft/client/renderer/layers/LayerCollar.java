package net.chococraft.client.renderer.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.chococraft.Chococraft;
import net.chococraft.client.renderer.states.ChocoboRenderState;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.Identifier;

public class LayerCollar extends RenderLayer<ChocoboRenderState, EntityModel<ChocoboRenderState>> {
	private final Identifier COLLAR_CHOCOBO = Chococraft.modLoc("textures/entities/chocobos/collar.png");
	private final Identifier COLLAR_CHICOBO = Chococraft.modLoc("textures/entities/chicobos/collar.png");

	public LayerCollar(RenderLayerParent<ChocoboRenderState, EntityModel<ChocoboRenderState>> rendererIn) {
		super(rendererIn);
	}

	@Override
	public void submit(PoseStack poseStack, SubmitNodeCollector nodeCollector, int packedLight, ChocoboRenderState renderState, float v, float v1) {
		if (renderState.isTame) {
			coloredCutoutModelCopyLayerRender(this.getParentModel(), renderState.isBaby ? COLLAR_CHICOBO : COLLAR_CHOCOBO,
					poseStack, nodeCollector, packedLight, renderState, -1, renderState.outlineColor);
		}
	}
}
