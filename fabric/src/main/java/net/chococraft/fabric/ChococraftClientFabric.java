package net.chococraft.fabric;

import net.chococraft.ChococraftClient;
import net.chococraft.client.gui.ChocoboInventoryScreen;
import net.chococraft.client.models.armor.ChocoDisguiseModel;
import net.chococraft.client.models.entities.AdultChocoboModel;
import net.chococraft.client.models.entities.ChicoboModel;
import net.chococraft.client.renderer.entities.ChocoboRenderer;
import net.chococraft.common.entity.AbstractChocobo;
import net.chococraft.fabric.common.packets.OpenChocoboScreenPayload;
import net.chococraft.registry.ModEntities;
import net.chococraft.registry.ModMenus;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.equine.AbstractHorse;

public class ChococraftClientFabric implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		ModelLayerRegistry.registerModelLayer(ChococraftClient.CHOCOBO, AdultChocoboModel::createBodyLayer);
		ModelLayerRegistry.registerModelLayer(ChococraftClient.CHICOBO, ChicoboModel::createBodyLayer);
		ModelLayerRegistry.registerModelLayer(ChococraftClient.CHOCO_DISGUISE, ChocoDisguiseModel::createArmorDefinition);

		EntityRenderers.register(ModEntities.CHOCOBO.get(), ChocoboRenderer::new);

		ChococraftClient.init();

		FabricDefaultAttributeRegistry.register(ModEntities.CHOCOBO.get(), AbstractChocobo.createAttributes());

		ClientPlayNetworking.registerGlobalReceiver(OpenChocoboScreenPayload.ID, (payload, context) -> {
			Entity entity = context.client().level.getEntity(payload.entityId());
			if (entity instanceof AbstractHorse) {
				AbstractChocobo abstractChocobo = (AbstractChocobo) entity;
				ChocoboInventoryScreen.openInventory(payload.containerId(), abstractChocobo);
			}
		});
		ClientLifecycleEvents.CLIENT_STARTED.register(client ->
				MenuScreens.register(ModMenus.CHOCOBO.get(), ChocoboInventoryScreen::new));
	}
}
