package net.chococraft.common.inventory;

import net.chococraft.common.init.ModContainers;
import net.chococraft.common.tileentities.ChocoboNestTile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import java.util.Objects;

public class NestContainer extends Container {
    private ChocoboNestTile tile;
    private PlayerEntity player;

    public NestContainer(final int windowId, final PlayerInventory playerInventory, final PacketBuffer data) {
        this(windowId, playerInventory, getTileEntity(playerInventory, data));
    }

    private static ChocoboNestTile getTileEntity(final PlayerInventory playerInventory, final PacketBuffer data) {
        Objects.requireNonNull(playerInventory, "playerInventory cannot be null!");
        Objects.requireNonNull(data, "data cannot be null!");
        final TileEntity tileAtPos = playerInventory.player.level.getBlockEntity(data.readBlockPos());

        if (tileAtPos instanceof ChocoboNestTile) {
            return (ChocoboNestTile) tileAtPos;
        }

        throw new IllegalStateException("Tile entity is not correct! " + tileAtPos);
    }

    public NestContainer(int id, PlayerInventory playerInventoryIn, ChocoboNestTile chocoboNest) {
        super(ModContainers.NEST.get(), id);

        this.tile = chocoboNest;
        this.player = playerInventoryIn.player;

        this.addSlot(new SlotItemHandler(tile.getInventory(), 0, 80, 35));

        this.bindPlayerInventory(player);
    }

    private void bindPlayerInventory(PlayerEntity player) {
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(player.inventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(player.inventory, i, 8 + i * 18, 142));
        }
    }

    public ChocoboNestTile getTile() {
        return tile;
    }

    @Override
    public boolean stillValid(PlayerEntity playerIn) {
        return true;
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
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

    private static class SlotEgg extends SlotItemHandler {
        public SlotEgg(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }

        @Override
        public void setChanged() {
            super.setChanged();
        }

        @Override
        public boolean mayPickup(PlayerEntity playerIn) {
            return true;
        }

        @Override
        public boolean mayPlace(@Nonnull ItemStack stack) {
            return super.mayPlace(stack);
        }
    }
}
