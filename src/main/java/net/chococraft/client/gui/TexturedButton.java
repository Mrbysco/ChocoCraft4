package net.chococraft.client.gui;

import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

// Exists just for better naming
public class TexturedButton extends ImageButton {

    public TexturedButton(int x, int y, int width, int height, int textureX, int textureY, int yDiff, ResourceLocation location, int textureWidth, int textureHeight, OnPress onPress, OnTooltip onTooltip, Component text) {
        super(x, y, width, height, textureX, textureY, yDiff, location, textureWidth, textureHeight, onPress, onTooltip, text);
    }

    // TODO this is dumb, base mc has support for hover by default, fix
    public void setTexture(ResourceLocation resourceLocation, int textureX, int textureY, int textureWidth, int textureHeight) {
        this.resourceLocation = resourceLocation;
        this.xTexStart = textureX;
        this.yTexStart = textureY;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
    }
}