package net.chococraft.forge.datagen.client;

import net.chococraft.Chococraft;
import net.chococraft.registry.ModEntities;
import net.chococraft.registry.ModRegistry;
import net.chococraft.registry.ModSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.data.DataGenerator;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.data.LanguageProvider;

public class ChocoLanguage extends LanguageProvider {
	public ChocoLanguage(DataGenerator gen) {
		super(gen, Chococraft.MOD_ID, "en_us");
	}

	@Override
	protected void addTranslations() {
		add("itemGroup.chococraft.tab", "ChocoCraft 4");
		addBlock(ModRegistry.GYSAHL_GREEN, "Gysahl Green");
		addBlock(ModRegistry.STRAW, "Straw");

		addEntityType(ModEntities.CHOCOBO, "Chocobo");

		addItem(ModRegistry.GOLD_GYSAHL, "Golden Gysahl");
		addItem(ModRegistry.GYSAHL_GREEN_ITEM, "Gysahl Green");
		addItem(ModRegistry.GYSAHL_GREEN_SEEDS, "Gysahl Green Seeds");
		addItem(ModRegistry.LOVERLY_GYSAHL_GREEN, "Loverly Gysahl");
		addItem(ModRegistry.RED_GYSAHL, "Red Gysahl");
		addItem(ModRegistry.PINK_GYSAHL, "Pink Gysahl");
		addItem(ModRegistry.PICKLED_GYSAHL_RAW, "Raw Pickled Gysahl");
		addItem(ModRegistry.PICKLED_GYSAHL_COOKED, "Baked Pickled Gysahl");

		addItem(ModRegistry.CHOCOPEDIA, "Chocopedia");

		addItem(ModRegistry.CHOCOBO_FEATHER, "Chocobo Feather");
		addItem(ModRegistry.CHOCOBO_WHISTLE, "Chocobo Whistle");

		addItem(ModRegistry.CHOCOBO_SADDLE, "Chocobo Saddle");
		addItem(ModRegistry.CHOCOBO_SADDLE_BAGS, "Chocobo Saddle Bags");
		addItem(ModRegistry.CHOCOBO_SADDLE_PACK, "Chocobo Saddle Pack");

		addItem(ModRegistry.CHOCO_DISGUISE_HELMET, "Chocodisguise Helmet");
		addItem(ModRegistry.CHOCO_DISGUISE_CHESTPLATE, "Chocodisguise Body");
		addItem(ModRegistry.CHOCO_DISGUISE_LEGGINGS, "Chocodisguise Legs");
		addItem(ModRegistry.CHOCO_DISGUISE_BOOTS, "Chocodisguise Boots");

		addItem(ModRegistry.GYSAHL_CAKE, "Gysahl Cake");

		addItem(ModRegistry.CHOCOBO_DRUMSTICK_RAW, "Raw Chocobo Drumstick");
		addItem(ModRegistry.CHOCOBO_DRUMSTICK_COOKED, "Cooked Chocobo Drumstick");
		addItem(ModRegistry.YELLOW_CHOCOBO_SPAWN_EGG, "Yellow Chocobo Spawn Egg");
		addItem(ModRegistry.GREEN_CHOCOBO_SPAWN_EGG, "Green Chocobo Spawn Egg");
		addItem(ModRegistry.BLUE_CHOCOBO_SPAWN_EGG, "Blue Chocobo Spawn Egg");
		addItem(ModRegistry.WHITE_CHOCOBO_SPAWN_EGG, "White Chocobo Spawn Egg");
		addItem(ModRegistry.BLACK_CHOCOBO_SPAWN_EGG, "Black Chocobo Spawn Egg");
		addItem(ModRegistry.GOLD_CHOCOBO_SPAWN_EGG, "Golden Chocobo Spawn Egg");
		addItem(ModRegistry.PINK_CHOCOBO_SPAWN_EGG, "Pink Chocobo Spawn Egg");
		addItem(ModRegistry.RED_CHOCOBO_SPAWN_EGG, "Red Chocobo Spawn Egg");
		addItem(ModRegistry.PURPLE_CHOCOBO_SPAWN_EGG, "Purple Chocobo Spawn Egg");
		addItem(ModRegistry.FLAME_CHOCOBO_SPAWN_EGG, "Flame Chocobo Spawn Egg");

		add("chococraft.entity_chocobo.heal_fail", "§cHealth full");
		add("chococraft.entity_chocobo.tame_fail", "§cTame failed");
		add("chococraft.entity_chocobo.tame_success", "§aTame succeeded");
		add("chococraft.entity_chocobo.chocobo_followcmd", "Follow");
		add("chococraft.entity_chocobo.chocobo_wandercmd", "Wander");
		add("chococraft.entity_chocobo.chocobo_staycmd", "Stay");
		add("chococraft.entity_chocobo.not_owner", "§cYou are not the owner of this Chocobo!");

		int pages = 1;
		add("gui.chocobook.page" + pages++, "Table of Contents\n\nI. Chocobos\nII. In the wild\nIII. Gysahl Greens\n .1 Gysahl Mutations\n .2 Gysahl Pickles\nIV. Domestication\n .1 Breeding Chocobos\n .2 Chocobo Colours\nV. The Nether\nVI. Chocobo Pen\nVII. Chocobo Cake\n");
		add("gui.chocobook.page" + pages++, "I. Chocobos\n\nThese large, herbiv- orous birds are largely unable to fly, but their powerful legs allow them to run at high speeds. Their size and impressive speed make them a popular choice when looking for a mount.\n");
		add("gui.chocobook.page" + pages++, "Although largely too small to allow real flight, the Chocobo's wings enable the birds to glide and avoid fall damage to itself and its rider up to a cer- tain hight.\n\nDomesticated Chocobos can be deployed as mounts and transport animals.");
		add("gui.chocobook.page" + pages++, "There are even re- ports of desperate adventurers having to relyto their Chocobos as a source of food.");
		add("gui.chocobook.page" + pages++, "II. In the wild\n\nChocobos, in their yellow variety, found wild in most parts of the world. Flocks of mostly three, some- times five animals roam free in the wild. How- ever they are very scarce and an inter- ested ornithologist would have to search");
		add("gui.chocobook.page" + pages++, "wide areas to find a flock.");
		add("gui.chocobook.page" + pages++, "III. Gysahl Greens\n\nThe Chocobo's fa- vorite food are Gysahl Greens, these thick red root vegetables can be found growing widespread in all fertile areas. These plants can be crafted into seeds. The seeds then can be used to domestically grow");
		add("gui.chocobook.page" + pages++, "Gysahl Greens on tilted and moisturised land.");
		add("gui.chocobook.page" + pages++, "III.1 Gysahl Mutations\n\nWhen domestically growing Gysahl Greens, there is a chance of the root mutating into a Loverly or even Golden Gysahl. These special roots can be feed to two Chocobos of opposite gender, to initialise the mating process.");
		add("gui.chocobook.page" + pages++, "III.2 Gysahl Pickles\n\nGysahl Greens and sugar can be crafted into raw Gysahl pickles, which can be cooked in a furnace to receive Gysahl pickles. These treats are not only tasty, but also very nourishing.");
		add("gui.chocobook.page" + pages++, "IV. Domestication\n\nChocobos can be tamed by feeding them Gysahl Greens, the birds are very fond of the root vegetable and will sometimes form a bond to whomever is feeding them their fa- vorite food item. If tamed, the owner can fit either a chocobo");
		add("gui.chocobook.page" + pages++, "saddle or pack bags on a Chocobo. Fitted with a saddle the Chocobo can be additionally equipped with saddle bags. (" + ChatFormatting.ITALIC + "Pack bags and saddle bags can be accessed by shift-right click" + ChatFormatting.RESET + ").");
		add("gui.chocobook.page" + pages++, "IV.1 Chocobo Breeding\n\nIf feed with Loverly or Golden Gysahls, Chocobos of different gender can be breed to produce an off- spring. These infant Chocobos are called Chicobos and even- tually grow into a Chocobo. Besides in- creasing the number");
		add("gui.chocobook.page" + pages++, "of Chocobos in a domesticated flock, breeding is the only way to obtain the different varieties of Chocobos available. There is a slight chance of mutation, which can be in- creased with the use of Golden Gysahls. The following combinations of parents can cause");
		add("gui.chocobook.page" + pages++, "their offspring to mutate into these subspecies:\n\n 1. " + ChatFormatting.GOLD + "Yellow" + ChatFormatting.BLACK + " + " + ChatFormatting.GOLD + "Yellow" + ChatFormatting.BLACK + "\n = " + ChatFormatting.GREEN + "Green" + ChatFormatting.BLACK + " or " + ChatFormatting.BLUE + "Blue" + ChatFormatting.BLACK + "\n\n 2. " + ChatFormatting.GREEN + "Green" + ChatFormatting.BLACK + " + " + ChatFormatting.BLUE + "Blue" + ChatFormatting.BLACK + "\n = " + ChatFormatting.GRAY + "White" + ChatFormatting.BLACK + "\n\n 3. " + ChatFormatting.GOLD + "Yellow" + ChatFormatting.BLACK + " + " + ChatFormatting.GRAY + "White" + ChatFormatting.BLACK + "\n = " + "Black");
		add("gui.chocobook.page" + pages++, "4. Black + " + ChatFormatting.GRAY + "White" + ChatFormatting.BLACK + "\n = " + ChatFormatting.GOLD + "Gold" + ChatFormatting.BLACK + "\n\nTo enable the off- spring to mutate into a golden " + "Chocobo, the use of Golden Gysahls is mandatory.");
		add("gui.chocobook.page" + pages++, "IV.2 Chocobo Colours\n\n" + ChatFormatting.GOLD + "Yellow Chocobos" + ChatFormatting.BLACK + " are the average variety and have no " + "clear abilities, they can be used however as fast riding and transport animals." + "\n\n" + ChatFormatting.GREEN + "Green Chocobos" + ChatFormatting.BLACK + " have the ability to climb, which means they do");
		add("gui.chocobook.page" + pages++, "not have the need to jump when ascending a block level. They are slightly faster than their yellow cousins.\n\n" + ChatFormatting.BLUE + "Blue Chocobos" + ChatFormatting.BLACK + " have the ability to travel fast on water. " + "Al- though all Chocobos have the ability to swim, the blue ones can do it very " + "fast,");
		add("gui.chocobook.page" + pages++, "thus are the first choice of transpor- tation when traveling by water route.\n\n" + ChatFormatting.GRAY + "White Chocobos" + ChatFormatting.BLACK + " have the combined abilities to climb like the green Chocobos " + "and to travel fast on water like the blue. In ad- dition they are slightly faster " + "than the blue");
		add("gui.chocobook.page" + pages++, "or green varieties.\n\nBlack Chocobos have the abilities to jump very hight, climb and travel fast on water. Also they are faster than the white ones.\n\n" + ChatFormatting.GOLD + "Golden Chocobos" + ChatFormatting.BLACK + " are the fastes variety and are not " + "only able to glide like the other");
		add("gui.chocobook.page" + pages++, "subspecies, but can fully fly.\n\nAll Chocobo sub- species can glide and avoid fall damage up to a certain hight. Golden Chocobos don't take fall damage at all.");
		add("gui.chocobook.page" + pages++, "V. The Nether\n\nThe Nether is popu- lated by a special breed of Chocobo. The Purple Chocobos roaming the hostile tunnels and caves of the nether in search of the scarce avail- able food. This makes them even harder to find than their yellow");
		add("gui.chocobook.page" + pages++, "cousins. Living in the nether had the purple Chocobos develop the ability to swim in lava and fly.");
		add("gui.chocobook.page" + pages++, "VI. Chocobo Pen\n\nAn area covered with straw and equipped with a water filled cauldron will count as a Chocobo pen. If a hurt Chocobo will stand on the straw near the cauldron it will automatically heal over time.");
		add("gui.chocobook.page" + pages++, "VII. Chocobo Cake\n\nChocobo Cakes can be used to have Chicobos instantly grow up into Chocobos.\n\nRecipe:\nTake an egg, mix it with two units of sugar, two buckets of milk and two bundles of grain. Finally garnish it with two Gysahl Greens.\nEnjoy.");

		addSubtitle(ModSounds.AMBIENT_SOUND.get(), "Chocobo Kwehs");
		addSubtitle(ModSounds.WHISTLE_SOUND_FOLLOW.get(), "Whistles to follow");
		addSubtitle(ModSounds.WHISTLE_SOUND_WANDER.get(), "Whistles to wander");
		addSubtitle(ModSounds.WHISTLE_SOUND_STAY.get(), "Whistles to stay");

		//Patchouli
		add("info.chocopedia.book.name", "Chocopedia");
		add("info.chococraft.book.subtitle", "Chococraft Guide");
		add("info.chococraft.book.landing", "The chocopedia is your guide to Chococraft 4. $(br2)It contains information on all of the different abilities and items that you can use in Chococraft 4.");

		add("info.chococraft.book.chocobos.name", "Chocobos");
		add("info.chococraft.book.chocobos.desc", "These entries contain information about $(l:chococraft:info/chocobo)Chocobos$()");

		add("info.chococraft.book.gysahls.name", "Gysahls");
		add("info.chococraft.book.gysahls.desc", "These entries contain information about Gysahls");

		add("info.chococraft.book.equipments.name", "Tools & Equipments");
		add("info.chococraft.book.equipments.desc", "These entries contain information about the Tools & Equipments of ChocoCraft 4");

		add("info.chococraft.book.misc.name", "Misc");
		add("info.chococraft.book.misc.desc", "These entries contain information about other stuff from ChocoCraft 4.");

		add("info.chococraft.book.legacy.name", "Legacy");
		add("info.chococraft.book.legacy.desc", "These are the original entries that can be found in the original Chocopedia Book.");
		add("info.chococraft.book.legacy.entry.name", "Legacy Info");


		add("info.chococraft.book.chocobo.entry.name", "Chocobo");
		add("info.chococraft.book.chocobo.text", "Chocobos are passive mobs but scared of players so be careful around them.");
		add("info.chococraft.book.chocobo.text2", "Yellow Chocobos spawn in Plains and Hills type biomes. Flame Chocobos spawn in The Nether. You however can get different color Chocobos by breeding them with $(l:chococraft:info/loverly_gysahl)Loverly$() (20%% Chance of new color with new and better abilities) or $(l:chococraft:info/gold_gysahl)Golden Gysahls$() (50%% Chance). $(l:chococraft:info/gold_gysahl)Golden Gysahls$() are REQUIRED to get a Gold Chocobo from breeding.");
		add("info.chococraft.book.chocobo.text3", "A group of Chocobos that have spawned in a Plains Biome near a mountain.");
		add("info.chococraft.book.chocobo.text4", "You breed Chocobos by feeding them Gysahl. All Chocobos can be bred together. But only males and females can breed. You breed a chocobo by feeding the Chocobos either Golden or Loverly Gysahls.");
		add("info.chococraft.book.chocobo.green.description", "Obtainable by breeding two Yellow Chocobos.");
		add("info.chococraft.book.chocobo.green.description2", "Can walk up two block high walls.");
		add("info.chococraft.book.chocobo.blue.description", "Obtainable by breeding two Yellow Chocobos.");
		add("info.chococraft.book.chocobo.blue.description2", "Travel twice as fast as regular Chocobos on water and give Water Breathing.");
		add("info.chococraft.book.chocobo.white.description", "Obtainable by breeding Green and Blue Chocobos.");
		add("info.chococraft.book.chocobo.white.description2", "Travel faster than Yellow, Green or Blue Chocobos and are able to swim and climb.");
		add("info.chococraft.book.chocobo.black.description", "Obtainable by breeding White and Yellow Chocobos.");
		add("info.chococraft.book.chocobo.black.description2", "Travel faster than the White Chocobo in addition to being able to jump higher and giving you Night Vision.");
		add("info.chococraft.book.chocobo.gold.description", "Obtainable by breeding White and Black Chocobos. Breeding a Gold Chocobo with another Gold Chocobo with Golden Gysahls will always result in a gold offspring.");
		add("info.chococraft.book.chocobo.gold.description2", "Are able to fly, climb, swimg and are immune to fall damage. They're tied for fastest with the Pink and Red Chocobos.");
		add("info.chococraft.book.chocobo.purple.description", "(Currently unobtainable).");
		add("info.chococraft.book.chocobo.purple.description2", "Have the same abilities of the Flame Chocobo.");
		add("info.chococraft.book.chocobo.flame.description", "They can only be obtained by finding them in The Nether.");
		add("info.chococraft.book.chocobo.flame.description2", "Have the same abilities of the Gold Chocobo (Except being a bit slower).");
		add("info.chococraft.book.chocobo.red.description", "They can only be obtained by feeding Gold Chocobos a Red Gysahl.");
		add("info.chococraft.book.chocobo.red.description2", "Have the same abilities of the Gold Chocobo.");
		add("info.chococraft.book.chocobo.pink.description", "They can only be obtained by feeding Gold Chocobos a Pink Gysahl.");
		add("info.chococraft.book.chocobo.pink.description2", "Have the same abilities of the Gold Chocobo.");

		add("info.chococraft.book.chicobo.entry.name", "Chicobo");
		add("info.chococraft.book.chicobo.text1", "Chicobos are the baby versions of $(l:chococraft:info/chocobo)Chocobos$() that you get from breeding them.$(br)" +
				"Can be instantly grown by feeding it $(l:chococraft:info/gysahl_cake)Gysahl Cake$() after being tamed.");

		add("info.chococraft.book.loverly_gysahl.entry.name", "Loverly Gysahl");
		add("info.chococraft.book.loverly_gysahl.text1", "$(bold)Loverly Gysahl$() is a mutation of the garden-variety $(l:chococraft:info/gysahl_green)Gysahl Green$() and is used for breeding $(l:chococraft:info/chocobo)Chocobos$().$(br)" +
				"$(bold)Loverly Gysahl$() is obtainable by harvesting $(l:chococraft:info/gysahl_green)Gysahl Green$(), there's a 15%% to get a $(bold)Loverly Gysahl$()");

		add("info.chococraft.book.gold_gysahl.entry.name", "Gold Gysahl");
		add("info.chococraft.book.gold_gysahl.text1", "$(bold)Gold Gysahl$() is a mutation of the garden-variety $(l:chococraft:info/gysahl_green)Gysahl Green$() and is used for breeding $(l:chococraft:info/chocobo)Chocobos$().$(br)" +
				"$(bold)Gold Gysahl$() is obtainable by harvesting $(l:chococraft:info/gysahl_green)Gysahl Green$(), there's a 5%% to get a $(bold)Gold Gysahl$()$(br)" +
				"Mutated variants of $(l:chococraft:info/gysahl_green)Gysahl Green$() can not be crafted into seeds.");

		add("info.chococraft.book.red_gysahl.entry.name", "Red Gysahl");
		add("info.chococraft.book.red_gysahl.text1", "$(bold)Red Gysahl$() is a mutation of the garden-variety $(l:chococraft:info/gysahl_green)Gysahl Green$() and is to turn a $(l:chococraft:info/chocobo#gold)Gold Chocobo$() into a $(l:chococraft:info/chocobo#red)Red Chocobo$().");

		add("info.chococraft.book.pink_gysahl.entry.name", "Pink Gysahl");
		add("info.chococraft.book.pink_gysahl.text1", "$(bold)Pink Gysahl$() is a mutation of the garden-variety $(l:chococraft:info/gysahl_green)Gysahl Green$() and is to turn a $(l:chococraft:info/chocobo#gold)Gold Chocobo$() into a $(l:chococraft:info/chocobo#pink)Pink Chocobo$().");

		add("info.chococraft.book.gysahl_green.entry.name", "Gysahl Green");
		add("info.chococraft.book.gysahl_green.text1", "Gysahl can be found throughout the Overworld in small patches. Gysahl can be used for taming and healing $(l:chococraft:info/chocobo)Chocobos$().$(br)" +
				"To have a chance of taming a $(l:chococraft:info/chocobo)Chocobos$() you have to hand feed it a Gysahl. Once tamed it'll display a collar similar to tamed wolves.");
		add("info.chococraft.book.gysahl_green.text2", "A patch of Gysahl Green that generated in a Plains biome.");
		add("info.chococraft.book.gysahl_green.text3", "Gysahl Green can be farmed by planting Gyshal Green Seeds. After harvesting wild Gysahl the player can craft it into Gysahl Green Seeds.");
		add("info.chococraft.book.gysahl_green.text4", "The seeds can be planted on Farmland like most other crops can. When the crop matures and is harvested there's a 15%% to get a $(l:chococraft:info/loverly_gysahl)Loverly Gysahl$() and a a 5%% to get a $(l:chococraft:info/gold_gysahl)Gold Gysahl$().");

		add("info.chococraft.book.pickled_gysahl.entry.name", "Raw Pickled Gysahl");
		add("info.chococraft.book.pickled_gysahl.text1", "Raw Pickled Gysahl is a food item that can be crafted from $(l:chococraft:info/gysahl_green)Gysahl Green$().$(br)" +
				"The raw version can not be eaten, you have to cook it in order for it to be edible.");

		add("info.chococraft.book.raw_drumstick.entry.name", "Chocobo Drumstick");
		add("info.chococraft.book.raw_drumstick.text1", "Raw Chocobo Drumstick is a food item that can be obtained by killing $(l:chococraft:info/chocobo)Chocobos$().");

		add("info.chococraft.book.gysahl_cake.entry.name", "Gysahl Cake");
		add("info.chococraft.book.gysahl_cake.text1", "Gysahl Cakes are used to instantly age a $(l:chococraft:info/chicobo)Chicobo$() to a $(l:chococraft:info/chocobo)Chocobo$().$(br)" +
				"You must first tame the $(l:chococraft:info/chicobo)Chicobo$() to feed it a Gysahl Cake. It can NOT be placed.");

		add("info.chococraft.book.chocodisguise.entry.name", "Choco Disguise");
		add("info.chococraft.book.chocodisguise.text1", "Choco Disguise is a set of armor that can be worn to reduce the risk of wild $(l:chococraft:info/chocobo)Chocobos$() running from you when approaching.");

		add("info.chococraft.book.saddle.entry.name", "Chocobo Saddle");
		add("info.chococraft.book.saddle.text1", "Chocobo Saddle's allow you to ride your tamed $(l:chococraft:info/chocobo)Chocobo$().$(br)" +
				"There's different types of saddles, the basic variant just allows you to ride the chocobo while others also grant storage space onto the $(l:chococraft:info/chocobo)Chocobo$().");
		add("info.chococraft.book.saddle.text2", "The $(bold)Chocobo Saddle Bags$() gives you acces to 18 inventory slots.$(br)" +
				"The $(bold)Chocobo Saddle Pack$() gives you access to 45 inventory slots.");

		add("info.chococraft.book.straw.entry.name", "Straw");
		add("info.chococraft.book.straw.text1", "Straw blocks can be placed in your $(l:chococraft:info/chocobo)Chocobo$() pen to allow your $(l:chococraft:info/chocobo)Chocobo$() to heal naturally when inside the pen.");

		add("info.chococraft.book.feather.entry.name", "Chocobo Feather");
		add("info.chococraft.book.feather.text1", "$(l:chococraft:info/chocobo)Chocobos$() every so often shed feathers. They are useful for crafting a $(l:chococraft:info/chocodisguise)Choco Disguise$() armor set.");

		add("info.chococraft.book.whistle.entry.name", "Chocobo Whistle");
		add("info.chococraft.book.whistle.text1", "The Chocobo Whistle allows you to command your tamed $(l:chococraft:info/chocobo)Chocobos$() by right-clicking them with it. $(br2)There are three commands that you can do, $(o)Wander$(), $(o)Follow$(), and $(o)Stay$().");

		//ClothConfig (fabric)
		add("text.autoconfig.chococraft.option.spawning", "Spawning");
		add("text.autoconfig.chococraft.option.spawning.chocoboSpawnWeight", "Chocobo Spawn Weight");
		add("text.autoconfig.chococraft.option.spawning.chocoboPackSizeMin", "Chocobo Pack Max Size");
		add("text.autoconfig.chococraft.option.spawning.chocoboPackSizeMax", "Chocobo Pack Min Size");
		add("text.autoconfig.chococraft.option.chocobo", "Chocobo");
		add("text.autoconfig.chococraft.option.chocobo.tameChance", "Tame Chance");
		add("text.autoconfig.chococraft.option.naming", "Naming");
		add("text.autoconfig.chococraft.option.naming.nameTamedChocobos", "Name Tamed Chocobos");
		add("text.autoconfig.chococraft.option.naming.maleNames", "Male Chocobo Names");
		add("text.autoconfig.chococraft.option.naming.femaleNames", "Female Chocobo Names");
	}

	public void addSubtitle(SoundEvent sound, String name) {
		String path = Chococraft.MOD_ID + ".subtitle." + sound.getLocation().getPath();
		this.add(path, name);
	}
}
