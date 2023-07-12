package net.chococraft.common.entity.goal;

import net.chococraft.common.entity.AbstractChocobo;
import net.chococraft.registry.ModRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.EnumSet;

public class ChocoboHealInPenGoal extends Goal {
	private final AbstractChocobo chocobo;

	public ChocoboHealInPenGoal(AbstractChocobo chocobo) {
		this.chocobo = chocobo;
		this.setFlags(EnumSet.of(Flag.JUMP));
	}

	@Override
	public boolean canUse() {
		if (chocobo.level().getGameTime() % 40 == 0 && chocobo.getHealth() != chocobo.getMaxHealth()) {
			return chocobo.level().getBlockState(chocobo.blockPosition()).is(ModRegistry.STRAW.get());
		}

		return false;
	}

	@Override
	public void start() {
		BlockPos pos = chocobo.blockPosition();
		Iterable<BlockPos> positions = BlockPos.betweenClosed(pos.offset(-5, 0, -5), pos.offset(5, 0, 5));
		for (BlockPos position : positions) {
			BlockState state = chocobo.level().getBlockState(position);
			if (state.is(Blocks.WATER_CAULDRON) && state.getValue(BlockStateProperties.LEVEL_CAULDRON) == 3) {
				chocobo.heal(chocobo.getRandom().nextInt(3) + 1);
			}
		}
	}
}
