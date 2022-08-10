package net.chococraft.datagen.data;

import net.chococraft.common.init.ModRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class ChocoRecipes extends RecipeProvider {
	public ChocoRecipes(DataGenerator generator) {
		super(generator);
	}

	@Override
	protected void buildCraftingRecipes(Consumer<FinishedRecipe> recipeConsumer) {
		ShapedRecipeBuilder.shaped(ModRegistry.CHOCO_DISGUISE_BOOTS.get())
				.pattern("F F").pattern("F F").define('F', ModRegistry.CHOCOBO_FEATHER.get())
				.unlockedBy("has_chocobo_feather", has(ModRegistry.CHOCOBO_FEATHER.get())).save(recipeConsumer);
		ShapedRecipeBuilder.shaped(ModRegistry.CHOCO_DISGUISE_CHESTPLATE.get())
				.pattern("F F").pattern("FFF").pattern("FFF").define('F', ModRegistry.CHOCOBO_FEATHER.get())
				.unlockedBy("has_chocobo_feather", has(ModRegistry.CHOCOBO_FEATHER.get())).save(recipeConsumer);
		ShapedRecipeBuilder.shaped(ModRegistry.CHOCO_DISGUISE_HELMET.get())
				.pattern("FFF").pattern("F F").define('F', ModRegistry.CHOCOBO_FEATHER.get())
				.unlockedBy("has_chocobo_feather", has(ModRegistry.CHOCOBO_FEATHER.get())).save(recipeConsumer);
		ShapedRecipeBuilder.shaped(ModRegistry.CHOCO_DISGUISE_LEGGINGS.get())
				.pattern("FFF").pattern("F F").pattern("F F").define('F', ModRegistry.CHOCOBO_FEATHER.get())
				.unlockedBy("has_chocobo_feather", has(ModRegistry.CHOCOBO_FEATHER.get())).save(recipeConsumer);

		ShapedRecipeBuilder.shaped(ModRegistry.CHOCOBO_SADDLE_PACK.get())
				.pattern("TFT").pattern("WSW").pattern("TLT")
				.define('L', Ingredient.of(Tags.Items.LEATHER)).define('T', Ingredient.of(Tags.Items.STRING))
				.define('W', Items.WHITE_WOOL).define('F', ModRegistry.CHOCOBO_FEATHER.get()).define('S', ModRegistry.CHOCOBO_SADDLE_BAGS.get())
				.unlockedBy("has_chocobo_saddle_bags", has(ModRegistry.CHOCOBO_SADDLE_BAGS.get())).save(recipeConsumer);

		ShapedRecipeBuilder.shaped(ModRegistry.CHOCOBO_SADDLE.get())
				.pattern("TLT").pattern(" F ").define('F', ModRegistry.CHOCOBO_FEATHER.get())
				.define('L', Ingredient.of(Tags.Items.LEATHER)).define('T', Ingredient.of(Tags.Items.STRING))
				.unlockedBy("has_chocobo_feather", has(ModRegistry.CHOCOBO_FEATHER.get())).save(recipeConsumer);
		ShapelessRecipeBuilder.shapeless(ModRegistry.CHOCOBO_SADDLE.get())
				.requires(Items.SADDLE).requires(ModRegistry.CHOCOBO_FEATHER.get())
				.unlockedBy("has_chocobo_feather", has(ModRegistry.CHOCOBO_FEATHER.get()))
				.save(recipeConsumer, "chococraft:chocobo_saddle_alt");

		ShapedRecipeBuilder.shaped(ModRegistry.CHOCOBO_SADDLE_BAGS.get())
				.pattern(" F ").pattern("LSL").pattern(" L ").define('F', ModRegistry.CHOCOBO_FEATHER.get())
				.define('L', Ingredient.of(Tags.Items.LEATHER)).define('S', ModRegistry.CHOCOBO_SADDLE.get())
				.unlockedBy("has_chocobo_feather", has(ModRegistry.CHOCOBO_FEATHER.get())).save(recipeConsumer);

		ShapelessRecipeBuilder.shapeless(ModRegistry.CHOCOBO_WHISTLE.get())
				.requires(Ingredient.of(Tags.Items.INGOTS_GOLD)).requires(ModRegistry.CHOCOBO_FEATHER.get())
				.unlockedBy("has_chocobo_feather", has(ModRegistry.CHOCOBO_FEATHER.get()))
				.save(recipeConsumer);

		ShapelessRecipeBuilder.shapeless(ModRegistry.CHOCOPEDIA.get())
				.requires(Items.BOOK).requires(ModRegistry.GYSAHL_GREEN_ITEM.get())
				.unlockedBy("has_gysahl_green", has(ModRegistry.GYSAHL_GREEN_ITEM.get()))
				.save(recipeConsumer);

		ShapedRecipeBuilder.shaped(ModRegistry.GYSAHL_CAKE.get())
				.pattern("BGB").pattern("SES").pattern("WGW")
				.define('B', Items.MILK_BUCKET).define('G', ModRegistry.GYSAHL_GREEN_ITEM.get())
				.define('S', Items.SUGAR).define('E', Ingredient.of(Tags.Items.EGGS)).define('W', Ingredient.of(Tags.Items.CROPS_WHEAT))
				.unlockedBy("has_gysahl_green", has(ModRegistry.GYSAHL_GREEN_ITEM.get())).save(recipeConsumer);

		ShapelessRecipeBuilder.shapeless(ModRegistry.GYSAHL_GREEN_SEEDS.get())
				.requires(ModRegistry.GYSAHL_GREEN_ITEM.get())
				.unlockedBy("has_gysahl_green", has(ModRegistry.GYSAHL_GREEN_ITEM.get()))
				.save(recipeConsumer, "chococraft:gysahl_green_to_seeds");

		SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModRegistry.CHOCOBO_DRUMSTICK_RAW.get()),
						ModRegistry.CHOCOBO_DRUMSTICK_COOKED.get(), 0.35F, 200).unlockedBy("has_raw_drumstick", has(ModRegistry.CHOCOBO_DRUMSTICK_RAW.get()))
				.save(recipeConsumer);
		SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(ModRegistry.CHOCOBO_DRUMSTICK_RAW.get()),
						ModRegistry.CHOCOBO_DRUMSTICK_COOKED.get(), 0.35F, 600).unlockedBy("has_raw_drumstick", has(ModRegistry.CHOCOBO_DRUMSTICK_RAW.get()))
				.save(recipeConsumer, "chococraft:chocobo_drumstick_cooked_from_campfire_cooking");
		SimpleCookingRecipeBuilder.smoking(Ingredient.of(ModRegistry.CHOCOBO_DRUMSTICK_RAW.get()),
						ModRegistry.CHOCOBO_DRUMSTICK_COOKED.get(), 0.35F, 100).unlockedBy("has_raw_drumstick", has(ModRegistry.CHOCOBO_DRUMSTICK_RAW.get()))
				.save(recipeConsumer, "chococraft:chocobo_drumstick_cooked_from_smoking");

		ShapelessRecipeBuilder.shapeless(ModRegistry.PICKLED_GYSAHL_RAW.get(), 3)
				.requires(ModRegistry.GYSAHL_GREEN_ITEM.get()).requires(ModRegistry.GYSAHL_GREEN_ITEM.get())
				.requires(ModRegistry.GYSAHL_GREEN_ITEM.get()).requires(Items.SUGAR).requires(Items.WATER_BUCKET)
				.unlockedBy("has_gysahl_green", has(ModRegistry.GYSAHL_GREEN_ITEM.get()))
				.save(recipeConsumer);

		SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModRegistry.PICKLED_GYSAHL_RAW.get()),
						ModRegistry.PICKLED_GYSAHL_COOKED.get(), 0.35F, 200).unlockedBy("has_raw_drumstick", has(ModRegistry.PICKLED_GYSAHL_RAW.get()))
				.save(recipeConsumer);
		SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(ModRegistry.PICKLED_GYSAHL_RAW.get()),
						ModRegistry.PICKLED_GYSAHL_COOKED.get(), 0.35F, 600).unlockedBy("has_raw_drumstick", has(ModRegistry.PICKLED_GYSAHL_RAW.get()))
				.save(recipeConsumer, "chococraft:pickled_gysahl_cooked_from_campfire_cooking");
		SimpleCookingRecipeBuilder.smoking(Ingredient.of(ModRegistry.PICKLED_GYSAHL_RAW.get()),
						ModRegistry.PICKLED_GYSAHL_COOKED.get(), 0.35F, 100).unlockedBy("has_raw_drumstick", has(ModRegistry.PICKLED_GYSAHL_RAW.get()))
				.save(recipeConsumer, "chococraft:pickled_gysahl_cooked_from_smoking");


		ShapedRecipeBuilder.shaped(ModRegistry.STRAW.get())
				.pattern("WW").define('W', Items.WHEAT)
				.unlockedBy("has_wheat", has(Items.WHEAT)).save(recipeConsumer);


		ShapelessRecipeBuilder.shapeless(ModRegistry.RED_GYSAHL.get())
				.requires(ModRegistry.GYSAHL_GREEN_ITEM.get()).requires(Ingredient.of(Tags.Items.DYES_RED))
				.unlockedBy("has_gysahl_green", has(ModRegistry.GYSAHL_GREEN_ITEM.get()))
				.save(recipeConsumer);

		ShapelessRecipeBuilder.shapeless(ModRegistry.PINK_GYSAHL.get())
				.requires(ModRegistry.GYSAHL_GREEN_ITEM.get()).requires(Ingredient.of(Tags.Items.DYES_PINK))
				.unlockedBy("has_gysahl_green", has(ModRegistry.GYSAHL_GREEN_ITEM.get()))
				.save(recipeConsumer);
	}
}
