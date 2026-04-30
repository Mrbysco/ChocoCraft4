package net.chococraft.fabric.datagen.client;

import com.google.gson.JsonObject;
import net.chococraft.Chococraft;
import net.chococraft.registry.ModSounds;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class ChocoSoundProvider implements DataProvider {
	private final Map<String, SoundDefinition> sounds = new LinkedHashMap<>();

	protected final FabricPackOutput dataOutput;

	public ChocoSoundProvider(FabricPackOutput dataOutput) {
		this.dataOutput = dataOutput;
	}

	/**
	 * Registers the sound definitions that should be generated via one of the {@code add} methods.
	 */
	public void registerSounds() {
		this.add(ModSounds.AMBIENT_SOUND, definition()
				.subtitle(modSubtitle(ModSounds.AMBIENT_SOUND.getId()))
				.with(sound(modLoc("entity/chocobo/kweh"))));
		this.add(ModSounds.WHISTLE_SOUND_FOLLOW, definition()
				.subtitle(modSubtitle(ModSounds.WHISTLE_SOUND_FOLLOW.getId()))
				.with(sound(modLoc("entity/chocobo/kwehwhistlefollow"))));
		this.add(ModSounds.WHISTLE_SOUND_STAY, definition()
				.subtitle(modSubtitle(ModSounds.WHISTLE_SOUND_STAY.getId()))
				.with(sound(modLoc("entity/chocobo/kwehwhistlestay"))));
		this.add(ModSounds.WHISTLE_SOUND_WANDER, definition()
				.subtitle(modSubtitle(ModSounds.WHISTLE_SOUND_WANDER.getId()))
				.with(sound(modLoc("entity/chocobo/kwehwhistlewander"))));
	}

	public Identifier modLoc(String path) {
		return Chococraft.modLoc(path);
	}

	public String modSubtitle(Identifier id) {
		return Chococraft.MOD_ID + ".subtitle." + id.getPath();
	}

	@Override
	public CompletableFuture<?> run(CachedOutput cache) {
		this.sounds.clear();
		this.registerSounds();
		if (!this.sounds.isEmpty()) {
			return this.save(cache, this.dataOutput.getOutputFolder(PackOutput.Target.RESOURCE_PACK)
					.resolve(this.dataOutput.getModId()).resolve("sounds.json"));
		}

		return CompletableFuture.allOf();
	}

	@Override
	public String getName() {
		return "Sound Definitions";
	}

	// Quick helpers

	/**
	 * Creates a new {@link SoundDefinition}, which will host a set of
	 * {@link SoundDefinition.Sound}s and the necessary parameters.
	 */
	protected static SoundDefinition definition() {
		return SoundDefinition.definition();
	}

	/**
	 * Creates a new sound with the given name and type.
	 *
	 * @param name The name of the sound to create.
	 * @param type The type of sound to create.
	 */
	protected static SoundDefinition.Sound sound(final Identifier name, final SoundDefinition.SoundType type) {
		return SoundDefinition.Sound.sound(name, type);
	}

	/**
	 * Creates a new sound with the given name and {@link SoundDefinition.SoundType#SOUND} as
	 * sound type.
	 *
	 * @param name The name of the sound to create.
	 */
	protected static SoundDefinition.Sound sound(final Identifier name) {
		return sound(name, SoundDefinition.SoundType.SOUND);
	}

	// Addition methods

	/**
	 * Adds the entry name associated with the supplied {@link SoundEvent} with the given
	 * {@link SoundDefinition} to the list.
	 *
	 * <p>This method should be preferred when dealing with a {@code RegistryObject} or
	 * {@code RegistryDelegate}.</p>
	 *
	 * @param soundEvent A {@code Supplier} for the given {@link SoundEvent}.
	 * @param definition A {@link SoundDefinition} that defines the given sound.
	 */
	protected void add(final Supplier<SoundEvent> soundEvent, final SoundDefinition definition) {
		this.add(soundEvent.get(), definition);
	}

	/**
	 * Adds the entry name associated with the given {@link SoundEvent} with the
	 * {@link SoundDefinition} to the list.
	 *
	 * <p>This method should be preferred when a {@code SoundEvent} is already
	 * available in the method context. If you already have a {@code Supplier} for
	 * it, refer to {@link #add(Supplier, SoundDefinition)}.</p>
	 *
	 * @param soundEvent A {@link SoundEvent}.
	 * @param definition The {@link SoundDefinition} that defines the given event.
	 */
	protected void add(final SoundEvent soundEvent, final SoundDefinition definition) {
		this.add(soundEvent.location(), definition);
	}

	/**
	 * Adds the {@link SoundEvent} referenced by the given {@link Identifier} with the
	 * {@link SoundDefinition} to the list.
	 *
	 * @param soundEvent The {@link Identifier} that identifies the event.
	 * @param definition The {@link SoundDefinition} that defines the given event.
	 */
	protected void add(final Identifier soundEvent, final SoundDefinition definition) {
		this.addSounds(soundEvent.getPath(), definition);
	}

	/**
	 * Adds the {@link SoundEvent} with the specified name along with its {@link SoundDefinition}
	 * to the list.
	 *
	 * <p>The given sound event must NOT contain the namespace the name is a part of, since
	 * the sound definition specification doesn't allow sounds to be defined outside the
	 * namespace they're in. For this reason, any namespace will automatically be stripped
	 * from the name.</p>
	 *
	 * @param soundEvent The name of the {@link SoundEvent}.
	 * @param definition The {@link SoundDefinition} that defines the given event.
	 */
	protected void add(final String soundEvent, final SoundDefinition definition) {
		this.add(Identifier.tryParse(soundEvent), definition);
	}

	private void addSounds(final String soundEvent, final SoundDefinition definition) {
		if (this.sounds.put(soundEvent, definition) != null) {
			throw new IllegalStateException("Sound event '" + this.dataOutput.getModId() + ":" + soundEvent + "' already exists");
		}
	}

	private CompletableFuture<?> save(final CachedOutput cache, final Path targetFile) {
		return DataProvider.saveStable(cache, this.mapToJson(this.sounds), targetFile);
	}

	private JsonObject mapToJson(final Map<String, SoundDefinition> map) {
		final JsonObject obj = new JsonObject();
		// namespaces are ignored when serializing
		map.forEach((k, v) -> obj.add(k, v.serialize()));
		return obj;
	}
}
