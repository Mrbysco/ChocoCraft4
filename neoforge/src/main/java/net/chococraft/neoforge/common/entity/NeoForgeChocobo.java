package net.chococraft.neoforge.common.entity;

import net.chococraft.common.entity.AbstractChocobo;
import net.chococraft.common.items.ChocoboSaddleItem;
import net.chococraft.neoforge.common.inventory.NeoForgeSaddleBagMenu;
import net.chococraft.neoforge.common.inventory.SaddleItemStackHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

public class NeoForgeChocobo extends AbstractChocobo {

	public final ItemStackHandler inventory = new ItemStackHandler(45) {
		@Override
		public boolean isItemValid(int slot, @NotNull ItemStack stack) {
			if (getSaddle().isEmpty()) {
				return false;
			}
			if (getSaddle().getItem() instanceof ChocoboSaddleItem saddleItem) {
				switch (saddleItem.getInventorySize()) {
					case 18 -> {
						return ((slot > 10 && slot < 16) || (slot > 19 && slot < 25) || (slot > 28 && slot < 34)) && super.isItemValid(slot, stack);
					}
					case 45 -> {
						return super.isItemValid(slot, stack);
					}
					default -> {
						return false;
					}
				}
			}


			return super.isItemValid(slot, stack);
		}
	};
	public final SaddleItemStackHandler saddleItemStackHandler = new SaddleItemStackHandler() {
		@Override
		public boolean isItemValid(int slot, @NotNull ItemStack stack) {
			return stack.isEmpty() || stack.getItem() instanceof ChocoboSaddleItem;
		}

		@Override
		public void setStackInSlot(int slot, @NotNull ItemStack stack) {
			NeoForgeChocobo.this.setSaddleType(stack);
			super.setStackInSlot(slot, stack);
		}

		@Override
		protected void onContentsChanged(int slot) {
			NeoForgeChocobo.this.setSaddleType(getStackInSlot(slot));
			super.onContentsChanged(slot);
		}
	};

	public NeoForgeChocobo(EntityType<? extends AbstractChocobo> type, Level world) {
		super(type, world);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.put(NBTKEY_SADDLE_ITEM, this.saddleItemStackHandler.serializeNBT());
		compound.put(NBTKEY_INVENTORY, this.inventory.serializeNBT());
	}

	@Override
	protected void setSaddled(Player player, InteractionHand hand, ItemStack heldItemStack) {
		if (!this.level().isClientSide) {
			this.saddleItemStackHandler.setStackInSlot(0, heldItemStack.getItem().getDefaultInstance());
			this.setSaddleType(heldItemStack);
			this.usePlayerItem(player, hand, heldItemStack);
		}
	}

	@Override
	public void openCustomInventoryScreen(Player player) {
		if (!this.level().isClientSide && (!this.isVehicle() || this.hasPassenger(player)) && this.isTame()) {
			ServerPlayer serverPlayer = (ServerPlayer) player;
			if (player.containerMenu != player.inventoryMenu) {
				player.closeContainer();
			}

			serverPlayer.nextContainerCounter();

			serverPlayer.openMenu(new SimpleMenuProvider((ix, playerInventory, playerEntityx) ->
					new NeoForgeSaddleBagMenu(ix, playerInventory, this), this.getDisplayName()), buf -> {
				buf.writeUUID(getUUID());
			});
		}
	}

	@Override
	protected void reconfigureInventory(ItemStack oldSaddle, ItemStack newSaddle) {
		if (!this.getCommandSenderWorld().isClientSide) {
			// TODO: Handle resizing. ItemStackHandler#setSize() clears the internal inventory!
			for (int i = 0; i < this.inventory.getSlots(); i++) {
				if (this.isAlive()) {
					ItemStack stack = this.inventory.extractItem(i, Integer.MAX_VALUE, false);
					Containers.dropItemStack(this.getCommandSenderWorld(), this.getX(), this.getY() + .5, this.getZ(), stack);
				}
			}
		}

		for (Player player : this.level().players()) {
			if (player.containerMenu instanceof NeoForgeSaddleBagMenu bagContainer) {
				bagContainer.refreshSlots(bagContainer.getChocobo(), player.getInventory());
			}
		}
	}

	@Override
	protected void dropInventory() {
		if (this.inventory != null && this.isSaddled()) {
			for (int i = 0; i < this.inventory.getSlots(); i++) {
				if (!this.inventory.getStackInSlot(i).isEmpty())
					this.spawnAtLocation(this.inventory.getStackInSlot(i), 0.0f);
			}
		}
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);

		this.saddleItemStackHandler.deserializeNBT(compound.getCompound(NBTKEY_SADDLE_ITEM));
		setSaddleType(this.saddleItemStackHandler.getStackInSlot(0));
		this.inventory.deserializeNBT(compound.getCompound(NBTKEY_INVENTORY));
	}

	@Override
	public float getStepHeight() {
		return getAbilityInfo().getStepHeight(isVehicle());
	}

	public IItemHandler getInventory() {
		return this.inventory;
	}
}
