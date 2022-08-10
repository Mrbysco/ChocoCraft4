package net.chococraft.datagen;

import net.chococraft.datagen.client.ChocoBlockModels;
import net.chococraft.datagen.client.ChocoBlockstates;
import net.chococraft.datagen.client.ChocoItemModels;
import net.chococraft.datagen.client.ChocoLanguage;
import net.chococraft.datagen.client.ChocoSoundProvider;
import net.chococraft.datagen.client.patchouli.PatchouliProvider;
import net.chococraft.datagen.data.ChocoLoot;
import net.chococraft.datagen.data.ChocoRecipes;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModDatagenerator {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		ExistingFileHelper helper = event.getExistingFileHelper();

		if (event.includeServer()) {
			generator.addProvider(new ChocoLoot(generator));
			generator.addProvider(new ChocoRecipes(generator));
		}
		if (event.includeClient()) {
			generator.addProvider(new ChocoLanguage(generator));
			generator.addProvider(new ChocoBlockModels(generator, helper));
			generator.addProvider(new ChocoBlockstates(generator, helper));
			generator.addProvider(new ChocoItemModels(generator, helper));
			generator.addProvider(new ChocoSoundProvider(generator, helper));
		}
		generator.addProvider(new PatchouliProvider(generator));
	}
}