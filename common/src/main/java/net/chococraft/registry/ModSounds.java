package net.chococraft.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.chococraft.Chococraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;

public class ModSounds {
	public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Chococraft.MOD_ID, Registries.SOUND_EVENT);

	public static final RegistrySupplier<SoundEvent> AMBIENT_SOUND = SOUND_EVENTS.register("entity.chocobo.kweh", () ->
			SoundEvent.createVariableRangeEvent(Chococraft.modLoc("entity.chocobo.kweh")));
	public static final RegistrySupplier<SoundEvent> WHISTLE_SOUND_FOLLOW = SOUND_EVENTS.register("entity.chocobo.kwehwhistlefollow", () ->
			SoundEvent.createVariableRangeEvent(Chococraft.modLoc("entity.chocobo.kwehwhistlefollow")));
	public static final RegistrySupplier<SoundEvent> WHISTLE_SOUND_STAY = SOUND_EVENTS.register("entity.chocobo.kwehwhistlestay", () ->
			SoundEvent.createVariableRangeEvent(Chococraft.modLoc("entity.chocobo.kwehwhistlestay")));
	public static final RegistrySupplier<SoundEvent> WHISTLE_SOUND_WANDER = SOUND_EVENTS.register("entity.chocobo.kwehwhistlewander", () ->
			SoundEvent.createVariableRangeEvent(Chococraft.modLoc("entity.chocobo.kwehwhistlewander")));
}
