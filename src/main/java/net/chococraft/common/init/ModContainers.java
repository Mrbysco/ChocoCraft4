package net.chococraft.common.init;

import net.chococraft.Chococraft;
import net.chococraft.common.inventory.NestContainer;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModContainers {
	public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Chococraft.MODID);

	public static final RegistryObject<MenuType<NestContainer>> NEST = CONTAINERS.register("nest", () ->
			IForgeContainerType.create((windowId, inv, data) -> new NestContainer(windowId, inv, data)));
}
