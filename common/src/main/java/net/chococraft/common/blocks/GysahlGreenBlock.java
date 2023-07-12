package net.chococraft.common.blocks;

import net.chococraft.registry.ModRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class GysahlGreenBlock extends CropBlock {
	public static final int MAX_AGE = 4;
	public static final IntegerProperty AGE = IntegerProperty.create("age", 0, MAX_AGE);

	@SuppressWarnings("unused") // used by class factory
	public GysahlGreenBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(this.getAgeProperty(), 0));
	}

	@Override
	protected ItemLike getBaseSeedId() {
		return ModRegistry.GYSAHL_GREEN_SEEDS::get;
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		boolean canSurvive = super.canSurvive(state, level, pos);
		if (level instanceof WorldGenLevel && state.getValue(AGE) == MAX_AGE) {
			BlockPos blockPos2 = pos.below();
			return this.mayPlaceOn(level.getBlockState(blockPos2), level, blockPos2);
		}
		return canSurvive;
	}

	@Override
	protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
		return super.mayPlaceOn(state, level, pos) || state.is(BlockTags.DIRT);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(AGE);
	}

	@Override
	public IntegerProperty getAgeProperty() {
		return AGE;
	}

	@Override
	public int getMaxAge() {
		return MAX_AGE;
	}
}
