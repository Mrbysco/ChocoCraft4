package net.chococraft.fabric;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.chococraft.Chococraft;
import net.chococraft.common.entity.AbstractChocobo;
import net.chococraft.fabric.common.config.FabricBreedingConfig;
import net.chococraft.fabric.common.config.FabricChocoConfig;
import net.chococraft.fabric.common.entity.FabricChocobo;
import net.chococraft.fabric.common.world.FeatureInjector;
import net.chococraft.fabric.event.MountEvent;
import net.chococraft.fabric.registry.ModDataSerializers;
import net.chococraft.registry.ModEntities;
import net.fabricmc.api.ModInitializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;

public class ChococraftFabric implements ModInitializer {
	public static final ResourceLocation OPEN_CHOCOBO_SCREEN = new ResourceLocation(Chococraft.MOD_ID, "open_chocobo_screen");

	public static ConfigHolder<FabricChocoConfig> config;
	public static ConfigHolder<FabricBreedingConfig> breedingConfig;

	@Override
	public void onInitialize() {
		config = AutoConfig.register(FabricChocoConfig.class, Toml4jConfigSerializer::new);
		breedingConfig = AutoConfig.register(FabricBreedingConfig.class, GsonConfigSerializer::new);

		EntityDataSerializers.registerSerializer(ModDataSerializers.CHOCOBO_COLOR);
		EntityDataSerializers.registerSerializer(ModDataSerializers.MOVEMENT_TYPE);

		Chococraft.init();

		FeatureInjector.init();

		SpawnPlacements.register(ModEntities.CHOCOBO.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, FabricChocobo::checkChocoboSpawnRules);

		MountEvent.MOUNTING.register((entityMounting, entityBeingMounted, level, isMounting) -> {
			if (isMounting) return InteractionResult.PASS;
			if (!entityBeingMounted.isAlive()) return InteractionResult.PASS;
			if (!(entityBeingMounted instanceof AbstractChocobo)) return InteractionResult.PASS;

			if (entityBeingMounted.isInWater()) return InteractionResult.PASS;

			if (!entityBeingMounted.onGround())
				return InteractionResult.FAIL;

			return InteractionResult.PASS;
		});
	}
}
