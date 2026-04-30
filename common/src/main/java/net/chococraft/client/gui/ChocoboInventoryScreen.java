package net.chococraft.client.gui;

import net.chococraft.Chococraft;
import net.chococraft.common.entity.AbstractChocobo;
import net.chococraft.common.inventory.SaddleBagMenu;
import net.chococraft.platform.Services;
import net.chococraft.registry.ModRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ChocoboInventoryScreen extends AbstractContainerScreen<SaddleBagMenu> {
	private static final Identifier INV_TEXTURE_NULL = Chococraft.modLoc("textures/gui/chocobo_inventory_null.png");
	private static final Identifier INV_TEXTURE_SMALL = Chococraft.modLoc("textures/gui/chocobo_inventory_small.png");
	private static final Identifier INV_TEXTURE_LARGE = Chococraft.modLoc("textures/gui/chocobo_inventory_large.png");


	public ChocoboInventoryScreen(SaddleBagMenu container, Inventory playerInventory, Component title) {
		super(container, playerInventory, title, 176, 204);
	}

	public static void openInventory(int windowId, AbstractChocobo chocobo) {
		Player player = Minecraft.getInstance().player;
		SaddleBagMenu saddleContainer = Services.PLATFORM.constructMenu(windowId, player.getInventory(), chocobo);
		player.containerMenu = saddleContainer;
		Minecraft.getInstance().setScreen(new ChocoboInventoryScreen(saddleContainer, player.getInventory(), chocobo.getDisplayName()));
	}

	@Override
	public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
		extractBackground(graphics, mouseX, mouseY, a);
		super.extractRenderState(graphics, mouseX, mouseY, a);
		extractTooltip(graphics, mouseX, mouseY);
	}

	@Override
	public void extractBackground(GuiGraphicsExtractor graphics, int x, int y, float partialTicks) {
		Identifier texture = INV_TEXTURE_NULL;
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
		graphics.blit(RenderPipelines.GUI_TEXTURED, texture, i, j, 0, 0, this.imageWidth, this.imageHeight, 256, 256);
		graphics.blit(RenderPipelines.GUI_TEXTURED, texture, i - 24, j + 10, 0, 204, 27, 33, 256, 256);
	}

	@Override
	protected void extractLabels(GuiGraphicsExtractor graphics, int xm, int ym) {
		graphics.text(font, title, 8, 6, ARGB.opaque(0x888888), false);
		graphics.text(font, this.playerInventoryTitle, 8, this.imageHeight - 96 + 2, ARGB.opaque(0x888888), false);
	}
}
