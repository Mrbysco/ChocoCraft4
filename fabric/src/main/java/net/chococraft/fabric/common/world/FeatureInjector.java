package net.chococraft.fabric.common.world;

import net.chococraft.common.world.worldgen.ModFeatures;
import net.chococraft.fabric.ChococraftFabric;
import net.chococraft.fabric.common.config.FabricChocoConfig;
import net.chococraft.registry.ModEntities;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.levelgen.GenerationStep;

import java.util.function.Predicate;

public class FeatureInjector {

	public static void init() {
		FabricChocoConfig config = ChococraftFabric.config.get();

		Predicate<BiomeSelectionContext> overworld = (ctx -> ctx.hasTag(BiomeTags.IS_OVERWORLD));
		BiomeModifications.addFeature(overworld, GenerationStep.Decoration.VEGETAL_DECORATION, ModFeatures.PLACED_PATCH_GYSAHL_GREEN);

		Predicate<BiomeSelectionContext> plains = (ctx -> ctx.hasTag(BiomeTags.HAS_VILLAGE_PLAINS) || ctx.hasTag(BiomeTags.IS_MOUNTAIN) || ctx.hasTag(BiomeTags.IS_NETHER));
		BiomeModifications.addSpawn(plains, MobCategory.CREATURE, ModEntities.CHOCOBO.get(),
				config.spawning.chocoboSpawnWeight, config.spawning.chocoboPackSizeMin, config.spawning.chocoboPackSizeMax);
	}
}
