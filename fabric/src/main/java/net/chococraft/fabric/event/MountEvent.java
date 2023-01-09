package net.chococraft.fabric.event;

import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public interface MountEvent<T> {
	Event<Mounting> MOUNTING = EventFactory.createLoop(new MountEvent.Mounting[0]);

	interface Mounting {
		InteractionResult onMount(Entity entityMounting, Entity entityBeingMounted, Level level, boolean isMounting);
	}
}
