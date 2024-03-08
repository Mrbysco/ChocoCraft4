package net.chococraft.neoforge.common.inventory;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;

import org.jetbrains.annotations.NotNull;

public class SaddleItemStackHandler extends ItemStackHandler {
	public SaddleItemStackHandler() {
		super(1);
	}

	@Override
	public void setStackInSlot(int slot, @NotNull ItemStack stack) {
		this.validateSlotIndex(slot);
		if (!ItemStack.isSameItem(stacks.get(slot), stack)) {
			this.stacks.set(slot, stack);
			this.onContentsChanged(slot);
		}
	}

	@Override
	public int getSlotLimit(int slot) {
		return 1;
	}
}
