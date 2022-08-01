package net.chococraft.common.entities.properties;

import net.chococraft.utils.GenericByteEnumSerializer;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;

public class ModDataSerializers {
	public final static EntityDataSerializer<ChocoboColor> CHOCOBO_COLOR = new GenericByteEnumSerializer<>(ChocoboColor.values());
	public final static EntityDataSerializer<MovementType> MOVEMENT_TYPE = new GenericByteEnumSerializer<>(MovementType.values());

	public static void init() {
		EntityDataSerializers.registerSerializer(CHOCOBO_COLOR);
		EntityDataSerializers.registerSerializer(MOVEMENT_TYPE);
	}
}
