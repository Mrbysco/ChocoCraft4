package net.chococraft.utils;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldUtils {
    public static int getDistanceToSurface(BlockPos startPos, World world) {
        BlockPos lastLiquidPos = null;

        for (BlockPos pos = startPos; pos.getY() < world.getMaxBuildHeight(); pos = pos.above()) {
            BlockState state = world.getBlockState(pos);
            if (!state.getMaterial().isLiquid())
                break;

            lastLiquidPos = pos;
        }

        return lastLiquidPos == null ? -1 : lastLiquidPos.getY() - startPos.getY();
    }
}
