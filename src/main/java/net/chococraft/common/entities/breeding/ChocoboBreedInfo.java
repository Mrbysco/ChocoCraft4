package net.chococraft.common.entities.breeding;

import net.minecraft.nbt.CompoundTag;

import javax.annotation.Nullable;

public class ChocoboBreedInfo {
    public final static String NBTKEY_MOTHER_STATSNAPSHOT = "mother";
    public final static String NBTKEY_FATHER_STATSNAPSHOT = "father";

    private ChocoboStatSnapshot mother = ChocoboStatSnapshot.DEFAULT;
    private ChocoboStatSnapshot father = ChocoboStatSnapshot.DEFAULT;

    public ChocoboStatSnapshot getMother() {
        return this.mother;
    }

    public ChocoboStatSnapshot getFather() {
        return this.father;
    }

    public ChocoboBreedInfo(ChocoboStatSnapshot mother, ChocoboStatSnapshot father) {
        this.mother = mother;
        this.father = father;
    }

    public ChocoboBreedInfo(CompoundTag nbt) {
        if (nbt.contains(NBTKEY_MOTHER_STATSNAPSHOT))
            this.mother = new ChocoboStatSnapshot(nbt.getCompound(NBTKEY_MOTHER_STATSNAPSHOT));
        if (nbt.contains(NBTKEY_FATHER_STATSNAPSHOT))
            this.father = new ChocoboStatSnapshot(nbt.getCompound(NBTKEY_FATHER_STATSNAPSHOT));
    }

    public CompoundTag serialize() {
        CompoundTag nbt = new CompoundTag();
        nbt.put(NBTKEY_MOTHER_STATSNAPSHOT, this.mother.serialize());
        nbt.put(NBTKEY_FATHER_STATSNAPSHOT, this.father.serialize());
        return nbt;
    }

    public static ChocoboBreedInfo getFromNbtOrDefault(@Nullable CompoundTag nbt) {
        return nbt != null ? new ChocoboBreedInfo(nbt) : new ChocoboBreedInfo(ChocoboStatSnapshot.DEFAULT, ChocoboStatSnapshot.DEFAULT);
    }
}
