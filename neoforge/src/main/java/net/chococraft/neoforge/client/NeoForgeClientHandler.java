package net.chococraft.neoforge.client;

import net.chococraft.ChococraftClient;
import net.chococraft.client.models.armor.ChocoDisguiseModel;
import net.chococraft.client.models.entities.AdultChocoboModel;
import net.chococraft.client.models.entities.ChicoboModel;
import net.chococraft.client.renderer.entities.ChocoboRenderer;
import net.chococraft.registry.ModEntities;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

public class NeoForgeClientHandler {
	public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(ModEntities.CHOCOBO.get(), ChocoboRenderer::new);
	}

	public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(ChococraftClient.CHOCOBO, AdultChocoboModel::createBodyLayer);
		event.registerLayerDefinition(ChococraftClient.CHICOBO, ChicoboModel::createBodyLayer);
		event.registerLayerDefinition(ChococraftClient.CHOCO_DISGUISE, ChocoDisguiseModel::createArmorDefinition);
	}
}
