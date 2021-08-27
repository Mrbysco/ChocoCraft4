package net.chococraft.common.init;

import net.minecraft.item.Food;

public class ModFoods {
	public static final Food GYSAHL_GREEN = (new Food.Builder()).hunger(1).saturation(1F).build();
	public static final Food CHOCOBO_DRUMSTICK_RAW = (new Food.Builder()).hunger(2).saturation(1F).meat().build();
	public static final Food CHOCOBO_DRUMSTICK_COOKED = (new Food.Builder()).hunger(4).saturation(1F).meat().build();
	public static final Food PICKLED_GYSAHL_RAW = (new Food.Builder()).hunger(1).saturation(1F).build();
	public static final Food PICKLED_GYSAHL_COOKED = (new Food.Builder()).hunger(3).saturation(1F).build();
}
