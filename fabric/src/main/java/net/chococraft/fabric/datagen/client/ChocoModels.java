package net.chococraft.fabric.datagen.client;

import dev.architectury.registry.registries.RegistrySupplier;
import net.chococraft.common.blocks.GysahlGreenBlock;
import net.chococraft.registry.ModRegistry;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ChocoModels extends FabricModelProvider {
	public ChocoModels(FabricDataOutput packOutput) {
		super(packOutput);
	}

	@Override
	public void generateBlockStateModels(BlockModelGenerators blockModels) {
		buildStraw(blockModels, ModRegistry.STRAW.get());
		ResourceLocation resourcelocation = ModelLocationUtils.getModelLocation(ModRegistry.STRAW.get());
		blockModels.registerSimpleItemModel(ModRegistry.STRAW.get(), resourcelocation);

		blockModels.createCropBlock(ModRegistry.GYSAHL_GREEN.get(), GysahlGreenBlock.AGE,
				0, 1, 2, 3, 4);
	}

	@Override
	public void generateItemModels(ItemModelGenerators itemModels) {
		for (RegistrySupplier<Item> item : ModRegistry.ITEMS) {
			if (item.is(ModRegistry.STRAW_ITEM.getKey()) || item.is(ModRegistry.GYSAHL_GREEN_SEEDS.getKey())) {
				continue;
			}
			itemModels.generateFlatItem(item.get(), ModelTemplates.FLAT_ITEM);
		}
	}

	protected void buildStraw(BlockModelGenerators generators, Block block) {
		ResourceLocation resourcelocation = TexturedModel.CARPET.create(block, generators.modelOutput);

		MultiVariant multivariant = BlockModelGenerators.plainVariant(resourcelocation);
		generators.blockStateOutput.accept(
				BlockModelGenerators.createSimpleBlock(block, multivariant)
		);
	}
}
