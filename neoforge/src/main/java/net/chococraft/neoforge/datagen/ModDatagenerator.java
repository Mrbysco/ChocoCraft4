package net.chococraft.neoforge.datagen;

import net.chococraft.Chococraft;
import net.chococraft.neoforge.datagen.client.ChocoLanguage;
import net.chococraft.neoforge.datagen.client.ChocoModels;
import net.chococraft.neoforge.datagen.client.ChocoSoundProvider;
import net.chococraft.neoforge.datagen.data.ChocoBlockTags;
import net.chococraft.neoforge.datagen.data.ChocoDatapack;
import net.chococraft.neoforge.datagen.data.ChocoItemTags;
import net.chococraft.neoforge.datagen.data.ChocoLoot;
import net.chococraft.neoforge.datagen.data.ChocoRecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber
public class ModDatagenerator {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent.Client event) {
		DataGenerator generator = event.getGenerator();
		PackOutput packOutput = generator.getPackOutput();
		CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

		generator.addProvider(true, new ChocoLoot(packOutput, lookupProvider));
		generator.addProvider(true, new ChocoRecipes.Runner(packOutput, lookupProvider));
		generator.addProvider(true, new ChocoBlockTags(packOutput, lookupProvider));
		generator.addProvider(true, new ChocoItemTags(packOutput, lookupProvider));

//		generator.addProvider(true, new PatchouliProvider(packOutput, lookupProvider)); TODO: Re-enable when we have a patchouli provider build for 1.21.4

		generator.addProvider(true, new ChocoDatapack(
				packOutput, lookupProvider, Set.of(Chococraft.MOD_ID)));

		generator.addProvider(true, new ChocoLanguage(packOutput));
		generator.addProvider(true, new ChocoModels(packOutput));
		generator.addProvider(true, new ChocoSoundProvider(packOutput));
	}
}