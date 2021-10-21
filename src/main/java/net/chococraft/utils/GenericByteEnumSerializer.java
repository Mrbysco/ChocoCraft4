package net.chococraft.utils;

import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.IDataSerializer;

public class GenericByteEnumSerializer<E extends Enum<E>> implements IDataSerializer<E> {
    private E[] values;

    public GenericByteEnumSerializer(E[] values) {
        this.values = values;
    }

    @Override
    public void write(PacketBuffer buf, E value) {
        buf.writeByte(value.ordinal());
    }

    @Override
    public E read(PacketBuffer buf) {
        return values[buf.readByte()];
    }

    @Override
    public DataParameter<E> createAccessor(int id) {
        return new DataParameter<>(id, this);
    }

    @Override
    public E copy(E value) {
        return value;
    }
}
