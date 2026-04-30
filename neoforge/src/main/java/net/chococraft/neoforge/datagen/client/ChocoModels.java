package net.chococraft.neoforge.datagen.client;

import dev.chococraft.registration.RegistryObject;
import net.chococraft.Chococraft;
import net.chococraft.common.blocks.GysahlGreenBlock;
import net.chococraft.registry.ModRegistry;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class ChocoModels extends ModelProvider {
	public ChocoModels(PackOutput output) {
		super(output, Chococraft.MOD_ID);
	}

	@Override
	protected void registerModels(@NotNull BlockModelGenerators blockModels, @NotNull ItemModelGenerators itemModels) {
		buildStraw(blockModels, ModRegistry.STRAW.get());
		Identifier identifier = ModelLocationUtils.getModelLocation(ModRegistry.STRAW.get());
		blockModels.registerSimpleItemModel(ModRegistry.STRAW.get(), identifier);

		blockModels.createCropBlock(ModRegistry.GYSAHL_GREEN.get(), GysahlGreenBlock.AGE,
				0, 1, 2, 3, 4);

		for (RegistryObject<Item, ? extends Item> item : ModRegistry.ITEMS.getEntries()) {
			if (item.asHolder().is(ModRegistry.STRAW_ITEM.getResourceKey()) || item.asHolder().is(ModRegistry.GYSAHL_GREEN_SEEDS.getResourceKey())) {
				continue;
			}
			itemModels.generateFlatItem(item.get(), ModelTemplates.FLAT_ITEM);
		}
	}

	protected void buildStraw(BlockModelGenerators generators, Block block) {
		Identifier identifier = TexturedModel.CARPET.create(block, generators.modelOutput);

		MultiVariant multivariant = BlockModelGenerators.plainVariant(identifier);
		generators.blockStateOutput.accept(
				BlockModelGenerators.createSimpleBlock(block, multivariant)
		);
	}
}
