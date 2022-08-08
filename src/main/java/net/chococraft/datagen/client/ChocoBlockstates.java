package net.chococraft.datagen.client;

import net.chococraft.Chococraft;
import net.chococraft.common.init.ModRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.VariantBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class ChocoBlockstates extends BlockStateProvider {
	public ChocoBlockstates(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, Chococraft.MODID, existingFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		buildCrops((CropBlock) ModRegistry.GYSAHL_GREEN.get());
		buildStraw(ModRegistry.STRAW.get());
	}

	protected void buildCrops(CropBlock block) {
		VariantBlockStateBuilder builder = getVariantBuilder(block);
		for (int i = 0; i <= block.getMaxAge(); i++) {
			ModelFile file = models().cross(ForgeRegistries.BLOCKS.getKey(block).getPath() + "_stage" + (i),
					new ResourceLocation(Chococraft.MODID, "block/" + ForgeRegistries.BLOCKS.getKey(block).getPath() + i));
			builder.partialState().with(block.getAgeProperty(), i).modelForState().modelFile(file).addModel();
		}
	}

	protected void buildStraw(Block block) {
		ModelFile clusterBlock = models().getExistingFile(modLoc("block/" + ForgeRegistries.BLOCKS.getKey(block).getPath()));
		getVariantBuilder(block)
				.partialState().modelForState().modelFile(clusterBlock).addModel();
	}
}
