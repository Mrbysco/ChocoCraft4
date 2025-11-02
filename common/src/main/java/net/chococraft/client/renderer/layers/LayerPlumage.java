package net.chococraft.client.renderer.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.chococraft.Chococraft;
import net.chococraft.client.renderer.states.ChocoboRenderState;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

public class LayerPlumage extends RenderLayer<ChocoboRenderState, EntityModel<ChocoboRenderState>> {

	private final ResourceLocation PLUMAGE = Chococraft.modLoc("textures/entities/chocobos/plumage.png");

	public LayerPlumage(RenderLayerParent<ChocoboRenderState, EntityModel<ChocoboRenderState>> rendererIn) {
		super(rendererIn);
	}

	@Override
	public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight, ChocoboRenderState renderState, float v, float v1) {
		if (!renderState.isInvisible && renderState.isMale && !renderState.isBaby) {
			coloredCutoutModelCopyLayerRender(this.getParentModel(), PLUMAGE,
					poseStack, submitNodeCollector, packedLight, renderState, -1, renderState.outlineColor);
		}
	}
}
