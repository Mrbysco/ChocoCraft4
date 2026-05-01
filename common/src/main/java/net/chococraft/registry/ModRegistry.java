package net.chococraft.registry;

import dev.chococraft.registration.RegistrationProvider;
import dev.chococraft.registration.RegistryObject;
import net.chococraft.Chococraft;
import net.chococraft.common.blocks.GysahlGreenBlock;
import net.chococraft.common.blocks.StrawBlock;
import net.chococraft.common.entity.properties.ChocoboColor;
import net.chococraft.common.items.ChocoboSaddleItem;
import net.chococraft.common.items.ChocoboSpawnEggItem;
import net.chococraft.common.items.ChocopediaItem;
import net.chococraft.common.items.CustomBlockNamedItem;
import net.chococraft.common.items.armor.AbstractChocoDisguiseItem;
import net.chococraft.common.items.armor.ModArmorMaterial;
import net.chococraft.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

public class ModRegistry {
	public static final RegistrationProvider<Block> BLOCKS = RegistrationProvider.get(BuiltInRegistries.BLOCK, Chococraft.MOD_ID);
	public static final RegistrationProvider<Item> ITEMS = RegistrationProvider.get(BuiltInRegistries.ITEM, Chococraft.MOD_ID);
	public static final RegistrationProvider<CreativeModeTab> CREATIVE_MODE_TABS = RegistrationProvider.get(BuiltInRegistries.CREATIVE_MODE_TAB, Chococraft.MOD_ID);

	public static final RegistryObject<Block, GysahlGreenBlock> GYSAHL_GREEN = BLOCKS.register("gysahl_green",
			() -> new GysahlGreenBlock(blockBuilder("gysahl_green").mapColor(MapColor.GRASS).noCollision().randomTicks().instabreak().sound(SoundType.CROP)));

	public static final RegistryObject<Block, StrawBlock> STRAW = BLOCKS.register("straw",
			() -> new StrawBlock(blockBuilder("straw").mapColor(MapColor.COLOR_YELLOW).sound(SoundType.GRASS)));

	public static final RegistryObject<Item, ChocoboSaddleItem> CHOCOBO_SADDLE = ITEMS.register("chocobo_saddle", () -> new ChocoboSaddleItem(itemBuilder("chocobo_saddle"), 0));
	public static final RegistryObject<Item, ChocoboSaddleItem> CHOCOBO_SADDLE_BAGS = ITEMS.register("chocobo_saddle_bags", () -> new ChocoboSaddleItem(itemBuilder("chocobo_saddle_bags"), 18));
	public static final RegistryObject<Item, ChocoboSaddleItem> CHOCOBO_SADDLE_PACK = ITEMS.register("chocobo_saddle_pack", () -> new ChocoboSaddleItem(itemBuilder("chocobo_saddle_pack"), 45));

	public static final RegistryObject<Item, ChocoboSpawnEggItem> YELLOW_CHOCOBO_SPAWN_EGG = ITEMS.register("yellow_chocobo_spawn_egg", () -> new ChocoboSpawnEggItem(itemBuilder("yellow_chocobo_spawn_egg"), ChocoboColor.YELLOW));
	public static final RegistryObject<Item, ChocoboSpawnEggItem> GREEN_CHOCOBO_SPAWN_EGG = ITEMS.register("green_chocobo_spawn_egg", () -> new ChocoboSpawnEggItem(itemBuilder("green_chocobo_spawn_egg"), ChocoboColor.GREEN));
	public static final RegistryObject<Item, ChocoboSpawnEggItem> BLUE_CHOCOBO_SPAWN_EGG = ITEMS.register("blue_chocobo_spawn_egg", () -> new ChocoboSpawnEggItem(itemBuilder("blue_chocobo_spawn_egg"), ChocoboColor.BLUE));
	public static final RegistryObject<Item, ChocoboSpawnEggItem> WHITE_CHOCOBO_SPAWN_EGG = ITEMS.register("white_chocobo_spawn_egg", () -> new ChocoboSpawnEggItem(itemBuilder("white_chocobo_spawn_egg"), ChocoboColor.WHITE));
	public static final RegistryObject<Item, ChocoboSpawnEggItem> BLACK_CHOCOBO_SPAWN_EGG = ITEMS.register("black_chocobo_spawn_egg", () -> new ChocoboSpawnEggItem(itemBuilder("black_chocobo_spawn_egg"), ChocoboColor.BLACK));
	public static final RegistryObject<Item, ChocoboSpawnEggItem> GOLD_CHOCOBO_SPAWN_EGG = ITEMS.register("gold_chocobo_spawn_egg", () -> new ChocoboSpawnEggItem(itemBuilder("gold_chocobo_spawn_egg"), ChocoboColor.GOLD));
	public static final RegistryObject<Item, ChocoboSpawnEggItem> PINK_CHOCOBO_SPAWN_EGG = ITEMS.register("pink_chocobo_spawn_egg", () -> new ChocoboSpawnEggItem(itemBuilder("pink_chocobo_spawn_egg"), ChocoboColor.PINK));
	public static final RegistryObject<Item, ChocoboSpawnEggItem> RED_CHOCOBO_SPAWN_EGG = ITEMS.register("red_chocobo_spawn_egg", () -> new ChocoboSpawnEggItem(itemBuilder("red_chocobo_spawn_egg"), ChocoboColor.RED));
	public static final RegistryObject<Item, ChocoboSpawnEggItem> PURPLE_CHOCOBO_SPAWN_EGG = ITEMS.register("purple_chocobo_spawn_egg", () -> new ChocoboSpawnEggItem(itemBuilder("purple_chocobo_spawn_egg"), ChocoboColor.PURPLE));
	public static final RegistryObject<Item, ChocoboSpawnEggItem> FLAME_CHOCOBO_SPAWN_EGG = ITEMS.register("flame_chocobo_spawn_egg", () -> new ChocoboSpawnEggItem(itemBuilder("flame_chocobo_spawn_egg"), ChocoboColor.FLAME));


