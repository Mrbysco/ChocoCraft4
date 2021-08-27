package net.chococraft.common.tileentities;

import net.chococraft.common.entities.breeding.ChocoboBreedInfo;
import net.chococraft.common.init.ModRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nullable;

public class ChocoboEggTile extends TileEntity {
    public final static String NBTKEY_BREEDINFO = "BreedInfo";
    private ChocoboBreedInfo breedInfo;

    public ChocoboEggTile() {
        super(ModRegistry.CHOCOBO_EGG_TILE.get());
    }

    @Override
    public void read(BlockState state, CompoundNBT compound) {
        super.read(state, compound);
        this.breedInfo = new ChocoboBreedInfo(compound.getCompound(NBTKEY_BREEDINFO));
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        if (this.breedInfo != null) {
            compound.put(NBTKEY_BREEDINFO, this.breedInfo.serialize());
        }
        return super.write(compound);
    }

    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT nbt = super.getUpdateTag();
        this.write(nbt);
        return nbt;
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT compound) {
        super.read(state, compound);
    }

    @Nullable
    public ChocoboBreedInfo getBreedInfo() {
        return this.breedInfo;
    }

    public void setBreedInfo(ChocoboBreedInfo breedInfo) {
        this.breedInfo = breedInfo;
    }
}
