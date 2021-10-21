package net.chococraft.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.chococraft.Chococraft;
import net.chococraft.common.entities.ChocoboEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Chococraft.MODID, value = Dist.CLIENT)
public class RenderChocoboOverlay {
    public static final ResourceLocation ICONS = new ResourceLocation(Chococraft.MODID, "textures/gui/icons.png");

    @SubscribeEvent
    public static void onGuiIngameOverlayRender(RenderGameOverlayEvent.Post event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.HEALTHMOUNT) return;
        Minecraft minecraft = Minecraft.getInstance();
        MatrixStack matrixStack = event.getMatrixStack();
        Entity mountedEntity = minecraft.player.getVehicle();
        if (!(mountedEntity instanceof ChocoboEntity)) return;
        ChocoboEntity chocobo = (ChocoboEntity) mountedEntity;

        minecraft.getTextureManager().bind(ICONS);

        final int width = event.getWindow().getGuiScaledWidth();
        final int height = event.getWindow().getGuiScaledHeight();
        int left_align = width / 2 + 91;
        int top = height - 39; //right_height = 39
        top -= Math.ceil(chocobo.getHealth() / 20) * 10; //Offset it based on the amount of health rendered
        float staminaPercentage = chocobo.getStaminaPercentage() * 10;

        for (int i = 0; i < 10; ++i) {
            int x = left_align - i * 8 - 9;
            if (i >= staminaPercentage) {
                // render empty
                AbstractGui.blit(matrixStack, x, top, 0, 0, 9, 9, 32, 32);
            } else {
                if (i == ((int) staminaPercentage)) {
                    // draw partial
                    AbstractGui.blit(matrixStack, x, top, 0, 0, 9, 9, 32, 32);
                    int iconHeight = (int) (9 * (staminaPercentage - ((int) staminaPercentage)));
                    AbstractGui.blit(matrixStack, x, top + (9 - iconHeight), 0, 18 + (9 - iconHeight), 9, iconHeight, 32, 32);
                } else {
                    // draw full
                    AbstractGui.blit(matrixStack, x, top, 0, 18, 9, 9, 32, 32);
                }
            }
        }
    }
}
