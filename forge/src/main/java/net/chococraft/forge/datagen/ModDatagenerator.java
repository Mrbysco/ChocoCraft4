package net.chococraft.forge.datagen;

import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.chococraft.Chococraft;
import net.chococraft.common.world.worldgen.ModConfiguredFeatures;
import net.chococraft.forge.common.modifier.AddChocoboModifier;
import net.chococraft.forge.datagen.client.ChocoLanguage;
import net.chococraft.forge.datagen.data.ChocoRecipes;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraftforge.common.Tags.Biomes;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers.AddFeaturesBiomeModifier;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModDatagenerator {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		final RegistryOps<JsonElement> ops = RegistryOps.create(JsonOps.INSTANCE, RegistryAccess.builtinCopy());
		DataGenerator generator = event.getGenerator();
		ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

		if (event.includeServer()) {
//			generator.addProvider(event.includeServer(), new ChocoLoot(generator));
			generator.addProvider(event.includeServer(), new ChocoRecipes(generator));

			generator.addProvider(event.includeServer(), JsonCodecProvider.forDatapackRegistry(
					generator, existingFileHelper, Chococraft.MOD_ID, ops, Registry.PLACED_FEATURE_REGISTRY, getPlacedFeatures(ops)));

			generator.addProvider(event.includeServer(), JsonCodecProvider.forDatapackRegistry(
					generator, existingFileHelper, Chococraft.MOD_ID, ops, ForgeRegistries.Keys.BIOME_MODIFIERS, getBiomeModifiers(ops)));
		}
		if (event.includeClient()) {
			generator.addProvider(event.includeClient(), new ChocoLanguage(generator));
//			generator.addProvider(event.includeClient(), new ChocoBlockModels(generator, existingFileHelper));
//			generator.addProvider(event.includeClient(), new ChocoBlockstates(generator, existingFileHelper));
//			generator.addProvider(event.includeClient(), new ChocoItemModels(generator, existingFileHelper));
//			generator.addProvider(event.includeClient(), new ChocoSoundProvider(generator, existingFileHelper));
		}
//		generator.addProvider(true, new PatchouliProvider(generator));
	}

	public static Map<ResourceLocation, PlacedFeature> getPlacedFeatures(RegistryOps<JsonElement> ops) {
		final ResourceKey<ConfiguredFeature<?, ?>> gysahlGreenFeatureKey = ModConfiguredFeatures.PATCH_GYSAHL_GREEN.unwrapKey().get().cast(Registry.CONFIGURED_FEATURE_REGISTRY).get();
		final Holder<ConfiguredFeature<?, ?>> gysahlGreenFeatureHolder = ops.registry(Registry.CONFIGURED_FEATURE_REGISTRY).get().getOrCreateHolderOrThrow(gysahlGreenFeatureKey);
		final PlacedFeature gysahlGreenFeature = new PlacedFeature(
				gysahlGreenFeatureHolder,
				List.of(CountPlacement.of(UniformInt.of(0, 5)),
						RarityFilter.onAverageOnceEvery(2),
						InSquarePlacement.spread(), PlacementUtils.RANGE_4_4, BiomeFilter.biome()));

		return Map.of(
				new ResourceLocation(Chococraft.MOD_ID, "patch_gysahl_green"), gysahlGreenFeature
		);
	}

	public static Map<ResourceLocation, BiomeModifier> getBiomeModifiers(RegistryOps<JsonElement> ops) {
		final HolderSet.Named<Biome> plainsTag = new HolderSet.Named<>(ops.registry(Registry.BIOME_REGISTRY).get(), Biomes.IS_PLAINS);
		final BiomeModifier addPlains = new AddChocoboModifier(plainsTag);
		final HolderSet.Named<Biome> mountainTag = new HolderSet.Named<>(ops.registry(Registry.BIOME_REGISTRY).get(), Biomes.IS_MOUNTAIN);
		final BiomeModifier addHills = new AddChocoboModifier(mountainTag);
		final HolderSet.Named<Biome> netherTag = new HolderSet.Named<>(ops.registry(Registry.BIOME_REGISTRY).get(), BiomeTags.IS_NETHER);
		final AddChocoboModifier addNether = new AddChocoboModifier(netherTag);

		final HolderSet.Named<Biome> overworldTag = new HolderSet.Named<>(ops.registry(Registry.BIOME_REGISTRY).get(), BiomeTags.IS_OVERWORLD);
		final BiomeModifier addGysahlGreen = new AddFeaturesBiomeModifier(
				overworldTag,
				HolderSet.direct(ops.registry(Registry.PLACED_FEATURE_REGISTRY).get().getOrCreateHolderOrThrow(ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY,
						new ResourceLocation(Chococraft.MOD_ID, "patch_gysahl_green")))),
				Decoration.VEGETAL_DECORATION);

		return Map.of(
				new ResourceLocation(Chococraft.MOD_ID, "add_plains_chocobos"), addPlains,
				new ResourceLocation(Chococraft.MOD_ID, "add_mountain_chocobos"), addHills,
				new ResourceLocation(Chococraft.MOD_ID, "add_nether_chocobos"), addNether,
				new ResourceLocation(Chococraft.MOD_ID, "add_gysahl_green"), addGysahlGreen
		);
	}
}