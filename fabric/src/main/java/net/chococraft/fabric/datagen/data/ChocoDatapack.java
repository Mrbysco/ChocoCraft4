package net.chococraft.fabric.datagen.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;

import java.util.concurrent.CompletableFuture;

public class ChocoDatapack  extends FabricDynamicRegistryProvider {

	public ChocoDatapack(FabricDataOutput output, CompletableFuture<Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void configure(Provider registries, Entries entries) {
		entries.addAll(registries.lookupOrThrow(Registries.CONFIGURED_FEATURE));
		entries.addAll(registries.lookupOrThrow(Registries.PLACED_FEATURE));
	}

	@Override
	public String getName() {
		return "ChocoCraft datapack";
	}
}
