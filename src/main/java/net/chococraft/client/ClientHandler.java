package net.chococraft.client;

import net.chococraft.client.renderer.entities.ChocoboRenderer;
import net.chococraft.common.init.ModEntities;
import net.chococraft.common.init.ModRegistry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientHandler {

	public static void onClientSetup(final FMLClientSetupEvent event) {
		RenderTypeLookup.setRenderLayer(ModRegistry.GYSAHL_GREEN.get(), RenderType.cutout());

		RenderingRegistry.registerEntityRenderingHandler(ModEntities.CHOCOBO.get(), ChocoboRenderer::new);
	}
}