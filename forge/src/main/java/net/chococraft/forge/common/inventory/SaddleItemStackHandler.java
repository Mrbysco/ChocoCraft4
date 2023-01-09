package net.chococraft.forge.common.inventory;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;

public abstract class SaddleItemStackHandler implements IItemHandler, IItemHandlerModifiable, INBTSerializable<CompoundTag> {
	protected ItemStack itemStack = ItemStack.EMPTY;

	@Override
	public void setStackInSlot(int slot, @Nonnull ItemStack stack) {
		ItemStack oldStack = this.itemStack;
		this.itemStack = stack;
		// dont update if we change from empty to empty
		if (!(oldStack.isEmpty() && stack.isEmpty())) {
			this.onStackChanged();
		}
	}

	@Override
	public int getSlots() {
		return 1;
	}

	@Nonnull
	@Override
	public ItemStack getStackInSlot(int slot) {
		return this.itemStack;
	}

	@Nonnull
	@Override
	public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
		if (stack.isEmpty())
			return ItemStack.EMPTY;

		if (this.itemStack.isEmpty()) {
			if (simulate)
				return ItemHandlerHelper.copyStackWithSize(stack, stack.getCount() - 1);
			this.itemStack = stack.split(1);
		}
		this.onStackChanged();
		return stack;
	}

	@Nonnull
	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		if (amount <= 0)
			return ItemStack.EMPTY;

		if (simulate)
			return ItemHandlerHelper.copyStackWithSize(this.itemStack, amount);
		ItemStack outStack = this.itemStack.split(amount);
		this.onStackChanged();
		return outStack;
	}

	@Override
	public int getSlotLimit(int slot) {
		return 1;
	}

	@Override
	public CompoundTag serializeNBT() {
		return this.itemStack.save(new CompoundTag());
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		this.itemStack = ItemStack.of(nbt);
		this.onStackChanged();
	}

	protected abstract void onStackChanged();
}
