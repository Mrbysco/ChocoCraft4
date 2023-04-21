package net.chococraft.forge.common.inventory;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import org.jetbrains.annotations.NotNull;

public class SaddleItemStackHandler extends ItemStackHandler {
	public SaddleItemStackHandler() {
		super(1);
	}

	@Override
	public void setStackInSlot(int slot, @NotNull ItemStack stack) {
		this.validateSlotIndex(slot);
		if (!stacks.get(slot).sameItem(stack)) {
			this.stacks.set(slot, stack);
			this.onContentsChanged(slot);
		}
	}

	@Override
	public int getSlotLimit(int slot) {
		return 1;
	}
}
