package net.chococraft.common.items.armor;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;

public abstract class AbstractChocoDisguiseItem extends ArmorItem {

	public AbstractChocoDisguiseItem(ArmorMaterial material, ArmorType type, Properties properties) {
		super(material, type, properties);
	}
}
