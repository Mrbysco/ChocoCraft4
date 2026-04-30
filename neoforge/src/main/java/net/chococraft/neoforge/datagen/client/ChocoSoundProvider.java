package net.chococraft.neoforge.datagen.client;

import net.chococraft.Chococraft;
import net.chococraft.registry.ModSounds;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

public class ChocoSoundProvider extends SoundDefinitionsProvider {

	public ChocoSoundProvider(PackOutput packOutput) {
		super(packOutput, Chococraft.MOD_ID);
	}

	@Override
	public void registerSounds() {
		this.add(ModSounds.AMBIENT_SOUND.getId(), definition()
				.subtitle(modSubtitle(ModSounds.AMBIENT_SOUND.getId()))
				.with(sound(modLoc("entity/chocobo/kweh"))));
		this.add(ModSounds.WHISTLE_SOUND_FOLLOW.getId(), definition()
				.subtitle(modSubtitle(ModSounds.WHISTLE_SOUND_FOLLOW.getId()))
				.with(sound(modLoc("entity/chocobo/kwehwhistlefollow"))));
		this.add(ModSounds.WHISTLE_SOUND_STAY.getId(), definition()
				.subtitle(modSubtitle(ModSounds.WHISTLE_SOUND_STAY.getId()))
				.with(sound(modLoc("entity/chocobo/kwehwhistlestay"))));
		this.add(ModSounds.WHISTLE_SOUND_WANDER.getId(), definition()
				.subtitle(modSubtitle(ModSounds.WHISTLE_SOUND_WANDER.getId()))
				.with(sound(modLoc("entity/chocobo/kwehwhistlewander"))));
	}


	public String modSubtitle(Identifier id) {
		return Chococraft.MOD_ID + ".subtitle." + id.getPath();
	}

	public Identifier modLoc(String name) {
		return Chococraft.modLoc(name);
	}
}
