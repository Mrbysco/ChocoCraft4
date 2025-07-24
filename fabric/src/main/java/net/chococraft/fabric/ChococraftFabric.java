package net.chococraft.fabric;

import dev.architectury.event.events.common.LifecycleEvent;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.chococraft.Chococraft;
import net.chococraft.common.entity.AbstractChocobo;
import net.chococraft.fabric.common.config.FabricBreedingConfig;
import net.chococraft.fabric.common.config.FabricChocoConfig;
import net.chococraft.fabric.common.entity.FabricChocobo;
import net.chococraft.fabric.common.packets.OpenChocoboScreenPayload;
import net.chococraft.fabric.common.world.FeatureInjector;
import net.chococraft.fabric.event.MountEvent;
import net.chococraft.fabric.registry.ModDataSerializers;
import net.chococraft.registry.ModEntities;
import net.chococraft.registry.ModRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricTrackedDataRegistry;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;

public class ChococraftFabric implements ModInitializer {
	public static final ResourceLocation OPEN_CHOCOBO_SCREEN = Chococraft.modLoc("open_chocobo_screen");

	public static ConfigHolder<FabricChocoConfig> config;
	public static ConfigHolder<FabricBreedingConfig> breedingConfig;

	@Override
	public void onInitialize() {
		config = AutoConfig.register(FabricChocoConfig.class, Toml4jConfigSerializer::new);
		breedingConfig = AutoConfig.register(FabricBreedingConfig.class, GsonConfigSerializer::new);

		FabricTrackedDataRegistry.register(Chococraft.modLoc("chocobo_color"), ModDataSerializers.CHOCOBO_COLOR);
		FabricTrackedDataRegistry.register(Chococraft.modLoc("movement_type"), ModDataSerializers.MOVEMENT_TYPE);

		PayloadTypeRegistry.playS2C().register(OpenChocoboScreenPayload.ID, OpenChocoboScreenPayload.CODEC);

		Chococraft.init();

		FeatureInjector.init();

		SpawnPlacements.register(ModEntities.CHOCOBO.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, FabricChocobo::checkChocoboSpawnRules);

		MountEvent.MOUNTING.register((entityMounting, entityBeingMounted, level, isMounting) -> {
			if (isMounting) return InteractionResult.PASS;
			if (!entityBeingMounted.isAlive()) return InteractionResult.PASS;
			if (!(entityBeingMounted instanceof AbstractChocobo)) return InteractionResult.PASS;

			if (entityBeingMounted.isInWater()) return InteractionResult.PASS;

			if (!entityBeingMounted.onGround())
				return InteractionResult.FAIL;

			return InteractionResult.PASS;
		});

		LifecycleEvent.SETUP.register(() -> {
			ModRegistry.registerCompostables();
		});
	}
}
