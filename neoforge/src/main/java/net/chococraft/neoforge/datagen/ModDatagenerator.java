package net.chococraft.neoforge.datagen;

import net.chococraft.Chococraft;
import net.chococraft.common.world.worldgen.ModFeatures;
import net.chococraft.neoforge.common.modifier.AddChocoboModifier;
import net.chococraft.neoforge.datagen.client.ChocoLanguage;
import net.chococraft.neoforge.datagen.client.ChocoModels;
import net.chococraft.neoforge.datagen.client.ChocoSoundProvider;
import net.chococraft.neoforge.datagen.data.ChocoLoot;
import net.chococraft.neoforge.datagen.data.ChocoRecipes;
import net.minecraft.core.Cloner;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.Tags.Biomes;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class ModDatagenerator {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent.Client event) {
		DataGenerator generator = event.getGenerator();
		PackOutput packOutput = generator.getPackOutput();
		CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

		generator.addProvider(true, new ChocoLoot(packOutput, lookupProvider));
		generator.addProvider(true, new ChocoRecipes.Runner(packOutput, lookupProvider));

//		generator.addProvider(true, new PatchouliProvider(packOutput, lookupProvider)); TODO: Re-enable when we have a patchouli provider build for 1.21.4

		generator.addProvider(true, new DatapackBuiltinEntriesProvider(
				packOutput, CompletableFuture.supplyAsync(ModDatagenerator::getProvider), Set.of(Chococraft.MOD_ID)));


		generator.addProvider(true, new ChocoLanguage(packOutput));
		generator.addProvider(true, new ChocoModels(packOutput));
		generator.addProvider(true, new ChocoSoundProvider(packOutput));
	}

	private static RegistrySetBuilder.PatchedRegistries getProvider() {
		final RegistrySetBuilder registryBuilder = new RegistrySetBuilder();
		registryBuilder.add(Registries.CONFIGURED_FEATURE, ModFeatures::configuredBootstrap);
		registryBuilder.add(Registries.PLACED_FEATURE, ModFeatures::placedBootstrap);
		registryBuilder.add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, context -> {
			HolderGetter<Biome> biomeGetter = context.lookup(Registries.BIOME);
			HolderGetter<PlacedFeature> placedGetter = context.lookup(Registries.PLACED_FEATURE);

			context.register(createModifierKey("add_plains_chocobos"), new AddChocoboModifier(
					biomeGetter.getOrThrow(Biomes.IS_PLAINS))
			);
			context.register(createModifierKey("add_mountain_chocobos"), new AddChocoboModifier(
					biomeGetter.getOrThrow(Biomes.IS_MOUNTAIN))
			);
			context.register(createModifierKey("add_nether_chocobos"), new AddChocoboModifier(
					biomeGetter.getOrThrow(BiomeTags.IS_NETHER))
			);

			context.register(createModifierKey("add_gysahl_green"), new BiomeModifiers.AddFeaturesBiomeModifier(
					biomeGetter.getOrThrow(BiomeTags.IS_OVERWORLD),
					HolderSet.direct(placedGetter.getOrThrow(ModFeatures.PLACED_PATCH_GYSAHL_GREEN)),
					Decoration.VEGETAL_DECORATION
			));
		});
		// We need the BIOME registry to be present, so we can use a biome tag, doesn't matter that it's empty
		registryBuilder.add(Registries.BIOME, $ -> {
		});
		RegistryAccess.Frozen regAccess = RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY);
		Cloner.Factory cloner$factory = new Cloner.Factory();
		net.neoforged.neoforge.registries.DataPackRegistriesHooks.getDataPackRegistriesWithDimensions().forEach(data -> data.runWithArguments(cloner$factory::addCodec));
		return registryBuilder.buildPatch(regAccess, VanillaRegistries.createLookup(), cloner$factory);
	}

	public static ResourceKey<BiomeModifier> createModifierKey(String name) {
		return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(Chococraft.MOD_ID, name));
	}
}