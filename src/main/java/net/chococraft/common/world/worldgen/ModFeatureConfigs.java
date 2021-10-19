package net.chococraft.common.world.worldgen;

import net.chococraft.Chococraft;
import net.chococraft.common.ChocoConfig;
import net.chococraft.common.blocks.GysahlGreenBlock;
import net.chococraft.common.init.ModRegistry;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.levelgen.feature.blockplacers.SimpleBlockPlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.data.worldgen.Features;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class ModFeatureConfigs {
	protected static final BlockState GYSAHL_GREEN = ModRegistry.GYSAHL_GREEN.get().defaultBlockState().setValue(GysahlGreenBlock.AGE, GysahlGreenBlock.MAX_AGE);

	public static final RandomPatchConfiguration GYSAHL_GREEN_PATCH_CONFIG = (new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(ModFeatureConfigs.GYSAHL_GREEN),
			SimpleBlockPlacer.INSTANCE)).tries(ChocoConfig.COMMON.gysahlGreenPatchSize.get()).build();

	public static final ConfiguredFeature<?, ?> PATCH_GYSAHL_GREEN = register("patch_gysahl_green", Feature.FLOWER
			.configured(GYSAHL_GREEN_PATCH_CONFIG)
			.decorated(Features.Decorators.ADD_32)
			.decorated(Features.Decorators.HEIGHTMAP_SQUARE)
			.rarity((int)ChocoConfig.COMMON.gysahlGreenSpawnChance.get().doubleValue() * 10));

	private static <FC extends FeatureConfiguration> ConfiguredFeature<FC, ?> register(String key, ConfiguredFeature<FC, ?> feature) {
		return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(Chococraft.MODID, key), feature);
	}
}
