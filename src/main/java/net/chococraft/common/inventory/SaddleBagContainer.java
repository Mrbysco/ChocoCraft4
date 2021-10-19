package net.chococraft.common.inventory;

import net.chococraft.common.entities.ChocoboEntity;
import net.chococraft.common.items.ChocoboSaddleItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SaddleBagContainer extends AbstractContainerMenu {
    private ChocoboEntity chocobo;
    public SaddleBagContainer(int id, Inventory player, ChocoboEntity chocobo) {
        super((MenuType<?>)null, id);
        this.chocobo = chocobo;
        this.refreshSlots(chocobo, player);
    }

    public void refreshSlots(ChocoboEntity chocobo, Inventory player) {
        this.slots.clear();
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

    private void bindPlayerInventory(Player player) {
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(player.getInventory(), col + row * 9 + 9, 8 + col * 18, 122 + row * 18));
            }
        }

        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(player.getInventory(), i, 8 + i * 18, 180));
        }
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return this.chocobo.isAlive() && this.chocobo.distanceTo(playerIn) < 8.0F;
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        return ItemStack.EMPTY;
    }
}
