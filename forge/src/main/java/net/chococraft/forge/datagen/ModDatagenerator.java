package net.chococraft.forge.datagen;

import net.chococraft.Chococraft;
import net.chococraft.common.world.worldgen.ModFeatures;
import net.chococraft.forge.common.modifier.AddChocoboModifier;
import net.chococraft.forge.datagen.client.ChocoBlockModels;
import net.chococraft.forge.datagen.client.ChocoBlockstates;
import net.chococraft.forge.datagen.client.ChocoItemModels;
import net.chococraft.forge.datagen.client.ChocoLanguage;
import net.chococraft.forge.datagen.client.ChocoSoundProvider;
import net.chococraft.forge.datagen.client.patchouli.PatchouliProvider;
import net.chococraft.forge.datagen.data.ChocoLoot;
import net.chococraft.forge.datagen.data.ChocoRecipes;
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
import net.minecraftforge.common.Tags.Biomes;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers.AddFeaturesBiomeModifier;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModDatagenerator {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		PackOutput packOutput = generator.getPackOutput();
		ExistingFileHelper helper = event.getExistingFileHelper();

		if (event.includeServer()) {
			generator.addProvider(event.includeServer(), new ChocoLoot(packOutput));
			generator.addProvider(event.includeServer(), new ChocoRecipes(packOutput));

			generator.addProvider(true, new PatchouliProvider(packOutput));

//			generator.addProvider(event.includeServer(), JsonCodecProvider.forDatapackRegistry(
//					generator, helper, Chococraft.MOD_ID, ops, Registry.PLACED_FEATURE_REGISTRY, getPlacedFeatures(ops)));
//
//			generator.addProvider(event.includeServer(), JsonCodecProvider.forDatapackRegistry(
//					generator, helper, Chococraft.MOD_ID, ops, ForgeRegistries.Keys.BIOME_MODIFIERS, getBiomeModifiers(ops)));

			generator.addProvider(event.includeServer(), new DatapackBuiltinEntriesProvider(
					packOutput, CompletableFuture.supplyAsync(ModDatagenerator::getProvider), Set.of(Chococraft.MOD_ID)));
		}
		if (event.includeClient()) {
			generator.addProvider(event.includeClient(), new ChocoLanguage(packOutput));
			generator.addProvider(event.includeClient(), new ChocoBlockModels(packOutput, helper));
			generator.addProvider(event.includeClient(), new ChocoBlockstates(packOutput, helper));
			generator.addProvider(event.includeClient(), new ChocoItemModels(packOutput, helper));
			generator.addProvider(event.includeClient(), new ChocoSoundProvider(packOutput, helper));
		}
	}

	private static HolderLookup.Provider getProvider() {
		final RegistrySetBuilder registryBuilder = new RegistrySetBuilder();
		registryBuilder.add(Registries.CONFIGURED_FEATURE, context -> {
			ModFeatures.configuredBootstrap(context);
		});
		registryBuilder.add(Registries.PLACED_FEATURE, context -> {
			ModFeatures.placedBootstrap(context);
		});
		registryBuilder.add(ForgeRegistries.Keys.BIOME_MODIFIERS, context -> {
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

			context.register(createModifierKey("add_gysahl_green"), new AddFeaturesBiomeModifier(
					biomeGetter.getOrThrow(BiomeTags.IS_OVERWORLD),
					HolderSet.direct(placedGetter.getOrThrow(ModFeatures.PLACED_PATCH_GYSAHL_GREEN)),
					Decoration.VEGETAL_DECORATION
			));
		});
		// We need the BIOME registry to be present so we can use a biome tag, doesn't matter that it's empty
		registryBuilder.add(Registries.BIOME, context -> {
		});
		RegistryAccess.Frozen regAccess = RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY);
		return registryBuilder.buildPatch(regAccess, VanillaRegistries.createLookup());
	}

	public static ResourceKey<BiomeModifier> createModifierKey(String name) {
		return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(Chococraft.MOD_ID, name));
	}
}