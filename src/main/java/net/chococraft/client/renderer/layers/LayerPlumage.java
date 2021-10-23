package net.chococraft.client.renderer.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.chococraft.Chococraft;
import net.chococraft.client.models.entities.AdultChocoboModel;
import net.chococraft.common.entities.ChocoboEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

public class LayerPlumage extends LayerRenderer<ChocoboEntity, AdultChocoboModel> {

	private ResourceLocation PLUMAGE = new ResourceLocation(Chococraft.MODID,"textures/entities/chocobos/plumage.png");
	
	public LayerPlumage(IEntityRenderer<ChocoboEntity, AdultChocoboModel> rendererIn) {
		super(rendererIn);
	}


	@Override
	public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, ChocoboEntity chocoboEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		if (chocoboEntity.isTame() && !chocoboEntity.isInvisible()) {
			renderColoredCutoutModel(this.getParentModel(), PLUMAGE, matrixStackIn, bufferIn, packedLightIn, chocoboEntity, 1.0F, 1.0F, 1.0F);
		}
	}
}
