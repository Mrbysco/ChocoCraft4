package net.chococraft.client;

import dev.architectury.registry.menu.MenuRegistry;
import net.chococraft.Chococraft;
import net.chococraft.client.gui.ChocoboInventoryScreen;
import net.chococraft.registry.ModMenus;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ClientHandler {
	public static final ModelLayerLocation CHOCOBO = new ModelLayerLocation(new ResourceLocation(Chococraft.MOD_ID, "main"), "chocobo");
	public static final ModelLayerLocation CHICOBO = new ModelLayerLocation(new ResourceLocation(Chococraft.MOD_ID, "main"), "chicobo");
	public static final ModelLayerLocation CHOCO_DISGUISE = new ModelLayerLocation(new ResourceLocation(Chococraft.MOD_ID, "main"), "choco_disguise");

	public static void initializeScreen() {
		MenuRegistry.registerScreenFactory(ModMenus.CHOCOBO.get(), ChocoboInventoryScreen::new);
	}
}
