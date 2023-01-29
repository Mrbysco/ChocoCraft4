package net.chococraft.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.chococraft.Chococraft;
import net.chococraft.ChococraftExpectPlatform;
import net.chococraft.common.entity.AbstractChocobo;
import net.chococraft.common.inventory.SaddleBagMenu;
import net.chococraft.registry.ModRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ChocoboInventoryScreen extends AbstractContainerScreen<SaddleBagMenu> {
	private static final ResourceLocation INV_TEXTURE_NULL = new ResourceLocation(Chococraft.MOD_ID, "textures/gui/chocobo_inventory_null.png");
	private static final ResourceLocation INV_TEXTURE_SMALL = new ResourceLocation(Chococraft.MOD_ID, "textures/gui/chocobo_inventory_small.png");
	private static final ResourceLocation INV_TEXTURE_LARGE = new ResourceLocation(Chococraft.MOD_ID, "textures/gui/chocobo_inventory_large.png");


	public ChocoboInventoryScreen(SaddleBagMenu container, Inventory playerInventory, Component title) {
		super(container, playerInventory, title);

		this.imageWidth = 176;
		this.imageHeight = 204;
	}

	public static void openInventory(int windowId, AbstractChocobo chocobo) {
		Player player = Minecraft.getInstance().player;
		SaddleBagMenu saddleContainer = ChococraftExpectPlatform.constructMenu(windowId, player.getInventory(), chocobo);
		player.containerMenu = saddleContainer;
		Minecraft.getInstance().setScreen(new ChocoboInventoryScreen(saddleContainer, player.getInventory(), chocobo.getDisplayName()));
	}

	@Override
	public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		renderBackground(poseStack);
		super.render(poseStack, mouseX, mouseY, partialTicks);
		renderTooltip(poseStack, mouseX, mouseY);
	}

	@Override
	protected void renderBg(PoseStack poseStack, float partialTicks, int x, int y) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

		ItemStack saddleStack = menu.getSlot(0).getItem();
		if (!saddleStack.isEmpty()) {
			Item item = saddleStack.getItem();
			if (item == ModRegistry.CHOCOBO_SADDLE.get()) {
				RenderSystem.setShaderTexture(0, INV_TEXTURE_NULL);
			} else if (item == ModRegistry.CHOCOBO_SADDLE_BAGS.get()) {
				RenderSystem.setShaderTexture(0, INV_TEXTURE_SMALL);
			} else if (item == ModRegistry.CHOCOBO_SADDLE_PACK.get()) {
				RenderSystem.setShaderTexture(0, INV_TEXTURE_LARGE);
			}
		} else {
			RenderSystem.setShaderTexture(0, INV_TEXTURE_NULL);
		}

		int i = (this.width - this.imageWidth) / 2;
		int j = (this.height - this.imageHeight) / 2;
		this.blit(poseStack, i, j, 0, 0, this.imageWidth, this.imageHeight);
		this.blit(poseStack, i - 24, j + 10, 0, 204, 27, 33);
	}

	@Override
	protected void renderLabels(PoseStack poseStack, int x, int y) {
		this.font.draw(poseStack, title, 8, 6, 0x888888);
		this.font.draw(poseStack, this.playerInventoryTitle, 8, this.imageHeight - 96 + 2, 0x888888);
	}
}
