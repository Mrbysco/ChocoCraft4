package net.chococraft.client.renderer.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.chococraft.Chococraft;
import net.chococraft.client.renderer.states.ChocoboRenderState;
import net.chococraft.registry.ModRegistry;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class LayerSaddle extends RenderLayer<ChocoboRenderState, EntityModel<ChocoboRenderState>> {
	private final ResourceLocation SADDLE = Chococraft.modLoc("textures/entities/chocobos/saddle.png");
	private final ResourceLocation SADDLE_BAG = Chococraft.modLoc("textures/entities/chocobos/saddle_bag.png");
	private final ResourceLocation PACK_BAG = Chococraft.modLoc("textures/entities/chocobos/pack_bag.png");

	public LayerSaddle(RenderLayerParent<ChocoboRenderState, EntityModel<ChocoboRenderState>> rendererIn) {
		super(rendererIn);
	}

	@Override
	public void render(PoseStack poseStack, MultiBufferSource bufferSource, int i, ChocoboRenderState renderState, float f, float g) {
		if (!renderState.isInvisible && renderState.isSaddled && !renderState.isBaby) {
			ResourceLocation saddleTexture = null;

			ItemStack saddleStack = renderState.saddle;
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

			renderColoredCutoutModel(this.getParentModel(), saddleTexture, poseStack, bufferSource, i, renderState, -1);
		}
	}
}
