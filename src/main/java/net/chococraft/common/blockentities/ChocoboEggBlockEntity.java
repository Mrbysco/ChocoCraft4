package net.chococraft.common.blockentities;

import net.chococraft.common.entities.breeding.ChocoboBreedInfo;
import net.chococraft.common.init.ModRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class ChocoboEggBlockEntity extends BlockEntity {
    public final static String NBTKEY_BREEDINFO = "BreedInfo";
    private ChocoboBreedInfo breedInfo;

    public ChocoboEggBlockEntity(BlockPos pos, BlockState state) {
        super(ModRegistry.CHOCOBO_EGG_TILE.get(), pos, state);
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        this.breedInfo = new ChocoboBreedInfo(compound.getCompound(NBTKEY_BREEDINFO));
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        saveAdditional(tag);
        return super.save(tag);
    }


    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        if (this.breedInfo != null) {
            compound.put(NBTKEY_BREEDINFO, this.breedInfo.serialize());
        }
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag nbt = super.getUpdateTag();
        this.saveAdditional(nbt);
        return nbt;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        super.handleUpdateTag(tag);
    }

    @Nullable
    public ChocoboBreedInfo getBreedInfo() {
        return this.breedInfo;
    }

    public void setBreedInfo(ChocoboBreedInfo breedInfo) {
        this.breedInfo = breedInfo;
    }
}
