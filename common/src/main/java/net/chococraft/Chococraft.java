package net.chococraft;

import com.mojang.logging.LogUtils;
import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.event.events.common.PlayerEvent;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import net.chococraft.common.entity.AbstractChocobo;
import net.chococraft.common.entity.properties.ModDataSerializers;
import net.chococraft.common.world.worldgen.ModConfiguredFeatures;
import net.chococraft.registry.ModEntities;
import net.chococraft.registry.ModMenus;
import net.chococraft.registry.ModRegistry;
import net.chococraft.registry.ModSounds;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.ComposterBlock;
import org.slf4j.Logger;

public class Chococraft {
	public static final String MOD_ID = "chococraft";
	public static final Logger LOGGER = LogUtils.getLogger();

	public static final CreativeModeTab CREATIVE_TAB = CreativeTabRegistry.create(new ResourceLocation(MOD_ID, "tab"), () ->
			new ItemStack(ModRegistry.GYSAHL_GREEN.get()));


	public static void init() {
		ModEntities.ENTITY_TYPES.register();
		ModRegistry.BLOCKS.register();
		ModRegistry.ITEMS.register();
		ModSounds.SOUND_EVENTS.register();
		ModMenus.MENU_TYPES.register();

		EntityAttributeRegistry.register(ModEntities.CHOCOBO, () -> AbstractChocobo.createAttributes());

		LifecycleEvent.SETUP.register(() -> {
			ModDataSerializers.init();
			ModConfiguredFeatures.init();
			registerCompostables();
		});

		PlayerEvent.PLAYER_QUIT.register((player) -> {
			if (player.getVehicle() != null) {
				Entity entityRide = player.getVehicle();
				if (entityRide instanceof AbstractChocobo) {
					player.removeVehicle();
				}
			}
		});
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
}
