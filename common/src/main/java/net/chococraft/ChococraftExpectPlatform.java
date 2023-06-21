package net.chococraft;

import com.google.gson.internal.LinkedTreeMap;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.chococraft.common.entity.AbstractChocobo;
import net.chococraft.common.inventory.SaddleBagMenu;
import net.chococraft.common.items.armor.AbstractChocoDisguiseItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;

import java.nio.file.Path;
import java.util.List;

public class ChococraftExpectPlatform {
	@ExpectPlatform
	public static Path getConfigDirectory() {
		// Just throw an error, the content should get replaced at runtime.
		throw new AssertionError();
	}

	@ExpectPlatform
	public static boolean isModLoaded(String modID) {
		// Just throw an error, the content should get replaced at runtime.
		throw new AssertionError();
	}

	@ExpectPlatform
	public static EntityType.Builder<? extends AbstractChocobo> constructChocoboEntityType() {
		// Just throw an error, the content should get replaced at runtime.
		throw new AssertionError();
	}

	@ExpectPlatform
	public static SaddleBagMenu constructMenu(int i, Inventory inventory, FriendlyByteBuf friendlyByteBuf) {
		// Just throw an error, the content should get replaced at runtime.
		throw new AssertionError();
	}

	@ExpectPlatform
	public static SaddleBagMenu constructMenu(int i, Inventory inventory, AbstractChocobo abstractChocobo) {
		// Just throw an error, the content should get replaced at runtime.
		throw new AssertionError();
	}

	@ExpectPlatform
	public static AbstractChocoDisguiseItem constructChocoDisguise(ArmorMaterial material, EquipmentSlot slot, Item.Properties properties) {
		// Just throw an error, the content should get replaced at runtime.
		throw new AssertionError();
	}

	@ExpectPlatform
	public static LinkedTreeMap<String, LinkedTreeMap<String, List<LinkedTreeMap<String, String>>>> getBreedingInfoMap() {
		// Just throw an error, the content should get replaced at runtime.
		throw new AssertionError();
	}

	@ExpectPlatform
	public static float getTameChance() {
		// Just throw an error, the content should get replaced at runtime.
		throw new AssertionError();
	}

	@ExpectPlatform
	public static boolean canChocobosFly() {
		// Just throw an error, the content should get replaced at runtime.
		throw new AssertionError();
	}

	@ExpectPlatform
	public static boolean nameTamedChocobos() {
		// Just throw an error, the content should get replaced at runtime.
		throw new AssertionError();
	}

	@ExpectPlatform
	public static List<? extends String> getConfiguredMaleNames() {
		// Just throw an error, the content should get replaced at runtime.
		throw new AssertionError();
	}


	@ExpectPlatform
	public static List<? extends String> getConfiguredFemaleNames() {
		// Just throw an error, the content should get replaced at runtime.
		throw new AssertionError();
	}
}
