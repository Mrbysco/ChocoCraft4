package net.chococraft.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.chococraft.Chococraft;
import net.chococraft.common.inventory.NestContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class NestScreen extends ContainerScreen<NestContainer> {
    private final static ResourceLocation TEXTURE = new ResourceLocation(Chococraft.MODID, "textures/gui/chocobo_nest.png");
    private final static ResourceLocation TEXTURE_SHELTERED = new ResourceLocation(Chococraft.MODID, "textures/gui/chocobo_nest_sheltered.png");

    public NestScreen(NestContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        this.getMinecraft().getTextureManager().bind(this.getMenu().getTile().isSheltered() ? TEXTURE_SHELTERED : TEXTURE);
        blit(matrixStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    protected void renderLabels(MatrixStack matrixStack, int mouseX, int mouseY) {
        super.renderLabels(matrixStack, mouseX, mouseY);
    }
}
