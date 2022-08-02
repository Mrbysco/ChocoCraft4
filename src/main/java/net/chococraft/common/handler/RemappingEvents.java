package net.chococraft.common.handler;

import net.chococraft.Chococraft;
import net.chococraft.common.init.ModRegistry;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Chococraft.MODID)
public class RemappingEvents {
	@SubscribeEvent
	public static void remapMissingItems(final RegistryEvent.MissingMappings<Item> event) {
		for (RegistryEvent.MissingMappings.Mapping<Item> map : event.getMappings(Chococraft.MODID)) {
			if (map.key.getNamespace().equals("lovely_gysahl_green")) {
				map.remap(ModRegistry.LOVERLY_GYSAHL_GREEN.get());
			}
		}
	}
}
