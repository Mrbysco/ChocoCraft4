package net.chococraft.fabric.platform;

import com.google.gson.internal.LinkedTreeMap;
import net.chococraft.common.entity.AbstractChocobo;
import net.chococraft.common.entity.properties.ChocoboColor;
import net.chococraft.common.entity.properties.MovementType;
import net.chococraft.common.inventory.SaddleBagMenu;
import net.chococraft.common.items.armor.AbstractChocoDisguiseItem;
import net.chococraft.fabric.ChococraftFabric;
import net.chococraft.fabric.common.entity.FabricChocobo;
import net.chococraft.fabric.common.inventory.FabricSaddleBagMenu;
import net.chococraft.fabric.common.items.FabricChocoDisguiseItem;
import net.chococraft.fabric.registry.ModDataSerializers;
import net.chococraft.platform.services.IPlatformHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;

import java.nio.file.Path;
import java.util.List;

public class FabricPlatformHelper implements IPlatformHelper {
	public Path getConfigDirectory() {
		return FabricLoader.getInstance().getConfigDir();
	}

	public boolean isModLoaded(String modID) {
		return FabricLoader.getInstance().isModLoaded(modID);
	}

	public EntityType.Builder<? extends AbstractChocobo> constructChocoboEntityType() {
		return EntityType.Builder.of(FabricChocobo::new, MobCategory.CREATURE).sized(1.2f, 2.8f).clientTrackingRange(64);
	}

	public SaddleBagMenu constructMenu(int i, Inventory inventory, FriendlyByteBuf friendlyByteBuf) {
		return FabricSaddleBagMenu.create(i, inventory, friendlyByteBuf);
	}

	public SaddleBagMenu constructMenu(int i, Inventory inventory, AbstractChocobo abstractChocobo) {
		return new FabricSaddleBagMenu(i, inventory, (FabricChocobo) abstractChocobo);
	}

	public AbstractChocoDisguiseItem constructChocoDisguise(ArmorMaterial material, ArmorType type, Item.Properties properties) {
		return new FabricChocoDisguiseItem(material, type, properties);
	}

	public LinkedTreeMap<String, LinkedTreeMap<String, List<LinkedTreeMap<String, String>>>> getBreedingInfoMap() {
		return ChococraftFabric.breedingConfig.get().breedingInfo;
	}

	public float getTameChance() {
		return (float) ChococraftFabric.config.get().chocobo.tameChance;
	}

	public boolean canChocobosFly() {
		return ChococraftFabric.config.get().chocobo.canChocobosFly;
	}

	public int kwehIntervalLimit() {
		return ChococraftFabric.config.get().chocobo.kwehIntervalLimit;
	}

	public boolean nameTamedChocobos() {
		return ChococraftFabric.config.get().naming.nameTamedChocobos;
	}

	public List<? extends String> getConfiguredMaleNames() {
		return ChococraftFabric.config.get().naming.maleNames;
	}

	public List<? extends String> getConfiguredFemaleNames() {
		return ChococraftFabric.config.get().naming.femaleNames;
	}

	public EntityDataSerializer<ChocoboColor> getColorSerializer() {
		return ModDataSerializers.CHOCOBO_COLOR;
	}

	public EntityDataSerializer<MovementType> getMovementSerializer() {
		return ModDataSerializers.MOVEMENT_TYPE;
	}
}
