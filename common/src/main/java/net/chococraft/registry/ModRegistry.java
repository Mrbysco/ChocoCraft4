package net.chococraft.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.chococraft.Chococraft;
import net.chococraft.ChococraftExpectPlatform;
import net.chococraft.common.blocks.GysahlGreenBlock;
import net.chococraft.common.blocks.StrawBlock;
import net.chococraft.common.entity.properties.ChocoboColor;
import net.chococraft.common.items.ChocoboSaddleItem;
import net.chococraft.common.items.ChocoboSpawnEggItem;
import net.chococraft.common.items.ChocopediaItem;
import net.chococraft.common.items.CustomBlockNamedItem;
import net.chococraft.common.items.armor.ModArmorMaterial;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public class ModRegistry {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Chococraft.MOD_ID, Registries.BLOCK);
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Chococraft.MOD_ID, Registries.ITEM);

	public static final RegistrySupplier<Block> GYSAHL_GREEN = BLOCKS.register("gysahl_green", () ->
			new GysahlGreenBlock(BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP)));

	public static final RegistrySupplier<Block> STRAW = BLOCKS.register("straw", () ->
			new StrawBlock(BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.GRASS)));

	public static final RegistrySupplier<Item> CHOCOBO_SADDLE = ITEMS.register("chocobo_saddle", () -> new ChocoboSaddleItem(itemBuilder(), 0));
	public static final RegistrySupplier<Item> CHOCOBO_SADDLE_BAGS = ITEMS.register("chocobo_saddle_bags", () -> new ChocoboSaddleItem(itemBuilder(), 18));
	public static final RegistrySupplier<Item> CHOCOBO_SADDLE_PACK = ITEMS.register("chocobo_saddle_pack", () -> new ChocoboSaddleItem(itemBuilder(), 45));

	public static final RegistrySupplier<Item> YELLOW_CHOCOBO_SPAWN_EGG = ITEMS.register("yellow_chocobo_spawn_egg", () -> new ChocoboSpawnEggItem(itemBuilder(), ChocoboColor.YELLOW));
	public static final RegistrySupplier<Item> GREEN_CHOCOBO_SPAWN_EGG = ITEMS.register("green_chocobo_spawn_egg", () -> new ChocoboSpawnEggItem(itemBuilder(), ChocoboColor.GREEN));
	public static final RegistrySupplier<Item> BLUE_CHOCOBO_SPAWN_EGG = ITEMS.register("blue_chocobo_spawn_egg", () -> new ChocoboSpawnEggItem(itemBuilder(), ChocoboColor.BLUE));
	public static final RegistrySupplier<Item> WHITE_CHOCOBO_SPAWN_EGG = ITEMS.register("white_chocobo_spawn_egg", () -> new ChocoboSpawnEggItem(itemBuilder(), ChocoboColor.WHITE));
	public static final RegistrySupplier<Item> BLACK_CHOCOBO_SPAWN_EGG = ITEMS.register("black_chocobo_spawn_egg", () -> new ChocoboSpawnEggItem(itemBuilder(), ChocoboColor.BLACK));
	public static final RegistrySupplier<Item> GOLD_CHOCOBO_SPAWN_EGG = ITEMS.register("gold_chocobo_spawn_egg", () -> new ChocoboSpawnEggItem(itemBuilder(), ChocoboColor.GOLD));
	public static final RegistrySupplier<Item> PINK_CHOCOBO_SPAWN_EGG = ITEMS.register("pink_chocobo_spawn_egg", () -> new ChocoboSpawnEggItem(itemBuilder(), ChocoboColor.PINK));
	public static final RegistrySupplier<Item> RED_CHOCOBO_SPAWN_EGG = ITEMS.register("red_chocobo_spawn_egg", () -> new ChocoboSpawnEggItem(itemBuilder(), ChocoboColor.RED));
	public static final RegistrySupplier<Item> PURPLE_CHOCOBO_SPAWN_EGG = ITEMS.register("purple_chocobo_spawn_egg", () -> new ChocoboSpawnEggItem(itemBuilder(), ChocoboColor.PURPLE));
	public static final RegistrySupplier<Item> FLAME_CHOCOBO_SPAWN_EGG = ITEMS.register("flame_chocobo_spawn_egg", () -> new ChocoboSpawnEggItem(itemBuilder(), ChocoboColor.FLAME));


	public static final RegistrySupplier<Item> GYSAHL_GREEN_SEEDS = ITEMS.register("gysahl_green_seeds", () -> new CustomBlockNamedItem(ModRegistry.GYSAHL_GREEN, itemBuilder()));

	public static final RegistrySupplier<Item> GYSAHL_GREEN_ITEM = ITEMS.register("gysahl_green", () -> new Item(itemBuilder().food(ModFoods.GYSAHL_GREEN)));
	public static final RegistrySupplier<Item> CHOCOBO_WHISTLE = ITEMS.register("chocobo_whistle", () -> new Item(itemBuilder()));
	public static final RegistrySupplier<Item> CHOCOBO_FEATHER = ITEMS.register("chocobo_feather", () -> new Item(itemBuilder()));
	public static final RegistrySupplier<Item> LOVERLY_GYSAHL_GREEN = ITEMS.register("loverly_gysahl_green", () -> new Item(itemBuilder()));
	public static final RegistrySupplier<Item> GOLD_GYSAHL = ITEMS.register("gold_gysahl", () -> new Item(itemBuilder()));
	public static final RegistrySupplier<Item> RED_GYSAHL = ITEMS.register("red_gysahl", () -> new Item(itemBuilder()));
	public static final RegistrySupplier<Item> PINK_GYSAHL = ITEMS.register("pink_gysahl", () -> new Item(itemBuilder()));

	public static final RegistrySupplier<Item> CHOCOBO_DRUMSTICK_RAW = ITEMS.register("chocobo_drumstick_raw", () -> new Item(itemBuilder().food(ModFoods.CHOCOBO_DRUMSTICK_RAW)));
	public static final RegistrySupplier<Item> CHOCOBO_DRUMSTICK_COOKED = ITEMS.register("chocobo_drumstick_cooked", () -> new Item(itemBuilder().food(ModFoods.CHOCOBO_DRUMSTICK_COOKED)));
	public static final RegistrySupplier<Item> PICKLED_GYSAHL_RAW = ITEMS.register("pickled_gysahl_raw", () -> new Item(itemBuilder().food(ModFoods.PICKLED_GYSAHL_RAW)));
	public static final RegistrySupplier<Item> PICKLED_GYSAHL_COOKED = ITEMS.register("pickled_gysahl_cooked", () -> new Item(itemBuilder().food(ModFoods.PICKLED_GYSAHL_COOKED)));

	public static final RegistrySupplier<Item> CHOCOPEDIA = ITEMS.register("chocopedia", () -> new ChocopediaItem(itemBuilder()));
	public static final RegistrySupplier<Item> GYSAHL_CAKE = ITEMS.register("gysahl_cake", () -> new Item(itemBuilder().stacksTo(8)));

	public static final RegistrySupplier<Item> CHOCO_DISGUISE_HELMET = ITEMS.register("choco_disguise_helmet", () -> ChococraftExpectPlatform.constructChocoDisguise(ModArmorMaterial.CHOCO_DISGUISE, EquipmentSlot.HEAD, itemBuilder()));
	public static final RegistrySupplier<Item> CHOCO_DISGUISE_CHESTPLATE = ITEMS.register("choco_disguise_chestplate", () -> ChococraftExpectPlatform.constructChocoDisguise(ModArmorMaterial.CHOCO_DISGUISE, EquipmentSlot.CHEST, itemBuilder()));
	public static final RegistrySupplier<Item> CHOCO_DISGUISE_LEGGINGS = ITEMS.register("choco_disguise_leggings", () -> ChococraftExpectPlatform.constructChocoDisguise(ModArmorMaterial.CHOCO_DISGUISE, EquipmentSlot.LEGS, itemBuilder()));
	public static final RegistrySupplier<Item> CHOCO_DISGUISE_BOOTS = ITEMS.register("choco_disguise_boots", () -> ChococraftExpectPlatform.constructChocoDisguise(ModArmorMaterial.CHOCO_DISGUISE, EquipmentSlot.FEET, itemBuilder()));

	//Regular block items
	public static final RegistrySupplier<Item> STRAW_ITEM = ITEMS.register("straw", () -> new BlockItem(STRAW.get(), itemBuilder()));

	private static Item.Properties itemBuilder() {
		return new Item.Properties();
	}
}
