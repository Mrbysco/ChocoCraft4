package net.chococraft;

import com.mojang.logging.LogUtils;
import net.chococraft.registry.ModEntities;
import net.chococraft.registry.ModMenus;
import net.chococraft.registry.ModRegistry;
import net.chococraft.registry.ModSounds;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.ComposterBlock;
import org.slf4j.Logger;

public class Chococraft {
	public static final String MOD_ID = "chococraft";
	public static final Logger LOGGER = LogUtils.getLogger();

	public static final TagKey<Item> REPAIRS_CHOCO_DISGUISE = TagKey.create(Registries.ITEM, modLoc("repairs_choco_disguise"));

	public static void init() {
		ModEntities.load();
		ModRegistry.load();
		ModSounds.load();
		ModMenus.load();
	}

	public static void registerCompostables() {
		// 30% chance
		ComposterBlock.COMPOSTABLES.put(ModRegistry.GYSAHL_GREEN_SEEDS.get(), 0.3F);

		// 65% chance
		ComposterBlock.COMPOSTABLES.put(ModRegistry.GYSAHL_GREEN_ITEM.get(), 0.65F);
		ComposterBlock.COMPOSTABLES.put(ModRegistry.LOVERLY_GYSAHL_GREEN.get(), 0.65F);
		ComposterBlock.COMPOSTABLES.put(ModRegistry.GOLD_GYSAHL.get(), 0.65F);
		ComposterBlock.COMPOSTABLES.put(ModRegistry.RED_GYSAHL.get(), 0.65F);
		ComposterBlock.COMPOSTABLES.put(ModRegistry.PINK_GYSAHL.get(), 0.65F);
	}

	public static Identifier modLoc(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
