package net.chococraft.forge.client;

import net.chococraft.client.ClientHandler;
import net.chococraft.client.models.armor.ChocoDisguiseModel;
import net.chococraft.client.models.entities.AdultChocoboModel;
import net.chococraft.client.models.entities.ChicoboModel;
import net.chococraft.client.renderer.entities.ChocoboRenderer;
import net.chococraft.registry.ModEntities;
import net.minecraftforge.client.event.EntityRenderersEvent;

public class ForgeClientHandler {
	public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(ModEntities.CHOCOBO.get(), ChocoboRenderer::new);
	}

	public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(ClientHandler.CHOCOBO, () -> AdultChocoboModel.createBodyLayer());
		event.registerLayerDefinition(ClientHandler.CHICOBO, () -> ChicoboModel.createBodyLayer());
		event.registerLayerDefinition(ClientHandler.CHOCO_DISGUISE, () -> ChocoDisguiseModel.createArmorDefinition());
	}
}
