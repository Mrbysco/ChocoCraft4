package net.chococraft.neoforge.datagen.data;

import net.chococraft.Chococraft;
import net.chococraft.common.world.worldgen.ModFeatures;
import net.chococraft.neoforge.common.modifier.AddChocoboModifier;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.Tags.Biomes;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ChocoDatapack extends DatapackBuiltinEntriesProvider {
	public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
			.add(Registries.CONFIGURED_FEATURE, ModFeatures::configuredBootstrap)
			.add(Registries.PLACED_FEATURE, ModFeatures::placedBootstrap)
			.add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, context -> {
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

	public ChocoDatapack(PackOutput output, CompletableFuture<Provider> registries, Set<String> modIds) {
		super(output, registries, BUILDER, modIds);
	}

	public static ResourceKey<BiomeModifier> createModifierKey(String name) {
		return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, Chococraft.modLoc(name));
	}
}
