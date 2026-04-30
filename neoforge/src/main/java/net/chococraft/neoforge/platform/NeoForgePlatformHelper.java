package net.chococraft.neoforge.platform;

import com.google.gson.internal.LinkedTreeMap;
import net.chococraft.common.config.BreedingConfig;
import net.chococraft.common.config.ChocoConfig;
import net.chococraft.common.entity.AbstractChocobo;
import net.chococraft.common.entity.properties.ChocoboColor;
import net.chococraft.common.entity.properties.MovementType;
import net.chococraft.common.inventory.SaddleBagMenu;
import net.chococraft.common.items.armor.AbstractChocoDisguiseItem;
import net.chococraft.neoforge.common.entity.NeoForgeChocobo;
import net.chococraft.neoforge.common.inventory.NeoForgeSaddleBagMenu;
import net.chococraft.neoforge.common.items.NeoForgeChocoDisguiseItem;
import net.chococraft.neoforge.registry.ModDataSerializers;
import net.chococraft.platform.services.IPlatformHelper;
import net.chococraft.registry.ModRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;

import java.nio.file.Path;
import java.util.List;

public class NeoForgePlatformHelper implements IPlatformHelper {

	@Override
	public CreativeModeTab buildCreativeTab() {
		return CreativeModeTab.builder()
				.icon(() -> new ItemStack(ModRegistry.GYSAHL_GREEN.get()))
				.withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
				.title(Component.translatable("itemGroup.chococraft.tab"))
				.displayItems((displayParameters, output) -> {
					List<ItemStack> stacks = ModRegistry.ITEMS.getEntries().stream().map(reg -> new ItemStack(reg.get())).toList();
					output.acceptAll(stacks);
				}).build();
	}

	@Override
	public Path getConfigDirectory() {
		return FMLPaths.CONFIGDIR.get();
	}

	@Override
	public boolean isModLoaded(String modID) {
		return ModList.get().isLoaded(modID);
	}

	@Override
	public EntityType.Builder<? extends AbstractChocobo> constructChocoboEntityType() {
		return EntityType.Builder.of(NeoForgeChocobo::new, MobCategory.CREATURE).sized(1.2f, 2.8f).clientTrackingRange(64);
	}


	@Override
	public MenuType<SaddleBagMenu> createMenuType() {
		return IMenuTypeExtension.create(NeoForgeSaddleBagMenu::create);
	}

	@Override
	public SaddleBagMenu constructMenu(int i, Inventory inventory, AbstractChocobo abstractChocobo) {
		return new NeoForgeSaddleBagMenu(i, inventory, (NeoForgeChocobo) abstractChocobo);
	}

	@Override
	public AbstractChocoDisguiseItem constructChocoDisguise(ArmorMaterial material, ArmorType type, Item.Properties properties) {
		return new NeoForgeChocoDisguiseItem(material, type, properties);
	}

	@Override
	public LinkedTreeMap<String, LinkedTreeMap<String, List<LinkedTreeMap<String, String>>>> getBreedingInfoMap() {
		return BreedingConfig.breedingInfoHashmap;
	}

	@Override
	public float getTameChance() {
		return ChocoConfig.COMMON.tameChance.get().floatValue();
	}

	@Override
	public boolean canChocobosFly() {
		return ChocoConfig.COMMON.canChocobosFly.get();
	}

	@Override
	public int kwehIntervalLimit() {
		return ChocoConfig.COMMON.kwehIntervalLimit.get();
	}

	@Override
	public boolean nameTamedChocobos() {
		return ChocoConfig.COMMON.nameTamedChocobos.get();
	}

	@Override
	public List<? extends String> getConfiguredMaleNames() {
		return ChocoConfig.COMMON.maleNames.get();
	}

	@Override
	public List<? extends String> getConfiguredFemaleNames() {
		return ChocoConfig.COMMON.femaleNames.get();
	}

	@Override
	public EntityDataSerializer<ChocoboColor> getColorSerializer() {
		return ModDataSerializers.CHOCOBO_COLOR.get();
	}

	@Override
	public EntityDataSerializer<MovementType> getMovementSerializer() {
		return ModDataSerializers.MOVEMENT_TYPE.get();
	}
}
