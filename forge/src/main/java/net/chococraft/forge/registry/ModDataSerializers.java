package net.chococraft.forge.registry;

import net.chococraft.Chococraft;
import net.chococraft.common.entity.properties.ChocoboColor;
import net.chococraft.common.entity.properties.MovementType;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModDataSerializers {
	public static final DeferredRegister<EntityDataSerializer<?>> ENTITY_DATA_SERIALIZER = DeferredRegister.create(ForgeRegistries.Keys.ENTITY_DATA_SERIALIZERS, Chococraft.MOD_ID);

	public final static RegistryObject<EntityDataSerializer<ChocoboColor>> CHOCOBO_COLOR = ENTITY_DATA_SERIALIZER.register("chocobo_color", () -> EntityDataSerializer.simpleEnum(ChocoboColor.class));
	public final static RegistryObject<EntityDataSerializer<MovementType>> MOVEMENT_TYPE = ENTITY_DATA_SERIALIZER.register("movement_type", () -> EntityDataSerializer.simpleEnum(MovementType.class));
}
