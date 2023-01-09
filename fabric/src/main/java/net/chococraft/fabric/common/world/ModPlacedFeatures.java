package net.chococraft.fabric.common.world;

import net.chococraft.common.world.worldgen.ModConfiguredFeatures;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

import java.util.List;

public class ModPlacedFeatures {
	public static Holder<PlacedFeature> GYSASHL_GREEN = PlacementUtils.register("chococraft:patch_gysahl_green", ModConfiguredFeatures.PATCH_GYSAHL_GREEN,
			List.of(CountPlacement.of(UniformInt.of(0, 5)),
					RarityFilter.onAverageOnceEvery(2),
					InSquarePlacement.spread(), PlacementUtils.RANGE_4_4, BiomeFilter.biome()));

	public static void init() {
		//Load the class
	}
}
