package net.chococraft.datagen.client;

import net.chococraft.Chococraft;
import net.chococraft.common.init.ModRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ChocoItemModels extends ItemModelProvider {
	public ChocoItemModels(DataGenerator gen, ExistingFileHelper helper) {
		super(gen, Chococraft.MODID, helper);
	}

	@Override
	protected void registerModels() {
		generatedItem(ModRegistry.CHOCOBO_SADDLE.get());
		generatedItem(ModRegistry.CHOCOBO_SADDLE_BAGS.get());
		generatedItem(ModRegistry.CHOCOBO_SADDLE_PACK.get());

		generatedItem(ModRegistry.YELLOW_CHOCOBO_SPAWN_EGG.get());
		generatedItem(ModRegistry.GREEN_CHOCOBO_SPAWN_EGG.get());
		generatedItem(ModRegistry.BLUE_CHOCOBO_SPAWN_EGG.get());
		generatedItem(ModRegistry.WHITE_CHOCOBO_SPAWN_EGG.get());
		generatedItem(ModRegistry.BLACK_CHOCOBO_SPAWN_EGG.get());
		generatedItem(ModRegistry.GOLD_CHOCOBO_SPAWN_EGG.get());
		generatedItem(ModRegistry.PINK_CHOCOBO_SPAWN_EGG.get());
		generatedItem(ModRegistry.RED_CHOCOBO_SPAWN_EGG.get());
		generatedItem(ModRegistry.PURPLE_CHOCOBO_SPAWN_EGG.get());
		generatedItem(ModRegistry.FLAME_CHOCOBO_SPAWN_EGG.get());

		generatedItem(ModRegistry.CHOCOBO_WHISTLE.get());
		generatedItem(ModRegistry.CHOCOBO_FEATHER.get());
		generatedItem(ModRegistry.CHOCOBO_DRUMSTICK_RAW.get());
		generatedItem(ModRegistry.CHOCOBO_DRUMSTICK_COOKED.get());

		generatedItem(ModRegistry.GYSAHL_GREEN_SEEDS.get());
		generatedItem(ModRegistry.GYSAHL_GREEN_ITEM.get());
		generatedItem(ModRegistry.LOVERLY_GYSAHL_GREEN.get());
		generatedItem(ModRegistry.GOLD_GYSAHL.get());
		generatedItem(ModRegistry.RED_GYSAHL.get());
		generatedItem(ModRegistry.PINK_GYSAHL.get());
		generatedItem(ModRegistry.PICKLED_GYSAHL_RAW.get());
		generatedItem(ModRegistry.PICKLED_GYSAHL_COOKED.get());
		generatedItem(ModRegistry.GYSAHL_CAKE.get());

		generatedItem(ModRegistry.CHOCOPEDIA.get());

		generatedItem(ModRegistry.CHOCO_DISGUISE_HELMET.get());
		generatedItem(ModRegistry.CHOCO_DISGUISE_CHESTPLATE.get());
		generatedItem(ModRegistry.CHOCO_DISGUISE_LEGGINGS.get());
		generatedItem(ModRegistry.CHOCO_DISGUISE_BOOTS.get());

		withBlockParent(ModRegistry.STRAW.get());
	}

	private void withBlockParent(Block block) {
		ResourceLocation location = block.getRegistryName();
		withExistingParent(location.getPath(), modLoc("block/" + location.getPath()));
	}

	private void generatedItem(Item item) {
		ResourceLocation location = item.getRegistryName();
		singleTexture(location.getPath(), new ResourceLocation("item/generated"),
				"layer0", new ResourceLocation(Chococraft.MODID, "item/" + location.getPath()));
	}
}
