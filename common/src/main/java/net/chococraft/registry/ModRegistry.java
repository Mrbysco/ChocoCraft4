package net.chococraft.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.chococraft.Chococraft;
import net.chococraft.common.blocks.GysahlGreenBlock;
import net.chococraft.common.blocks.StrawBlock;
import net.chococraft.common.entity.properties.ChocoboColor;
import net.chococraft.common.items.ChocoboSaddleItem;
import net.chococraft.common.items.ChocoboSpawnEggItem;
import net.chococraft.common.items.ChocopediaItem;
import net.chococraft.common.items.CustomBlockNamedItem;
import net.chococraft.common.items.armor.ModArmorMaterial;
import net.chococraft.platform.Services;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import java.util.function.Function;

public class ModRegistry {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Chococraft.MOD_ID, Registries.BLOCK);
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Chococraft.MOD_ID, Registries.ITEM);
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS
			= DeferredRegister.create(Chococraft.MOD_ID, Registries.CREATIVE_MODE_TAB);

	public static final RegistrySupplier<Block> GYSAHL_GREEN = registerBlock("gysahl_green", (properties) ->
			new GysahlGreenBlock(properties.mapColor(MapColor.GRASS).noCollision().randomTicks().instabreak().sound(SoundType.CROP)), blockBuilder());

	public static final RegistrySupplier<Block> STRAW = registerBlock("straw", (properties) ->
			new StrawBlock(properties.mapColor(MapColor.COLOR_YELLOW).sound(SoundType.GRASS)), blockBuilder());

	public static final RegistrySupplier<Item> CHOCOBO_SADDLE = registerItem("chocobo_saddle", (properties) -> new ChocoboSaddleItem(properties, 0), itemBuilder());
	public static final RegistrySupplier<Item> CHOCOBO_SADDLE_BAGS = registerItem("chocobo_saddle_bags", (properties) -> new ChocoboSaddleItem(properties, 18), itemBuilder());
	public static final RegistrySupplier<Item> CHOCOBO_SADDLE_PACK = registerItem("chocobo_saddle_pack", (properties) -> new ChocoboSaddleItem(properties, 45), itemBuilder());

	public static final RegistrySupplier<Item> YELLOW_CHOCOBO_SPAWN_EGG = registerItem("yellow_chocobo_spawn_egg", (properties) -> new ChocoboSpawnEggItem(properties, ChocoboColor.YELLOW), itemBuilder());
	public static final RegistrySupplier<Item> GREEN_CHOCOBO_SPAWN_EGG = registerItem("green_chocobo_spawn_egg", (properties) -> new ChocoboSpawnEggItem(properties, ChocoboColor.GREEN), itemBuilder());
	public static final RegistrySupplier<Item> BLUE_CHOCOBO_SPAWN_EGG = registerItem("blue_chocobo_spawn_egg", (properties) -> new ChocoboSpawnEggItem(properties, ChocoboColor.BLUE), itemBuilder());
	public static final RegistrySupplier<Item> WHITE_CHOCOBO_SPAWN_EGG = registerItem("white_chocobo_spawn_egg", (properties) -> new ChocoboSpawnEggItem(properties, ChocoboColor.WHITE), itemBuilder());
	public static final RegistrySupplier<Item> BLACK_CHOCOBO_SPAWN_EGG = registerItem("black_chocobo_spawn_egg", (properties) -> new ChocoboSpawnEggItem(properties, ChocoboColor.BLACK), itemBuilder());
	public static final RegistrySupplier<Item> GOLD_CHOCOBO_SPAWN_EGG = registerItem("gold_chocobo_spawn_egg", (properties) -> new ChocoboSpawnEggItem(properties, ChocoboColor.GOLD), itemBuilder());
	public static final RegistrySupplier<Item> PINK_CHOCOBO_SPAWN_EGG = registerItem("pink_chocobo_spawn_egg", (properties) -> new ChocoboSpawnEggItem(properties, ChocoboColor.PINK), itemBuilder());
	public static final RegistrySupplier<Item> RED_CHOCOBO_SPAWN_EGG = registerItem("red_chocobo_spawn_egg", (properties) -> new ChocoboSpawnEggItem(properties, ChocoboColor.RED), itemBuilder());
	public static final RegistrySupplier<Item> PURPLE_CHOCOBO_SPAWN_EGG = registerItem("purple_chocobo_spawn_egg", (properties) -> new ChocoboSpawnEggItem(properties, ChocoboColor.PURPLE), itemBuilder());
	public static final RegistrySupplier<Item> FLAME_CHOCOBO_SPAWN_EGG = registerItem("flame_chocobo_spawn_egg", (properties) -> new ChocoboSpawnEggItem(properties, ChocoboColor.FLAME), itemBuilder());


	public static final RegistrySupplier<Item> GYSAHL_GREEN_SEEDS = registerItem("gysahl_green_seeds", (properties) -> new CustomBlockNamedItem(ModRegistry.GYSAHL_GREEN, properties), itemBuilder());

	public static final RegistrySupplier<Item> GYSAHL_GREEN_ITEM = registerItem("gysahl_green", Item::new, itemBuilder().food(ModFoods.GYSAHL_GREEN));
	public static final RegistrySupplier<Item> CHOCOBO_WHISTLE = registerItem("chocobo_whistle", Item::new, itemBuilder());
	public static final RegistrySupplier<Item> CHOCOBO_FEATHER = registerItem("chocobo_feather", Item::new, itemBuilder());
	public static final RegistrySupplier<Item> LOVERLY_GYSAHL_GREEN = registerItem("loverly_gysahl_green", Item::new, itemBuilder());
	public static final RegistrySupplier<Item> GOLD_GYSAHL = registerItem("gold_gysahl", Item::new, itemBuilder());
	public static final RegistrySupplier<Item> RED_GYSAHL = registerItem("red_gysahl", Item::new, itemBuilder());
	public static final RegistrySupplier<Item> PINK_GYSAHL = registerItem("pink_gysahl", Item::new, itemBuilder());

	public static final RegistrySupplier<Item> CHOCOBO_DRUMSTICK_RAW = registerItem("chocobo_drumstick_raw", Item::new, itemBuilder().food(ModFoods.CHOCOBO_DRUMSTICK_RAW));
	public static final RegistrySupplier<Item> CHOCOBO_DRUMSTICK_COOKED = registerItem("chocobo_drumstick_cooked", Item::new, itemBuilder().food(ModFoods.CHOCOBO_DRUMSTICK_COOKED));
	public static final RegistrySupplier<Item> PICKLED_GYSAHL_RAW = registerItem("pickled_gysahl_raw", Item::new, itemBuilder().food(ModFoods.PICKLED_GYSAHL_RAW));
	public static final RegistrySupplier<Item> PICKLED_GYSAHL_COOKED = registerItem("pickled_gysahl_cooked", Item::new, itemBuilder().food(ModFoods.PICKLED_GYSAHL_COOKED));

	public static final RegistrySupplier<Item> CHOCOPEDIA = registerItem("chocopedia", ChocopediaItem::new, itemBuilder());
	public static final RegistrySupplier<Item> GYSAHL_CAKE = registerItem("gysahl_cake", (properties) -> new Item(properties.stacksTo(8)), itemBuilder());

	public static final RegistrySupplier<Item> CHOCO_DISGUISE_HELMET = registerItem("choco_disguise_helmet", (properties) -> Services.PLATFORM.constructChocoDisguise(ModArmorMaterial.CHOCO_DISGUISE, ArmorType.HELMET, properties), itemBuilder());
	public static final RegistrySupplier<Item> CHOCO_DISGUISE_CHESTPLATE = registerItem("choco_disguise_chestplate", (properties) -> Services.PLATFORM.constructChocoDisguise(ModArmorMaterial.CHOCO_DISGUISE, ArmorType.CHESTPLATE, properties), itemBuilder());
	public static final RegistrySupplier<Item> CHOCO_DISGUISE_LEGGINGS = registerItem("choco_disguise_leggings", (properties) -> Services.PLATFORM.constructChocoDisguise(ModArmorMaterial.CHOCO_DISGUISE, ArmorType.LEGGINGS, properties), itemBuilder());
	public static final RegistrySupplier<Item> CHOCO_DISGUISE_BOOTS = registerItem("choco_disguise_boots", (properties) -> Services.PLATFORM.constructChocoDisguise(ModArmorMaterial.CHOCO_DISGUISE, ArmorType.BOOTS, properties), itemBuilder());

	//Regular block items
	public static final RegistrySupplier<Item> STRAW_ITEM = registerItem("straw", (properties) -> new BlockItem(STRAW.get(), properties), itemBuilder());

	public static RegistrySupplier<Block> registerBlock(String name, Function<BlockBehaviour.Properties, ? extends Block> func, BlockBehaviour.Properties props) {
		return BLOCKS.register(name, () -> func.apply(props.setId(ResourceKey.create(Registries.BLOCK, Chococraft.modLoc(name)))));
	}

	public static RegistrySupplier<Item> registerItem(String name, Function<Properties, ? extends Item> func, Item.Properties props) {
		return ITEMS.register(name, () -> func.apply(props.setId(ResourceKey.create(Registries.ITEM, Chococraft.modLoc(name)))));
	}

	private static Item.Properties itemBuilder() {
		return new Item.Properties();
	}

	private static BlockBehaviour.Properties blockBuilder() {
		return BlockBehaviour.Properties.of();
	}

	public static void registerCompostables() {
		ComposterBlock.COMPOSTABLES.put(GYSAHL_GREEN_SEEDS.get(), 0.3F);
		ComposterBlock.COMPOSTABLES.put(GYSAHL_GREEN_ITEM.get(), 0.65F);
		ComposterBlock.COMPOSTABLES.put(LOVERLY_GYSAHL_GREEN.get(), 0.65F);
		ComposterBlock.COMPOSTABLES.put(GOLD_GYSAHL.get(), 0.65F);
	}
}
