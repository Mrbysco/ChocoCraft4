package net.chococraft.common.inventory;

import net.chococraft.common.entities.ChocoboEntity;
import net.chococraft.common.items.ChocoboSaddleItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SaddleBagContainer extends Container {
    private ChocoboEntity chocobo;
    public SaddleBagContainer(int id, PlayerInventory player, ChocoboEntity chocobo) {
        super((ContainerType<?>)null, id);
        this.chocobo = chocobo;
        this.refreshSlots(chocobo, player);
    }

    public void refreshSlots(ChocoboEntity chocobo, PlayerInventory player) {
        this.inventorySlots.clear();
        bindPlayerInventory(player.player);

        ItemStack saddleStack = chocobo.getSaddle();
        if(!saddleStack.isEmpty() && saddleStack.getItem() instanceof ChocoboSaddleItem) {
            ChocoboSaddleItem saddleItem = (ChocoboSaddleItem) saddleStack.getItem();
            if(saddleItem.getInventorySize() == 18) {
                bindInventorySmall(chocobo.chocoboInventory);
            } else if(saddleItem.getInventorySize() == 54) {
                bindInventoryBig(chocobo.chocoboInventory);
            }
        }

        this.addSlot(new SlotChocoboSaddle(chocobo.saddleItemStackHandler, 0, -16, 18));
    }

    private void bindInventorySmall(IItemHandler inventory) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 5; col++) {
                this.addSlot(new SlotItemHandler(inventory, row * 5 + col, 44 + col * 18, 36 + row * 18));
            }
        }
    }

    private void bindInventoryBig(IItemHandler inventory) {
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new SlotItemHandler(inventory, row * 9 + col, 8 + col * 18, 18 + row * 18));
            }
        }
    }

    private void bindPlayerInventory(PlayerEntity player) {
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(player.inventory, col + row * 9 + 9, 8 + col * 18, 122 + row * 18));
            }
        }

        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(player.inventory, i, 8 + i * 18, 180));
        }
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return this.chocobo.isAlive() && this.chocobo.getDistance(playerIn) < 8.0F;
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        return ItemStack.EMPTY;
    }
}
