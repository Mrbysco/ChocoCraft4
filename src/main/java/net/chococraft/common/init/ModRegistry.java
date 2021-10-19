package net.chococraft.common.init;

import net.chococraft.Chococraft;
import net.chococraft.common.blockentities.ChocoboEggBlockEntity;
import net.chococraft.common.blockentities.ChocoboNestBlockEntity;
import net.chococraft.common.blocks.ChocoboEggBlock;
import net.chococraft.common.blocks.GysahlGreenBlock;
import net.chococraft.common.blocks.StrawNestBlock;
import net.chococraft.common.entities.properties.ChocoboColor;
import net.chococraft.common.items.AbilityFruitItem;
import net.chococraft.common.items.ChocoboEggBlockItem;
import net.chococraft.common.items.ChocoboSaddleItem;
import net.chococraft.common.items.ChocoboSpawnEggItem;
import net.chococraft.common.items.ChocopediaItem;
import net.chococraft.common.items.CustomBlockNamedItem;
import net.chococraft.common.items.enums.AbilityFruitType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Chococraft.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Chococraft.MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Chococraft.MODID);

    public static final RegistryObject<Block> GYSAHL_GREEN = BLOCKS.register("gysahl_green", () ->
            new GysahlGreenBlock(Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP)));

    public static final RegistryObject<Block> STRAW_NEST = BLOCKS.register("straw_nest", () ->
            new StrawNestBlock(Properties.of(Material.STONE).sound(SoundType.GRASS)));

    public static final RegistryObject<Block> CHOCOBO_EGG = BLOCKS.register("chocobo_egg", () ->
            new ChocoboEggBlock(Properties.of(Material.EGG).strength(0.5F).noOcclusion().sound(SoundType.GRASS)));


    public static final RegistryObject<BlockEntityType<ChocoboNestBlockEntity>> STRAW_NEST_TILE = BLOCK_ENTITIES.register("chocobo_nest", () -> BlockEntityType.Builder.of(
            ChocoboNestBlockEntity::new, ModRegistry.STRAW_NEST.get()).build(null));

    public static final RegistryObject<BlockEntityType<ChocoboEggBlockEntity>> CHOCOBO_EGG_TILE = BLOCK_ENTITIES.register("chocobo_egg", () -> BlockEntityType.Builder.of(
            ChocoboEggBlockEntity::new, ModRegistry.CHOCOBO_EGG.get()).build(null));


    public static final RegistryObject<Item> CHOCOBO_SADDLE = ITEMS.register("chocobo_saddle", () -> new ChocoboSaddleItem(itemBuilder(), 0));
    public static final RegistryObject<Item> CHOCOBO_SADDLE_BAGS = ITEMS.register("chocobo_saddle_bags", () -> new ChocoboSaddleItem(itemBuilder(), 18));
    public static final RegistryObject<Item> CHOCOBO_SADDLE_PACK = ITEMS.register("chocobo_saddle_pack", () -> new ChocoboSaddleItem(itemBuilder(), 54));

    public static final RegistryObject<Item> YELLOW_CHOCOBO_SPAWN_EGG = ITEMS.register("yellow_chocobo_spawn_egg", () -> new ChocoboSpawnEggItem(itemBuilder(), ChocoboColor.YELLOW));
    public static final RegistryObject<Item> GREEN_CHOCOBO_SPAWN_EGG = ITEMS.register("green_chocobo_spawn_egg", () -> new ChocoboSpawnEggItem(itemBuilder(), ChocoboColor.GREEN));
    public static final RegistryObject<Item> BLUE_CHOCOBO_SPAWN_EGG = ITEMS.register("blue_chocobo_spawn_egg", () -> new ChocoboSpawnEggItem(itemBuilder(), ChocoboColor.BLUE));
    public static final RegistryObject<Item> WHITE_CHOCOBO_SPAWN_EGG = ITEMS.register("white_chocobo_spawn_egg", () -> new ChocoboSpawnEggItem(itemBuilder(), ChocoboColor.WHITE));
    public static final RegistryObject<Item> BLACK_CHOCOBO_SPAWN_EGG = ITEMS.register("black_chocobo_spawn_egg", () -> new ChocoboSpawnEggItem(itemBuilder(), ChocoboColor.BLACK));
    public static final RegistryObject<Item> GOLD_CHOCOBO_SPAWN_EGG = ITEMS.register("gold_chocobo_spawn_egg", () -> new ChocoboSpawnEggItem(itemBuilder(), ChocoboColor.GOLD));
    public static final RegistryObject<Item> PINK_CHOCOBO_SPAWN_EGG = ITEMS.register("pink_chocobo_spawn_egg", () -> new ChocoboSpawnEggItem(itemBuilder(), ChocoboColor.PINK));
    public static final RegistryObject<Item> RED_CHOCOBO_SPAWN_EGG = ITEMS.register("red_chocobo_spawn_egg", () -> new ChocoboSpawnEggItem(itemBuilder(), ChocoboColor.RED));
    public static final RegistryObject<Item> PURPLE_CHOCOBO_SPAWN_EGG = ITEMS.register("purple_chocobo_spawn_egg", () -> new ChocoboSpawnEggItem(itemBuilder(), ChocoboColor.PURPLE));
    public static final RegistryObject<Item> FLAME_CHOCOBO_SPAWN_EGG = ITEMS.register("flame_chocobo_spawn_egg", () -> new ChocoboSpawnEggItem(itemBuilder(), ChocoboColor.FLAME));

    public static final RegistryObject<Item> SPIKE_FRUIT = ITEMS.register("spike_fruit", () -> new AbilityFruitItem(itemBuilder(), AbilityFruitType.SPRINT));
    public static final RegistryObject<Item> AEROSHROOM = ITEMS.register("aeroshroom", () -> new AbilityFruitItem(itemBuilder(), AbilityFruitType.GLIDE));
    public static final RegistryObject<Item> AQUA_BERRY = ITEMS.register("aqua_berry", () -> new AbilityFruitItem(itemBuilder(), AbilityFruitType.DIVE));
    public static final RegistryObject<Item> DEAD_PEPPER = ITEMS.register("dead_pepper", () -> new AbilityFruitItem(itemBuilder(), AbilityFruitType.FLY));

    public static final RegistryObject<Item> GYSAHL_GREEN_SEEDS = ITEMS.register("gysahl_green_seeds", () -> new CustomBlockNamedItem(() -> ModRegistry.GYSAHL_GREEN.get(), itemBuilder()));

    public static final RegistryObject<Item> GYSAHL_GREEN_ITEM = ITEMS.register("gysahl_green", () -> new Item(itemBuilder().food(ModFoods.GYSAHL_GREEN)));
    public static final RegistryObject<Item> CHOCOBO_WHISTLE = ITEMS.register("chocobo_whistle", () -> new Item(itemBuilder()));
    public static final RegistryObject<Item> CHOCOBO_FEATHER = ITEMS.register("chocobo_feather", () -> new Item(itemBuilder()));
    public static final RegistryObject<Item> LOVELY_GYSAHL_GREEN = ITEMS.register("lovely_gysahl_green", () -> new Item(itemBuilder()));

    public static final RegistryObject<Item> CHOCOBO_DRUMSTICK_RAW = ITEMS.register("chocobo_drumstick_raw", () -> new Item(itemBuilder().food(ModFoods.CHOCOBO_DRUMSTICK_RAW)));
    public static final RegistryObject<Item> CHOCOBO_DRUMSTICK_COOKED = ITEMS.register("chocobo_drumstick_cooked", () -> new Item(itemBuilder().food(ModFoods.CHOCOBO_DRUMSTICK_COOKED)));
    public static final RegistryObject<Item> PICKLED_GYSAHL_RAW = ITEMS.register("pickled_gysahl_raw", () -> new Item(itemBuilder().food(ModFoods.PICKLED_GYSAHL_RAW)));
    public static final RegistryObject<Item> PICKLED_GYSAHL_COOKED = ITEMS.register("pickled_gysahl_cooked", () -> new Item(itemBuilder().food(ModFoods.PICKLED_GYSAHL_COOKED)));

    public static final RegistryObject<Item> CHOCOPEDIA = ITEMS.register("chocopedia", () -> new ChocopediaItem(itemBuilder()));

    //Regular block items
    public static final RegistryObject<Item> STRAW_NEST_ITEM = ITEMS.register("straw_nest", () -> new BlockItem(STRAW_NEST.get(), itemBuilder()));
    public static final RegistryObject<Item> CHOCOBO_EGG_ITEM = ITEMS.register("chocobo_egg", () -> new ChocoboEggBlockItem(CHOCOBO_EGG.get(), itemBuilder()));


    private static Item.Properties itemBuilder() {
        return new Item.Properties().tab(Chococraft.creativeTab);
    }
}
