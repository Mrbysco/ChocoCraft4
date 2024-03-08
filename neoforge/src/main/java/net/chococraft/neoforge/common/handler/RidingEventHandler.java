package net.chococraft.neoforge.common.handler;

import net.chococraft.Chococraft;
import net.chococraft.common.entity.AbstractChocobo;
import net.neoforged.neoforge.event.entity.EntityMountEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Chococraft.MOD_ID)
public class RidingEventHandler {
	@SubscribeEvent
	public static void onMountEntity(EntityMountEvent event) {
		if (event.isMounting()) return;
		if (!event.getEntityBeingMounted().isAlive()) return;
		if (!(event.getEntityBeingMounted() instanceof AbstractChocobo)) return;

		if (event.getEntityBeingMounted().isInWater()) return;

		if (!event.getEntityBeingMounted().onGround())
			event.setCanceled(true);
	}
}
