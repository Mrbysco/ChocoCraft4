package net.chococraft.fabric.common.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;
import net.chococraft.Chococraft;
import net.chococraft.common.config.ChocoConfig;

import java.util.List;

@Config(name = Chococraft.MOD_ID)
public class FabricChocoConfig implements ConfigData {
	@ConfigEntry.Gui.CollapsibleObject
	public Spawning spawning = new Spawning();

	@ConfigEntry.Gui.CollapsibleObject
	public Chocobo chocobo = new Chocobo();

	@ConfigEntry.Gui.CollapsibleObject
	public Naming naming = new Naming();

	public static class Spawning {
		@Comment("Controls Chocobo Spawn Weight [Default: 10]")
		@ConfigEntry.BoundedDiscrete(min = 0, max = Integer.MAX_VALUE)
		public int chocoboSpawnWeight = 10;

		@Comment("Controls Chocobo Pack Size Min [Default: 1]")
		@ConfigEntry.BoundedDiscrete(min = 0, max = Integer.MAX_VALUE)
		public int chocoboPackSizeMin = 1;

		@Comment("Controls Chocobo Pack Size Max [Default: 3]")
		@ConfigEntry.BoundedDiscrete(min = 0, max = Integer.MAX_VALUE)
		public int chocoboPackSizeMax = 3;
	}

	public static class Chocobo {
		@Comment("This multiplier controls the tame chance per gysahl used, so .15 results in 15% chance to tame [Default: 0.15]")
		@ConfigEntry.BoundedDiscrete(min = 0, max = 1)
		public double tameChance = 0.15;

		@Comment("If certain chocobos are allowed to fly [Default: true]")
		public boolean canChocobosFly = true;

		@Comment("Determines the maximum interval duration for the Chocobo's ambient sound [Default: 100]")
		@ConfigEntry.BoundedDiscrete(min = 1, max = Integer.MAX_VALUE)
		public int kwehIntervalLimit = 100;
	}

	public static class Naming {
		@Comment("If taming a chocobo will provide them with a name (unless already named) [Default: true]")
		public boolean nameTamedChocobos = true;

		@Comment("The list of male names it can choose from if 'nameTamedChocobos' is enabled")
		public List<String> maleNames = List.of(ChocoConfig.maleNames);

		@Comment("The list of female names it can choose from if 'nameTamedChocobos' is enabled")
		public List<String> femaleNames = List.of(ChocoConfig.femaleNames);
	}
}