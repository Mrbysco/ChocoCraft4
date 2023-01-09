package net.chococraft.common.inventory;

import net.chococraft.common.entity.AbstractChocobo;
import net.chococraft.registry.ModMenus;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

public abstract class SaddleBagMenu extends AbstractContainerMenu {
	private final AbstractChocobo chocobo;

	public SaddleBagMenu(int id, Inventory inventory, AbstractChocobo chocobo) {
		super(ModMenus.CHOCOBO.get(), id);
		this.chocobo = chocobo;
	}
}
