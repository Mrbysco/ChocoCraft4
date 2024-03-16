package net.chococraft.fabric.registry;

import net.chococraft.common.entity.properties.ChocoboColor;
import net.chococraft.common.entity.properties.MovementType;
import net.chococraft.utils.GenericByteEnumSerializer;
import net.minecraft.network.syncher.EntityDataSerializer;

public class ModDataSerializers {
	public final static EntityDataSerializer<ChocoboColor> CHOCOBO_COLOR = new GenericByteEnumSerializer<>(ChocoboColor.values());
	public final static EntityDataSerializer<MovementType> MOVEMENT_TYPE = new GenericByteEnumSerializer<>(MovementType.values());
}
