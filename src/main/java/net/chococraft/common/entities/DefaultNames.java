package net.chococraft.common.entities;

import net.chococraft.common.config.ChocoConfig;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;

import java.util.List;
import java.util.Random;

public class DefaultNames {
	public static MutableComponent getRandomName(Random random, boolean isMale) {
		List<? extends String> names = isMale ? ChocoConfig.COMMON.maleNames.get() : ChocoConfig.COMMON.femaleNames.get();
		return new TextComponent(names.get(random.nextInt(names.size())));
	}
}
