package net.chococraft.client.gui;


import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.chococraft.Chococraft;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;


public class ChocoboBookScreen extends Screen {
	private final static ResourceLocation TEXTURE = new ResourceLocation(Chococraft.MODID, "textures/gui/chocobo_book.png");

	private int xSize = 130;
	private int ySize = 185;
	private int currentpage = 1;
	private int guiLeft;
	private int guiTop;

	public ChocoboBookScreen() {
		super(TextComponent.EMPTY);
	}

	public static void openScreen() {
		Minecraft.getInstance().setScreen(new ChocoboBookScreen());
	}

	@Override
	public void init() {
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;

		this.addRenderableWidget(new Button(this.guiLeft, this.guiTop + 165, 20, 20, TextComponent.EMPTY, (button) ->
				this.currentpage = (this.currentpage <= 1 ? 7 : this.currentpage - 1)));
		this.addRenderableWidget(new Button((this.guiLeft + xSize) - 20, this.guiTop + 165, 20, 20, TextComponent.EMPTY, (button) ->
				this.currentpage = (this.currentpage >= 7 ? 1 : this.currentpage + 1)));
	}

	@Override
	public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, TEXTURE);

		matrixStack.pushPose();
		matrixStack.translate(this.guiLeft, this.guiTop, 0);

		this.blit(matrixStack, 0, 0, 0, 0, this.xSize, this.ySize);

		Component name = new TranslatableComponent("gui.chocobook.title", currentpage);
		int nameLength = this.font.width(name);
		this.font.drawShadow(matrixStack, name, (this.xSize / 2) - (nameLength / 2), 4, -1);

		this.renderpage();

		matrixStack.popPose();
	}

	private void renderpage() {
		this.font.drawWordWrap(new TranslatableComponent("gui.chocobook.page" + (currentpage)), this.guiLeft + 5, this.guiTop + 20, 120, 0);
	}

}
