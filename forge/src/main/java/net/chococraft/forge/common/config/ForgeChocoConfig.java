package net.chococraft.forge.common.config;

import net.chococraft.Chococraft;
import net.chococraft.common.config.ChocoConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class ForgeChocoConfig {
	public static class Common {

		public final IntValue chocoboSpawnWeight;
		public final IntValue chocoboPackSizeMin;
		public final IntValue chocoboPackSizeMax;

		public final DoubleValue tameChance;

		public final BooleanValue nameTamedChocobos;
		public final BooleanValue canChocobosFly;
		public final IntValue kwehIntervalLimit;
		public final ConfigValue<List<? extends String>> maleNames;
		public final ConfigValue<List<? extends String>> femaleNames;

		Common(ForgeConfigSpec.Builder builder) {
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

			canChocobosFly = builder
					.comment("If certain chocobos are allowed to fly [Default: true]")
					.define("canChocobosFly", true);

			kwehIntervalLimit = builder
					.comment("Determines the maximum interval duration for the Chocobo's ambient sound [Default: 100]")
					.defineInRange("kwehIntervalLimit", 100, 1, Integer.MAX_VALUE);

			builder.pop();
			builder.comment("Naming configuration")
					.push("Naming");

			nameTamedChocobos = builder
					.comment("If taming a chocobo will provide them with a name (unless already named) [Default: true]")
					.define("nameTamedChocobos", true);

			maleNames = builder
					.comment("The list of male names it can choose from if 'nameTamedChocobos' is enabled")
					.defineList("maleNames", List.of(ChocoConfig.maleNames), o -> (o instanceof String));

			femaleNames = builder
					.comment("The list of female names it can choose from if 'nameTamedChocobos' is enabled")
					.defineList("femaleNames", List.of(ChocoConfig.femaleNames), o -> (o instanceof String));

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
		Chococraft.LOGGER.debug("Loaded Chococraft's config file {}", configEvent.getConfig().getFileName());
	}

	@SubscribeEvent
	public static void onFileChange(final ModConfigEvent.Reloading configEvent) {
		Chococraft.LOGGER.debug("Chococraft's config just got changed on the file system!");
		if (configEvent.getConfig().getModId().equals(Chococraft.MOD_ID)) {
			if (COMMON.chocoboPackSizeMin.get() > COMMON.chocoboPackSizeMax.get()) {
				int t = COMMON.chocoboPackSizeMax.get();
				COMMON.chocoboPackSizeMax.set(COMMON.chocoboPackSizeMin.get());
				COMMON.chocoboPackSizeMin.set(t);
			}
			configEvent.getConfig().save();
		}
	}
}