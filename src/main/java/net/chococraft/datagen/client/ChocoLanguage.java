package net.chococraft.datagen.client;

import net.chococraft.Chococraft;
import net.chococraft.common.init.ModEntities;
import net.chococraft.common.init.ModRegistry;
import net.chococraft.common.init.ModSounds;
import net.minecraft.data.DataGenerator;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.RegistryObject;

public class ChocoLanguage extends LanguageProvider {
	public ChocoLanguage(DataGenerator gen) {
		super(gen, Chococraft.MODID, "en_us");
	}

	@Override
	protected void addTranslations() {
		add("itemGroup.chococraft", "ChocoCraft 4");
		addBlock(ModRegistry.GYSAHL_GREEN, "Gysahl Green");
		addBlock(ModRegistry.STRAW, "Straw");

		addEntityType(ModEntities.CHOCOBO, "Chocobo");

		addItem(ModRegistry.GOLD_GYSAHL, "Golden Gysahl");
		addItem(ModRegistry.GYSAHL_GREEN_ITEM, "Gysahl Green");
		addItem(ModRegistry.GYSAHL_GREEN_SEEDS, "Gysahl Green Seeds");
		addItem(ModRegistry.LOVERLY_GYSAHL_GREEN, "Loverly Gysahl");
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

		add("gui.chocobook.title", "Chocopedia Page %s");
		add("gui.chocobook.page1", "Chocobos are huge, ostrich like birds found in the plains and hills type biomes, their diet consists of gysahl greens and pretty much nothing else. They are normally found with a yellow color and have two legs with three-toed feet, large wings, and long necks.");
		add("gui.chocobook.page2", "So I guess you're wondering now, how the hell do I tame a Chocobo? Well, that's easy, to tame a Chocobo you must find green gysahls to feed them, which spawn all over the world; Can't get it to love you? Shove some more gysahls in, it'll work!");
		add("gui.chocobook.page3", "So, how do I ride it? Sometimes Chocobos will drop a unique feather, combine this with a normal saddle to make a Chocobo saddle, and right click the Chocobo to put it on. Maybe you can make a better saddle, one with storage or something.");
		add("gui.chocobook.page4", "So you have a plain Chocobo, that's great but you can do better. Using a Chocopedia on a Chocobo will let you unlock new abilities by using the experience that you have, which will drain from your soul. This includes Flying, Gliding, Sprinting, and Diving.");
		add("gui.chocobook.page5", "Neat, so I unlocked an ability, how do I use it? To fly you must sprint to gain momentum then jump, gliding is literally just falling slower, to sprint press the sprint key (immersion broken sadface,) to dive literally just jump in water.");
		add("gui.chocobook.page6", "I have a Chocobo, but I want MOOR! Okay, so there's a mommy and daddy Chocobo, the daddy has colored feathers, get them to boop by feeding both a loverly gysahl which is gained rarely by farming gysahls; Put the egg in a nest, and with enough time and space, it will hatch.");
		add("gui.chocobook.page7", "Help, my Chocobos pooped out a worse Chocobo! That's okay, there's a chance your new baby will be worse in some ways, but most of the time you'll get a faster, healthier, and more energetic Chocobo than the previous generation. Just keep trying!");

		addSubtitle(ModSounds.AMBIENT_SOUND, "Chocobo Kwehs");
		addSubtitle(ModSounds.WHISTLE_SOUND_FOLLOW, "Whistles to follow");
		addSubtitle(ModSounds.WHISTLE_SOUND_WANDER, "Whistles to wander");
		addSubtitle(ModSounds.WHISTLE_SOUND_STAY, "Whistles to stay");

		//Patchouli
		add("info.chocopedia.book.name", "Chocopedia");
		add("info.chococraft.book.subtitle", "Chococraft Guide");
		add("info.chococraft.book.landing", "The chocopedia is your guide to Chococraft 4. It contains information on all of the different abilities and items that you can use in Chococraft 4.");

		add("info.chococraft.book.legacy.name", "Legacy");
		add("info.chococraft.book.legacy.desc", "The original text that can be found in the original Chocopedia.");
		add("info.chococraft.book.legacy.entry.name", "Legacy Info");

		add("info.chococraft.book.info.name", "Info");
		add("info.chococraft.book.info.desc", "These entries contain information about items/blocks/entities depicted in the entries.");

		add("info.chococraft.book.chocobo.entry.name", "Chocobo");
		add("info.chococraft.book.chocobo.text", "Chocobos are passive mobs. If attacked, they will run and try to hide from you.");
		add("info.chococraft.book.chocobo.text2", "Yellow Chocobos spawn in Plains and Hills type biomes. Flame Chocobos spawn in The Nether. You however can get different color Chocobos by breeding them with $(l:chococraft:info/loverly_gysahl)Loverly$() (20%% Chance of new color with new and better abilities) or $(l:chococraft:info/gold_gysahl)Golden Gysahls$() (50%% Chance). $(l:chococraft:info/gold_gysahl)Golden Gysahls$() are REQUIRED to get a Gold Chocobo from breeding.");
		add("info.chococraft.book.chocobo.text3", "There are 10 different colors of Chocobo: Yellow, Blue, Green, White, Black, Gold, Red, Pink, Purple(Unobtainable) and Flame. Information on how to obtain them can be found on the next pages.");
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
		add("info.chococraft.book.chocobo.red.description", "(Currently Unobtainable) Normally they can only be obtained by feeding Gold Chocobos a Red Gysahl.");
		add("info.chococraft.book.chocobo.red.description2", "Have the same abilities of the Gold Chocobo.");
		add("info.chococraft.book.chocobo.pink.description", "(Currently Unobtainable) Normally they can only be obtained by feeding Gold Chocobos a Pink Gysahl.");
		add("info.chococraft.book.chocobo.pink.description2", "Have the same abilities of the Gold Chocobo.");

		add("info.chococraft.book.chicobo.entry.name", "Chicobo");
		add("info.chococraft.book.chicobo.text1", "Chicobos are the baby versions of $(l:chococraft:info/chocobo)Chocobos$() that you get from breeding them.$(br)" +
				"Can be instantly grown by feeding it $(l:chococraft:info/gysahl_cake)Gysahl Cake$() after being tamed.");

		add("info.chococraft.book.loverly_gysahl.entry.name", "Loverly Gysahl");
		add("info.chococraft.book.loverly_gysahl.text1", "$(bold)Loverly Gysahl$() is a mutation of the garden-variety Gysahl Green and is used for breeding $(l:chococraft:info/chocobo)Chocobos$().$(br)" +
				"$(bold)Loverly Gysahl$() is obtainable by harvesting $(l:chococraft:info/gysahl_green)Gysahl Green$(), there's a 15%% to get a $(bold)Loverly Gysahl$()");

		add("info.chococraft.book.gold_gysahl.entry.name", "Gold Gysahl");
		add("info.chococraft.book.gold_gysahl.text1", "$(bold)Gold Gysahl$() is a mutation of the garden-variety Gysahl Green and is used for breeding $(l:chococraft:info/chocobo)Chocobos$().$(br)" +
				"$(bold)Gold Gysahl$() is obtainable by harvesting $(l:chococraft:info/gysahl_green)Gysahl Green$(), there's a 5%% to get a $(bold)Gold Gysahl$()$(br)" +
				"Mutated variants of $(l:chococraft:info/gysahl_green)Gysahl Green$() can not be crafted into seeds.");

		add("info.chococraft.book.gysahl_green.entry.name", "Gysahl Green");
		add("info.chococraft.book.gysahl_green.text1", "Gysahl can be found throughout the Overworld in small patches. Gysahl can be used for taming and healing $(l:chococraft:info/chocobo)Chocobos$()$(br)" +
				"To have a chance of taming a $(l:chococraft:info/chocobo)Chocobos$() you have to hand feed it a Gysahl. Once tamed it'll display a collar similar to tamed wolves.");
		add("info.chococraft.book.gysahl_green.text2", "A patch of Gysahl Green that generated in a Plains biome.");
		add("info.chococraft.book.gysahl_green.text3", "Gysahl Green can be farmed by planting Gyshal Green Seeds. After harvesting wild Gysahl the player can craft it into Gysahl Green Seeds.");
		add("info.chococraft.book.gysahl_green.text4", "The seeds can be planted on Farmland like most other crops can. When the crop matures and is harvested there's a 15%% to get a $(l:chococraft:info/loverly_gysahl)Loverly Gysahl$() and a a 5%% to get a $(l:chococraft:info/gold_gysahl)Gold Gysahl$()");

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

	}

	public void addSubtitle(RegistryObject<SoundEvent> sound, String name) {
		this.addSubtitle(sound.get(), name);
	}

	public void addSubtitle(SoundEvent sound, String name) {
		String path = Chococraft.MODID + ".subtitle." + sound.getLocation().getPath();
		this.add(path, name);
	}
}
