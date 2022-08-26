package net.chococraft.common.init;

import net.chococraft.Chococraft;
import net.chococraft.common.config.ChocoConfig;
import net.chococraft.common.entities.Chocobo;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, Chococraft.MODID);

	public static final RegistryObject<EntityType<Chocobo>> CHOCOBO = ENTITIES.register("chocobo", () ->
			register("chocobo", EntityType.Builder.of(Chocobo::new, MobCategory.CREATURE).sized(1.2f, 2.8f).clientTrackingRange(64)));

	public static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> builder) {
		return builder.build(id);
	}

	public static void addSpawns(BiomeLoadingEvent event) {
		if (event.getName() == null) {
			return;
		}

		ResourceKey<Biome> biomeKey = ResourceKey.create(Registry.BIOME_REGISTRY, event.getName());
		if (BiomeDictionary.hasType(biomeKey, Type.PLAINS) || BiomeDictionary.hasType(biomeKey, Type.HILLS) || BiomeDictionary.hasType(biomeKey, Type.NETHER)) {
			event.getSpawns().getSpawner(MobCategory.CREATURE).add(new SpawnerData(ModEntities.CHOCOBO.get(),
					ChocoConfig.COMMON.chocoboSpawnWeight.get(), ChocoConfig.COMMON.chocoboPackSizeMin.get(), ChocoConfig.COMMON.chocoboPackSizeMax.get()));
		}
	}

	public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
		event.put(ModEntities.CHOCOBO.get(), Chocobo.createAttributes().build());
	}

	public static void initializeSpawnPlacements() {
		SpawnPlacements.register(ModEntities.CHOCOBO.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Chocobo::checkChocoboSpawnRules);
	}
}
