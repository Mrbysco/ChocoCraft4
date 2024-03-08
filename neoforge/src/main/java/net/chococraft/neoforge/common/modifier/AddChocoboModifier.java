package net.chococraft.neoforge.common.modifier;

import com.mojang.serialization.Codec;
import net.chococraft.neoforge.common.config.NeoForgeChocoConfig;
import net.chococraft.registry.ModEntities;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.MobSpawnSettingsBuilder;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo.BiomeInfo.Builder;

public record AddChocoboModifier(HolderSet<Biome> biomes) implements BiomeModifier {
	@Override
	public void modify(Holder<Biome> biome, Phase phase, Builder builder) {
		if (phase == Phase.ADD && this.biomes.contains(biome)) {
			MobSpawnSettingsBuilder spawns = builder.getMobSpawnSettings();

			MobSpawnSettings.SpawnerData spawner = new SpawnerData(ModEntities.CHOCOBO.get(),
					NeoForgeChocoConfig.COMMON.chocoboSpawnWeight.get(),
					NeoForgeChocoConfig.COMMON.chocoboPackSizeMin.get(),
					NeoForgeChocoConfig.COMMON.chocoboPackSizeMax.get());
			EntityType<?> type = spawner.type;
			spawns.addSpawn(type.getCategory(), spawner);
		}
	}

	@Override
	public Codec<? extends BiomeModifier> codec() {
		return ModModifiers.ADD_CHOCOBO.get();
	}
}
