package net.chococraft.neoforge.datagen.client;

import net.chococraft.Chococraft;
import net.chococraft.common.items.armor.ModArmorMaterial;
import net.minecraft.client.data.models.EquipmentAssetProvider;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAsset;

import java.util.function.BiConsumer;

public class ChocoEquipmentAssetProvider extends EquipmentAssetProvider {
  public ChocoEquipmentAssetProvider(PackOutput output) {
    super(output);
  }

  @Override
  protected void registerModels(BiConsumer<ResourceKey<EquipmentAsset>, EquipmentClientInfo> output) {
    output.accept(ModArmorMaterial.CHOCO_DISGUISE.assetId(), onlyHumanoid("chocodisguise"));
  }

  public static EquipmentClientInfo onlyHumanoid(String name) {
    return EquipmentClientInfo.builder()
      .addLayers(EquipmentClientInfo.LayerType.HUMANOID,
        EquipmentClientInfo.Layer.leatherDyeable(Chococraft.modLoc(name), false)
      ).build();
  }
}
