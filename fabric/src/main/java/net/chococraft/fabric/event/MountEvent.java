package net.chococraft.fabric.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public interface MountEvent {
	Event<MountEvent> MOUNTING = EventFactory.createArrayBacked(MountEvent.class,
			(listeners) -> (entityMounting, entityBeingMounted, level, isMounting) -> {
				for (MountEvent event : listeners) {
					InteractionResult result = event.onMount(entityMounting, entityBeingMounted, level, isMounting);

					if (result != InteractionResult.PASS) {
						return result;
					}
				}

				return InteractionResult.PASS;
			}
	);

	InteractionResult onMount(Entity entityMounting, Entity entityBeingMounted, Level level, boolean isMounting);
}
