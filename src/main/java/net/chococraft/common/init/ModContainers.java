package net.chococraft.common.init;

import net.chococraft.Chococraft;
import net.chococraft.common.inventory.NestContainer;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModContainers {
	public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Chococraft.MODID);

	public static final RegistryObject<MenuType<NestContainer>> NEST = CONTAINERS.register("nest", () -> IForgeMenuType.create(NestContainer::new));
}
