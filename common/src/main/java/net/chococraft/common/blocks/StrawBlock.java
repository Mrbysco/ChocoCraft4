package net.chococraft.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class StrawBlock extends Block {
	protected static final VoxelShape AABB = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 1.0D, 15.0D);

	public StrawBlock(Properties properties) {
		super(properties);
	}

	public VoxelShape getShape(BlockState p_49341_, BlockGetter p_49342_, BlockPos p_49343_, CollisionContext p_49344_) {
		return AABB;
	}
}
