package net.chococraft.fabric.datagen.data;

import net.chococraft.common.blocks.GysahlGreenBlock;
import net.chococraft.registry.ModEntities;
import net.chococraft.registry.ModRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricEntityLootSubProvider;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.concurrent.CompletableFuture;

public class ChocoLoot {

	public static class ChocoBlockLoot extends FabricBlockLootSubProvider {

		public ChocoBlockLoot(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
			super(dataOutput, registryLookup);
		}

		@Override
		public void generate() {
			HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
			this.dropSelf(ModRegistry.STRAW.get());
			LootItemCondition.Builder condition = LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModRegistry.GYSAHL_GREEN.get())
					.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(GysahlGreenBlock.AGE, GysahlGreenBlock.MAX_AGE));
			this.add(ModRegistry.GYSAHL_GREEN.get(), applyExplosionDecay(ModRegistry.GYSAHL_GREEN.get(), LootTable.lootTable().withPool(LootPool.lootPool()
					.add(LootItem.lootTableItem(ModRegistry.GYSAHL_GREEN.get()))).withPool(LootPool.lootPool().when(condition)
					.add(LootItem.lootTableItem(ModRegistry.GYSAHL_GREEN_ITEM.get())
							.apply(ApplyBonusCount.addBonusBinomialDistributionCount(registrylookup.getOrThrow(Enchantments.FORTUNE), 0.5714286F, 3)))).withPool(LootPool.lootPool().when(condition)
					.add(LootItem.lootTableItem(ModRegistry.LOVERLY_GYSAHL_GREEN.get()).when(LootItemRandomChanceCondition.randomChance(0.15F)))
					.add(LootItem.lootTableItem(ModRegistry.GOLD_GYSAHL.get()).when(LootItemRandomChanceCondition.randomChance(0.05F))))
			));
		}
	}

	public static class ChocoEntityLoot extends FabricEntityLootSubProvider {
		public ChocoEntityLoot(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
			super(dataOutput, registryLookup);
		}

		@Override
		public void generate() {
			this.add(ModEntities.CHOCOBO.get(), LootTable.lootTable()
					.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
							.add(LootItem.lootTableItem(ModRegistry.CHOCOBO_FEATHER.get())
									.apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
									.apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
							)
					)
					.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
							.add(LootItem.lootTableItem(ModRegistry.CHOCOBO_DRUMSTICK_RAW.get())
									.apply(SmeltItemFunction.smelted().when(this.shouldSmeltLoot()))
									.apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
							)
					)
			);
		}
	}
}