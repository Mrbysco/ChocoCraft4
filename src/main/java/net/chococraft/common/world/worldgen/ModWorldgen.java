package net.chococraft.common.world.worldgen;

import net.chococraft.common.ChocoConfig;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ModWorldgen {
	@SubscribeEvent(priority = EventPriority.HIGH)
	public void biomeLoadingEvent(BiomeLoadingEvent event) {
		BiomeGenerationSettingsBuilder builder = event.getGeneration();
		Biome.BiomeCategory category = event.getCategory();
		boolean overworldOnly = ChocoConfig.COMMON.gysahlGreensSpawnOnlyInOverworld.get();
		if(!overworldOnly || (overworldOnly && !category.equals(Biome.BiomeCategory.NETHER) && !category.equals(Biome.BiomeCategory.THEEND))) {
			builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModFeatureConfigs.PATCH_GYSAHL_GREEN);
		}
	}
}
