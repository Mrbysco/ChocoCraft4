package net.chococraft.common.items.armor;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;

public abstract class AbstractChocoDisguiseItem extends ArmorItem {

	public AbstractChocoDisguiseItem(ArmorMaterial material, ArmorItem.Type type, Properties properties) {
		super(material, type, properties);
	}
}
