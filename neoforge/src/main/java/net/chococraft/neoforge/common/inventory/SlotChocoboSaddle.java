package net.chococraft.neoforge.common.inventory;

import net.chococraft.common.items.ChocoboSaddleItem;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.transfer.IndexModifier;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ResourceHandlerSlot;
import org.jetbrains.annotations.NotNull;

public class SlotChocoboSaddle extends ResourceHandlerSlot {
	public SlotChocoboSaddle(ResourceHandler<ItemResource> handler, IndexModifier<ItemResource> slotModifier, int index, int xPosition, int yPosition) {
		super(handler, slotModifier, index, xPosition, yPosition);
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
