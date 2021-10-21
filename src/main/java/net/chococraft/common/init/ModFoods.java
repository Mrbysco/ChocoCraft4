package net.chococraft.common.init;

import net.minecraft.item.Food;

public class ModFoods {
	public static final Food GYSAHL_GREEN = (new Food.Builder()).nutrition(1).saturationMod(1F).build();
	public static final Food CHOCOBO_DRUMSTICK_RAW = (new Food.Builder()).nutrition(2).saturationMod(1F).meat().build();
	public static final Food CHOCOBO_DRUMSTICK_COOKED = (new Food.Builder()).nutrition(4).saturationMod(1F).meat().build();
	public static final Food PICKLED_GYSAHL_RAW = (new Food.Builder()).nutrition(1).saturationMod(1F).build();
	public static final Food PICKLED_GYSAHL_COOKED = (new Food.Builder()).nutrition(3).saturationMod(1F).build();
}
