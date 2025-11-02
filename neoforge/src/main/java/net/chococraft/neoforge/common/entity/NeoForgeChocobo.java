package net.chococraft.neoforge.common.entity;

import net.chococraft.common.entity.AbstractChocobo;
import net.chococraft.common.items.ChocoboSaddleItem;
import net.chococraft.neoforge.common.inventory.NeoForgeSaddleBagMenu;
import net.chococraft.neoforge.common.inventory.SaddleItemStackHandler;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jetbrains.annotations.NotNull;

public class NeoForgeChocobo extends AbstractChocobo {

	public final ItemStacksResourceHandler inventory = new ItemStacksResourceHandler(45) {

		@Override
		public boolean isValid(int index, ItemResource resource) {
			if (getSaddle().isEmpty()) {
				return false;
			}
			if (getSaddle().getItem() instanceof ChocoboSaddleItem saddleItem) {
				switch (saddleItem.getInventorySize()) {
					case 18 -> {
						return ((index > 10 && index < 16) || (index > 19 && index < 25) || (index > 28 && index < 34)) && super.isValid(index, resource);
					}
					case 45 -> {
						return super.isValid(index, resource);
					}
					default -> {
						return false;
					}
				}
			}
			return super.isValid(index, resource);
		}
	};
	public final SaddleItemStackHandler saddleItemStackHandler = new SaddleItemStackHandler() {
		@Override
		public boolean isValid(int index, @NotNull ItemResource resource) {
			return resource.isEmpty() || resource.getItem() instanceof ChocoboSaddleItem;
		}

		@Override
		public void set(int index, ItemResource resource, int amount) {
			NeoForgeChocobo.this.setSaddleType(resource.toStack());
			super.set(index, resource, amount);
		}

		@Override
		protected void onContentsChanged(int index, ItemStack previousContents) {
			NeoForgeChocobo.this.setSaddleType(getResource(index).toStack());
			super.onContentsChanged(index, previousContents);
		}
	};

	public NeoForgeChocobo(EntityType<? extends AbstractChocobo> type, Level world) {
		super(type, world);
	}

	@Override
	public void addAdditionalSaveData(ValueOutput output) {
		super.addAdditionalSaveData(output);

		ValueOutput saddleOutput = output.child(NBTKEY_SADDLE_ITEM);
		this.saddleItemStackHandler.serialize(saddleOutput);

		ValueOutput inventoryOutput = output.child(NBTKEY_INVENTORY);
		this.inventory.serialize(inventoryOutput);
	}

	@Override
	public void readAdditionalSaveData(ValueInput input) {
		super.readAdditionalSaveData(input);

		ValueInput saddleInput = input.childOrEmpty(NBTKEY_SADDLE_ITEM);
		this.saddleItemStackHandler.deserialize(saddleInput);
		setSaddleType(this.saddleItemStackHandler.getResource(0).toStack());

		ValueInput inventoryInput = input.childOrEmpty(NBTKEY_INVENTORY);
		this.inventory.deserialize(inventoryInput);
	}


	@Override
	protected void setSaddled(Player player, InteractionHand hand, ItemStack heldItemStack) {
		if (!this.level().isClientSide()) {
			try (Transaction tx = Transaction.openRoot()) {
				if (this.saddleItemStackHandler.insert(0, ItemResource.of(heldItemStack.getItem()), 1, tx) != 1) {
					return;
				}
				tx.commit();
			}

			this.setSaddleType(heldItemStack);
			this.usePlayerItem(player, hand, heldItemStack);
		}
	}

	@Override
	public void openCustomInventoryScreen(Player player) {
		if (!this.level().isClientSide() && (!this.isVehicle() || this.hasPassenger(player)) && this.isTame()) {
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
		if (!this.level().isClientSide()) {
			// TODO: Handle resizing. ItemStackHandler#setSize() clears the internal inventory!
			try (Transaction tx = Transaction.openRoot()) {
				for (int i = 0; i < this.inventory.size(); i++) {
					if (this.isAlive()) {
						Containers.dropItemStack(this.level(), this.getX(), this.getY() + .5, this.getZ(),
								this.inventory.getResource(i).toStack(this.inventory.getAmountAsInt(i))
						);
					}
				}
				tx.commit();
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
			try (Transaction tx = Transaction.openRoot()) {
				for (int i = 0; i < this.inventory.size(); i++) {
					if (this.isAlive()) {
						this.spawnAtLocation((ServerLevel) level(),
								this.inventory.getResource(i).toStack(this.inventory.getAmountAsInt(i)), 0.0f);
					}
				}
			}
		}
	}

	public ResourceHandler<ItemResource> getInventory() {
		return this.inventory;
	}
}
