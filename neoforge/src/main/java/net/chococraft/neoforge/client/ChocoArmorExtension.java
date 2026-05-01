package net.chococraft.neoforge.client;

import net.chococraft.Chococraft;
import net.chococraft.client.models.armor.ChocoDisguiseModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.Equippable;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class ChocoArmorExtension implements IClientItemExtensions {
	private static final Identifier TEXTURE = Chococraft.modLoc("textures/models/armor/chocodisguise.png");
	private static final ChocoDisguiseModel HEAD_MODEL = new ChocoDisguiseModel(ChocoDisguiseModel.createBodyLayer().bakeRoot(), ArmorType.HELMET);
	private static final ChocoDisguiseModel CHEST_MODEL = new ChocoDisguiseModel(ChocoDisguiseModel.createBodyLayer().bakeRoot(), ArmorType.CHESTPLATE);
	private static final ChocoDisguiseModel LEGGINGS_MODEL = new ChocoDisguiseModel(ChocoDisguiseModel.createBodyLayer().bakeRoot(), ArmorType.LEGGINGS);
	private static final ChocoDisguiseModel FEET_MODEL = new ChocoDisguiseModel(ChocoDisguiseModel.createBodyLayer().bakeRoot(), ArmorType.BOOTS);

	@Override
	public @Nullable Identifier getArmorTexture(@NonNull ItemStack stack, EquipmentClientInfo.@NonNull LayerType type,
	                                            EquipmentClientInfo.@NonNull Layer layer, @NonNull Identifier _default) {
		return TEXTURE;
	}

	@Override
	public @NonNull Model getHumanoidArmorModel(ItemStack itemStack, EquipmentClientInfo.@NonNull LayerType layerType, @NonNull Model original) {
		Equippable equippable = itemStack.get(DataComponents.EQUIPPABLE);
		EquipmentSlot slot = equippable != null ? equippable.slot() : null;

		ChocoDisguiseModel model = switch (slot) {
			case HEAD -> HEAD_MODEL;
			case CHEST -> CHEST_MODEL;
			case LEGS -> LEGGINGS_MODEL;
			case FEET -> FEET_MODEL;
			case null, default -> null;
		};
		if (model == null) {
			return original;
		}
		model.head.visible = slot == EquipmentSlot.HEAD;
		model.body.visible = slot == EquipmentSlot.CHEST;
		model.rightArm.visible = slot == EquipmentSlot.CHEST;
		model.leftArm.visible = slot == EquipmentSlot.CHEST;
		model.rightLeg.visible = slot == EquipmentSlot.LEGS;
		model.leftLeg.visible = slot == EquipmentSlot.LEGS;

		return model;
	}
}