package net.chococraft.neoforge.common.inventory;

import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;

public class SaddleItemStackHandler extends ItemStacksResourceHandler {
	public SaddleItemStackHandler() {
		super(1);
	}

	@Override
	public void set(int index, ItemResource resource, int amount) {
		super.set(index, resource, amount);
	}

//	@Override
//	public void setStackInSlot(int slot, @NotNull ItemStack stack) {
//		this.validateSlotIndex(slot);
//		if (!ItemStack.isSameItem(stacks.get(slot), stack)) {
//			this.stacks.set(slot, stack);
//			this.onContentsChanged(slot);
//		}
//	}

	@Override
	protected int getCapacity(int index, ItemResource resource) {
		return 1;
	}
}
