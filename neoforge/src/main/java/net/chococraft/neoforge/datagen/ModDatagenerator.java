package net.chococraft.neoforge.datagen;

import net.chococraft.Chococraft;
import net.chococraft.common.world.worldgen.ModFeatures;
import net.chococraft.neoforge.common.modifier.AddChocoboModifier;
import net.chococraft.neoforge.datagen.client.ChocoLanguage;
import net.chococraft.neoforge.datagen.client.ChocoModels;
import net.chococraft.neoforge.datagen.client.ChocoSoundProvider;
import net.chococraft.neoforge.datagen.client.patchouli.PatchouliProvider;
import net.chococraft.neoforge.datagen.data.ChocoBlockTags;
import net.chococraft.neoforge.datagen.data.ChocoItemTags;
import net.chococraft.neoforge.datagen.data.ChocoLoot;
import net.chococraft.neoforge.datagen.data.ChocoRecipes;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber
public class ModDatagenerator {

	@SubscribeEvent
	public static void gatherData(GatherDataEvent.Client event) {
		event.createDatapackRegistryObjects(BUILDER);
		DataGenerator generator = event.getGenerator();
		PackOutput packOutput = generator.getPackOutput();
		CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

		generator.addProvider(true, new ChocoLoot(packOutput, lookupProvider));
		generator.addProvider(true, new ChocoRecipes.Runner(packOutput, lookupProvider));
		generator.addProvider(true, new ChocoBlockTags(packOutput, lookupProvider));
		generator.addProvider(true, new ChocoItemTags(packOutput, lookupProvider));

		generator.addProvider(true, new PatchouliProvider(packOutput, lookupProvider));

		generator.addProvider(true, new ChocoLanguage(packOutput));
		generator.addProvider(true, new ChocoModels(packOutput));
		generator.addProvider(true, new ChocoSoundProvider(packOutput));
	}

	public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
			.add(Registries.CONFIGURED_FEATURE, ModFeatures::configuredBootstrap)
			.add(Registries.PLACED_FEATURE, ModFeatures::placedBootstrap)
			.add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, context -> {
				HolderGetter<Biome> biomeGetter = context.lookup(Registries.BIOME);
				HolderGetter<PlacedFeature> placedGetter = context.lookup(Registries.PLACED_FEATURE);

				context.register(createModifierKey("add_plains_chocobos"), new AddChocoboModifier(
						biomeGetter.getOrThrow(Tags.Biomes.IS_PLAINS))
				);
				context.register(createModifierKey("add_mountain_chocobos"), new AddChocoboModifier(
						biomeGetter.getOrThrow(Tags.Biomes.IS_MOUNTAIN))
				);
				context.register(createModifierKey("add_nether_chocobos"), new AddChocoboModifier(
						biomeGetter.getOrThrow(Tags.Biomes.IS_NETHER))
				);

				context.register(createModifierKey("add_gysahl_green"), new BiomeModifiers.AddFeaturesBiomeModifier(
						biomeGetter.getOrThrow(Tags.Biomes.IS_OVERWORLD),
						HolderSet.direct(placedGetter.getOrThrow(ModFeatures.PLACED_PATCH_GYSAHL_GREEN)),
						GenerationStep.Decoration.VEGETAL_DECORATION
				));
			});

	public static ResourceKey<BiomeModifier> createModifierKey(String name) {
		return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, Chococraft.modLoc(name));
	}
}