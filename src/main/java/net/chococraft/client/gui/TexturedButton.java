package net.chococraft.client.gui;

import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class TexturedButton extends ImageButton {

    public TexturedButton(int x, int y, int width, int height, int textureX, int textureY, int yDiff, ResourceLocation location, int textureWidth, int textureHeight, IPressable onPress, ITooltip onTooltip, ITextComponent text) {
        super(x, y, width, height, textureX, textureY, yDiff, location, textureWidth, textureHeight, onPress, onTooltip, text);
    }

    public void setTexture(ResourceLocation resourceLocation, int textureX, int textureY, int textureWidth, int textureHeight) {
        this.resourceLocation = resourceLocation;
        this.xTexStart = textureX;
        this.yTexStart = textureY;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
    }
}