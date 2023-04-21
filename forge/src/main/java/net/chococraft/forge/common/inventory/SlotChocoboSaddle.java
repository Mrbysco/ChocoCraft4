package net.chococraft.forge.common.inventory;

import net.chococraft.common.items.ChocoboSaddleItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import org.jetbrains.annotations.NotNull;

public class SlotChocoboSaddle extends SlotItemHandler {
	public SlotChocoboSaddle(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
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
