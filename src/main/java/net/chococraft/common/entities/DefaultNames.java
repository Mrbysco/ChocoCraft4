package net.chococraft.common.entities;

import net.chococraft.common.config.ChocoConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.RandomSource;

import java.util.List;

public class DefaultNames {
	public static MutableComponent getRandomName(RandomSource random, boolean isMale) {
		List<? extends String> names = isMale ? ChocoConfig.COMMON.maleNames.get() : ChocoConfig.COMMON.femaleNames.get();
		return Component.literal(names.get(random.nextInt(names.size())));
	}
}
