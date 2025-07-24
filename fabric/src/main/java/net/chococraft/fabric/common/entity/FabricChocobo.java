package net.chococraft.fabric.common.entity;

import dev.architectury.registry.menu.MenuRegistry;
import net.chococraft.common.entity.AbstractChocobo;
import net.chococraft.common.items.ChocoboSaddleItem;
import net.chococraft.fabric.common.inventory.FabricSaddleBagMenu;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerListener;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class FabricChocobo extends AbstractChocobo implements ContainerListener {
	protected SimpleContainer inventory;

	public FabricChocobo(EntityType<? extends AbstractChocobo> type, Level world) {
		super(type, world);
		this.createInventory();
	}

	protected int getInventorySize() {
		return 46;
	}

	protected void createInventory() {
		SimpleContainer simpleContainer = this.inventory;
		this.inventory = new SimpleContainer(getInventorySize()) {
			@Override
			public boolean canPlaceItem(int slot, ItemStack stack) {
				if (slot == 0) {
					return stack.isEmpty() || stack.getItem() instanceof ChocoboSaddleItem;
				} else {
					if (this.getItem(0).isEmpty()) {
						return false;
					}
					if (this.getItem(slot).getItem() instanceof ChocoboSaddleItem saddleItem) {
						switch (saddleItem.getInventorySize()) {
							case 18 -> {
								return ((slot > 10 && slot < 16) || (slot > 19 && slot < 25) || (slot > 28 && slot < 34)) && super.canPlaceItem(slot, stack);
							}
							case 45 -> {
								return super.canPlaceItem(slot, stack);
							}
							default -> {
								return false;
							}
						}
					}


					return super.canPlaceItem(slot, stack);
				}
			}
		};
		if (simpleContainer != null) {
			simpleContainer.removeListener(this);
			int i = Math.min(simpleContainer.getContainerSize(), this.inventory.getContainerSize());

			for (int j = 0; j < i; ++j) {
				ItemStack itemStack = simpleContainer.getItem(j);
				if (!itemStack.isEmpty()) {
					this.inventory.setItem(j, itemStack.copy());
				}
			}
		}

		this.inventory.addListener(this);
	}

	public SimpleContainer getInventory() {
		return inventory;
	}

	@Override
	public void addAdditionalSaveData(ValueOutput output) {
		super.addAdditionalSaveData(output);

		this.inventory.storeAsItemList(output.list("Items", ItemStack.OPTIONAL_CODEC));
	}

	@Override
	public void readAdditionalSaveData(ValueInput input) {
		super.readAdditionalSaveData(input);

		this.inventory.fromItemList(input.listOrEmpty("Items", ItemStack.OPTIONAL_CODEC));
		setSaddleType(this.inventory.getItem(0));
	}

	@Override
	protected void setSaddled(Player player, InteractionHand hand, ItemStack heldItemStack) {
		if (!this.level().isClientSide) {
			this.inventory.setItem(0, heldItemStack.getItem().getDefaultInstance());
			this.setSaddleType(heldItemStack);
			this.usePlayerItem(player, hand, heldItemStack);
		}
	}

	@Override
	public void openCustomInventoryScreen(Player player) {
		if (!this.level().isClientSide && (!this.isVehicle() || this.hasPassenger(player)) && this.isTame()) {
			ServerPlayer serverPlayer = (ServerPlayer) player;
			if (serverPlayer.containerMenu != serverPlayer.inventoryMenu) {
				serverPlayer.closeContainer();
			}

			serverPlayer.nextContainerCounter();

			MenuRegistry.openExtendedMenu(serverPlayer, new SimpleMenuProvider((ix, playerInventory, playerEntityx) ->
					new FabricSaddleBagMenu(ix, playerInventory, this), this.getDisplayName()), buf -> buf.writeUUID(getUUID()));
		}
	}

	@Override
	protected void reconfigureInventory(ItemStack oldSaddle, ItemStack newSaddle) {
		if (!this.level().isClientSide) {
			// TODO: Handle resizing. ItemStackHandler#setSize() clears the internal inventory!
			for (int i = 0; i < this.inventory.getContainerSize(); i++) {
				if (i > 0) {
					if (this.isAlive()) {
						ItemStack stack = this.inventory.removeItem(i, Integer.MAX_VALUE);
						this.inventory.setChanged();
						Containers.dropItemStack(this.level(), this.getX(), this.getY() + .5, this.getZ(), stack);
					}
				}
			}
		}

		for (Player player : this.level().players()) {
			if (player.containerMenu instanceof FabricSaddleBagMenu bagContainer) {
				bagContainer.refreshSlots(bagContainer.getChocobo(), player.getInventory());
			}
		}
	}

	@Override
	protected void dropInventory() {
		if (this.inventory != null && this.isSaddled()) {
			for (int i = 0; i < this.inventory.getContainerSize(); i++) {
				if (!this.inventory.getItem(i).isEmpty())
					this.spawnAtLocation((ServerLevel) level(), this.inventory.getItem(i), 0.0f);
			}
		}
	}

	@Override
	public void containerChanged(Container container) {
		FabricChocobo.this.setSaddleType(container.getItem(0));
	}
}
