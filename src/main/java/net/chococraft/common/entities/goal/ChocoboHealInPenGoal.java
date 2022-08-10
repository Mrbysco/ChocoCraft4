package net.chococraft.common.entities.goal;

import net.chococraft.common.entities.ChocoboEntity;
import net.chococraft.common.init.ModRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;

import java.util.EnumSet;

public class ChocoboHealInPenGoal extends Goal {
	private final ChocoboEntity chocobo;

	public ChocoboHealInPenGoal(ChocoboEntity chocobo) {
		this.chocobo = chocobo;
		this.setFlags(EnumSet.of(Goal.Flag.JUMP));
	}

	@Override
	public boolean canUse() {
		if (chocobo.level.getGameTime() % 40 == 0 && chocobo.getHealth() != chocobo.getMaxHealth()) {
			if (chocobo.level.getBlockState(chocobo.blockPosition()).is(ModRegistry.STRAW.get())) {
				return true;
			}
		}

		return false;
	}

	@Override
	public void start() {
		BlockPos pos = chocobo.blockPosition();
		Iterable<BlockPos> positions = BlockPos.betweenClosed(pos.offset(-5, 0, -5), pos.offset(5, 0, 5));
		for (BlockPos position : positions) {
			BlockState state = chocobo.level.getBlockState(position);
			if (state.is(Blocks.CAULDRON) && state.getValue(BlockStateProperties.LEVEL_CAULDRON) == 3) {
				chocobo.heal(chocobo.getRandom().nextInt(3) + 1);
			}
		}
	}
}
