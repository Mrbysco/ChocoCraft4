package net.chococraft.forge.datagen.client;

import net.chococraft.Chococraft;
import net.chococraft.registry.ModRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ChocoBlockModels extends BlockModelProvider {
	public ChocoBlockModels(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
		super(packOutput, Chococraft.MOD_ID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		buildStraw(ModRegistry.STRAW.getId());
	}


	protected void buildStraw(ResourceLocation resourceLocation) {
		withExistingParent(resourceLocation.getPath(),
				mcLoc("block/carpet"))
				.texture("wool", modLoc(BLOCK_FOLDER + "/" + resourceLocation.getPath()));
	}
}
