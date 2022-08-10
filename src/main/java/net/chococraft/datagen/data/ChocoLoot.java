package net.chococraft.datagen.data;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.chococraft.common.blocks.GysahlGreenBlock;
import net.chococraft.common.init.ModEntities;
import net.chococraft.common.init.ModRegistry;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.data.loot.EntityLootTables;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSet;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTable.Builder;
import net.minecraft.loot.LootTableManager;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.ValidationTracker;
import net.minecraft.loot.conditions.BlockStateProperty;
import net.minecraft.loot.conditions.EntityHasProperty;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.RandomChance;
import net.minecraft.loot.functions.ApplyBonus;
import net.minecraft.loot.functions.LootingEnchantBonus;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.loot.functions.Smelt;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;


public class ChocoLoot extends LootTableProvider {
	public ChocoLoot(DataGenerator gen) {
		super(gen);
	}

	@Override
	protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, Builder>>>, LootParameterSet>> getTables() {
		return ImmutableList.of(Pair.of(ChocoBlockLoot::new, LootParameterSets.BLOCK), Pair.of(ChocoEntityLoot::new, LootParameterSets.ENTITY));
	}

	private static class ChocoBlockLoot extends BlockLootTables {
		@Override
		protected void addTables() {
			this.dropSelf(ModRegistry.STRAW.get());
			ILootCondition.IBuilder condition = BlockStateProperty.hasBlockStateProperties(ModRegistry.GYSAHL_GREEN.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(GysahlGreenBlock.AGE, GysahlGreenBlock.MAX_AGE));
			this.add(ModRegistry.GYSAHL_GREEN.get(), applyExplosionDecay(ModRegistry.GYSAHL_GREEN.get(), LootTable.lootTable().withPool(LootPool.lootPool()
					.add(ItemLootEntry.lootTableItem(ModRegistry.GYSAHL_GREEN.get()))).withPool(LootPool.lootPool().when(condition)
					.add(ItemLootEntry.lootTableItem(ModRegistry.GYSAHL_GREEN_ITEM.get())
							.apply(ApplyBonus.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 3)))).withPool(LootPool.lootPool().when(condition)
					.add(ItemLootEntry.lootTableItem(ModRegistry.LOVERLY_GYSAHL_GREEN.get()).when(RandomChance.randomChance(0.15F)))
					.add(ItemLootEntry.lootTableItem(ModRegistry.GOLD_GYSAHL.get()).when(RandomChance.randomChance(0.05F))))
			));
		}

		@Override
		protected Iterable<Block> getKnownBlocks() {
			return ModRegistry.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
		}
	}

	private static class ChocoEntityLoot extends EntityLootTables {
		@Override
		protected void addTables() {
			this.add(ModEntities.CHOCOBO.get(), LootTable.lootTable()
					.withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1))
							.add(ItemLootEntry.lootTableItem(ModRegistry.CHOCOBO_FEATHER.get())
									.apply(SetCount.setCount(RandomValueRange.between(0.0F, 2.0F)))
									.apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.0F, 1.0F)))))
					.withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1))
							.add(ItemLootEntry.lootTableItem(ModRegistry.CHOCOBO_DRUMSTICK_RAW.get())
									.apply(Smelt.smelted().when(EntityHasProperty.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE)))
									.apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.0F, 1.0F))))));
		}

		@Override
		protected Iterable<EntityType<?>> getKnownEntities() {
			Stream<EntityType<?>> entityTypeStream = ModEntities.ENTITIES.getEntries().stream().map(RegistryObject::get);
			return entityTypeStream::iterator;
		}
	}

	@Override
	protected void validate(Map<ResourceLocation, LootTable> map, @Nonnull ValidationTracker validationtracker) {
		map.forEach((name, table) -> LootTableManager.validate(validationtracker, name, table));
	}
}