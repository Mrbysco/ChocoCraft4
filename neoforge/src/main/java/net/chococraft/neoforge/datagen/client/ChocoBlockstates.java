package net.chococraft.neoforge.datagen.client;

import net.chococraft.Chococraft;
import net.chococraft.common.blocks.GysahlGreenBlock;
import net.chococraft.registry.ModRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.VariantBlockStateBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ChocoBlockstates extends BlockStateProvider {
	public ChocoBlockstates(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
		super(packOutput, Chococraft.MOD_ID, existingFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		buildCrops((GysahlGreenBlock) ModRegistry.GYSAHL_GREEN.get());
		buildStraw(ModRegistry.STRAW.get());
	}

	protected void buildCrops(GysahlGreenBlock block) {
		VariantBlockStateBuilder builder = getVariantBuilder(block);
		for (int i = 0; i <= block.getMaxAge(); i++) {
			ModelFile file = models().cross(BuiltInRegistries.BLOCK.getKey(block).getPath() + "_stage" + (i),
					new ResourceLocation(Chococraft.MOD_ID, "block/" + BuiltInRegistries.BLOCK.getKey(block).getPath() + i)).renderType("cutout");
			builder.partialState().with(block.getAgeProperty(), i).modelForState().modelFile(file).addModel();
		}
	}

	protected void buildStraw(Block block) {
		ModelFile clusterBlock = models().getExistingFile(modLoc("block/" + BuiltInRegistries.BLOCK.getKey(block).getPath()));
		getVariantBuilder(block)
				.partialState().modelForState().modelFile(clusterBlock).addModel();
	}
}
