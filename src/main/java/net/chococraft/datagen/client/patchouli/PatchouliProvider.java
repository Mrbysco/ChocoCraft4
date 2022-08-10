package net.chococraft.datagen.client.patchouli;

import net.chococraft.Chococraft;
import net.chococraft.common.entities.properties.ChocoboColor;
import net.chococraft.common.init.ModEntities;
import net.chococraft.common.init.ModRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import xyz.brassgoggledcoders.patchouliprovider.BookBuilder;
import xyz.brassgoggledcoders.patchouliprovider.PatchouliBookProvider;

import java.util.function.Consumer;

public class PatchouliProvider extends PatchouliBookProvider {
	public PatchouliProvider(DataGenerator gen) {
		super(gen, Chococraft.MODID, "en_us");
	}

	@Override
	protected void addBooks(Consumer<BookBuilder> consumer) {
		BookBuilder bookBuilder = createBookBuilder("chocopedia",
				"info.chocopedia.book.name", "info.chococraft.book.landing")
				.setSubtitle("info.chococraft.book.subtitle")
				.setCustomBookItem(new ItemStack(ModRegistry.CHOCOPEDIA.get()))
				.setCreativeTab("chococraft")
				.setModel("chococraft:chocopedia")
				.setBookTexture("chococraft:textures/gui/patchouli_book.png")
				.setDontGenerateBook(true)
				.setShowProgress(false)
				.setUseBlockyFont(true)
				.setI18n(true)
				.setFillerTexture("chococraft:textures/gui/chocobo_page_filler.png")
				.addMacro("$(item)", "$(#c47567)");


		//Legacy category
		bookBuilder = bookBuilder.addCategory("legacy", "info.chococraft.book.legacy.name",
						"info.chococraft.book.legacy.desc", "chococraft:chocopedia")
				//Add legacy info entry
				.addEntry("legacy/info", "info.chococraft.book.legacy.entry.name", "chococraft:chocopedia")
				.addTextPage("gui.chocobook.page1").build()
				.addTextPage("gui.chocobook.page2").build()
				.addTextPage("gui.chocobook.page3").build()
				.addTextPage("gui.chocobook.page4").build()
				.addTextPage("gui.chocobook.page5").build()
				.addTextPage("gui.chocobook.page6").build()
				.addTextPage("gui.chocobook.page7").build()
				.build()
				.build(); //Back to the bookbuilder

		//Crafting category
		bookBuilder = bookBuilder.addCategory("info", "info.chococraft.book.info.name",
						"info.chococraft.book.info.desc", "chococraft:chocobo_feather")

				//Add Chocobo entry
				.addEntry("info/chocobo", "info.chococraft.book.chocobo.entry.name", ModRegistry.YELLOW_CHOCOBO_SPAWN_EGG.getId().toString())
				.addEntityPage(ModEntities.CHOCOBO.getId()).setEntityNbt(getChocoboData(ChocoboColor.YELLOW))
				.setText("info.chococraft.book.chocobo.text").build()
				.addTextPage("info.chococraft.book.chocobo.text2").build()
				.addSpotlightPage(new ItemStack(ModRegistry.GYSAHL_GREEN_ITEM.get()))
				.setText("info.chococraft.book.chocobo.text4").build()
				.addImagePage(new ResourceLocation(Chococraft.MODID, "textures/gui/entry/chocobo.png"))
				.setText("info.chococraft.book.chocobo.text3").setBorder(true).build()
				.addEntityPage(ModEntities.CHOCOBO.getId()).setEntityNbt(getChocoboData(ChocoboColor.GREEN))
				.setName("Chocobo (Green)")
				.setText("info.chococraft.book.chocobo.green.description").build()
				.addTextPage("info.chococraft.book.chocobo.green.description2").build()
				.addEntityPage(ModEntities.CHOCOBO.getId()).setEntityNbt(getChocoboData(ChocoboColor.BLUE))
				.setName("Chocobo (Blue)")
				.setText("info.chococraft.book.chocobo.blue.description").build()
				.addTextPage("info.chococraft.book.chocobo.blue.description2").build()
				.addEntityPage(ModEntities.CHOCOBO.getId()).setEntityNbt(getChocoboData(ChocoboColor.WHITE))
				.setName("Chocobo (White)")
				.setText("info.chococraft.book.chocobo.white.description").build()
				.addTextPage("info.chococraft.book.chocobo.white.description2").build()
				.addEntityPage(ModEntities.CHOCOBO.getId()).setEntityNbt(getChocoboData(ChocoboColor.BLACK))
				.setName("Chocobo (Black)")
				.setText("info.chococraft.book.chocobo.black.description").build()
				.addTextPage("info.chococraft.book.chocobo.black.description2").build()
				.addEntityPage(ModEntities.CHOCOBO.getId()).setEntityNbt(getChocoboData(ChocoboColor.GOLD)).setAnchor("gold")
				.setName("Chocobo (Gold)")
				.setText("info.chococraft.book.chocobo.gold.description").build()
				.addTextPage("info.chococraft.book.chocobo.gold.description2").build()
				.addEntityPage(ModEntities.CHOCOBO.getId()).setEntityNbt(getChocoboData(ChocoboColor.PURPLE))
				.setName("Chocobo (Purple)")
				.setText("info.chococraft.book.chocobo.purple.description").build()
				.addTextPage("info.chococraft.book.chocobo.purple.description2").build()
				.addEntityPage(ModEntities.CHOCOBO.getId()).setEntityNbt(getChocoboData(ChocoboColor.FLAME))
				.setName("Chocobo (Flame)")
				.setText("info.chococraft.book.chocobo.flame.description").build()
				.addTextPage("info.chococraft.book.chocobo.flame.description2").build()
				.addEntityPage(ModEntities.CHOCOBO.getId()).setEntityNbt(getChocoboData(ChocoboColor.PINK)).setAnchor("pink")
				.setName("Chocobo (Pink)")
				.setText("info.chococraft.book.chocobo.pink.description").build()
				.addTextPage("info.chococraft.book.chocobo.pink.description2").build()
				.addEntityPage(ModEntities.CHOCOBO.getId()).setEntityNbt(getChocoboData(ChocoboColor.RED)).setAnchor("red")
				.setName("Chocobo (Red)")
				.setText("info.chococraft.book.chocobo.red.description").build()
				.addTextPage("info.chococraft.book.chocobo.red.description2").build()
				.build()

				//Add Chicobo entry
				.addEntry("info/chicobo", "info.chococraft.book.chicobo.entry.name", ModRegistry.YELLOW_CHOCOBO_SPAWN_EGG.getId().toString())
				.addTextPage("info.chococraft.book.chicobo.text1").build()
				.addEntityPage(ModEntities.CHOCOBO.getId()).setEntityNbt(getChicoboData(ChocoboColor.YELLOW))
				.setName("Chicobo (Yellow)").build()
				.build()

				//Add Loverly Gysahl entry
				.addEntry("info/loverly_gysahl", "info.chococraft.book.loverly_gysahl.entry.name", ModRegistry.LOVERLY_GYSAHL_GREEN.getId().toString())
				.addTextPage("info.chococraft.book.loverly_gysahl.text1").build()
				.addSpotlightPage(new ItemStack(ModRegistry.LOVERLY_GYSAHL_GREEN.get())).build()
				.build()

				//Add Golden Gysahl entry
				.addEntry("info/gold_gysahl", "info.chococraft.book.gold_gysahl.entry.name", ModRegistry.GOLD_GYSAHL.getId().toString())
				.addTextPage("info.chococraft.book.gold_gysahl.text1").build()
				.addSpotlightPage(new ItemStack(ModRegistry.GOLD_GYSAHL.get())).build()
				.build()

				//Add Red Gysahl entry
				.addEntry("info/red_gysahl", "info.chococraft.book.red_gysahl.entry.name", ModRegistry.RED_GYSAHL.getId().toString())
				.addTextPage("info.chococraft.book.red_gysahl.text1").build()
				.addCraftingPage(new ResourceLocation(Chococraft.MODID, "red_gysahl")).build()
				.build()

				//Add Pink Gysahl entry
				.addEntry("info/pink_gysahl", "info.chococraft.book.pink_gysahl.entry.name", ModRegistry.PINK_GYSAHL.getId().toString())
				.addTextPage("info.chococraft.book.pink_gysahl.text1").build()
				.addCraftingPage(new ResourceLocation(Chococraft.MODID, "pink_gysahl")).build()
				.build()

				//Add Gysahl Green entry
				.addEntry("info/gysahl_green", "info.chococraft.book.gysahl_green.entry.name", ModRegistry.GYSAHL_GREEN_ITEM.getId().toString())
				.addTextPage("info.chococraft.book.gysahl_green.text1").build()
				.addImagePage(new ResourceLocation(Chococraft.MODID, "textures/gui/entry/gysahl.png"))
				.setText("info.chococraft.book.gysahl_green.text2").setBorder(true).build()
				.addCraftingPage(new ResourceLocation(Chococraft.MODID, "gysahl_green_to_seeds"))
				.setText("info.chococraft.book.gysahl_green.text3").build()
				.addTextPage("info.chococraft.book.gysahl_green.text4").build()
				.addSpotlightPage(new ItemStack(ModRegistry.GYSAHL_GREEN_ITEM.get())).build()
				.build()

				//Add Pickled Gysahl entry
				.addEntry("info/pickled_gysahl", "info.chococraft.book.pickled_gysahl.entry.name", ModRegistry.PICKLED_GYSAHL_RAW.getId().toString())
				.addTextPage("info.chococraft.book.pickled_gysahl.text1").build()
				.addCraftingPage(new ResourceLocation(Chococraft.MODID, "pickled_gysahl_raw")).build()
				.addSmeltingPage(new ResourceLocation(Chococraft.MODID, "pickled_gysahl_cooked")).build()
				.addSmokingPage(new ResourceLocation(Chococraft.MODID, "pickled_gysahl_cooked_from_smoking")).build()
				.addCampfirePage(new ResourceLocation(Chococraft.MODID, "pickled_gysahl_cooked_from_campfire_cooking")).build()
				.build()

				//Add Raw Drumstick entry
				.addEntry("info/raw_drumstick", "info.chococraft.book.raw_drumstick.entry.name", ModRegistry.CHOCOBO_DRUMSTICK_RAW.getId().toString())
				.addTextPage("info.chococraft.book.raw_drumstick.text1").build()
				.addSmeltingPage(new ResourceLocation(Chococraft.MODID, "chocobo_drumstick_cooked")).build()
				.addSmokingPage(new ResourceLocation(Chococraft.MODID, "chocobo_drumstick_cooked_from_smoking")).build()
				.addCampfirePage(new ResourceLocation(Chococraft.MODID, "chocobo_drumstick_cooked_from_campfire_cooking")).build()
				.build()

				//Add Gysahl Cake entry
				.addEntry("info/gysahl_cake", "info.chococraft.book.gysahl_cake.entry.name", ModRegistry.GYSAHL_CAKE.getId().toString())
				.addTextPage("info.chococraft.book.gysahl_cake.text1").build()
				.addCraftingPage(new ResourceLocation(Chococraft.MODID, "gysahl_cake")).build()
				.build()

				//Add Choco Disguise entry
				.addEntry("info/chocodisguise", "info.chococraft.book.chocodisguise.entry.name", ModRegistry.CHOCO_DISGUISE_HELMET.getId().toString())
				.addTextPage("info.chococraft.book.chocodisguise.text1").build()
				.addImagePage(new ResourceLocation(Chococraft.MODID, "textures/gui/entry/chocodisguise.png")).setBorder(true).build()
				.addCraftingPage(new ResourceLocation(Chococraft.MODID, "choco_disguise_helmet"))
				.setRecipe2(new ResourceLocation(Chococraft.MODID, "choco_disguise_chestplate")).build()
				.addCraftingPage(new ResourceLocation(Chococraft.MODID, "choco_disguise_leggings"))
				.setRecipe2(new ResourceLocation(Chococraft.MODID, "choco_disguise_boots")).build()
				.build()

				//Add Saddle Cake entry
				.addEntry("info/saddle", "info.chococraft.book.saddle.entry.name", ModRegistry.CHOCOBO_SADDLE.getId().toString())
				.addTextPage("info.chococraft.book.saddle.text1").build()
				.addCraftingPage(new ResourceLocation(Chococraft.MODID, "chocobo_saddle"))
				.setRecipe2(new ResourceLocation(Chococraft.MODID, "chocobo_saddle_alt")).build()
				.addTextPage("info.chococraft.book.saddle.text2").build()
				.addCraftingPage(new ResourceLocation(Chococraft.MODID, "chocobo_saddle_bags"))
				.setRecipe2(new ResourceLocation(Chococraft.MODID, "chocobo_saddle_pack")).build()
				.build()

				//Add Straw entry
				.addEntry("info/straw", "info.chococraft.book.straw.entry.name", ModRegistry.STRAW_ITEM.getId().toString())
				.addTextPage("info.chococraft.book.straw.text1").build()
				.addCraftingPage(new ResourceLocation(Chococraft.MODID, "straw")).build()
				.build()

				//Add Feather entry
				.addEntry("info/feather", "info.chococraft.book.feather.entry.name", ModRegistry.CHOCOBO_FEATHER.getId().toString())
				.addTextPage("info.chococraft.book.feather.text1").build()
				.addSpotlightPage(new ItemStack(ModRegistry.CHOCOBO_FEATHER.get())).build()
				.build()

				.build(); //Back to the bookbuilder


		//Finish book
		bookBuilder.build(consumer);
	}

	public CompoundNBT getChocoboData(ChocoboColor color) {
		CompoundNBT tag = new CompoundNBT();
		tag.putByte("Color", (byte) color.ordinal());
		return tag;
	}

	public CompoundNBT getChicoboData(ChocoboColor color) {
		CompoundNBT tag = new CompoundNBT();
		tag.putInt("Age", -1);
		tag.putByte("Color", (byte) color.ordinal());
		return tag;
	}
}
