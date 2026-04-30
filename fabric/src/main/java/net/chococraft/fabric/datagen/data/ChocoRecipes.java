package net.chococraft.fabric.datagen.data;

import net.chococraft.registry.ModRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.concurrent.CompletableFuture;

public class ChocoRecipes extends FabricRecipeProvider {
	public ChocoRecipes(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
		return new Provider(provider, recipeOutput);
	}

	@Override
	public String getName() {
		return "Telepass recipes";
	}

	public static class Provider extends RecipeProvider {
		public Provider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
			super(provider, recipeOutput);
		}

		@Override
		public void buildRecipes() {
			shaped(RecipeCategory.MISC, ModRegistry.CHOCO_DISGUISE_BOOTS.get())
					.pattern("F F").pattern("F F").define('F', ModRegistry.CHOCOBO_FEATHER.get())
					.unlockedBy("has_chocobo_feather", has(ModRegistry.CHOCOBO_FEATHER.get())).save(output);
			shaped(RecipeCategory.MISC, ModRegistry.CHOCO_DISGUISE_CHESTPLATE.get())
					.pattern("F F").pattern("FFF").pattern("FFF").define('F', ModRegistry.CHOCOBO_FEATHER.get())
					.unlockedBy("has_chocobo_feather", has(ModRegistry.CHOCOBO_FEATHER.get())).save(output);
			shaped(RecipeCategory.MISC, ModRegistry.CHOCO_DISGUISE_HELMET.get())
					.pattern("FFF").pattern("F F").define('F', ModRegistry.CHOCOBO_FEATHER.get())
					.unlockedBy("has_chocobo_feather", has(ModRegistry.CHOCOBO_FEATHER.get())).save(output);
			shaped(RecipeCategory.MISC, ModRegistry.CHOCO_DISGUISE_LEGGINGS.get())
					.pattern("FFF").pattern("F F").pattern("F F")
					.define('F', ModRegistry.CHOCOBO_FEATHER.get())
					.unlockedBy("has_chocobo_feather", has(ModRegistry.CHOCOBO_FEATHER.get())).save(output);

			shaped(RecipeCategory.MISC, ModRegistry.CHOCOBO_SADDLE_PACK.get())
					.pattern("TFT").pattern("WSW").pattern("TLT")
					.define('L', Ingredient.of(tagSet(ConventionalItemTags.LEATHERS)))
					.define('T', Ingredient.of(tagSet(ConventionalItemTags.STRINGS)))
					.define('W', Items.WHITE_WOOL)
					.define('F', ModRegistry.CHOCOBO_FEATHER.get())
					.define('S', ModRegistry.CHOCOBO_SADDLE_BAGS.get())
					.unlockedBy("has_chocobo_saddle_bags", has(ModRegistry.CHOCOBO_SADDLE_BAGS.get())).save(output);

			shaped(RecipeCategory.MISC, ModRegistry.CHOCOBO_SADDLE.get())
					.pattern("TLT").pattern(" F ").define('F', ModRegistry.CHOCOBO_FEATHER.get())
					.define('L', Ingredient.of(tagSet(ConventionalItemTags.LEATHERS)))
					.define('T', Ingredient.of(tagSet(ConventionalItemTags.STRINGS)))
					.unlockedBy("has_chocobo_feather", has(ModRegistry.CHOCOBO_FEATHER.get())).save(output);
			shapeless(RecipeCategory.MISC, ModRegistry.CHOCOBO_SADDLE.get())
					.requires(Items.SADDLE).requires(ModRegistry.CHOCOBO_FEATHER.get())
					.unlockedBy("has_chocobo_feather", has(ModRegistry.CHOCOBO_FEATHER.get()))
					.save(output, "chococraft:chocobo_saddle_alt");

			shaped(RecipeCategory.MISC, ModRegistry.CHOCOBO_SADDLE_BAGS.get())
					.pattern(" F ").pattern("LSL").pattern(" L ")
					.define('F', ModRegistry.CHOCOBO_FEATHER.get())
					.define('L', Ingredient.of(tagSet(ConventionalItemTags.LEATHERS)))
					.define('S', ModRegistry.CHOCOBO_SADDLE.get())
					.unlockedBy("has_chocobo_feather", has(ModRegistry.CHOCOBO_FEATHER.get())).save(output);

			shapeless(RecipeCategory.MISC, ModRegistry.CHOCOBO_WHISTLE.get())
					.requires(Ingredient.of(tagSet(ConventionalItemTags.GOLD_INGOTS))).requires(ModRegistry.CHOCOBO_FEATHER.get())
					.unlockedBy("has_chocobo_feather", has(ModRegistry.CHOCOBO_FEATHER.get()))
					.save(output);

			shapeless(RecipeCategory.MISC, ModRegistry.CHOCOPEDIA.get())
					.requires(Items.BOOK).requires(ModRegistry.GYSAHL_GREEN_ITEM.get())
					.unlockedBy("has_gysahl_green", has(ModRegistry.GYSAHL_GREEN_ITEM.get()))
					.save(output);

			shaped(RecipeCategory.FOOD, ModRegistry.GYSAHL_CAKE.get())
					.pattern("BGB").pattern("SES").pattern("WGW")
					.define('B', Items.MILK_BUCKET).define('G', ModRegistry.GYSAHL_GREEN_ITEM.get())
					.define('S', Items.SUGAR)
					.define('E', Ingredient.of(tagSet(ConventionalItemTags.EGGS)))
					.define('W', Ingredient.of(tagSet(ConventionalItemTags.WHEAT_CROPS)))
					.unlockedBy("has_gysahl_green", has(ModRegistry.GYSAHL_GREEN_ITEM.get())).save(output);

			shapeless(RecipeCategory.MISC, ModRegistry.GYSAHL_GREEN_SEEDS.get())
					.requires(ModRegistry.GYSAHL_GREEN_ITEM.get())
					.unlockedBy("has_gysahl_green", has(ModRegistry.GYSAHL_GREEN_ITEM.get()))
					.save(output, "chococraft:gysahl_green_to_seeds");

			SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModRegistry.CHOCOBO_DRUMSTICK_RAW.get()), RecipeCategory.FOOD, CookingBookCategory.FOOD,
							ModRegistry.CHOCOBO_DRUMSTICK_COOKED.get(), 0.35F, 200).unlockedBy("has_raw_drumstick", has(ModRegistry.CHOCOBO_DRUMSTICK_RAW.get()))
					.save(output);
			SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(ModRegistry.CHOCOBO_DRUMSTICK_RAW.get()), RecipeCategory.FOOD,
							ModRegistry.CHOCOBO_DRUMSTICK_COOKED.get(), 0.35F, 600).unlockedBy("has_raw_drumstick", has(ModRegistry.CHOCOBO_DRUMSTICK_RAW.get()))
					.save(output, "chococraft:chocobo_drumstick_cooked_from_campfire_cooking");
			SimpleCookingRecipeBuilder.smoking(Ingredient.of(ModRegistry.CHOCOBO_DRUMSTICK_RAW.get()), RecipeCategory.FOOD,
							ModRegistry.CHOCOBO_DRUMSTICK_COOKED.get(), 0.35F, 100).unlockedBy("has_raw_drumstick", has(ModRegistry.CHOCOBO_DRUMSTICK_RAW.get()))
					.save(output, "chococraft:chocobo_drumstick_cooked_from_smoking");

			shapeless(RecipeCategory.FOOD, ModRegistry.PICKLED_GYSAHL_RAW.get(), 3)
					.requires(ModRegistry.GYSAHL_GREEN_ITEM.get()).requires(ModRegistry.GYSAHL_GREEN_ITEM.get())
					.requires(ModRegistry.GYSAHL_GREEN_ITEM.get()).requires(Items.SUGAR).requires(Items.WATER_BUCKET)
					.unlockedBy("has_gysahl_green", has(ModRegistry.GYSAHL_GREEN_ITEM.get()))
					.save(output);

			SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModRegistry.PICKLED_GYSAHL_RAW.get()), RecipeCategory.FOOD, CookingBookCategory.FOOD,
							ModRegistry.PICKLED_GYSAHL_COOKED.get(), 0.35F, 200).unlockedBy("has_raw_drumstick", has(ModRegistry.PICKLED_GYSAHL_RAW.get()))
					.save(output);
			SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(ModRegistry.PICKLED_GYSAHL_RAW.get()), RecipeCategory.FOOD,
							ModRegistry.PICKLED_GYSAHL_COOKED.get(), 0.35F, 600).unlockedBy("has_raw_drumstick", has(ModRegistry.PICKLED_GYSAHL_RAW.get()))
					.save(output, "chococraft:pickled_gysahl_cooked_from_campfire_cooking");
			SimpleCookingRecipeBuilder.smoking(Ingredient.of(ModRegistry.PICKLED_GYSAHL_RAW.get()), RecipeCategory.FOOD,
							ModRegistry.PICKLED_GYSAHL_COOKED.get(), 0.35F, 100).unlockedBy("has_raw_drumstick", has(ModRegistry.PICKLED_GYSAHL_RAW.get()))
					.save(output, "chococraft:pickled_gysahl_cooked_from_smoking");


			shaped(RecipeCategory.MISC, ModRegistry.STRAW.get())
					.pattern("WW").define('W', Items.WHEAT)
					.unlockedBy("has_wheat", has(Items.WHEAT)).save(output);


			shapeless(RecipeCategory.MISC, ModRegistry.RED_GYSAHL.get())
					.requires(ModRegistry.GYSAHL_GREEN_ITEM.get()).requires(Ingredient.of(tagSet(ConventionalItemTags.RED_DYES)))
					.unlockedBy("has_gysahl_green", has(ModRegistry.GYSAHL_GREEN_ITEM.get()))
					.save(output);

			shapeless(RecipeCategory.MISC, ModRegistry.PINK_GYSAHL.get())
					.requires(ModRegistry.GYSAHL_GREEN_ITEM.get()).requires(Ingredient.of(tagSet(ConventionalItemTags.PINK_DYES)))
					.unlockedBy("has_gysahl_green", has(ModRegistry.GYSAHL_GREEN_ITEM.get()))
					.save(output);
		}

		private HolderSet<Item> tagSet(TagKey<Item> tagKey) {
			return this.registries.lookupOrThrow(Registries.ITEM).getOrThrow(tagKey);
		}
	}
}
