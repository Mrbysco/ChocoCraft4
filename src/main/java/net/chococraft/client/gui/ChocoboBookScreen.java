package net.chococraft.client.gui;


import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.chococraft.Chococraft;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;


public class ChocoboBookScreen extends Screen {
	private final static ResourceLocation TEXTURE = new ResourceLocation(Chococraft.MODID, "textures/gui/chocobo_book.png");

	private int xSize = 130;
	private int ySize = 185;
	private int currentpage = 1;
	private int guiLeft;
	private int guiTop;

	public ChocoboBookScreen() {
		super(Component.empty());
	}

	public static void openScreen() {
		Minecraft.getInstance().setScreen(new ChocoboBookScreen());
	}

	@Override
	public void init() {
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;

		this.addRenderableWidget(new Button(this.guiLeft, this.guiTop + 165, 20, 20, Component.empty(), (button) ->
				this.currentpage = (this.currentpage <= 1 ? 7 : this.currentpage - 1)));
		this.addRenderableWidget(new Button((this.guiLeft + xSize) - 20, this.guiTop + 165, 20, 20, Component.empty(), (button) ->
				this.currentpage = (this.currentpage >= 7 ? 1 : this.currentpage + 1)));
	}

	@Override
	public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(poseStack);
		super.render(poseStack, mouseX, mouseY, partialTicks);
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, TEXTURE);

		poseStack.pushPose();
		poseStack.translate(this.guiLeft, this.guiTop, 0);

		this.blit(poseStack, 0, 0, 0, 0, this.xSize, this.ySize);

		Component name = Component.translatable("gui.chocobook.title", currentpage);
		int nameLength = this.font.width(name);
		this.font.drawShadow(poseStack, name, (this.xSize / 2) - (nameLength / 2), 4, -1);

		this.renderpage();

		poseStack.popPose();
	}

	private void renderpage() {
		this.font.drawWordWrap(Component.translatable("gui.chocobook.page" + (currentpage)), this.guiLeft + 5, this.guiTop + 20, 120, 0);
	}

}
