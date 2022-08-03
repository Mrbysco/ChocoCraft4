package net.chococraft.datagen.data;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.chococraft.common.blocks.GysahlGreenBlock;
import net.chococraft.common.init.ModEntities;
import net.chococraft.common.init.ModRegistry;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.EntityLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
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
	protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, Builder>>>, LootContextParamSet>> getTables() {
		return ImmutableList.of(Pair.of(ChocoBlockLoot::new, LootContextParamSets.BLOCK), Pair.of(ChocoEntityLoot::new, LootContextParamSets.ENTITY));
	}

	private static class ChocoBlockLoot extends BlockLoot {
		@Override
		protected void addTables() {
			this.dropSelf(ModRegistry.STRAW.get());
			LootItemCondition.Builder condition = LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModRegistry.GYSAHL_GREEN.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(GysahlGreenBlock.AGE, GysahlGreenBlock.MAX_AGE));
			this.add(ModRegistry.GYSAHL_GREEN.get(), applyExplosionDecay(ModRegistry.GYSAHL_GREEN.get(), LootTable.lootTable().withPool(LootPool.lootPool()
					.add(LootItem.lootTableItem(ModRegistry.GYSAHL_GREEN.get()))).withPool(LootPool.lootPool().when(condition)
					.add(LootItem.lootTableItem(ModRegistry.GYSAHL_GREEN_ITEM.get())
							.apply(ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 3)))).withPool(LootPool.lootPool().when(condition)
					.add(LootItem.lootTableItem(ModRegistry.LOVERLY_GYSAHL_GREEN.get()).when(LootItemRandomChanceCondition.randomChance(0.15F)))
					.add(LootItem.lootTableItem(ModRegistry.GOLD_GYSAHL.get()).when(LootItemRandomChanceCondition.randomChance(0.05F))))
			));
		}

		@Override
		protected Iterable<Block> getKnownBlocks() {
			return ModRegistry.BLOCKS.getEntries().stream().map(net.minecraftforge.registries.RegistryObject::get)::iterator;
		}
	}

	private static class ChocoEntityLoot extends EntityLoot {
		@Override
		protected void addTables() {
			this.add(ModEntities.CHOCOBO.get(), LootTable.lootTable()
					.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(ModRegistry.CHOCOBO_FEATHER.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F))).apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))))
					.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(ModRegistry.CHOCOBO_DRUMSTICK_RAW.get()).apply(SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE))).apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))));
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