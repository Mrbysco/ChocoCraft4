package net.chococraft.client.gui;


import net.chococraft.Chococraft;
import net.chococraft.ChococraftExpectPlatform;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.PageButton;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;


public class ChocoboBookScreen extends Screen {
	private final static ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Chococraft.MOD_ID, "textures/gui/chocobo_book.png");
	private final Component bookTitle = Component.literal("The Chocopedia").withStyle(ChatFormatting.GOLD);
	private final Component bookAuthor = Component.literal("by Clienthax");

	private final int xSize = 192;
	private final int ySize = 192;
	private int currentPage = 0;
	private final int pageCount;
	private int guiLeft;
	private int guiTop;
	private Component pageMsg = CommonComponents.EMPTY;
	private ArrayList<Integer> genderlessPages;

	public ChocoboBookScreen() {
		super(Component.empty());

		int maxPages = 1;
		genderlessPages = new ArrayList<>();
		for (int i = 1; i < 128; i++) {
			String unlocalized = "gui.chocobook.page" + i;
			if(ChococraftExpectPlatform.genderless()){
				String genderless = unlocalized + ".genderless";
				if(I18n.exists(genderless)){
					System.out.println(i);
					genderlessPages.add(i);
				}
			}
			if (I18n.get(unlocalized).equals(unlocalized)) {
				maxPages = i - 1;
				break;
			}
		}
		this.pageCount = maxPages;
	}

	public static void openScreen() {
		Minecraft.getInstance().setScreen(new ChocoboBookScreen());
	}

	@Override
	public void init() {
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;

		this.addRenderableWidget(new PageButton((this.guiLeft + xSize) - 66, this.guiTop + 158, true, (p_98297_) -> {
			this.currentPage = (this.currentPage >= pageCount ? 0 : this.currentPage + 1);
		}, true));
		this.addRenderableWidget(new PageButton(this.guiLeft + 36, this.guiTop + 158, false, (p_98287_) -> {
			this.currentPage = (this.currentPage <= 0 ? pageCount : this.currentPage - 1);
		}, true));
	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		super.render(guiGraphics, mouseX, mouseY, partialTicks);


		if (this.currentPage == 0) {
			guiGraphics.drawString(font, bookTitle, guiLeft + (this.xSize / 2) - (this.font.width(bookTitle) / 2), guiTop + 24, 0, false);
			guiGraphics.drawString(font, bookAuthor, guiLeft + (this.xSize / 2) - (this.font.width(bookAuthor) / 2), guiTop + 44, 0, false);
		} else {
			if (currentPage > 1) {
				this.pageMsg = Component.translatable("book.pageIndicator", currentPage - 1, Math.max(pageCount - 1, 1));
				guiGraphics.drawString(font, this.pageMsg, guiLeft + ((this.xSize / 2) - (this.font.width(bookAuthor) / 2) - 6), guiTop + 14, 0, false);
			}

			this.renderPage(guiGraphics);
		}
	}

	@Override
	public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		this.renderTransparentBackground(guiGraphics);
		guiGraphics.blit(TEXTURE, guiLeft, guiTop, 0, 0, 192, 192);
	}

	private void renderPage(GuiGraphics guiGraphics) {
		int i = (this.width - this.xSize) / 2;
		boolean genderless = ChococraftExpectPlatform.genderless() && genderlessPages.contains(currentPage);
		MutableComponent component = Component.translatable("gui.chocobook.page" + (currentPage) + (genderless ? ".genderless" : ""));
		guiGraphics.drawWordWrap(font, component, i + 34, this.guiTop + 26, 120, 0);
	}

}
