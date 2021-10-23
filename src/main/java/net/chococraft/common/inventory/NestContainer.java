package net.chococraft.common.inventory;

import net.chococraft.common.blockentities.ChocoboNestBlockEntity;
import net.chococraft.common.init.ModContainers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.SlotItemHandler;

import java.util.Objects;

public class NestContainer extends AbstractContainerMenu {
    private ChocoboNestBlockEntity tile;

    public NestContainer(final int windowId, final Inventory playerInventory, final FriendlyByteBuf data) {
        this(windowId, playerInventory, getTileEntity(playerInventory, data));
    }

    private static ChocoboNestBlockEntity getTileEntity(final Inventory playerInventory, final FriendlyByteBuf data) {
        Objects.requireNonNull(playerInventory, "playerInventory cannot be null!");
        Objects.requireNonNull(data, "data cannot be null!");
        final BlockEntity tileAtPos = playerInventory.player.level.getBlockEntity(data.readBlockPos());

        if (tileAtPos instanceof ChocoboNestBlockEntity) {
            return (ChocoboNestBlockEntity) tileAtPos;
        }

        throw new IllegalStateException("Tile entity is not correct! " + tileAtPos);
    }

    public NestContainer(int id, Inventory playerInventoryIn, ChocoboNestBlockEntity chocoboNest) {
        super(ModContainers.NEST.get(), id);

        this.tile = chocoboNest;

        this.addSlot(new SlotItemHandler(tile.getInventory(), 0, 80, 35));

        this.bindPlayerInventory(playerInventoryIn);
    }

    private void bindPlayerInventory(Inventory inventory) {
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(inventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(inventory, i, 8 + i * 18, 142));
        }
    }

    public ChocoboNestBlockEntity getTile() {
        return tile;
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return true;
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            final int tileSize = 1;

            if (index < tileSize) {
                if (!this.moveItemStackTo(itemstack1, tileSize, slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, tileSize, false)) {
                return ItemStack.EMPTY;
            }

            this.tile.onInventoryChanged();

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }
        }

        return itemstack;
    }
}
