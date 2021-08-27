package net.chococraft.common.world.worldgen;

import net.chococraft.Chococraft;
import net.chococraft.common.ChocoConfig;
import net.chococraft.common.blocks.GysahlGreenBlock;
import net.chococraft.common.init.ModRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class ModFeatureConfigs {
	protected static final BlockState GYSAHL_GREEN = ModRegistry.GYSAHL_GREEN.get().getDefaultState().with(GysahlGreenBlock.AGE, GysahlGreenBlock.MAX_AGE);

	public static final BlockClusterFeatureConfig GYSAHL_GREEN_PATCH_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(ModFeatureConfigs.GYSAHL_GREEN),
			SimpleBlockPlacer.PLACER)).tries(ChocoConfig.COMMON.gysahlGreenPatchSize.get()).build();

	public static final ConfiguredFeature<?, ?> PATCH_GYSAHL_GREEN = register("patch_gysahl_green", Feature.FLOWER
			.withConfiguration(GYSAHL_GREEN_PATCH_CONFIG)
			.withPlacement(Features.Placements.VEGETATION_PLACEMENT)
			.withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT)
			.chance((int)ChocoConfig.COMMON.gysahlGreenSpawnChance.get().doubleValue() * 10));

	private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String key, ConfiguredFeature<FC, ?> feature) {
		return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(Chococraft.MODID, key), feature);
	}
}
