package net.chococraft.fabric.datagen;

import net.chococraft.common.world.worldgen.ModFeatures;
import net.chococraft.fabric.datagen.client.ChocoLanguage;
import net.chococraft.fabric.datagen.client.ChocoModels;
import net.chococraft.fabric.datagen.client.ChocoSoundProvider;
import net.chococraft.fabric.datagen.data.ChocoDatapack;
import net.chococraft.fabric.datagen.data.ChocoLoot;
import net.chococraft.fabric.datagen.data.ChocoRecipes;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;

public class ModDatagenerator implements DataGeneratorEntrypoint {

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator generator) {
		var pack = generator.createPack();

		pack.addProvider(ChocoLoot.ChocoBlockLoot::new);
		pack.addProvider(ChocoLoot.ChocoEntityLoot::new);
		pack.addProvider(ChocoRecipes::new);
		pack.addProvider(ChocoDatapack::new);

		pack.addProvider(ChocoLanguage::new);
		pack.addProvider(ChocoModels::new);
		pack.addProvider(ChocoSoundProvider::new);
	}

	@Override
	public void buildRegistry(RegistrySetBuilder registryBuilder) {
		registryBuilder.add(Registries.CONFIGURED_FEATURE, ModFeatures::configuredBootstrap);
		registryBuilder.add(Registries.PLACED_FEATURE, ModFeatures::placedBootstrap);
	}
}