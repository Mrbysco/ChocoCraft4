package net.chococraft.forge.datagen.client;

import net.chococraft.Chococraft;
import net.chococraft.registry.ModRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ChocoItemModels extends ItemModelProvider {
	public ChocoItemModels(DataGenerator gen, ExistingFileHelper helper) {
		super(gen, Chococraft.MOD_ID, helper);
	}

	@Override
	protected void registerModels() {
		generatedItem(ModRegistry.CHOCOBO_SADDLE.getId());
		generatedItem(ModRegistry.CHOCOBO_SADDLE_BAGS.getId());
		generatedItem(ModRegistry.CHOCOBO_SADDLE_PACK.getId());

		generatedItem(ModRegistry.YELLOW_CHOCOBO_SPAWN_EGG.getId());
		generatedItem(ModRegistry.GREEN_CHOCOBO_SPAWN_EGG.getId());
		generatedItem(ModRegistry.BLUE_CHOCOBO_SPAWN_EGG.getId());
		generatedItem(ModRegistry.WHITE_CHOCOBO_SPAWN_EGG.getId());
		generatedItem(ModRegistry.BLACK_CHOCOBO_SPAWN_EGG.getId());
		generatedItem(ModRegistry.GOLD_CHOCOBO_SPAWN_EGG.getId());
		generatedItem(ModRegistry.PINK_CHOCOBO_SPAWN_EGG.getId());
		generatedItem(ModRegistry.RED_CHOCOBO_SPAWN_EGG.getId());
		generatedItem(ModRegistry.PURPLE_CHOCOBO_SPAWN_EGG.getId());
		generatedItem(ModRegistry.FLAME_CHOCOBO_SPAWN_EGG.getId());

		generatedItem(ModRegistry.CHOCOBO_WHISTLE.getId());
		generatedItem(ModRegistry.CHOCOBO_FEATHER.getId());
		generatedItem(ModRegistry.CHOCOBO_DRUMSTICK_RAW.getId());
		generatedItem(ModRegistry.CHOCOBO_DRUMSTICK_COOKED.getId());

		generatedItem(ModRegistry.GYSAHL_GREEN_SEEDS.getId());
		generatedItem(ModRegistry.GYSAHL_GREEN_ITEM.getId());
		generatedItem(ModRegistry.LOVERLY_GYSAHL_GREEN.getId());
		generatedItem(ModRegistry.GOLD_GYSAHL.getId());
		generatedItem(ModRegistry.RED_GYSAHL.getId());
		generatedItem(ModRegistry.PINK_GYSAHL.getId());
		generatedItem(ModRegistry.PICKLED_GYSAHL_RAW.getId());
		generatedItem(ModRegistry.PICKLED_GYSAHL_COOKED.getId());
		generatedItem(ModRegistry.GYSAHL_CAKE.getId());

		generatedItem(ModRegistry.CHOCOPEDIA.getId());

		generatedItem(ModRegistry.CHOCO_DISGUISE_HELMET.getId());
		generatedItem(ModRegistry.CHOCO_DISGUISE_CHESTPLATE.getId());
		generatedItem(ModRegistry.CHOCO_DISGUISE_LEGGINGS.getId());
		generatedItem(ModRegistry.CHOCO_DISGUISE_BOOTS.getId());

		withBlockParent(ModRegistry.STRAW.getId());
	}

	private void withBlockParent(ResourceLocation location) {
		withExistingParent(location.getPath(), modLoc("block/" + location.getPath()));
	}

	private void generatedItem(ResourceLocation location) {
		singleTexture(location.getPath(), new ResourceLocation("item/generated"),
				"layer0", new ResourceLocation(Chococraft.MOD_ID, "item/" + location.getPath()));
	}
}
