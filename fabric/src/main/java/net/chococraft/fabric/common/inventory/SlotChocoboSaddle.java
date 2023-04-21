package net.chococraft.fabric.common.inventory;

import net.chococraft.common.items.ChocoboSaddleItem;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import org.jetbrains.annotations.NotNull;

public class SlotChocoboSaddle extends Slot {
	public SlotChocoboSaddle(Container itemHandler, int index, int xPosition, int yPosition) {
		super(itemHandler, index, xPosition, yPosition);
	}

	@Override
	public boolean mayPlace(@NotNull ItemStack stack) {
		return stack.isEmpty() || stack.getItem() instanceof ChocoboSaddleItem;
	}

	@Override
	public int getMaxStackSize(@NotNull ItemStack stack) {
		return 1;
	}
}
