package net.chococraft.common.entity;

import net.chococraft.ChococraftExpectPlatform;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.RandomSource;

import java.util.List;

public class DefaultNames {
	public static MutableComponent getRandomName(RandomSource random, boolean isMale) {
		List<? extends String> names = isMale ? ChococraftExpectPlatform.getConfiguredMaleNames() : ChococraftExpectPlatform.getConfiguredFemaleNames();
		return Component.literal(names.get(random.nextInt(names.size())));
	}
}
