package net.chococraft.common.entities.breeding;

import net.chococraft.Chococraft;
import net.chococraft.common.entities.ChocoboEntity;
import net.chococraft.common.init.ModRegistry;
import net.chococraft.common.tileentities.ChocoboEggTile;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;

public class ChocoboMateGoal extends Goal {
    private final static Vector3i[] LAY_EGG_CHECK_OFFSETS =
            {
                    new Vector3i(0, 0, 0), new Vector3i(-1, 0, 0), new Vector3i(-1, 0, -1),
                    new Vector3i(0, 0, -1), new Vector3i(+1, 0, -1), new Vector3i(+1, 0, 0),
                    new Vector3i(+1, 0, +1), new Vector3i(0, 0, +1), new Vector3i(-1, 0, +1),

                    new Vector3i(0, 1, 0), new Vector3i(-1, 1, 0), new Vector3i(-1, 1, -1),
                    new Vector3i(0, 1, -1), new Vector3i(+1, 1, -1), new Vector3i(+1, 1, 0),
                    new Vector3i(+1, 1, +1), new Vector3i(0, 1, +1), new Vector3i(-1, 1, +1),
            };

    private final ChocoboEntity chocobo;
    private final World world;
    private final double moveSpeed;
    private ChocoboEntity targetMate;
    private int spawnBabyDelay;

    public ChocoboMateGoal(ChocoboEntity chocobo, double moveSpeed) {
        this.chocobo = chocobo;
        this.world = chocobo.world;
        this.moveSpeed = moveSpeed;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean shouldExecute() {
        return this.chocobo.isInLove() && (this.targetMate = this.getNearbyMate()) != null;
    }

    @Override
    public boolean shouldContinueExecuting() {
        return this.targetMate.isAlive() && this.targetMate.isInLove() && this.spawnBabyDelay < 60;
    }

    @Override
    public void resetTask() {
        this.targetMate = null;
        this.spawnBabyDelay = 0;
    }

    @Override
    public void tick() {
        this.chocobo.getLookController().setLookPositionWithEntity(this.targetMate, 10.0F, (float) this.chocobo.getVerticalFaceSpeed());
        this.chocobo.getNavigator().tryMoveToEntityLiving(this.targetMate, this.moveSpeed);
        ++this.spawnBabyDelay;

        if (this.spawnBabyDelay >= 60 && this.chocobo.getDistanceSq(this.targetMate) < 9.0D) {
            this.spawnEgg();
        }
    }

    @Nullable
    private ChocoboEntity getNearbyMate() {
        List<ChocoboEntity> list = this.world.getEntitiesWithinAABB(ChocoboEntity.class, this.chocobo.getBoundingBox().grow(8.0D));
        double dist = Double.MAX_VALUE;
        ChocoboEntity closestMate = null;

        for (ChocoboEntity entry : list) {
            if (this.chocobo.canMateWith(entry) && this.chocobo.getDistanceSq(entry) < dist) {
                closestMate = entry;
                dist = this.chocobo.getDistanceSq(entry);
            }
        }

        return closestMate;
    }

    private void spawnEgg() {
        if (this.chocobo.isMale()) return;

        this.chocobo.setGrowingAge(6000);
        this.targetMate.setGrowingAge(6000);
        this.chocobo.resetInLove();
        this.targetMate.resetInLove();

        BlockPos pos = this.chocobo.getPosition();
        for (Vector3i offset : LAY_EGG_CHECK_OFFSETS) {
            BlockPos offsetPos = pos.add(offset);
            BlockState state = this.world.getBlockState(offsetPos);
            if (state.getMaterial().isReplaceable() && !state.getMaterial().isLiquid() && ModRegistry.CHOCOBO_EGG.get().isValidPosition(state, this.world, offsetPos)) {
                if (!this.world.setBlockState(offsetPos, ModRegistry.CHOCOBO_EGG.get().getDefaultState())) {
                    Chococraft.log.error("Unable to place egg @ {}, setBlockState() returned false!", offsetPos);
                    return;
                }

                TileEntity tile = this.world.getTileEntity(offsetPos);
                if(tile instanceof ChocoboEggTile) {
                    ChocoboEggTile eggTile = (ChocoboEggTile)tile;
                    eggTile.setBreedInfo(new ChocoboBreedInfo(new ChocoboStatSnapshot(this.chocobo), new ChocoboStatSnapshot(this.targetMate)));
                } else {
                    Chococraft.log.error("Unable to place egg @ {}, no tile entity was found at the given position!", offsetPos);
                    return;
                }

                return;
            }
        }

    }
}
