package net.chococraft.forge.common.handler;

import net.chococraft.Chococraft;
import net.chococraft.common.entity.AbstractChocobo;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Chococraft.MOD_ID)
public class RidingEventHandler {
	@SubscribeEvent
	public static void onMountEntity(EntityMountEvent event) {
		if (event.isMounting()) return;
		if (!event.getEntityBeingMounted().isAlive()) return;
		if (!(event.getEntityBeingMounted() instanceof AbstractChocobo)) return;

		if (event.getEntityBeingMounted().isInWater()) return;

		if (!event.getEntityBeingMounted().isOnGround())
			event.setCanceled(true);
	}
}
