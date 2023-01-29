package net.chococraft.client.renderer.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.chococraft.Chococraft;
import net.chococraft.common.entity.AbstractChocobo;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

public class LayerCollar extends RenderLayer<AbstractChocobo, EntityModel<AbstractChocobo>> {
	private final ResourceLocation COLLAR_CHOCOBO = new ResourceLocation(Chococraft.MOD_ID, "textures/entities/chocobos/collar.png");
	private final ResourceLocation COLLAR_CHICOBO = new ResourceLocation(Chococraft.MOD_ID, "textures/entities/chicobos/collar.png");

	public LayerCollar(RenderLayerParent<AbstractChocobo, EntityModel<AbstractChocobo>> rendererIn) {
		super(rendererIn);
	}

	@Override
	public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLightIn, AbstractChocobo chocoboEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		if (chocoboEntity.isTame() && !chocoboEntity.isInvisible()) {
			renderColoredCutoutModel(this.getParentModel(), chocoboEntity.isBaby() ? COLLAR_CHICOBO : COLLAR_CHOCOBO, poseStack, bufferSource, packedLightIn, chocoboEntity, 1.0F, 1.0F, 1.0F);
		}
	}
}
