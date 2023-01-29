package net.chococraft.client.renderer.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.chococraft.Chococraft;
import net.chococraft.common.entity.AbstractChocobo;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

public class LayerPlumage extends RenderLayer<AbstractChocobo, EntityModel<AbstractChocobo>> {

	private final ResourceLocation PLUMAGE = new ResourceLocation(Chococraft.MOD_ID, "textures/entities/chocobos/plumage.png");

	public LayerPlumage(RenderLayerParent<AbstractChocobo, EntityModel<AbstractChocobo>> rendererIn) {
		super(rendererIn);
	}

	@Override
	public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLightIn, AbstractChocobo chocoboEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		if (!chocoboEntity.isInvisible() && chocoboEntity.isMale() && !chocoboEntity.isBaby()) {
			renderColoredCutoutModel(this.getParentModel(), PLUMAGE, poseStack, bufferSource, packedLightIn, chocoboEntity, 1.0F, 1.0F, 1.0F);
		}
	}
}
