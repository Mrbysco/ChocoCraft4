package net.chococraft.common.items.armor;

import net.chococraft.Chococraft;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Util;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

import java.util.EnumMap;

public class ModArmorMaterial {
	public static final ArmorMaterial CHOCO_DISGUISE = new ArmorMaterial(6, Util.make(new EnumMap<>(ArmorType.class), map -> {
		map.put(ArmorType.BOOTS, 3);
		map.put(ArmorType.LEGGINGS, 7);
		map.put(ArmorType.CHESTPLATE, 6);
		map.put(ArmorType.HELMET, 3);
		map.put(ArmorType.BODY, 0);
	}),
			10,
			SoundEvents.ARMOR_EQUIP_LEATHER,
			0.0F,
			0.0F, ItemTags.REPAIRS_LEATHER_ARMOR,
			createAsset("choco_disguise"));

	private static ResourceKey<EquipmentAsset> createAsset(String name) {
		return ResourceKey.create(EquipmentAssets.ROOT_ID, Chococraft.modLoc(name));
	}
}
