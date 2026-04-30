package net.chococraft.registry;

import dev.chococraft.registration.RegistrationProvider;
import dev.chococraft.registration.specialised.BlockRegistrationProvider;
import dev.chococraft.registration.specialised.BlockRegistryObject;
import dev.chococraft.registration.specialised.ItemRegistrationProvider;
import dev.chococraft.registration.specialised.ItemRegistryObject;
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
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import java.util.function.Supplier;

public class ModRegistry {
	public static final BlockRegistrationProvider BLOCKS = BlockRegistrationProvider.get(Chococraft.MOD_ID);
	public static final ItemRegistrationProvider ITEMS = ItemRegistrationProvider.get(Chococraft.MOD_ID);
	public static final RegistrationProvider<CreativeModeTab> CREATIVE_MODE_TABS = RegistrationProvider.get(BuiltInRegistries.CREATIVE_MODE_TAB, Chococraft.MOD_ID);

	public static final BlockRegistryObject<GysahlGreenBlock> GYSAHL_GREEN = BLOCKS.register("gysahl_green", blockBuilder("gysahl_green"), (properties) ->
			new GysahlGreenBlock(properties.mapColor(MapColor.GRASS).noCollision().randomTicks().instabreak().sound(SoundType.CROP)));

	public static final BlockRegistryObject<StrawBlock> STRAW = BLOCKS.register("straw", blockBuilder("straw"), (properties) ->
			new StrawBlock(properties.mapColor(MapColor.COLOR_YELLOW).sound(SoundType.GRASS)));

	public static final ItemRegistryObject<ChocoboSaddleItem> CHOCOBO_SADDLE = ITEMS.register("chocobo_saddle", itemBuilder("chocobo_saddle"), (properties) -> new ChocoboSaddleItem(properties, 0));
	public static final ItemRegistryObject<ChocoboSaddleItem> CHOCOBO_SADDLE_BAGS = ITEMS.register("chocobo_saddle_bags", itemBuilder("chocobo_saddle_bags"), (properties) -> new ChocoboSaddleItem(properties, 18));
	public static final ItemRegistryObject<ChocoboSaddleItem> CHOCOBO_SADDLE_PACK = ITEMS.register("chocobo_saddle_pack", itemBuilder("chocobo_saddle_pack"), (properties) -> new ChocoboSaddleItem(properties, 45));

	public static final ItemRegistryObject<ChocoboSpawnEggItem> YELLOW_CHOCOBO_SPAWN_EGG = ITEMS.register("yellow_chocobo_spawn_egg", itemBuilder("yellow_chocobo_spawn_egg"), (properties) -> new ChocoboSpawnEggItem(properties, ChocoboColor.YELLOW));
	public static final ItemRegistryObject<ChocoboSpawnEggItem> GREEN_CHOCOBO_SPAWN_EGG = ITEMS.register("green_chocobo_spawn_egg", itemBuilder("green_chocobo_spawn_egg"), (properties) -> new ChocoboSpawnEggItem(properties, ChocoboColor.GREEN));
	public static final ItemRegistryObject<ChocoboSpawnEggItem> BLUE_CHOCOBO_SPAWN_EGG = ITEMS.register("blue_chocobo_spawn_egg", itemBuilder("blue_chocobo_spawn_egg"), (properties) -> new ChocoboSpawnEggItem(properties, ChocoboColor.BLUE));
	public static final ItemRegistryObject<ChocoboSpawnEggItem> WHITE_CHOCOBO_SPAWN_EGG = ITEMS.register("white_chocobo_spawn_egg", itemBuilder("white_chocobo_spawn_egg"), (properties) -> new ChocoboSpawnEggItem(properties, ChocoboColor.WHITE));
	public static final ItemRegistryObject<ChocoboSpawnEggItem> BLACK_CHOCOBO_SPAWN_EGG = ITEMS.register("black_chocobo_spawn_egg", itemBuilder("black_chocobo_spawn_egg"), (properties) -> new ChocoboSpawnEggItem(properties, ChocoboColor.BLACK));
	public static final ItemRegistryObject<ChocoboSpawnEggItem> GOLD_CHOCOBO_SPAWN_EGG = ITEMS.register("gold_chocobo_spawn_egg", itemBuilder("gold_chocobo_spawn_egg"), (properties) -> new ChocoboSpawnEggItem(properties, ChocoboColor.GOLD));
	public static final ItemRegistryObject<ChocoboSpawnEggItem> PINK_CHOCOBO_SPAWN_EGG = ITEMS.register("pink_chocobo_spawn_egg", itemBuilder("pink_chocobo_spawn_egg"), (properties) -> new ChocoboSpawnEggItem(properties, ChocoboColor.PINK));
	public static final ItemRegistryObject<ChocoboSpawnEggItem> RED_CHOCOBO_SPAWN_EGG = ITEMS.register("red_chocobo_spawn_egg", itemBuilder("red_chocobo_spawn_egg"), (properties) -> new ChocoboSpawnEggItem(properties, ChocoboColor.RED));
	public static final ItemRegistryObject<ChocoboSpawnEggItem> PURPLE_CHOCOBO_SPAWN_EGG = ITEMS.register("purple_chocobo_spawn_egg", itemBuilder("purple_chocobo_spawn_egg"), (properties) -> new ChocoboSpawnEggItem(properties, ChocoboColor.PURPLE));
	public static final ItemRegistryObject<ChocoboSpawnEggItem> FLAME_CHOCOBO_SPAWN_EGG = ITEMS.register("flame_chocobo_spawn_egg", itemBuilder("flame_chocobo_spawn_egg"), (properties) -> new ChocoboSpawnEggItem(properties, ChocoboColor.FLAME));


