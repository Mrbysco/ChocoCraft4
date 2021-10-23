package net.chococraft.common.world.worldgen;

import net.chococraft.common.ChocoConfig;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ModWorldgen {
	@SubscribeEvent(priority = EventPriority.HIGH)
	public void biomeLoadingEvent(BiomeLoadingEvent event) {
		BiomeGenerationSettingsBuilder builder = event.getGeneration();
		Biome.Category category = event.getCategory();
		boolean overworldOnly = ChocoConfig.COMMON.gysahlGreensSpawnOnlyInOverworld.get();
		if(!overworldOnly || !category.equals(Biome.Category.NETHER) && !category.equals(Biome.Category.THEEND)) {
			builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModFeatureConfigs.PATCH_GYSAHL_GREEN);
		}
	}
}
