package net.chococraft.forge;

import com.google.gson.internal.LinkedTreeMap;
import net.chococraft.common.entity.AbstractChocobo;
import net.chococraft.common.inventory.SaddleBagMenu;
import net.chococraft.common.items.armor.AbstractChocoDisguiseItem;
import net.chococraft.forge.common.config.BreedingConfig;
import net.chococraft.forge.common.config.ForgeChocoConfig;
import net.chococraft.forge.common.entity.ForgeChocobo;
import net.chococraft.forge.common.inventory.ForgeSaddleBagMenu;
import net.chococraft.forge.common.items.ForgeChocoDisguiseItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;
import java.util.List;

public class ChococraftExpectPlatformImpl {
	public static Path getConfigDirectory() {
		return FMLPaths.CONFIGDIR.get();
	}

	public static boolean isModLoaded(String modID) {
		return ModList.get().isLoaded(modID);
	}

	public static EntityType.Builder<? extends AbstractChocobo> constructChocoboEntityType() {
		return EntityType.Builder.of(ForgeChocobo::new, MobCategory.CREATURE).sized(1.2f, 2.8f).clientTrackingRange(64);
	}

	public static SaddleBagMenu constructMenu(int i, Inventory inventory, FriendlyByteBuf friendlyByteBuf) {
		return ForgeSaddleBagMenu.create(i, inventory, friendlyByteBuf);
	}

	public static SaddleBagMenu constructMenu(int i, Inventory inventory, AbstractChocobo abstractChocobo) {
		return new ForgeSaddleBagMenu(i, inventory, (ForgeChocobo) abstractChocobo);
	}

	public static AbstractChocoDisguiseItem constructChocoDisguise(ArmorMaterial material, EquipmentSlot slot, Item.Properties properties) {
		return new ForgeChocoDisguiseItem(material, slot, properties);
	}

	public static LinkedTreeMap<String, LinkedTreeMap<String, List<LinkedTreeMap<String, String>>>> getBreedingInfoMap() {
		return BreedingConfig.breedingInfoHashmap;
	}

	public static float getTameChance() {
		return ForgeChocoConfig.COMMON.tameChance.get().floatValue();
	}

	public static boolean nameTamedChocobos() {
		return ForgeChocoConfig.COMMON.nameTamedChocobos.get();
	}

	public static List<? extends String> getConfiguredMaleNames() {
		return ForgeChocoConfig.COMMON.maleNames.get();
	}

	public static List<? extends String> getConfiguredFemaleNames() {
		return ForgeChocoConfig.COMMON.femaleNames.get();
	}
}
