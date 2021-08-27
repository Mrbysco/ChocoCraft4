package net.chococraft.common.init;

import net.chococraft.Chococraft;
import net.chococraft.common.ChocoConfig;
import net.chococraft.common.entities.ChocoboEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo.Spawners;
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
            register("chocobo", EntityType.Builder.<ChocoboEntity>create(ChocoboEntity::new, EntityClassification.CREATURE)
                    .size(1.2f, 2.8f).trackingRange(64)));

    public static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> builder) {
        return builder.build(id);
    }

    public static void addSpawns(BiomeLoadingEvent event) {
        RegistryKey<Biome> biomeKey = RegistryKey.getOrCreateKey(Registry.BIOME_KEY, event.getName());
        if(BiomeDictionary.hasType(biomeKey, Type.PLAINS) || BiomeDictionary.hasType(biomeKey, Type.HILLS) || BiomeDictionary.hasType(biomeKey, Type.NETHER)) {

            event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new Spawners(ModEntities.CHOCOBO.get(),
                    ChocoConfig.COMMON.chocoboSpawnWeight.get(), ChocoConfig.COMMON.chocoboPackSizeMin.get(), ChocoConfig.COMMON.chocoboPackSizeMax.get()));
        }
    }

    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.CHOCOBO.get(), ChocoboEntity.createAttributes().create());
    }
}
