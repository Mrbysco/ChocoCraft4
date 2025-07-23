package net.chococraft.neoforge.datagen.client;

import dev.architectury.registry.registries.RegistrySupplier;
import net.chococraft.Chococraft;
import net.chococraft.common.blocks.GysahlGreenBlock;
import net.chococraft.registry.ModRegistry;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.Variant;
import net.minecraft.client.data.models.blockstates.VariantProperties;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
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
		ResourceLocation resourcelocation = ModelLocationUtils.getModelLocation(ModRegistry.STRAW.get());
		blockModels.registerSimpleItemModel(ModRegistry.STRAW.get(), resourcelocation);

		blockModels.createCropBlock(ModRegistry.GYSAHL_GREEN.get(), GysahlGreenBlock.AGE,
				0, 1, 2, 3, 4);

		for (RegistrySupplier<Item> item : ModRegistry.ITEMS) {
			if (item.is(ModRegistry.STRAW_ITEM.getKey()) || item.is(ModRegistry.GYSAHL_GREEN_SEEDS.getKey())) {
				continue;
			}
			itemModels.generateFlatItem(item.get(), ModelTemplates.FLAT_ITEM);
		}
	}

	protected void buildStraw(BlockModelGenerators generators, Block block) {
		ResourceLocation resourcelocation = TexturedModel.CARPET.create(block, generators.modelOutput);

		generators.blockStateOutput
				.accept(
						MultiVariantGenerator.multiVariant(block, Variant.variant()
								.with(VariantProperties.MODEL, resourcelocation))
				);
	}
}
