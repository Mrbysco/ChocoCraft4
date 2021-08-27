package net.chococraft.common.entities.properties;

import net.chococraft.utils.GenericByteEnumSerializer;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.IDataSerializer;

public class EntityDataSerializers {
    public final static IDataSerializer<ChocoboColor> CHOCOBO_COLOR = new GenericByteEnumSerializer<>(ChocoboColor.values());
    public final static IDataSerializer<MovementType> MOVEMENT_TYPE = new GenericByteEnumSerializer<>(MovementType.values());

    public static void init() {
        DataSerializers.registerSerializer(CHOCOBO_COLOR);
        DataSerializers.registerSerializer(MOVEMENT_TYPE);
    }
}
