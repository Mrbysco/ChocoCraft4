package net.chococraft.forge.datagen.client;

import net.chococraft.Chococraft;
import net.chococraft.common.blocks.GysahlGreenBlock;
import net.chococraft.registry.ModRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.VariantBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

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
			ModelFile file = models().cross(ForgeRegistries.BLOCKS.getKey(block).getPath() + "_stage" + (i),
					new ResourceLocation(Chococraft.MOD_ID, "block/" + ForgeRegistries.BLOCKS.getKey(block).getPath() + i)).renderType("cutout");
			builder.partialState().with(block.getAgeProperty(), i).modelForState().modelFile(file).addModel();
		}
	}

	protected void buildStraw(Block block) {
		ModelFile clusterBlock = models().getExistingFile(modLoc("block/" + ForgeRegistries.BLOCKS.getKey(block).getPath()));
		getVariantBuilder(block)
				.partialState().modelForState().modelFile(clusterBlock).addModel();
	}
}
