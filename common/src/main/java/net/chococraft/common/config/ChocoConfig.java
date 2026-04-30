package net.chococraft.common.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class ChocoConfig {
	public static final String[] maleNames =
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

	public static final String[] femaleNames =
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

		public final ModConfigSpec.IntValue chocoboSpawnWeight;
		public final ModConfigSpec.IntValue chocoboPackSizeMin;
		public final ModConfigSpec.IntValue chocoboPackSizeMax;

		public final ModConfigSpec.DoubleValue tameChance;

		public final ModConfigSpec.BooleanValue nameTamedChocobos;
		public final ModConfigSpec.BooleanValue canChocobosFly;
		public final ModConfigSpec.IntValue kwehIntervalLimit;
		public final ModConfigSpec.ConfigValue<List<? extends String>> maleNames;
		public final ModConfigSpec.ConfigValue<List<? extends String>> femaleNames;

		Common(ModConfigSpec.Builder builder) {
			builder.comment("Spawning configuration")
					.push("spawning");

			chocoboSpawnWeight = builder
					.comment("Controls Chocobo Spawn Weight [Default: 10]")
					.defineInRange("chocoboSpawnWeight", 10, 1, Integer.MAX_VALUE);

			chocoboPackSizeMin = builder
					.comment("Controls Chocobo Pack Size Min [Default: 1]")
					.defineInRange("chocoboPackSizeMin", 1, 1, Integer.MAX_VALUE);

			chocoboPackSizeMax = builder
					.comment("Controls Chocobo Pack Size Max [Default: 3]")
					.defineInRange("chocoboPackSizeMax", 3, 1, Integer.MAX_VALUE);

			builder.pop();

			builder.comment("Chocobo configuration")
					.push("chocobo");

			tameChance = builder
					.comment("This multiplier controls the tame chance per gysahl used, so .15 results in 15% chance to tame [Default: 0.15]")
					.defineInRange("tameChance", 0.15, 0, 1);

			canChocobosFly = builder
					.comment("If certain chocobos are allowed to fly [Default: true]")
					.define("canChocobosFly", true);

			kwehIntervalLimit = builder
					.comment("Determines the maximum interval duration for the Chocobo's ambient sound [Default: 100]")
					.defineInRange("kwehIntervalLimit", 100, 1, Integer.MAX_VALUE);

			builder.pop();
			builder.comment("Naming configuration")
					.push("naming");

			nameTamedChocobos = builder
					.comment("If taming a chocobo will provide them with a name (unless already named) [Default: true]")
					.define("nameTamedChocobos", true);

			maleNames = builder
					.comment("The list of male names it can choose from if 'nameTamedChocobos' is enabled")
					.defineList("maleNames", List.of(ChocoConfig.maleNames), String::new,o -> (o instanceof String));

			femaleNames = builder
					.comment("The list of female names it can choose from if 'nameTamedChocobos' is enabled")
					.defineList("femaleNames", List.of(ChocoConfig.femaleNames), String::new,o -> (o instanceof String));

			builder.pop();
		}
	}

	public static final ModConfigSpec commonSpec;
	public static final Common COMMON;

	static {
		final Pair<Common, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(Common::new);
		commonSpec = specPair.getRight();
		COMMON = specPair.getLeft();
	}
}
