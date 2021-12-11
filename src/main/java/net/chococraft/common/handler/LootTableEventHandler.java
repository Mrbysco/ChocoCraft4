package net.chococraft.common.handler;

import net.chococraft.Chococraft;
import net.chococraft.common.ChocoConfig;
import net.chococraft.common.init.ModRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Chococraft.MODID)
public class LootTableEventHandler {

    @SubscribeEvent
    public static void onLootTableLoad(LootTableLoadEvent event) {
        if (!ChocoConfig.COMMON.addAbilityFruitsToDungeonLoot.get()) return;

        ResourceLocation lootTable = event.getName();
        if (!lootTable.getPath().startsWith("chests/")) return;

        event.getTable().addPool(getInjectPool());
    }

    public static LootPool getInjectPool() {
        LootPool.Builder builder = LootPool.lootPool();

        builder.add(injectFruit(ModRegistry.SPIKE_FRUIT.get()));
        builder.add(injectFruit(ModRegistry.AEROSHROOM.get()));
        builder.add(injectFruit(ModRegistry.AQUA_BERRY.get()));
        builder.add(injectFruit(ModRegistry.DEAD_PEPPER.get()));

        builder.setBonusRolls(UniformGenerator.between(0, 1))
                .name("ability_fruits");

        return builder.build();
    }

    private static LootPoolEntryContainer.Builder injectFruit(Item item) {
        return LootItem.lootTableItem(item)
                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                .setQuality(1)
                .setWeight(ChocoConfig.COMMON.abilityFruitDungeonLootWeight.get());
    }
}
