package net.chococraft;

import com.mojang.logging.LogUtils;
import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.event.events.common.PlayerEvent;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import dev.architectury.registry.level.entity.SpawnPlacementsRegistry;
import dev.architectury.registry.registries.RegistrySupplier;
import net.chococraft.common.entity.AbstractChocobo;
import net.chococraft.common.entity.properties.ModDataSerializers;
import net.chococraft.registry.ModEntities;
import net.chococraft.registry.ModMenus;
import net.chococraft.registry.ModRegistry;
import net.chococraft.registry.ModSounds;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.levelgen.Heightmap;
import org.slf4j.Logger;

public class Chococraft {
	public static final String MOD_ID = "chococraft";
	public static final Logger LOGGER = LogUtils.getLogger();

	public static final RegistrySupplier<CreativeModeTab> CREATIVE_TAB = ModRegistry.CREATIVE_MODE_TABS.register(
			"tab", () -> dev.architectury.registry.CreativeTabRegistry.create(
					Component.translatable("itemGroup.chococraft.tab"),
					() -> new ItemStack(ModRegistry.GYSAHL_GREEN.get())
			)
	);

	public static void init() {
		ModEntities.ENTITY_TYPES.register();
		ModRegistry.BLOCKS.register();
		ModRegistry.ITEMS.register();
		ModRegistry.CREATIVE_MODE_TABS.register();
		ModSounds.SOUND_EVENTS.register();
		ModMenus.MENU_TYPES.register();

		EntityAttributeRegistry.register(ModEntities.CHOCOBO, () -> AbstractChocobo.createAttributes());

		LifecycleEvent.SETUP.register(() -> {
			ModDataSerializers.init();
			registerCompostables();

			ModRegistry.ITEMS.forEach(supplier -> {
				CreativeTabRegistry.append(CREATIVE_TAB, supplier.get());
			});
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
