package net.chococraft.common.world.worldgen;

import net.chococraft.common.ChocoConfig;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

import java.util.List;

public class ModPlacements {
	public static final List<PlacementModifier> GYSAHL_PLACEMENT =
			List.of(CountPlacement.of(UniformInt.of(0, 5)),
					RarityFilter.onAverageOnceEvery((int) ChocoConfig.COMMON.gysahlGreenSpawnChance.get().doubleValue() * 10),
					InSquarePlacement.spread(), PlacementUtils.RANGE_4_4, BiomeFilter.biome());

	public static final PlacedFeature PATCH_GYSAHL_GREEN = PlacementUtils.register("patch_gysahl_green",
			ModFeatureConfigs.PATCH_GYSAHL_GREEN.placed(GYSAHL_PLACEMENT));
}
