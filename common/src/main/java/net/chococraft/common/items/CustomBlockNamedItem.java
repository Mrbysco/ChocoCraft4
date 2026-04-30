package net.chococraft.common.items;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class CustomBlockNamedItem extends BlockItem {
	private final Supplier<? extends Block> blockSupplier;

	public CustomBlockNamedItem(Supplier<? extends Block> blockSupplier, Properties properties) {
		super(null, properties);
		this.blockSupplier = blockSupplier;
	}

	@Override
	public Block getBlock() {
		return this.blockSupplier.get() == null ? null : this.blockSupplier.get();
	}
}
