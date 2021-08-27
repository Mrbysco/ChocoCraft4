package net.chococraft.client.renderer.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.chococraft.Chococraft;
import net.chococraft.client.models.entities.AdultChocoboModel;
import net.chococraft.common.entities.ChocoboEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

public class LayerCollar extends LayerRenderer<ChocoboEntity, AdultChocoboModel<ChocoboEntity>> {
	private ResourceLocation COLLAR_CHOCOBO = new ResourceLocation(Chococraft.MODID,"textures/entities/chocobos/collar.png");
	private ResourceLocation COLLAR_CHICOBO = new ResourceLocation(Chococraft.MODID,"textures/entities/chicobos/collar.png");
	
	public LayerCollar(IEntityRenderer<ChocoboEntity, AdultChocoboModel<ChocoboEntity>> rendererIn) {
		super(rendererIn);
	}

	@Override
	public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, ChocoboEntity chocoboEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		if (chocoboEntity.isTamed() && !chocoboEntity.isInvisible()) {
			renderCutoutModel(this.getEntityModel(), chocoboEntity.isChild()? COLLAR_CHICOBO : COLLAR_CHOCOBO, matrixStackIn, bufferIn, packedLightIn, chocoboEntity, 1.0F, 1.0F, 1.0F);
		}
	}
}
