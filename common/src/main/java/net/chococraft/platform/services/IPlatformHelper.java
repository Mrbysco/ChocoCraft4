package net.chococraft.platform.services;

import com.google.gson.internal.LinkedTreeMap;
import net.chococraft.common.entity.AbstractChocobo;
import net.chococraft.common.entity.properties.ChocoboColor;
import net.chococraft.common.entity.properties.MovementType;
import net.chococraft.common.inventory.SaddleBagMenu;
import net.chococraft.common.items.armor.AbstractChocoDisguiseItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;

import java.nio.file.Path;
import java.util.List;

public interface IPlatformHelper {

	Path getConfigDirectory();
	
	boolean isModLoaded(String modID);

	EntityType.Builder<? extends AbstractChocobo> constructChocoboEntityType();
	
	SaddleBagMenu constructMenu(int i, Inventory inventory, FriendlyByteBuf friendlyByteBuf);

	SaddleBagMenu constructMenu(int i, Inventory inventory, AbstractChocobo abstractChocobo);
	
	AbstractChocoDisguiseItem constructChocoDisguise(ArmorMaterial material, ArmorType type, Item.Properties properties);
	
	LinkedTreeMap<String, LinkedTreeMap<String, List<LinkedTreeMap<String, String>>>> getBreedingInfoMap();

	float getTameChance();

	boolean canChocobosFly();
	
	boolean nameTamedChocobos();
	
	List<? extends String> getConfiguredMaleNames();
	
	List<? extends String> getConfiguredFemaleNames();

	int kwehIntervalLimit();
	
	EntityDataSerializer<ChocoboColor> getColorSerializer();
	
	EntityDataSerializer<MovementType> getMovementSerializer();
}
