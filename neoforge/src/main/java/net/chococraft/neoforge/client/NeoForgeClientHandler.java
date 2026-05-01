package net.chococraft.neoforge.client;

import net.chococraft.ChococraftClient;
import net.chococraft.client.gui.ChocoboInventoryScreen;
import net.chococraft.client.models.armor.ChocoDisguiseModel;
import net.chococraft.client.models.entities.AdultChocoboModel;
import net.chococraft.client.models.entities.ChicoboModel;
import net.chococraft.client.renderer.entities.ChocoboRenderer;
import net.chococraft.registry.ModEntities;
import net.chococraft.registry.ModMenus;
import net.chococraft.registry.ModRegistry;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

@EventBusSubscriber(Dist.CLIENT)
public class NeoForgeClientHandler {

	@SubscribeEvent
	public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(ModEntities.CHOCOBO.get(), ChocoboRenderer::new);
	}

	@SubscribeEvent
	public static void registerMenuScreen(RegisterMenuScreensEvent event) {
		event.register(ModMenus.CHOCOBO.get(), ChocoboInventoryScreen::new);
	}

	@SubscribeEvent
	public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(ChococraftClient.CHOCOBO, AdultChocoboModel::createBodyLayer);
		event.registerLayerDefinition(ChococraftClient.CHICOBO, ChicoboModel::createBodyLayer);
		event.registerLayerDefinition(ChococraftClient.CHOCO_DISGUISE, ChocoDisguiseModel::createArmorDefinition);
	}

	@SubscribeEvent
	public static void registerClientExtension(RegisterClientExtensionsEvent event) {
		event.registerItem(
				new ChocoArmorExtension(),
				ModRegistry.CHOCO_DISGUISE_HELMET.get(),
				ModRegistry.CHOCO_DISGUISE_CHESTPLATE.get(),
				ModRegistry.CHOCO_DISGUISE_LEGGINGS.get(),
				ModRegistry.CHOCO_DISGUISE_BOOTS.get()
		);
	}
}
