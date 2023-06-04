package net.chococraft.forge.datagen.client.patchouli;

import net.chococraft.Chococraft;
import net.chococraft.common.entity.properties.ChocoboColor;
import net.chococraft.registry.ModEntities;
import net.chococraft.registry.ModRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import xyz.brassgoggledcoders.patchouliprovider.BookBuilder;
import xyz.brassgoggledcoders.patchouliprovider.PatchouliBookProvider;

import java.util.function.Consumer;

public class PatchouliProvider extends PatchouliBookProvider {
	public PatchouliProvider(PackOutput packOutput) {
		super(packOutput, Chococraft.MOD_ID, "en_us");
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


		bookBuilder = bookBuilder.addCategory("chocobos", "info.chococraft.book.chocobos.name",
						"info.chococraft.book.chocobos.desc", "chococraft:chocobo_feather")
				.setSortnum(0)

				//Add Chocobo entry
				.addEntry("chocobos/chocobo", "info.chococraft.book.chocobo.entry.name", ModRegistry.YELLOW_CHOCOBO_SPAWN_EGG.getId().toString())
				.addEntityPage(ModEntities.CHOCOBO.getId()).setEntityNbt(getChocoboData(ChocoboColor.YELLOW))
				.setText("info.chococraft.book.chocobo.text").build()
				.addTextPage("info.chococraft.book.chocobo.text2").build()
				.addSpotlightPage(new ItemStack(ModRegistry.GYSAHL_GREEN_ITEM.get()))
				.setText("info.chococraft.book.chocobo.text4").build()
				.addImagePage(new ResourceLocation(Chococraft.MOD_ID, "textures/gui/entry/chocobo.png"))
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
				.addEntry("chocobos/chicobo", "info.chococraft.book.chicobo.entry.name", ModRegistry.YELLOW_CHOCOBO_SPAWN_EGG.getId().toString())
				.addTextPage("info.chococraft.book.chicobo.text1").build()
				.addEntityPage(ModEntities.CHOCOBO.getId()).setEntityNbt(getChicoboData(ChocoboColor.YELLOW))
				.setName("Chicobo (Yellow)").build()
				.build()

				//Add Raw Drumstick entry
				.addEntry("chocobos/raw_drumstick", "info.chococraft.book.raw_drumstick.entry.name", ModRegistry.CHOCOBO_DRUMSTICK_RAW.getId().toString())
				.addTextPage("info.chococraft.book.raw_drumstick.text1").build()
				.addSmeltingPage(new ResourceLocation(Chococraft.MOD_ID, "chocobo_drumstick_cooked")).build()
				.addSmokingPage(new ResourceLocation(Chococraft.MOD_ID, "chocobo_drumstick_cooked_from_smoking")).build()
				.addCampfirePage(new ResourceLocation(Chococraft.MOD_ID, "chocobo_drumstick_cooked_from_campfire_cooking")).build()
				.build()

				//Add Feather entry
				.addEntry("chocobos/feather", "info.chococraft.book.feather.entry.name", ModRegistry.CHOCOBO_FEATHER.getId().toString())
				.addTextPage("info.chococraft.book.feather.text1").build()
				.addSpotlightPage(new ItemStack(ModRegistry.CHOCOBO_FEATHER.get())).build()
				.build()

				.build(); //Back to the bookbuilder

		bookBuilder = bookBuilder.addCategory("gysahls", "info.chococraft.book.gysahls.name",
						"info.chococraft.book.gysahls.desc", "chococraft:gysahl_green")
				.setSortnum(1)

				//Add Loverly Gysahl entry
				.addEntry("gysahls/loverly_gysahl", "info.chococraft.book.loverly_gysahl.entry.name", ModRegistry.LOVERLY_GYSAHL_GREEN.getId().toString())
				.addTextPage("info.chococraft.book.loverly_gysahl.text1").build()
				.addSpotlightPage(new ItemStack(ModRegistry.LOVERLY_GYSAHL_GREEN.get())).build()
				.build()

				//Add Golden Gysahl entry
				.addEntry("gysahls/gold_gysahl", "info.chococraft.book.gold_gysahl.entry.name", ModRegistry.GOLD_GYSAHL.getId().toString())
				.addTextPage("info.chococraft.book.gold_gysahl.text1").build()
				.addSpotlightPage(new ItemStack(ModRegistry.GOLD_GYSAHL.get())).build()
				.build()

				//Add Red Gysahl entry
				.addEntry("gysahls/red_gysahl", "info.chococraft.book.red_gysahl.entry.name", ModRegistry.RED_GYSAHL.getId().toString())
				.addTextPage("info.chococraft.book.red_gysahl.text1").build()
				.addCraftingPage(new ResourceLocation(Chococraft.MOD_ID, "red_gysahl")).build()
				.build()

				//Add Pink Gysahl entry
				.addEntry("gysahls/pink_gysahl", "info.chococraft.book.pink_gysahl.entry.name", ModRegistry.PINK_GYSAHL.getId().toString())
				.addTextPage("info.chococraft.book.pink_gysahl.text1").build()
				.addCraftingPage(new ResourceLocation(Chococraft.MOD_ID, "pink_gysahl")).build()
				.build()

				//Add Gysahl Green entry
				.addEntry("gysahls/gysahl_green", "info.chococraft.book.gysahl_green.entry.name", ModRegistry.GYSAHL_GREEN_ITEM.getId().toString())
				.addTextPage("info.chococraft.book.gysahl_green.text1").build()
				.addImagePage(new ResourceLocation(Chococraft.MOD_ID, "textures/gui/entry/gysahl.png"))
				.setText("info.chococraft.book.gysahl_green.text2").setBorder(true).build()
				.addCraftingPage(new ResourceLocation(Chococraft.MOD_ID, "gysahl_green_to_seeds"))
				.setText("info.chococraft.book.gysahl_green.text3").build()
				.addTextPage("info.chococraft.book.gysahl_green.text4").build()
				.addSpotlightPage(new ItemStack(ModRegistry.GYSAHL_GREEN_ITEM.get())).build()
				.build()

				//Add Pickled Gysahl entry
				.addEntry("gysahls/pickled_gysahl", "info.chococraft.book.pickled_gysahl.entry.name", ModRegistry.PICKLED_GYSAHL_RAW.getId().toString())
				.addTextPage("info.chococraft.book.pickled_gysahl.text1").build()
				.addCraftingPage(new ResourceLocation(Chococraft.MOD_ID, "pickled_gysahl_raw")).build()
				.addSmeltingPage(new ResourceLocation(Chococraft.MOD_ID, "pickled_gysahl_cooked")).build()
				.addSmokingPage(new ResourceLocation(Chococraft.MOD_ID, "pickled_gysahl_cooked_from_smoking")).build()
				.addCampfirePage(new ResourceLocation(Chococraft.MOD_ID, "pickled_gysahl_cooked_from_campfire_cooking")).build()
				.build()

				//Add Gysahl Cake entry
				.addEntry("gysahls/gysahl_cake", "info.chococraft.book.gysahl_cake.entry.name", ModRegistry.GYSAHL_CAKE.getId().toString())
				.addTextPage("info.chococraft.book.gysahl_cake.text1").build()
				.addCraftingPage(new ResourceLocation(Chococraft.MOD_ID, "gysahl_cake")).build()
				.build()

				//Add Saddle Cake entry
				.addEntry("gysahls/saddle", "info.chococraft.book.saddle.entry.name", ModRegistry.CHOCOBO_SADDLE.getId().toString())
				.addTextPage("info.chococraft.book.saddle.text1").build()
				.addCraftingPage(new ResourceLocation(Chococraft.MOD_ID, "chocobo_saddle"))
				.setRecipe2(new ResourceLocation(Chococraft.MOD_ID, "chocobo_saddle_alt")).build()
				.addTextPage("info.chococraft.book.saddle.text2").build()
				.addCraftingPage(new ResourceLocation(Chococraft.MOD_ID, "chocobo_saddle_bags"))
				.setRecipe2(new ResourceLocation(Chococraft.MOD_ID, "chocobo_saddle_pack")).build()
				.build()

				.build(); //Back to the bookbuilder

		bookBuilder = bookBuilder.addCategory("equipments", "info.chococraft.book.equipments.name",
						"info.chococraft.book.equipments.desc", "chococraft:chocobo_saddle_pack")
				.setSortnum(2)

				//Add Choco Disguise entry
				.addEntry("equipments/chocodisguise", "info.chococraft.book.chocodisguise.entry.name", ModRegistry.CHOCO_DISGUISE_HELMET.getId().toString())
				.addTextPage("info.chococraft.book.chocodisguise.text1").build()
				.addImagePage(new ResourceLocation(Chococraft.MOD_ID, "textures/gui/entry/chocodisguise.png")).setBorder(true).build()
				.addCraftingPage(new ResourceLocation(Chococraft.MOD_ID, "choco_disguise_helmet"))
				.setRecipe2(new ResourceLocation(Chococraft.MOD_ID, "choco_disguise_chestplate")).build()
				.addCraftingPage(new ResourceLocation(Chococraft.MOD_ID, "choco_disguise_leggings"))
				.setRecipe2(new ResourceLocation(Chococraft.MOD_ID, "choco_disguise_boots")).build()
				.build()

				//Add Saddle Cake entry
				.addEntry("equipments/saddle", "info.chococraft.book.saddle.entry.name", ModRegistry.CHOCOBO_SADDLE.getId().toString())
				.addTextPage("info.chococraft.book.saddle.text1").build()
				.addCraftingPage(new ResourceLocation(Chococraft.MOD_ID, "chocobo_saddle"))
				.setRecipe2(new ResourceLocation(Chococraft.MOD_ID, "chocobo_saddle_alt")).build()
				.addTextPage("info.chococraft.book.saddle.text2").build()
				.addCraftingPage(new ResourceLocation(Chococraft.MOD_ID, "chocobo_saddle_bags"))
				.setRecipe2(new ResourceLocation(Chococraft.MOD_ID, "chocobo_saddle_pack")).build()
				.build()

				//Add Whistle entry
				.addEntry("equipments/whistle", "info.chococraft.book.whistle.entry.name", ModRegistry.CHOCOBO_WHISTLE.getId().toString())
				.addTextPage("info.chococraft.book.whistle.text1").build()
				.addCraftingPage(new ResourceLocation(Chococraft.MOD_ID, "chocobo_whistle")).build()
				.build()

				.build(); //Back to the bookbuilder

		bookBuilder = bookBuilder.addCategory("misc", "info.chococraft.book.misc.name",
						"info.chococraft.book.misc.desc", "chococraft:straw")
				.setSortnum(3)

				//Add Straw entry
				.addEntry("misc/straw", "info.chococraft.book.straw.entry.name", ModRegistry.STRAW_ITEM.getId().toString())
				.addTextPage("info.chococraft.book.straw.text1").build()
				.addCraftingPage(new ResourceLocation(Chococraft.MOD_ID, "straw")).build()
				.build()

				.build(); //Back to the bookbuilder

		//Legacy category
		bookBuilder = bookBuilder.addCategory("legacy", "info.chococraft.book.legacy.name",
						"info.chococraft.book.legacy.desc", "chococraft:chocopedia")
				.setSortnum(4)
				//Add legacy info entry
				.addEntry("legacy/info", "info.chococraft.book.legacy.entry.name", "chococraft:chocopedia")
				.addTextPage("info.chococraft.book.legacy.page1").build()
				.addTextPage("info.chococraft.book.legacy.page2").build()
				.addTextPage("info.chococraft.book.legacy.page3").build()
				.addTextPage("info.chococraft.book.legacy.page4").build()
				.addTextPage("info.chococraft.book.legacy.page5").build()
				.addTextPage("info.chococraft.book.legacy.page6").build()
				.addTextPage("info.chococraft.book.legacy.page7").build()
				.addTextPage("info.chococraft.book.legacy.page8").build()
				.addTextPage("info.chococraft.book.legacy.page9").build()
				.addTextPage("info.chococraft.book.legacy.page10").build()
				.addTextPage("info.chococraft.book.legacy.page11").build()
				.addTextPage("info.chococraft.book.legacy.page12").build()
				.addTextPage("info.chococraft.book.legacy.page13").build()
				.addTextPage("info.chococraft.book.legacy.page14").build()
				.addTextPage("info.chococraft.book.legacy.page15").build()
				.addTextPage("info.chococraft.book.legacy.page16").build()
				.addTextPage("info.chococraft.book.legacy.page17").build()
				.addTextPage("info.chococraft.book.legacy.page18").build()
				.addTextPage("info.chococraft.book.legacy.page19").build()
				.addTextPage("info.chococraft.book.legacy.page20").build()
				.addTextPage("info.chococraft.book.legacy.page21").build()
				.addTextPage("info.chococraft.book.legacy.page22").build()
				.addTextPage("info.chococraft.book.legacy.page23").build()
				.addTextPage("info.chococraft.book.legacy.page24").build()
				.addTextPage("info.chococraft.book.legacy.page25").build()
				.build()
				.build(); //Back to the bookbuilder

		//Finish book
		bookBuilder.build(consumer);
	}

	public CompoundTag getChocoboData(ChocoboColor color) {
		CompoundTag tag = new CompoundTag();
		tag.putByte("Color", (byte) color.ordinal());
		return tag;
	}

	public CompoundTag getChicoboData(ChocoboColor color) {
		CompoundTag tag = new CompoundTag();
		tag.putInt("Age", -1);
		tag.putByte("Color", (byte) color.ordinal());
		return tag;
	}
}