	public static final ItemRegistryObject<CustomBlockNamedItem> GYSAHL_GREEN_SEEDS = ITEMS.register("gysahl_green_seeds", itemBuilder("gysahl_green_seeds"), (properties) -> new CustomBlockNamedItem(ModRegistry.GYSAHL_GREEN, properties));

	public static final ItemRegistryObject<Item> GYSAHL_GREEN_ITEM = ITEMS.register("gysahl_green", itemBuilder("gysahl_green").food(ModFoods.GYSAHL_GREEN), Item::new);
	public static final ItemRegistryObject<Item> CHOCOBO_WHISTLE = ITEMS.register("chocobo_whistle", itemBuilder("chocobo_whistle"), Item::new);
	public static final ItemRegistryObject<Item> CHOCOBO_FEATHER = ITEMS.register("chocobo_feather", itemBuilder("chocobo_feather"), Item::new);
	public static final ItemRegistryObject<Item> LOVERLY_GYSAHL_GREEN = ITEMS.register("loverly_gysahl_green", itemBuilder("loverly_gysahl_green"), Item::new);
	public static final ItemRegistryObject<Item> GOLD_GYSAHL = ITEMS.register("gold_gysahl", itemBuilder("gold_gysahl"), Item::new);
	public static final ItemRegistryObject<Item> RED_GYSAHL = ITEMS.register("red_gysahl", itemBuilder("red_gysahl"), Item::new);
	public static final ItemRegistryObject<Item> PINK_GYSAHL = ITEMS.register("pink_gysahl", itemBuilder("pink_gysahl"), Item::new);

	public static final ItemRegistryObject<Item> CHOCOBO_DRUMSTICK_RAW = ITEMS.register("chocobo_drumstick_raw", itemBuilder("chocobo_drumstick_raw").food(ModFoods.CHOCOBO_DRUMSTICK_RAW), Item::new);
	public static final ItemRegistryObject<Item> CHOCOBO_DRUMSTICK_COOKED = ITEMS.register("chocobo_drumstick_cooked", itemBuilder("chocobo_drumstick_cooked").food(ModFoods.CHOCOBO_DRUMSTICK_COOKED), Item::new);
	public static final ItemRegistryObject<Item> PICKLED_GYSAHL_RAW = ITEMS.register("pickled_gysahl_raw", itemBuilder("pickled_gysahl_raw").food(ModFoods.PICKLED_GYSAHL_RAW), Item::new);
	public static final ItemRegistryObject<Item> PICKLED_GYSAHL_COOKED = ITEMS.register("pickled_gysahl_cooked", itemBuilder("pickled_gysahl_cooked").food(ModFoods.PICKLED_GYSAHL_COOKED), Item::new);

	public static final ItemRegistryObject<ChocopediaItem> CHOCOPEDIA = ITEMS.register("chocopedia", itemBuilder("chocopedia"), ChocopediaItem::new);
	public static final ItemRegistryObject<Item> GYSAHL_CAKE = ITEMS.register("gysahl_cake", itemBuilder("gysahl_cake"), (properties) -> new Item(properties.stacksTo(8)));

	public static final ItemRegistryObject<AbstractChocoDisguiseItem> CHOCO_DISGUISE_HELMET = ITEMS.register("choco_disguise_helmet", itemBuilder("choco_disguise_helmet"), (properties) -> Services.PLATFORM.constructChocoDisguise(ModArmorMaterial.CHOCO_DISGUISE, ArmorType.HELMET, properties));
	public static final ItemRegistryObject<AbstractChocoDisguiseItem> CHOCO_DISGUISE_CHESTPLATE = ITEMS.register("choco_disguise_chestplate", itemBuilder("choco_disguise_chestplate"), (properties) -> Services.PLATFORM.constructChocoDisguise(ModArmorMaterial.CHOCO_DISGUISE, ArmorType.CHESTPLATE, properties));
	public static final ItemRegistryObject<AbstractChocoDisguiseItem> CHOCO_DISGUISE_LEGGINGS = ITEMS.register("choco_disguise_leggings", itemBuilder("choco_disguise_leggings"), (properties) -> Services.PLATFORM.constructChocoDisguise(ModArmorMaterial.CHOCO_DISGUISE, ArmorType.LEGGINGS, properties));
	public static final ItemRegistryObject<AbstractChocoDisguiseItem> CHOCO_DISGUISE_BOOTS = ITEMS.register("choco_disguise_boots", itemBuilder("choco_disguise_boots"), (properties) -> Services.PLATFORM.constructChocoDisguise(ModArmorMaterial.CHOCO_DISGUISE, ArmorType.BOOTS, properties));

	//Regular block items
	public static final ItemRegistryObject<BlockItem> STRAW_ITEM = ITEMS.registerBlockItem(STRAW, itemBuilder("straw"));

	public static final Supplier<CreativeModeTab> CREATIVE_TAB = CREATIVE_MODE_TABS.register("tab", Services.PLATFORM::buildCreativeTab);


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