	public static final RegistryObject<Item, CustomBlockNamedItem> GYSAHL_GREEN_SEEDS = ITEMS.register("gysahl_green_seeds", () -> new CustomBlockNamedItem(ModRegistry.GYSAHL_GREEN, itemBuilder("gysahl_green_seeds")));

	public static final RegistryObject<Item, Item> GYSAHL_GREEN_ITEM = ITEMS.register("gysahl_green", () -> new Item(itemBuilder("gysahl_green").food(ModFoods.GYSAHL_GREEN)));
	public static final RegistryObject<Item, Item> CHOCOBO_WHISTLE = ITEMS.register("chocobo_whistle", () -> new Item(itemBuilder("chocobo_whistle")));
	public static final RegistryObject<Item, Item> CHOCOBO_FEATHER = ITEMS.register("chocobo_feather", () -> new Item(itemBuilder("chocobo_feather")));
	public static final RegistryObject<Item, Item> LOVERLY_GYSAHL_GREEN = ITEMS.register("loverly_gysahl_green", () -> new Item(itemBuilder("loverly_gysahl_green")));
	public static final RegistryObject<Item, Item> GOLD_GYSAHL = ITEMS.register("gold_gysahl", () -> new Item(itemBuilder("gold_gysahl")));
	public static final RegistryObject<Item, Item> RED_GYSAHL = ITEMS.register("red_gysahl", () -> new Item(itemBuilder("red_gysahl")));
	public static final RegistryObject<Item, Item> PINK_GYSAHL = ITEMS.register("pink_gysahl", () -> new Item(itemBuilder("pink_gysahl")));

	public static final RegistryObject<Item, Item> CHOCOBO_DRUMSTICK_RAW = ITEMS.register("chocobo_drumstick_raw", () -> new Item(itemBuilder("chocobo_drumstick_raw").food(ModFoods.CHOCOBO_DRUMSTICK_RAW)));
	public static final RegistryObject<Item, Item> CHOCOBO_DRUMSTICK_COOKED = ITEMS.register("chocobo_drumstick_cooked", () -> new Item(itemBuilder("chocobo_drumstick_cooked").food(ModFoods.CHOCOBO_DRUMSTICK_COOKED)));
	public static final RegistryObject<Item, Item> PICKLED_GYSAHL_RAW = ITEMS.register("pickled_gysahl_raw", () -> new Item(itemBuilder("pickled_gysahl_raw").food(ModFoods.PICKLED_GYSAHL_RAW)));
	public static final RegistryObject<Item, Item> PICKLED_GYSAHL_COOKED = ITEMS.register("pickled_gysahl_cooked", () -> new Item(itemBuilder("pickled_gysahl_cooked").food(ModFoods.PICKLED_GYSAHL_COOKED)));

	public static final RegistryObject<Item, ChocopediaItem> CHOCOPEDIA = ITEMS.register("chocopedia", () -> new ChocopediaItem(itemBuilder("chocopedia")));
	public static final RegistryObject<Item, Item> GYSAHL_CAKE = ITEMS.register("gysahl_cake", () -> new Item(itemBuilder("gysahl_cake").stacksTo(8)));

	public static final RegistryObject<Item, AbstractChocoDisguiseItem> CHOCO_DISGUISE_HELMET = ITEMS.register("choco_disguise_helmet", () -> Services.PLATFORM.constructChocoDisguise(ModArmorMaterial.CHOCO_DISGUISE, ArmorType.HELMET, itemBuilder("choco_disguise_helmet")));
	public static final RegistryObject<Item, AbstractChocoDisguiseItem> CHOCO_DISGUISE_CHESTPLATE = ITEMS.register("choco_disguise_chestplate", () -> Services.PLATFORM.constructChocoDisguise(ModArmorMaterial.CHOCO_DISGUISE, ArmorType.CHESTPLATE, itemBuilder("choco_disguise_chestplate")));
	public static final RegistryObject<Item, AbstractChocoDisguiseItem> CHOCO_DISGUISE_LEGGINGS = ITEMS.register("choco_disguise_leggings", () -> Services.PLATFORM.constructChocoDisguise(ModArmorMaterial.CHOCO_DISGUISE, ArmorType.LEGGINGS, itemBuilder("choco_disguise_leggings")));
	public static final RegistryObject<Item, AbstractChocoDisguiseItem> CHOCO_DISGUISE_BOOTS = ITEMS.register("choco_disguise_boots", () -> Services.PLATFORM.constructChocoDisguise(ModArmorMaterial.CHOCO_DISGUISE, ArmorType.BOOTS, itemBuilder("choco_disguise_boots")));

	//Regular block items
	public static final RegistryObject<Item, BlockItem> STRAW_ITEM = ITEMS.register("straw", () -> new BlockItem(STRAW.get(), itemBuilder("straw")));

	public static final RegistryObject<CreativeModeTab, CreativeModeTab> CREATIVE_TAB = CREATIVE_MODE_TABS.register("tab", Services.PLATFORM::buildCreativeTab);


	private static Item.Properties itemBuilder(String name) {
		return new Item.Properties().setId(ResourceKey.create(Registries.ITEM, Chococraft.modLoc(name)));
	}

	private static BlockBehaviour.Properties blockBuilder(String name) {
		return BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, Chococraft.modLoc(name)));
	}

	public static void load() {
		// Load class
	}
}
