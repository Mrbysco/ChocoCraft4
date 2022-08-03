package net.chococraft.common.handler;

import net.chococraft.Chococraft;
import net.chococraft.common.init.ModRegistry;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.MissingMappingsEvent;

@Mod.EventBusSubscriber(modid = Chococraft.MODID)
public class RemappingEvents {
	@SubscribeEvent
	public static void remapMissingItems(final MissingMappingsEvent event) {
		System.out.println("Hey");
		for (MissingMappingsEvent.Mapping<Item> map : event.getAllMappings(ForgeRegistries.Keys.ITEMS)) {
			if (map.getKey().getNamespace().equals(Chococraft.MODID) && map.getKey().getNamespace().equals("lovely_gysahl_green")) {
				map.remap(ModRegistry.LOVERLY_GYSAHL_GREEN.get());
			}
		}
	}
}