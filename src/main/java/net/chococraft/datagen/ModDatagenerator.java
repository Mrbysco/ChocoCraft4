package net.chococraft.datagen;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.chococraft.Chococraft;
import net.chococraft.common.blocks.GysahlGreenBlock;
import net.chococraft.common.init.ModRegistry;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.world.level.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static net.chococraft.common.init.ModRegistry.*;

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
		protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
			return ImmutableList.of(Pair.of(ModBlockTables::new, LootContextParamSets.BLOCK));
		}

		private static class ModBlockTables extends BlockLoot {

			@Override
			protected void addTables() {
				this.add(CHOCOBO_EGG.get(), noDrop());
				this.dropSelf(STRAW_NEST.get());
				LootItemCondition.Builder condition = LootItemBlockStatePropertyCondition.hasBlockStateProperties(GYSAHL_GREEN.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(GysahlGreenBlock.AGE, GysahlGreenBlock.MAX_AGE));
				this.add(GYSAHL_GREEN.get(), applyExplosionDecay(GYSAHL_GREEN.get(), LootTable.lootTable().withPool(LootPool.lootPool()
						.add(LootItem.lootTableItem(GYSAHL_GREEN.get()))).withPool(LootPool.lootPool().when(condition)
						.add(LootItem.lootTableItem(GYSAHL_GREEN_ITEM.get())
								.apply(ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 3)))).withPool(LootPool.lootPool().when(condition)
						.add(LootItem.lootTableItem(LOVELY_GYSAHL_GREEN.get()).when(LootItemRandomChanceCondition.randomChance(0.02F))))));
			}

			@Override
			protected Iterable<Block> getKnownBlocks() {
				return (Iterable<Block>) ModRegistry.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
			}
		}

		@Override
		protected void validate(Map<ResourceLocation, LootTable> map, @Nonnull ValidationContext validationtracker) {
			map.forEach((name, table) -> LootTables.validate(validationtracker, name, table));
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