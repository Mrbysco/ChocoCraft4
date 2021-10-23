package net.chococraft.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.chococraft.Chococraft;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ChocoboBookScreen extends Screen {
    private final static ResourceLocation TEXTURE = new ResourceLocation(Chococraft.MODID, "textures/gui/chocobo_book.png");

    private int xSize = 130;
    private int ySize = 185;
    private int currentpage = 1;
    private int guiLeft;
    private int guiTop;

    public ChocoboBookScreen() {
        super(StringTextComponent.EMPTY);
    }

    public static void openScreen() {
        Minecraft.getInstance().setScreen(new ChocoboBookScreen());
    }

    @Override
    public void init() {
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;

        this.addButton(new Button(this.guiLeft, this.guiTop + 165, 20, 20, StringTextComponent.EMPTY, (button) -> this.currentpage = (this.currentpage <= 1 ? 7 : this.currentpage - 1)));
        this.addButton(new Button( (this.guiLeft + xSize) - 20, this.guiTop + 165, 20, 20, StringTextComponent.EMPTY, (button) -> this.currentpage = (this.currentpage >= 7 ? 1 : this.currentpage + 1)));
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.minecraft.getTextureManager().bind(TEXTURE);

        RenderSystem.pushMatrix();
        RenderSystem.translatef(this.guiLeft, this.guiTop, 0);

        this.blit(matrixStack, 0, 0, 0, 0, this.xSize, this.ySize);

        ITextComponent name = new TranslationTextComponent("gui.chocobook.title", currentpage);
        int nameLength = this.font.width(name);
        this.font.drawShadow(matrixStack, name, (this.xSize / 2) - (nameLength / 2), 4, -1);

        this.minecraft.getTextureManager().bind(TEXTURE);
        this.renderpage();

        RenderSystem.popMatrix();
    }

    private void renderpage() {
        this.font.drawWordWrap(new TranslationTextComponent("gui.chocobook.page" + (currentpage)), 5, 20, 120, 0);
    }

}
