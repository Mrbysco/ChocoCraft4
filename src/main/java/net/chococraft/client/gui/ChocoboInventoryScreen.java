package net.chococraft.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.chococraft.Chococraft;
import net.chococraft.common.entities.ChocoboEntity;
import net.chococraft.common.init.ModRegistry;
import net.chococraft.common.inventory.SaddleBagContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ChocoboInventoryScreen extends AbstractContainerScreen<SaddleBagContainer> {
    private static final ResourceLocation INV_TEXTURE_NULL = new ResourceLocation(Chococraft.MODID, "textures/gui/chocobo_inventory_null.png");
    private static final ResourceLocation INV_TEXTURE_SMALL = new ResourceLocation(Chococraft.MODID, "textures/gui/chocobo_inventory_small.png");
    private static final ResourceLocation INV_TEXTURE_LARGE = new ResourceLocation(Chococraft.MODID, "textures/gui/chocobo_inventory_large.png");

    private ChocoboEntity chocobo;
    private Player player;

    public ChocoboInventoryScreen(SaddleBagContainer container, Inventory playerInventory, ChocoboEntity chocobo) {
        super(container, playerInventory, chocobo.getDisplayName());

        this.imageWidth = 176;
        this.imageHeight = 204;
        this.chocobo = chocobo;
        this.player = playerInventory.player;
    }

    public static void openInventory(int windowId, ChocoboEntity chocobo) {
        Player player = Minecraft.getInstance().player;
        SaddleBagContainer saddleContainer = new SaddleBagContainer(windowId, player.getInventory(), chocobo);
        player.containerMenu = saddleContainer;
        Minecraft.getInstance().setScreen(new ChocoboInventoryScreen(saddleContainer, player.getInventory(), chocobo));
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(PoseStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        ItemStack saddleStack = chocobo.getSaddle();
        if(!saddleStack.isEmpty()){
            Item item = saddleStack.getItem();
            if(item == ModRegistry.CHOCOBO_SADDLE.get()) {
                RenderSystem.setShaderTexture(0, INV_TEXTURE_NULL);
            } else if(item == ModRegistry.CHOCOBO_SADDLE_BAGS.get()) {
                RenderSystem.setShaderTexture(0, INV_TEXTURE_SMALL);
            } else if(item == ModRegistry.CHOCOBO_SADDLE_PACK.get()) {
                RenderSystem.setShaderTexture(0, INV_TEXTURE_LARGE);
            }
        } else {
            RenderSystem.setShaderTexture(0, INV_TEXTURE_NULL);
        }

        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        this.blit(matrixStack, i, j, 0, 0, this.imageWidth, this.imageHeight);
        this.blit(matrixStack, i - 24, j + 10, 0, 204, 27, 33);
    }

    @Override
    protected void renderLabels(PoseStack matrixStack, int x, int y) {
        this.font.draw(matrixStack, this.chocobo.getDisplayName().getString(), 8, 6, 0x888888);
        this.font.draw(matrixStack, this.player.getDisplayName().getString(), 8, this.imageHeight - 96 + 2, 0x888888);
    }
}
