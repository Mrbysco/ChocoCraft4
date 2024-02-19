package net.chococraft.common.world.worldgen;

import net.chococraft.common.blocks.GysahlGreenBlock;
import net.chococraft.registry.ModRegistry;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

import java.util.List;

public class ModFeatures {
	protected static final BlockState GYSAHL_GREEN = ModRegistry.GYSAHL_GREEN.get().defaultBlockState().setValue(GysahlGreenBlock.AGE, GysahlGreenBlock.MAX_AGE);

	public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_GYSAHL_GREEN = FeatureUtils.createKey("chococraft:patch_gysahl_green");

	public static void configuredBootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
		FeatureUtils.register(context, PATCH_GYSAHL_GREEN,
				Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK,
						new SimpleBlockConfiguration(BlockStateProvider.simple(GYSAHL_GREEN)))
		);
	}

	public static final ResourceKey<PlacedFeature> PLACED_PATCH_GYSAHL_GREEN = PlacementUtils.createKey("chococraft:patch_gysahl_green");

	public static void placedBootstrap(BootstapContext<PlacedFeature> context) {
		HolderGetter<ConfiguredFeature<?, ?>> holdergetter = context.lookup(Registries.CONFIGURED_FEATURE);

		PlacementUtils.register(context, PLACED_PATCH_GYSAHL_GREEN, holdergetter.getOrThrow(PATCH_GYSAHL_GREEN),
				List.of(
						CountPlacement.of(UniformInt.of(0, 5)),
						RarityFilter.onAverageOnceEvery(2),
						InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));
	}
}
