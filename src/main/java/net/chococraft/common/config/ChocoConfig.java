package net.chococraft.common.config;

import net.chococraft.Chococraft;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.apache.commons.lang3.tuple.Pair;

public class ChocoConfig {
	public static class Common {
		public final DoubleValue tameChance;

		Common(ForgeConfigSpec.Builder builder) {
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
		Chococraft.LOGGER.debug("Loaded Chococraft's config file {}", configEvent.getConfig().getFileName());
	}

	@SubscribeEvent
	public static void onFileChange(final ModConfigEvent.Reloading configEvent) {
		Chococraft.LOGGER.debug("Chococraft's config just got changed on the file system!");
	}
}