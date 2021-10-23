package net.chococraft.datagen;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.chococraft.Chococraft;
import net.chococraft.common.blocks.GysahlGreenBlock;
import net.chococraft.common.init.ModRegistry;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootParameterSet;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTableManager;
import net.minecraft.loot.ValidationTracker;
import net.minecraft.loot.conditions.BlockStateProperty;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.RandomChance;
import net.minecraft.loot.functions.ApplyBonus;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModDatagenerator {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		ExistingFileHelper helper = event.getExistingFileHelper();

		if (event.includeServer()) {
			generator.addProvider(new ModLoot(generator));
		}
		if (event.includeClient()) {
//			generator.addProvider(new FarmingItemModels(generator, helper));
		}
	}

	private static class ModLoot extends LootTableProvider {
		public ModLoot(DataGenerator gen) {
			super(gen);
		}

		@Override
		protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables() {
			return ImmutableList.of(Pair.of(ModBlockTables::new, LootParameterSets.BLOCK));
		}

		private static class ModBlockTables extends BlockLootTables {

			@Override
			protected void addTables() {
				this.add(ModRegistry.CHOCOBO_EGG.get(), noDrop());
				this.dropSelf(ModRegistry.STRAW_NEST.get());
				ILootCondition.IBuilder condition = BlockStateProperty.hasBlockStateProperties(ModRegistry.GYSAHL_GREEN.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(GysahlGreenBlock.AGE, GysahlGreenBlock.MAX_AGE));
				this.add(ModRegistry.GYSAHL_GREEN.get(), applyExplosionDecay(ModRegistry.GYSAHL_GREEN.get(), LootTable.lootTable().withPool(LootPool.lootPool()
						.add(ItemLootEntry.lootTableItem(ModRegistry.GYSAHL_GREEN.get()))).withPool(LootPool.lootPool().when(condition)
						.add(ItemLootEntry.lootTableItem(ModRegistry.GYSAHL_GREEN_ITEM.get())
								.apply(ApplyBonus.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 3)))).withPool(LootPool.lootPool().when(condition)
						.add(ItemLootEntry.lootTableItem(ModRegistry.LOVELY_GYSAHL_GREEN.get()).when(RandomChance.randomChance(0.02F))))));
			}

			@Override
			protected Iterable<Block> getKnownBlocks() {
				return ModRegistry.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
			}
		}

		@Override
		protected void validate(Map<ResourceLocation, LootTable> map, @Nonnull ValidationTracker validationtracker) {
			map.forEach((name, table) -> LootTableManager.validate(validationtracker, name, table));
		}
	}

	private static class FarmingItemModels extends ItemModelProvider {
		public FarmingItemModels(DataGenerator gen, ExistingFileHelper helper) {
			super(gen, Chococraft.MODID, helper);
		}

		@Override
		protected void registerModels() {
			ModRegistry.ITEMS.getEntries().stream()
				.map(RegistryObject::get)
				.forEach(item -> {
					String path = Objects.requireNonNull(item.getRegistryName()).getPath();
					if(path.equals("straw_nest") || path.equals("chocobo_egg")) {
						this.withExistingParent(path, modLoc("block/" + path));
					} else {
						this.singleTexture(path, mcLoc("item/generated"), "layer0", modLoc("item/" + path));
					}
				});
		}

		@Override
		public String getName() {
			return "Item Models";
		}
	}
}