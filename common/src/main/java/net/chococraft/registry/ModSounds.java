package net.chococraft.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.chococraft.Chococraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class ModSounds {
	public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Chococraft.MOD_ID, Registries.SOUND_EVENT);

	public static final RegistrySupplier<SoundEvent> AMBIENT_SOUND = SOUND_EVENTS.register("entity.chocobo.kweh", () ->
			SoundEvent.createVariableRangeEvent(new ResourceLocation(Chococraft.MOD_ID, "entity.chocobo.kweh")));
	public static final RegistrySupplier<SoundEvent> WHISTLE_SOUND_FOLLOW = SOUND_EVENTS.register("entity.chocobo.kwehwhistlefollow", () ->
			SoundEvent.createVariableRangeEvent(new ResourceLocation(Chococraft.MOD_ID, "entity.chocobo.kwehwhistlefollow")));
	public static final RegistrySupplier<SoundEvent> WHISTLE_SOUND_STAY = SOUND_EVENTS.register("entity.chocobo.kwehwhistlestay", () ->
			SoundEvent.createVariableRangeEvent(new ResourceLocation(Chococraft.MOD_ID, "entity.chocobo.kwehwhistlestay")));
	public static final RegistrySupplier<SoundEvent> WHISTLE_SOUND_WANDER = SOUND_EVENTS.register("entity.chocobo.kwehwhistlewander", () ->
			SoundEvent.createVariableRangeEvent(new ResourceLocation(Chococraft.MOD_ID, "entity.chocobo.kwehwhistlewander")));
}
