package net.chococraft.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class StrawBlock extends Block {
	protected static final VoxelShape AABB = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 1.0D, 15.0D);

	public StrawBlock(Properties properties) {
		super(properties);
	}

	public VoxelShape getShape(BlockState p_49341_, IBlockReader p_49342_, BlockPos p_49343_, ISelectionContext p_49344_) {
		return AABB;
	}
}
