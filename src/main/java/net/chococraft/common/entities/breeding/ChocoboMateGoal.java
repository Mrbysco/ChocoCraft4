package net.chococraft.common.entities.breeding;

import net.chococraft.Chococraft;
import net.chococraft.common.entities.ChocoboEntity;
import net.chococraft.common.init.ModRegistry;
import net.chococraft.common.blockentities.ChocoboEggBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;

public class ChocoboMateGoal extends Goal {
    private final static Vec3i[] LAY_EGG_CHECK_OFFSETS =
            {
                    new Vec3i(0, 0, 0), new Vec3i(-1, 0, 0), new Vec3i(-1, 0, -1),
                    new Vec3i(0, 0, -1), new Vec3i(+1, 0, -1), new Vec3i(+1, 0, 0),
                    new Vec3i(+1, 0, +1), new Vec3i(0, 0, +1), new Vec3i(-1, 0, +1),

                    new Vec3i(0, 1, 0), new Vec3i(-1, 1, 0), new Vec3i(-1, 1, -1),
                    new Vec3i(0, 1, -1), new Vec3i(+1, 1, -1), new Vec3i(+1, 1, 0),
                    new Vec3i(+1, 1, +1), new Vec3i(0, 1, +1), new Vec3i(-1, 1, +1),
            };

    private final ChocoboEntity chocobo;
    private final Level world;
    private final double moveSpeed;
    private ChocoboEntity targetMate;
    private int spawnBabyDelay;

    public ChocoboMateGoal(ChocoboEntity chocobo, double moveSpeed) {
        this.chocobo = chocobo;
        this.world = chocobo.level;
        this.moveSpeed = moveSpeed;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return this.chocobo.isInLove() && (this.targetMate = this.getNearbyMate()) != null;
    }

    @Override
    public boolean canContinueToUse() {
        return this.targetMate.isAlive() && this.targetMate.isInLove() && this.spawnBabyDelay < 60;
    }

    @Override
    public void stop() {
        this.targetMate = null;
        this.spawnBabyDelay = 0;
    }

    @Override
    public void tick() {
        this.chocobo.getLookControl().setLookAt(this.targetMate, 10.0F, (float) this.chocobo.getMaxHeadXRot());
        this.chocobo.getNavigation().moveTo(this.targetMate, this.moveSpeed);
        ++this.spawnBabyDelay;

        if (this.spawnBabyDelay >= 60 && this.chocobo.distanceToSqr(this.targetMate) < 9.0D) {
            this.spawnEgg();
        }
    }

    @Nullable
    private ChocoboEntity getNearbyMate() {
        List<ChocoboEntity> list = this.world.getEntitiesOfClass(ChocoboEntity.class, this.chocobo.getBoundingBox().inflate(8.0D));
        double dist = Double.MAX_VALUE;
        ChocoboEntity closestMate = null;

        for (ChocoboEntity entry : list) {
            if (this.chocobo.canMate(entry) && this.chocobo.distanceToSqr(entry) < dist) {
                closestMate = entry;
                dist = this.chocobo.distanceToSqr(entry);
            }
        }

        return closestMate;
    }

    private void spawnEgg() {
        if (this.chocobo.isMale()) return;

        this.chocobo.setAge(6000);
        this.targetMate.setAge(6000);
        this.chocobo.resetLove();
        this.targetMate.resetLove();

        BlockPos pos = this.chocobo.blockPosition();
        for (Vec3i offset : LAY_EGG_CHECK_OFFSETS) {
            BlockPos offsetPos = pos.offset(offset);
            BlockState state = this.world.getBlockState(offsetPos);
            if (state.getMaterial().isReplaceable() && !state.getMaterial().isLiquid() && ModRegistry.CHOCOBO_EGG.get().canSurvive(state, this.world, offsetPos)) {
                if (!this.world.setBlockAndUpdate(offsetPos, ModRegistry.CHOCOBO_EGG.get().defaultBlockState())) {
                    Chococraft.log.error("Unable to place egg @ {}, setBlockState() returned false!", offsetPos);
                    return;
                }

                BlockEntity tile = this.world.getBlockEntity(offsetPos);
                if(tile instanceof ChocoboEggBlockEntity) {
                    ChocoboEggBlockEntity eggTile = (ChocoboEggBlockEntity)tile;
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
