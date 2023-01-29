package net.chococraft.client.renderer.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.chococraft.Chococraft;
import net.chococraft.common.entity.AbstractChocobo;
import net.chococraft.registry.ModRegistry;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class LayerSaddle extends RenderLayer<AbstractChocobo, EntityModel<AbstractChocobo>> {
	private final ResourceLocation SADDLE = new ResourceLocation(Chococraft.MOD_ID, "textures/entities/chocobos/saddle.png");
	private final ResourceLocation SADDLE_BAG = new ResourceLocation(Chococraft.MOD_ID, "textures/entities/chocobos/saddle_bag.png");
	private final ResourceLocation PACK_BAG = new ResourceLocation(Chococraft.MOD_ID, "textures/entities/chocobos/pack_bag.png");

	public LayerSaddle(RenderLayerParent<AbstractChocobo, EntityModel<AbstractChocobo>> rendererIn) {
		super(rendererIn);
	}

	@Override
	public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLightIn, AbstractChocobo chocoboEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		if (!chocoboEntity.isInvisible() && chocoboEntity.isSaddled() && !chocoboEntity.isBaby()) {
			ResourceLocation saddleTexture = null;

			ItemStack saddleStack = chocoboEntity.getSaddle();
			if (!saddleStack.isEmpty()) {
				Item item = saddleStack.getItem();
				if (item == ModRegistry.CHOCOBO_SADDLE.get()) {
					saddleTexture = SADDLE;
				} else if (item == ModRegistry.CHOCOBO_SADDLE_BAGS.get()) {
					saddleTexture = SADDLE_BAG;
				} else if (item == ModRegistry.CHOCOBO_SADDLE_PACK.get()) {
					saddleTexture = PACK_BAG;
				}
			}

			renderColoredCutoutModel(this.getParentModel(), saddleTexture, poseStack, bufferSource, packedLightIn, chocoboEntity, 1.0F, 1.0F, 1.0F);
		}
	}
}
