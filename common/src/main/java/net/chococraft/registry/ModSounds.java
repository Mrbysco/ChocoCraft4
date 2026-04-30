package net.chococraft.registry;

import dev.chococraft.registration.RegistrationProvider;
import dev.chococraft.registration.RegistryObject;
import net.chococraft.Chococraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;

public class ModSounds {
	public static final RegistrationProvider<SoundEvent> SOUND_EVENTS = RegistrationProvider.get(BuiltInRegistries.SOUND_EVENT, Chococraft.MOD_ID);

	public static final RegistryObject<SoundEvent, SoundEvent> AMBIENT_SOUND = SOUND_EVENTS.register("entity.chocobo.kweh", () ->
			SoundEvent.createVariableRangeEvent(Chococraft.modLoc("entity.chocobo.kweh")));
	public static final RegistryObject<SoundEvent, SoundEvent> WHISTLE_SOUND_FOLLOW = SOUND_EVENTS.register("entity.chocobo.kwehwhistlefollow", () ->
			SoundEvent.createVariableRangeEvent(Chococraft.modLoc("entity.chocobo.kwehwhistlefollow")));
	public static final RegistryObject<SoundEvent, SoundEvent> WHISTLE_SOUND_STAY = SOUND_EVENTS.register("entity.chocobo.kwehwhistlestay", () ->
			SoundEvent.createVariableRangeEvent(Chococraft.modLoc("entity.chocobo.kwehwhistlestay")));
	public static final RegistryObject<SoundEvent, SoundEvent> WHISTLE_SOUND_WANDER = SOUND_EVENTS.register("entity.chocobo.kwehwhistlewander", () ->
			SoundEvent.createVariableRangeEvent(Chococraft.modLoc("entity.chocobo.kwehwhistlewander")));

	public static void load() {
		// Load class
	}
}
