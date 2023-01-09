package net.chococraft.forge.common.modifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.chococraft.Chococraft;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModModifiers {
	public static final DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS = DeferredRegister.create(
			ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, Chococraft.MOD_ID);

	public static final RegistryObject<Codec<AddChocoboModifier>> ADD_CHOCOBO = BIOME_MODIFIER_SERIALIZERS.register("add_chocobo", () -> {
		return RecordCodecBuilder.create((builder) -> {
			return builder.group(Biome.LIST_CODEC.fieldOf("biomes").forGetter(AddChocoboModifier::biomes))
					.apply(builder, AddChocoboModifier::new);
		});
	});
}
