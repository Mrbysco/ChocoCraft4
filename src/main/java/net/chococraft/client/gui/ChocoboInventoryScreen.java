package net.chococraft.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.chococraft.Chococraft;
import net.chococraft.common.entities.ChocoboEntity;
import net.chococraft.common.init.ModRegistry;
import net.chococraft.common.inventory.SaddleBagContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ChocoboInventoryScreen extends ContainerScreen<SaddleBagContainer> {
    private static final ResourceLocation INV_TEXTURE_NULL = new ResourceLocation(Chococraft.MODID, "textures/gui/chocobo_inventory_null.png");
    private static final ResourceLocation INV_TEXTURE_SMALL = new ResourceLocation(Chococraft.MODID, "textures/gui/chocobo_inventory_small.png");
    private static final ResourceLocation INV_TEXTURE_LARGE = new ResourceLocation(Chococraft.MODID, "textures/gui/chocobo_inventory_large.png");

    private ChocoboEntity chocobo;
    private PlayerEntity player;

    public ChocoboInventoryScreen(SaddleBagContainer container, PlayerInventory playerInventory, ChocoboEntity chocobo) {
        super(container, playerInventory, chocobo.getDisplayName());

        this.imageWidth = 176;
        this.imageHeight = 204;
        this.chocobo = chocobo;
        this.player = playerInventory.player;
    }

    public static void openInventory(int windowId, ChocoboEntity chocobo) {
        PlayerEntity player = Minecraft.getInstance().player;
        SaddleBagContainer saddleContainer = new SaddleBagContainer(windowId, player.inventory, chocobo);
        player.containerMenu = saddleContainer;
        Minecraft.getInstance().setScreen(new ChocoboInventoryScreen(saddleContainer, player.inventory, chocobo));
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        ItemStack saddleStack = chocobo.getSaddle();
        if(!saddleStack.isEmpty()){
            Item item = saddleStack.getItem();
            if(item == ModRegistry.CHOCOBO_SADDLE.get()) {
                this.minecraft.getTextureManager().bind(INV_TEXTURE_NULL);
            } else if(item == ModRegistry.CHOCOBO_SADDLE_BAGS.get()) {
                this.minecraft.getTextureManager().bind(INV_TEXTURE_SMALL);
            } else if(item == ModRegistry.CHOCOBO_SADDLE_PACK.get()) {
                this.minecraft.getTextureManager().bind(INV_TEXTURE_LARGE);
            }
        } else {
            this.minecraft.getTextureManager().bind(INV_TEXTURE_NULL);
        }

        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        this.blit(matrixStack, i, j, 0, 0, this.imageWidth, this.imageHeight);
        this.blit(matrixStack, i - 24, j + 10, 0, 204, 27, 33);
    }

    @Override
    protected void renderLabels(MatrixStack matrixStack, int x, int y) {
        this.font.draw(matrixStack, this.chocobo.getDisplayName().getString(), 8, 6, 0x888888);
        this.font.draw(matrixStack, this.player.getDisplayName().getString(), 8, this.imageHeight - 96 + 2, 0x888888);
    }
}
