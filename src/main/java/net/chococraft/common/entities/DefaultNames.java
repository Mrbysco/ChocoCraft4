package net.chococraft.common.entities;

import net.chococraft.common.config.ChocoConfig;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.util.List;
import java.util.Random;

public class DefaultNames {
	public static IFormattableTextComponent getRandomName(Random random, boolean isMale) {
		List<? extends String> names = isMale ? ChocoConfig.COMMON.maleNames.get() : ChocoConfig.COMMON.femaleNames.get();
		return new StringTextComponent(names.get(random.nextInt(names.size())));
	}
}
