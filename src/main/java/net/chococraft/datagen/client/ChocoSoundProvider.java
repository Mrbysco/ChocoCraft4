package net.chococraft.datagen.client;

import net.chococraft.Chococraft;
import net.chococraft.common.init.ModSounds;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinitionsProvider;

public class ChocoSoundProvider extends SoundDefinitionsProvider {

	public ChocoSoundProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, Chococraft.MODID, existingFileHelper);
	}

	@Override
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


	public String modSubtitle(ResourceLocation id) {
		return Chococraft.MODID + ".subtitle." + id.getPath();
	}

	public ResourceLocation modLoc(String name) {
		return new ResourceLocation(Chococraft.MODID, name);
	}
}
