package net.chococraft.client.gui;

import net.chococraft.Chococraft;
import net.chococraft.ChococraftExpectPlatform;
import net.chococraft.common.entity.AbstractChocobo;
import net.chococraft.common.inventory.SaddleBagMenu;
import net.chococraft.registry.ModRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
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
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		renderTooltip(guiGraphics, mouseX, mouseY);
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int x, int y) {
		ResourceLocation texture = INV_TEXTURE_NULL;
		ItemStack saddleStack = menu.getSlot(0).getItem();
		if (!saddleStack.isEmpty()) {
			Item item = saddleStack.getItem();
			if (item == ModRegistry.CHOCOBO_SADDLE.get()) {
				texture = INV_TEXTURE_NULL;
			} else if (item == ModRegistry.CHOCOBO_SADDLE_BAGS.get()) {
				texture = INV_TEXTURE_SMALL;
			} else if (item == ModRegistry.CHOCOBO_SADDLE_PACK.get()) {
				texture = INV_TEXTURE_LARGE;
			}
		} else {
			texture = INV_TEXTURE_NULL;
		}

		int i = (this.width - this.imageWidth) / 2;
		int j = (this.height - this.imageHeight) / 2;
		guiGraphics.blit(texture, i, j, 0, 0, this.imageWidth, this.imageHeight);
		guiGraphics.blit(texture, i - 24, j + 10, 0, 204, 27, 33);
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int x, int y) {
		guiGraphics.drawString(font, title, 8, 6, 0x888888, false);
		guiGraphics.drawString(font, this.playerInventoryTitle, 8, this.imageHeight - 96 + 2, 0x888888, false);
	}
}
