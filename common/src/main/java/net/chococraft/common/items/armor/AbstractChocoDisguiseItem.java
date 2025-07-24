package net.chococraft.common.items.armor;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;

public abstract class AbstractChocoDisguiseItem extends Item {

	public AbstractChocoDisguiseItem(ArmorMaterial material, ArmorType type, Properties properties) {
		super(properties.humanoidArmor(material, type));
	}
}
