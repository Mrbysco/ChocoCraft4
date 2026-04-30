package net.chococraft.fabric.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;

public interface PlayerQuitEvent {
	Event<PlayerQuitEvent> QUIT = EventFactory.createArrayBacked(PlayerQuitEvent.class,
			(listeners) -> (player) -> {
				for (PlayerQuitEvent event : listeners) {
					InteractionResult result = event.onQuit(player);

					if (result != InteractionResult.PASS) {
						return result;
					}
				}

				return InteractionResult.PASS;
			}
	);

	InteractionResult onQuit(Player player);
}
