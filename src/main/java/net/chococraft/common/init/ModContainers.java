package net.chococraft.common.init;

import net.chococraft.Chococraft;
import net.chococraft.common.inventory.NestContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModContainers {
	public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Chococraft.MODID);

	public static final RegistryObject<ContainerType<NestContainer>> NEST = CONTAINERS.register("nest", () -> IForgeContainerType.create(NestContainer::new));
}
