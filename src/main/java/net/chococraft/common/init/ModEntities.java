package net.chococraft.common.init;

import net.chococraft.Chococraft;
import net.chococraft.common.config.ChocoConfig;
import net.chococraft.common.entities.ChocoboEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo.Spawners;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntities {
	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, Chococraft.MODID);

	public static final RegistryObject<EntityType<ChocoboEntity>> CHOCOBO = ENTITIES.register("chocobo", () ->
			register("chocobo", EntityType.Builder.of(ChocoboEntity::new, EntityClassification.CREATURE).sized(1.2f, 2.8f).clientTrackingRange(64)));

	public static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> builder) {
		return builder.build(id);
	}

	public static void addSpawns(BiomeLoadingEvent event) {
		if (event.getName() == null) {
			return;
		}

		RegistryKey<Biome> biomeKey = RegistryKey.create(Registry.BIOME_REGISTRY, event.getName());
		if (BiomeDictionary.hasType(biomeKey, Type.PLAINS) || BiomeDictionary.hasType(biomeKey, Type.HILLS) || BiomeDictionary.hasType(biomeKey, Type.NETHER)) {

			event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new Spawners(ModEntities.CHOCOBO.get(),
					ChocoConfig.COMMON.chocoboSpawnWeight.get(), ChocoConfig.COMMON.chocoboPackSizeMin.get(), ChocoConfig.COMMON.chocoboPackSizeMax.get()));
		}
	}

	public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
		event.put(ModEntities.CHOCOBO.get(), ChocoboEntity.createAttributes().build());
	}

	public static void initializeSpawnPlacements() {
		EntitySpawnPlacementRegistry.register(ModEntities.CHOCOBO.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ChocoboEntity::checkChocoboSpawnRules);
	}
}
