package net.chococraft.common.init;

import net.chococraft.Chococraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
	public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Chococraft.MODID);

	public static final RegistryObject<SoundEvent> AMBIENT_SOUND = SOUND_EVENTS.register("entity.chocobo.kweh", () ->
			new SoundEvent(new ResourceLocation(Chococraft.MODID, "entity.chocobo.kweh")));
	public static final RegistryObject<SoundEvent> WHISTLE_SOUND_FOLLOW = SOUND_EVENTS.register("entity.chocobo.kwehwhistlefollow", () ->
			new SoundEvent(new ResourceLocation(Chococraft.MODID, "entity.chocobo.kwehwhistlefollow")));
	public static final RegistryObject<SoundEvent> WHISTLE_SOUND_STAY = SOUND_EVENTS.register("entity.chocobo.kwehwhistlestay", () ->
			new SoundEvent(new ResourceLocation(Chococraft.MODID, "entity.chocobo.kwehwhistlestay")));
	public static final net.minecraftforge.registries.RegistryObject<SoundEvent> WHISTLE_SOUND_WANDER = SOUND_EVENTS.register("entity.chocobo.kwehwhistlewander", () ->
			new SoundEvent(new ResourceLocation(Chococraft.MODID, "entity.chocobo.kwehwhistlewander")));
}
