package net.chococraft.fabric;

import fuzs.forgeconfigapiport.fabric.api.v5.ConfigRegistry;
import net.chococraft.Chococraft;
import net.chococraft.common.config.BreedingConfig;
import net.chococraft.common.config.BreedingConfigReloadManager;
import net.chococraft.common.config.ChocoConfig;
import net.chococraft.common.entity.AbstractChocobo;
import net.chococraft.fabric.common.entity.FabricChocobo;
import net.chococraft.fabric.common.packets.OpenChocoboScreenPayload;
import net.chococraft.fabric.common.world.FeatureInjector;
import net.chococraft.fabric.event.MountEvent;
import net.chococraft.fabric.event.PlayerQuitEvent;
import net.chococraft.fabric.registry.ModDataSerializers;
import net.chococraft.registry.ModEntities;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.CommonLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityDataRegistry;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.fml.config.ModConfig;

public class ChococraftFabric implements ModInitializer {
	public static final Identifier OPEN_CHOCOBO_SCREEN = Chococraft.modLoc("open_chocobo_screen");


	@Override
	public void onInitialize() {
		Chococraft.init();

		ConfigRegistry.INSTANCE.register(Chococraft.MOD_ID, ModConfig.Type.COMMON, ChocoConfig.commonSpec);

//		breedingConfig = AutoConfig.register(FabricBreedingConfig.class, GsonConfigSerializer::new);

		FabricEntityDataRegistry.register(Chococraft.modLoc("chocobo_color"), ModDataSerializers.CHOCOBO_COLOR);
		FabricEntityDataRegistry.register(Chococraft.modLoc("movement_type"), ModDataSerializers.MOVEMENT_TYPE);

		PayloadTypeRegistry.clientboundPlay().register(OpenChocoboScreenPayload.ID, OpenChocoboScreenPayload.CODEC);

		FeatureInjector.init();

		CommonLifecycleEvents.TAGS_LOADED.register((access, client) -> {
			BreedingConfig.initializeConfig();
		});
		ResourceLoader.get(PackType.SERVER_DATA).registerReloadListener(BreedingConfigReloadManager.ID, BreedingConfigReloadManager.INSTANCE);

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

		PlayerQuitEvent.QUIT.register(player -> {
			if (player.getVehicle() != null) {
				Entity entityRide = player.getVehicle();
				if (entityRide instanceof AbstractChocobo) {
					player.removeVehicle();
				}
			}
			return InteractionResult.PASS;
		});

		Chococraft.registerCompostables();

		FabricDefaultAttributeRegistry.register(ModEntities.CHOCOBO.get(), FabricChocobo.createAttributes());
	}
}
