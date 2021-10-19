package net.chococraft.utils;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;

public class GenericByteEnumSerializer<E extends Enum<E>> implements EntityDataSerializer<E> {
    private E[] values;

    public GenericByteEnumSerializer(E[] values) {
        this.values = values;
    }

    @Override
    public void write(FriendlyByteBuf buf, E value) {
        buf.writeByte(value.ordinal());
    }

    @Override
    public E read(FriendlyByteBuf buf) {
        return values[buf.readByte()];
    }

    @Override
    public EntityDataAccessor<E> createAccessor(int id) {
        return new EntityDataAccessor<>(id, this);
    }

    @Override
    public E copy(E value) {
        return value;
    }
}
