package net.chococraft.client;

import net.chococraft.client.gui.NestScreen;
import net.chococraft.client.renderer.entities.ChocoboRenderer;
import net.chococraft.common.init.ModContainers;
import net.chococraft.common.init.ModEntities;
import net.chococraft.common.init.ModRegistry;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientHandler {

    public static void onClientSetup(final FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.CHOCOBO.get(), ChocoboRenderer::new);

        RenderTypeLookup.setRenderLayer(ModRegistry.GYSAHL_GREEN.get(), RenderType.getCutout());

        ScreenManager.registerFactory(ModContainers.NEST.get(), NestScreen::new);
    }


//    public void openChocoboInfoGui(EntityChocobo chocobo, PlayerEntity player) {
//        super.openChocoboInfoGui(chocobo, player);
//        Minecraft.getMinecraft().displayGuiScreen(new GuiChocoboInfo(chocobo, player));
//    }
//
//    public void openChocoBook(PlayerEntity player) {
//        super.openChocoBook(player);
//        Minecraft.getMinecraft().displayGuiScreen(new GuiChocoboBook(player));
//    }
}