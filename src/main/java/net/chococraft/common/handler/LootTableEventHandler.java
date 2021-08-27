package net.chococraft.common.handler;

import net.chococraft.Chococraft;
import net.chococraft.common.ChocoConfig;
import net.chococraft.common.init.ModRegistry;
import net.minecraft.item.Item;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.util.ResourceLocation;
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
        LootPool.Builder builder = LootPool.builder();

        builder.addEntry(injectFruit(ModRegistry.SPIKE_FRUIT.get()));
        builder.addEntry(injectFruit(ModRegistry.AEROSHROOM.get()));
        builder.addEntry(injectFruit(ModRegistry.AQUA_BERRY.get()));
        builder.addEntry(injectFruit(ModRegistry.DEAD_PEPPER.get()));

        builder.bonusRolls(0, 1)
                .name("ability_fruits");

        return builder.build();
    }

    private static LootEntry.Builder injectFruit(Item item) {
        LootEntry.Builder<?> entry = ItemLootEntry.builder(item)
                .acceptFunction(SetCount.builder(new RandomValueRange(1, 1)))
                .quality(1)
                .weight(ChocoConfig.COMMON.abilityFruitDungeonLootWeight.get());

        return entry;
    }
}
