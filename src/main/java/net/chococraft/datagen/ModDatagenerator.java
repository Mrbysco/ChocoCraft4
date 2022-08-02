package net.chococraft.datagen;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.JsonOps;
import net.chococraft.Chococraft;
import net.chococraft.common.blocks.GysahlGreenBlock;
import net.chococraft.common.init.ModEntities;
import net.chococraft.common.init.ModRegistry;
import net.chococraft.common.world.worldgen.ModFeatureConfigs;
import net.chococraft.datagen.client.ChocoCraftItemModels;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.EntityLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.common.Tags.Biomes;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers.AddFeaturesBiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers.AddSpawnsBiomeModifier;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static net.chococraft.common.init.ModEntities.CHOCOBO;
import static net.chococraft.common.init.ModRegistry.CHOCOBO_DRUMSTICK_RAW;
import static net.chococraft.common.init.ModRegistry.CHOCOBO_FEATHER;
import static net.chococraft.common.init.ModRegistry.GYSAHL_GREEN;
import static net.chococraft.common.init.ModRegistry.GYSAHL_GREEN_ITEM;
import static net.chococraft.common.init.ModRegistry.LOVELY_GYSAHL_GREEN;
import static net.chococraft.common.init.ModRegistry.STRAW;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModDatagenerator {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		final RegistryOps<JsonElement> ops = RegistryOps.create(JsonOps.INSTANCE, RegistryAccess.builtinCopy());
		DataGenerator generator = event.getGenerator();
		ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

		if (event.includeServer()) {
			generator.addProvider(event.includeServer(), new ModLoot(generator));


			generator.addProvider(event.includeServer(), JsonCodecProvider.forDatapackRegistry(
					generator, existingFileHelper, Chococraft.MODID, ops, Registry.PLACED_FEATURE_REGISTRY, getConfiguredFeatures(ops)));

			generator.addProvider(event.includeServer(), JsonCodecProvider.forDatapackRegistry(
					generator, existingFileHelper, Chococraft.MODID, ops, ForgeRegistries.Keys.BIOME_MODIFIERS, getBiomeModifiers(ops)));
		}
		if (event.includeClient()) {
			generator.addProvider(event.includeServer(), new ChocoCraftItemModels(generator, existingFileHelper));
		}
	}

	public static Map<ResourceLocation, PlacedFeature> getConfiguredFeatures(RegistryOps<JsonElement> ops) {
		final ResourceKey<ConfiguredFeature<?, ?>> gysahlGreenFeatureKey = ModFeatureConfigs.PATCH_GYSAHL_GREEN.unwrapKey().get().cast(Registry.CONFIGURED_FEATURE_REGISTRY).get();
		final Holder<ConfiguredFeature<?, ?>> gysahlGreenFeatureHolder = ops.registry(Registry.CONFIGURED_FEATURE_REGISTRY).get().getOrCreateHolderOrThrow(gysahlGreenFeatureKey);
		final PlacedFeature gysahlGreenFeature = new PlacedFeature(
				gysahlGreenFeatureHolder,
				List.of(CountPlacement.of(UniformInt.of(0, 5)),
						RarityFilter.onAverageOnceEvery(3),
						InSquarePlacement.spread(), PlacementUtils.RANGE_4_4, BiomeFilter.biome()));

		return Map.of(
				new ResourceLocation(Chococraft.MODID, "patch_gysahl_green"), gysahlGreenFeature
		);
	}

	public static Map<ResourceLocation, BiomeModifier> getBiomeModifiers(RegistryOps<JsonElement> ops) {
		final HolderSet.Named<Biome> plainsTag = new HolderSet.Named<>(ops.registry(Registry.BIOME_REGISTRY).get(), Biomes.IS_PLAINS);
		final BiomeModifier addPlains = AddSpawnsBiomeModifier.singleSpawn(
				plainsTag, new SpawnerData(CHOCOBO.get(), 10, 1, 3));
		final HolderSet.Named<Biome> mountainTag = new HolderSet.Named<>(ops.registry(Registry.BIOME_REGISTRY).get(), Biomes.IS_MOUNTAIN);
		final BiomeModifier addHills = AddSpawnsBiomeModifier.singleSpawn(
				mountainTag, new SpawnerData(CHOCOBO.get(), 10, 1, 3));
		final HolderSet.Named<Biome> netherTag = new HolderSet.Named<>(ops.registry(Registry.BIOME_REGISTRY).get(), BiomeTags.IS_NETHER);
		final BiomeModifier addNether = AddSpawnsBiomeModifier.singleSpawn(
				netherTag, new SpawnerData(CHOCOBO.get(), 10, 1, 3));

		final HolderSet.Named<Biome> overworldTag = new HolderSet.Named<>(ops.registry(Registry.BIOME_REGISTRY).get(), BiomeTags.IS_OVERWORLD);
		final BiomeModifier addGysahlGreen = new AddFeaturesBiomeModifier(
				overworldTag,
				HolderSet.direct(ops.registry(Registry.PLACED_FEATURE_REGISTRY).get().getOrCreateHolderOrThrow(ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY,
						new ResourceLocation(Chococraft.MODID, "patch_gysahl_green")))),
				Decoration.VEGETAL_DECORATION);

		return Map.of(
				new ResourceLocation(Chococraft.MODID, "add_plains_chocobos"), addPlains,
				new ResourceLocation(Chococraft.MODID, "add_mountain_chocobos"), addHills,
				new ResourceLocation(Chococraft.MODID, "add_nether_chocobos"), addNether,
				new ResourceLocation(Chococraft.MODID, "add_gysahl_green"), addGysahlGreen
		);
	}

	private static class ModLoot extends LootTableProvider {
		public ModLoot(DataGenerator gen) {
			super(gen);
		}

		@Override
		protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
			return ImmutableList.of(Pair.of(ModBlockTables::new, LootContextParamSets.BLOCK), Pair.of(ModEntityTables::new, LootContextParamSets.ENTITY));
		}

		private static class ModBlockTables extends BlockLoot {
			@Override
			protected void addTables() {
				this.dropSelf(STRAW.get());
				LootItemCondition.Builder condition = LootItemBlockStatePropertyCondition.hasBlockStateProperties(GYSAHL_GREEN.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(GysahlGreenBlock.AGE, GysahlGreenBlock.MAX_AGE));
				this.add(GYSAHL_GREEN.get(), applyExplosionDecay(GYSAHL_GREEN.get(), LootTable.lootTable().withPool(LootPool.lootPool()
						.add(LootItem.lootTableItem(GYSAHL_GREEN.get()))).withPool(LootPool.lootPool().when(condition)
						.add(LootItem.lootTableItem(GYSAHL_GREEN_ITEM.get())
								.apply(ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 3)))).withPool(LootPool.lootPool().when(condition)
						.add(LootItem.lootTableItem(LOVELY_GYSAHL_GREEN.get()).when(LootItemRandomChanceCondition.randomChance(0.02F))))));
			}

			@Override
			protected Iterable<Block> getKnownBlocks() {
				return ModRegistry.BLOCKS.getEntries().stream().map(net.minecraftforge.registries.RegistryObject::get)::iterator;
			}
		}

		private static class ModEntityTables extends EntityLoot {
			@Override
			protected void addTables() {
				this.add(CHOCOBO.get(), LootTable.lootTable()
						.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(CHOCOBO_FEATHER.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F))).apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))))
						.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(CHOCOBO_DRUMSTICK_RAW.get()).apply(SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE))).apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))));
			}

			@Override
			protected Iterable<EntityType<?>> getKnownEntities() {
				Stream<EntityType<?>> entityTypeStream = ModEntities.ENTITY_TYPES.getEntries().stream().map(net.minecraftforge.registries.RegistryObject::get);
				return entityTypeStream::iterator;
			}
		}

		@Override
		protected void validate(Map<ResourceLocation, LootTable> map, @Nonnull ValidationContext validationtracker) {
			map.forEach((name, table) -> LootTables.validate(validationtracker, name, table));
		}
	}
}