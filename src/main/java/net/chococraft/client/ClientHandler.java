package net.chococraft.client;

import net.chococraft.Chococraft;
import net.chococraft.client.models.armor.ChocoDisguiseModel;
import net.chococraft.client.models.entities.AdultChocoboModel;
import net.chococraft.client.models.entities.ChicoboModel;
import net.chococraft.client.renderer.entities.ChocoboRenderer;
import net.chococraft.common.init.ModEntities;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent;

public class ClientHandler {
	public static final ModelLayerLocation CHOCOBO = new ModelLayerLocation(new ResourceLocation(Chococraft.MODID, "main"), "chocobo");
	public static final ModelLayerLocation CHICOBO = new ModelLayerLocation(new ResourceLocation(Chococraft.MODID, "main"), "chicobo");
	public static final ModelLayerLocation CHOCO_DISGUISE = new ModelLayerLocation(new ResourceLocation(Chococraft.MODID, "main"), "choco_disguise");

	public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(ModEntities.CHOCOBO.get(), ChocoboRenderer::new);
	}

	public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(CHOCOBO, () -> AdultChocoboModel.createBodyLayer());
		event.registerLayerDefinition(CHICOBO, () -> ChicoboModel.createBodyLayer());
		event.registerLayerDefinition(CHOCO_DISGUISE, () -> ChocoDisguiseModel.createArmorDefinition());
	}
}