package net.chococraft.common.entities.goal;

import net.chococraft.common.entities.Chocobo;
import net.chococraft.common.init.ModRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.EnumSet;

public class ChocoboHealInPenGoal extends Goal {
	private final Chocobo chocobo;

	public ChocoboHealInPenGoal(Chocobo chocobo) {
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
			if (state.is(Blocks.WATER_CAULDRON) && state.getValue(BlockStateProperties.LEVEL_CAULDRON) == 3) {
				chocobo.heal(chocobo.getRandom().nextInt(3) + 1);
			}
		}
	}
}
