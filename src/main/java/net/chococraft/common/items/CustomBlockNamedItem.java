package net.chococraft.common.items;

import net.minecraft.block.Block;
import net.minecraft.item.BlockNamedItem;

import java.util.function.Supplier;

public class CustomBlockNamedItem extends BlockNamedItem {
	private final Supplier<Block> blockSupplier;

	public CustomBlockNamedItem(Supplier<Block> blockSupplier, Properties properties) {
		super(null, properties);
		this.blockSupplier = blockSupplier;
	}

	@Override
	public Block getBlock() {
		return this.blockSupplier.get() == null ? null : this.blockSupplier.get().delegate.get();
	}
}
