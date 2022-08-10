package net.chococraft.common.config;

import net.chococraft.Chococraft;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.List;

public class ChocoConfig {
	private static String maleNames[] =
			{
					"Arkio", "Boco", "Choco", "Patch", "Eddie", "Big Bird", "Chobi", "Horse Bird", "Mr. Yellowpuffs", "Oscar", "Wild",
					"Stitch", "Milo", "Lewis", "Simon", "Steed", "Bocobo", "Chobo", "Butter Fingers", "Caspar", "Chubby",
					"Coco", "Fuzzy", "Hulk", "Flopsy", "Lionel", "Tidus", "Cloud", "Sephiroth", "Butz", "Cecil", "Golbez",
					"Squall", "Zidane", "Garnet", "Kuja", "Locke", "Celes", "Crafty", "Sparky", "Skippy",
					"Whiskers", "Mog's Mount", "Ruffles", "Quistis", "Noctis", "Firecracker", "Ballistic", "Blizzard",
					"Torobo", "Leon", "Firas", "Travis", "Indigo", "Montoya", "Cobalt", "Jinx", "Komet", "Beau", "Bone",
					"Claw", "Duke", "Easy", "Fire", "Fury", "Idol", "Iron", "Jack", "Mars", "Noir", "Snow", "Star", "Zero",
					"Ace", "Air", "Ice", "Max", "Neo", "Ray", "Alpha", "Arrow", "Avian", "Black", "Blade", "Blaze", "Blitz",
					"Chaos", "Dandy", "Jolly", "Omega", "Pluto", "Point", "Quake", "Titan", "Hope", "Ifrit", "Shiva", "Polonium",
					"Radon", "Sparks", "Lunik"

			};

	private static String femaleNames[] =
			{
					"Choco", "Patch", "Chobi", "Wild", "Chubby", "Crystal", "Coco", "Fuzzy", "Flopsy", "Lulu", "Yuna",
					"Cecil", "Kuja", "Terra", "Locke", "Celes", "Rikku", "Yuffie", "Selphie", "Rinoa", "Sparky",
					"Skippy", "Whiskers", "Pupu", "Quistis", "Noctis", "Tranquille", "Twinkling", "Capucine", "Heidi",
					"Danseuse", "Mercedes", "Psyche", "Victory", "Liberty", "Emma", "Fortune", "Soleil", "Luna", "Violet",
					"Lilith", "Lilli", "Jinx", "Coco", "Fleur", "Feder", "Flora", "Kugel", "Bleu", "Blue", "Chic", "Ciel",
					"Face", "Fire", "Fury", "Iris", "Jade", "Joli", "Kiku", "Lady", "Miel", "Momo", "Moon", "Nana",
					"Noir", "Nova", "Rain", "Rose", "Ruby", "Star", "Vega", "Air", "Aki", "Ayu", "Fee", "Sky", "Sun",
					"Amber", "Angel", "Azure", "Belle", "Clair", "Ebony", "Ember", "Fairy", "Flare", "Glory", "Jaune",
					"Jeune", "Jolly", "Lucky", "Olive", "Orange", "Venus", "Lightning", "Galindorf"
			};

	public static class Common {
		public final IntValue gysahlGreenPatchSize;
		public final IntValue gysahlGreenRarity;
		public final BooleanValue gysahlGreensSpawnOnlyInOverworld;

		public final IntValue chocoboSpawnWeight;
		public final IntValue chocoboPackSizeMin;
		public final IntValue chocoboPackSizeMax;

		public final DoubleValue tameChance;

		public final BooleanValue nameTamedChocobos;
		public final ConfigValue<List<? extends String>> maleNames;
		public final ConfigValue<List<? extends String>> femaleNames;

		Common(ForgeConfigSpec.Builder builder) {
			builder.comment("World generation related configuration")
					.push("World");

			gysahlGreenPatchSize = builder
					.worldRestart()
					.comment("Controls the Patch Size [Default: 64]")
					.defineInRange("gysahlGreenPatchSize", 64, 0, Integer.MAX_VALUE);

			gysahlGreenRarity = builder
					.worldRestart()
					.comment("Controls the Patch Rarity (Generating once every X tries) [Default: 2]")
					.defineInRange("gysahlGreenRarity", 2, 1, Integer.MAX_VALUE);

			gysahlGreensSpawnOnlyInOverworld = builder
					.worldRestart()
					.comment("Controls the weight compared to other world gen [Default: true]")
					.define("gysahlGreensSpawnOnlyInOverworld", true);

			builder.pop();

			builder.comment("Spawning configuration")
					.push("spawning");

			chocoboSpawnWeight = builder
					.comment("Controls Chocobo Spawn Weight [Default: 10]")
					.defineInRange("chocoboSpawnWeight", 10, 0, Integer.MAX_VALUE);

			chocoboPackSizeMin = builder
					.comment("Controls Chocobo Pack Size Min [Default: 1]")
					.defineInRange("chocoboPackSizeMin", 1, 0, Integer.MAX_VALUE);

			chocoboPackSizeMax = builder
					.comment("Controls Chocobo Pack Size Max [Default: 3]")
					.defineInRange("chocoboPackSizeMax", 3, 0, Integer.MAX_VALUE);

			builder.pop();

			builder.comment("Chocobo configuration")
					.push("Chocobo");

			tameChance = builder
					.comment("This multiplier controls the tame chance per gysahl used, so .15 results in 15% chance to tame [Default: 0.15]")
					.defineInRange("tameChance", 0.15, 0, 1);

			builder.pop();
			builder.comment("Naming configuration")
					.push("Naming");

			nameTamedChocobos = builder
					.comment("If taming a chocobo will provide them with a name (unless already named) [Default: true]")
					.define("nameTamedChocobos", true);

			maleNames = builder
					.comment("The list of male names it can choose from if 'nameTamedChocobos' is enabled")
					.defineList("maleNames", Arrays.asList(ChocoConfig.maleNames), o -> (o instanceof String));

			femaleNames = builder
					.comment("The list of female names it can choose from if 'nameTamedChocobos' is enabled")
					.defineList("femaleNames", Arrays.asList(ChocoConfig.femaleNames), o -> (o instanceof String));

			builder.pop();
		}
	}

	public static final ForgeConfigSpec commonSpec;
	public static final Common COMMON;

	static {
		final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
		commonSpec = specPair.getRight();
		COMMON = specPair.getLeft();
	}

	@SubscribeEvent
	public static void onLoad(final ModConfig.Loading configEvent) {
		Chococraft.log.debug("Loaded Chococraft's config file {}", configEvent.getConfig().getFileName());
	}

	@SubscribeEvent
	public static void onFileChange(final ModConfig.Reloading configEvent) {
		Chococraft.log.debug("Chococraft's config just got changed on the file system!");
		if (configEvent.getConfig().getModId().equals(Chococraft.MODID)) {
			if (COMMON.chocoboPackSizeMin.get() > COMMON.chocoboPackSizeMax.get()) {
				int t = COMMON.chocoboPackSizeMax.get();
				COMMON.chocoboPackSizeMax.set(COMMON.chocoboPackSizeMin.get());
				COMMON.chocoboPackSizeMin.set(t);
			}
			configEvent.getConfig().save();
		}
	}
}