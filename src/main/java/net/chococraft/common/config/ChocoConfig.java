package net.chococraft.common.config;

import net.chococraft.Chococraft;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.apache.commons.lang3.tuple.Pair;

public class ChocoConfig {
	public static class Common {
		public final IntValue gysahlGreenPatchSize;
		public final IntValue gysahlGreenRarity;
		public final BooleanValue gysahlGreensSpawnOnlyInOverworld;

		public final IntValue chocoboSpawnWeight;
		public final IntValue chocoboPackSizeMin;
		public final IntValue chocoboPackSizeMax;

		public final DoubleValue tameChance;

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
	public static void onLoad(final ModConfigEvent.Loading configEvent) {
		Chococraft.log.debug("Loaded Chococraft's config file {}", configEvent.getConfig().getFileName());
	}

	@SubscribeEvent
	public static void onFileChange(final ModConfigEvent.Reloading configEvent) {
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