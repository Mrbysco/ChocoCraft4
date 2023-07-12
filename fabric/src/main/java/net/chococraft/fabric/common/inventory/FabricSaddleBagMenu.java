package net.chococraft.fabric.common.inventory;

import net.chococraft.common.inventory.SaddleBagMenu;
import net.chococraft.common.items.ChocoboSaddleItem;
import net.chococraft.fabric.common.entity.FabricChocobo;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.UUID;

public class FabricSaddleBagMenu extends SaddleBagMenu {
	private final FabricChocobo chocobo;

	public FabricSaddleBagMenu(int id, Inventory inventory, FabricChocobo chocobo) {
		super(id, inventory, chocobo);
		this.chocobo = chocobo;
		this.refreshSlots(chocobo, inventory);
	}

	public static FabricSaddleBagMenu create(final int windowId, final Inventory inventory, final FriendlyByteBuf buffer) {
		UUID uuid = buffer.readUUID();
		List<FabricChocobo> chocobos = inventory.player.level().getEntitiesOfClass(FabricChocobo.class, inventory.player.getBoundingBox().inflate(16.0D),
				(test) -> test.getUUID().equals(uuid));
		FabricChocobo chocobo = chocobos.isEmpty() ? null : chocobos.get(0);
		return new FabricSaddleBagMenu(windowId, inventory, chocobo);
	}

	public FabricChocobo getChocobo() {
		return chocobo;
	}

	public void refreshSlots(FabricChocobo chocobo, Inventory inventory) {
		this.slots.clear();

		// Saddle slot
		this.addSlot(new SlotChocoboSaddle(chocobo.getInventory(), 0, -16, 18));

		//Chocobo inventory
		for (int row = 0; row < 5; row++) {
			for (int col = 0; col < 9; col++) {
				this.addSlot(new Slot(chocobo.getInventory(), row * 9 + col + 1, 8 + col * 18, 18 + row * 18) {
					@Override
					public boolean isActive() {
						ItemStack saddleStack = chocobo.getSaddle();
						if (saddleStack.isEmpty()) {
							return false;
						}

						if (!saddleStack.isEmpty() && saddleStack.getItem() instanceof ChocoboSaddleItem saddleItem) {
							switch (saddleItem.getInventorySize()) {
								default -> {
									return false;
								}
								case 18 -> {
									int index = getContainerSlot();
									return (index > 10 && index < 16) || (index > 19 && index < 25) || (index > 28 && index < 34);
								}
								case 45 -> {
									return super.isActive();
								}
							}
						}

						return super.isActive();
					}

					@Override
					public int getMaxStackSize(ItemStack itemStack) {
						if (itemStack.getItem() instanceof ChocoboSaddleItem) {
							return 1;
						}
						return super.getMaxStackSize(itemStack);
					}

					@Override
					public boolean mayPlace(ItemStack itemStack) {
						return super.mayPlace(itemStack) && isActive();
					}
				});
			}
		}

		//Player inventory
		for (int row = 0; row < 3; ++row) {
			for (int col = 0; col < 9; ++col) {
				this.addSlot(new Slot(inventory, col + row * 9 + 9, 8 + col * 18, 122 + row * 18));
			}
		}

		for (int i = 0; i < 9; ++i) {
			this.addSlot(new Slot(inventory, i, 8 + i * 18, 180));
		}
	}


	@Override
	public boolean stillValid(Player playerIn) {
		return this.chocobo.isAlive() && this.chocobo.distanceTo(playerIn) < 8.0F;
	}

	public ItemStack quickMoveStack(Player player, int index) {
		ItemStack itemstack = ItemStack.EMPTY;

		Slot slot = this.slots.get(index);
		if (slot != null && slot.hasItem()) {
			ItemStack itemstack1 = slot.getItem();
			itemstack = itemstack1.copy();
			int containerSize = this.chocobo.getInventory().getContainerSize();
			if (index < containerSize) {
				if (!this.moveItemStackTo(itemstack1, containerSize, this.slots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.moveItemStackTo(itemstack1, 0, containerSize, false)) {
				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}
		}

		return itemstack;
	}
}
