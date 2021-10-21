package net.chococraft.client.renderer.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.chococraft.Chococraft;
import net.chococraft.client.models.entities.AdultChocoboModel;
import net.chococraft.common.entities.ChocoboEntity;
import net.chococraft.common.init.ModRegistry;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class LayerSaddle extends LayerRenderer<ChocoboEntity, AdultChocoboModel<ChocoboEntity>> {
	private ResourceLocation SADDLE = new ResourceLocation(Chococraft.MODID,"textures/entities/chocobos/saddle.png");
	private ResourceLocation SADDLE_BAG = new ResourceLocation(Chococraft.MODID,"textures/entities/chocobos/saddle_bag.png");
	private ResourceLocation PACK_BAG = new ResourceLocation(Chococraft.MODID,"textures/entities/chocobos/pack_bag.png");
	
	public LayerSaddle(IEntityRenderer<ChocoboEntity, AdultChocoboModel<ChocoboEntity>> rendererIn) {
		super(rendererIn);
	}

	@Override
	public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, ChocoboEntity chocoboEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		if(!chocoboEntity.isInvisible() && chocoboEntity.isSaddled()) {
			ResourceLocation saddleTexture = null;

			ItemStack saddleStack = chocoboEntity.getSaddle();
			if(!saddleStack.isEmpty()){
				Item item = saddleStack.getItem();
				if(item == ModRegistry.CHOCOBO_SADDLE.get()) {
					saddleTexture = SADDLE;
				} else if(item == ModRegistry.CHOCOBO_SADDLE_BAGS.get()) {
					saddleTexture = SADDLE_BAG;
				} else if(item == ModRegistry.CHOCOBO_SADDLE_PACK.get()) {
					saddleTexture = PACK_BAG;
				}
			}

			renderColoredCutoutModel(this.getParentModel(), saddleTexture, matrixStackIn, bufferIn, packedLightIn, chocoboEntity, 1.0F, 1.0F, 1.0F);
		}
	}
}
