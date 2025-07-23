package net.chococraft.neoforge.platform;

import com.google.gson.internal.LinkedTreeMap;
import net.chococraft.common.entity.AbstractChocobo;
import net.chococraft.common.entity.properties.ChocoboColor;
import net.chococraft.common.entity.properties.MovementType;
import net.chococraft.common.inventory.SaddleBagMenu;
import net.chococraft.common.items.armor.AbstractChocoDisguiseItem;
import net.chococraft.neoforge.common.config.BreedingConfig;
import net.chococraft.neoforge.common.config.NeoForgeChocoConfig;
import net.chococraft.neoforge.common.entity.NeoForgeChocobo;
import net.chococraft.neoforge.common.inventory.NeoForgeSaddleBagMenu;
import net.chococraft.neoforge.common.items.NeoForgeChocoDisguiseItem;
import net.chococraft.neoforge.registry.ModDataSerializers;
import net.chococraft.platform.services.IPlatformHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLPaths;

import java.nio.file.Path;
import java.util.List;

public class NeoForgePlatformHelper implements IPlatformHelper {
	public Path getConfigDirectory() {
		return FMLPaths.CONFIGDIR.get();
	}

	public boolean isModLoaded(String modID) {
		return ModList.get().isLoaded(modID);
	}

	public EntityType.Builder<? extends AbstractChocobo> constructChocoboEntityType() {
		return EntityType.Builder.of(NeoForgeChocobo::new, MobCategory.CREATURE).sized(1.2f, 2.8f).clientTrackingRange(64);
	}

	public SaddleBagMenu constructMenu(int i, Inventory inventory, FriendlyByteBuf friendlyByteBuf) {
		return NeoForgeSaddleBagMenu.create(i, inventory, friendlyByteBuf);
	}

	public SaddleBagMenu constructMenu(int i, Inventory inventory, AbstractChocobo abstractChocobo) {
		return new NeoForgeSaddleBagMenu(i, inventory, (NeoForgeChocobo) abstractChocobo);
	}

	public AbstractChocoDisguiseItem constructChocoDisguise(ArmorMaterial material, ArmorType type, Item.Properties properties) {
		return new NeoForgeChocoDisguiseItem(material, type, properties);
	}

	public LinkedTreeMap<String, LinkedTreeMap<String, List<LinkedTreeMap<String, String>>>> getBreedingInfoMap() {
		return BreedingConfig.breedingInfoHashmap;
	}

	public float getTameChance() {
		return NeoForgeChocoConfig.COMMON.tameChance.get().floatValue();
	}

	public boolean canChocobosFly() {
		return NeoForgeChocoConfig.COMMON.canChocobosFly.get();
	}

	public int kwehIntervalLimit() {
		return NeoForgeChocoConfig.COMMON.kwehIntervalLimit.get();
	}

	public boolean nameTamedChocobos() {
		return NeoForgeChocoConfig.COMMON.nameTamedChocobos.get();
	}

	public List<? extends String> getConfiguredMaleNames() {
		return NeoForgeChocoConfig.COMMON.maleNames.get();
	}

	public List<? extends String> getConfiguredFemaleNames() {
		return NeoForgeChocoConfig.COMMON.femaleNames.get();
	}

	public EntityDataSerializer<ChocoboColor> getColorSerializer() {
		return ModDataSerializers.CHOCOBO_COLOR.get();
	}

	public EntityDataSerializer<MovementType> getMovementSerializer() {
		return ModDataSerializers.MOVEMENT_TYPE.get();
	}
}
