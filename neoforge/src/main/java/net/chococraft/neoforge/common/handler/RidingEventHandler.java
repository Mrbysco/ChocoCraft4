package net.chococraft.neoforge.common.handler;

import net.chococraft.Chococraft;
import net.chococraft.common.entity.AbstractChocobo;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityMountEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber(modid = Chococraft.MOD_ID)
public class RidingEventHandler {

	@SubscribeEvent
	public static void onLogOut(PlayerEvent.PlayerLoggedOutEvent event) {
		Player player = event.getEntity();
		if (player.getVehicle() != null) {
			Entity entityRide = player.getVehicle();
			if (entityRide instanceof AbstractChocobo) {
				player.removeVehicle();
			}
		}
	}

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
