package net.chococraft.fabric.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.chococraft.Chococraft;
import net.chococraft.ChococraftClient;
import net.chococraft.client.models.armor.ChocoDisguiseModel;
import net.chococraft.fabric.common.items.FabricChocoDisguiseItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.ArmorType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class ChocodisguiseArmorLayer<T extends HumanoidRenderState, M extends HumanoidModel<T>> extends RenderLayer<T, M> {
	private static final Identifier ARMOR_LOCATION = Chococraft.modLoc("textures/models/armor/chocodisguise.png");
	private final Map<ArmorType, ChocoDisguiseModel> chocoDisguiseMap = new HashMap<>();

	public ChocodisguiseArmorLayer(RenderLayerParent<T, M> renderLayerParent, EntityModelSet modelSet) {
		super(renderLayerParent);
		this.chocoDisguiseMap.put(ArmorType.CHESTPLATE, new ChocoDisguiseModel(modelSet.bakeLayer(ChococraftClient.CHOCO_DISGUISE), ArmorType.CHESTPLATE));
		this.chocoDisguiseMap.put(ArmorType.LEGGINGS, new ChocoDisguiseModel(modelSet.bakeLayer(ChococraftClient.CHOCO_DISGUISE), ArmorType.LEGGINGS));
		this.chocoDisguiseMap.put(ArmorType.BOOTS, new ChocoDisguiseModel(modelSet.bakeLayer(ChococraftClient.CHOCO_DISGUISE), ArmorType.BOOTS));
		this.chocoDisguiseMap.put(ArmorType.HELMET, new ChocoDisguiseModel(modelSet.bakeLayer(ChococraftClient.CHOCO_DISGUISE), ArmorType.HELMET));
	}

	@Override
	public void submit(@NotNull PoseStack poseStack, @NotNull SubmitNodeCollector submitNodeCollector, int packedLight,
	                   @NotNull T renderState, float f, float g) {
		this.renderArmorPiece(poseStack, submitNodeCollector, renderState, EquipmentSlot.CHEST, packedLight, this.getArmorModel(ArmorType.CHESTPLATE));
		this.renderArmorPiece(poseStack, submitNodeCollector, renderState, EquipmentSlot.LEGS, packedLight, this.getArmorModel(ArmorType.LEGGINGS));
		this.renderArmorPiece(poseStack, submitNodeCollector, renderState, EquipmentSlot.FEET, packedLight, this.getArmorModel(ArmorType.BOOTS));
		this.renderArmorPiece(poseStack, submitNodeCollector, renderState, EquipmentSlot.HEAD, packedLight, this.getArmorModel(ArmorType.HELMET));
	}

	private void renderArmorPiece(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, T renderState,
	                              EquipmentSlot slot, int packedLight, ChocoDisguiseModel humanoidModel) {
		if (humanoidModel == null) return;
		ItemStack itemStack = switch (slot) {
			case HEAD -> renderState.headEquipment;
			case CHEST -> renderState.chestEquipment;
			case LEGS -> renderState.legsEquipment;
			default -> renderState.feetEquipment;
		};
		if (itemStack.getItem() instanceof FabricChocoDisguiseItem) {
			humanoidModel.setupAnim(renderState);
			humanoidModel.head.yScale = 1.1F;

			this.setPartVisibility(humanoidModel, convertSlot(slot));
			submitNodeCollector.submitModel(
					humanoidModel,
					renderState,
					poseStack,
					RenderTypes.armorCutoutNoCull(this.getArmorLocation()),
					packedLight,
					OverlayTexture.NO_OVERLAY,
					renderState.outlineColor,
					null);

		}
	}

	private ArmorType convertSlot(EquipmentSlot slot) {
		return switch (slot) {
			case HEAD -> ArmorType.HELMET;
			case CHEST -> ArmorType.CHESTPLATE;
			case LEGS -> ArmorType.LEGGINGS;
			default -> ArmorType.BOOTS;
		};
	}

	protected void setPartVisibility(ChocoDisguiseModel humanoidModel, ArmorType equipmentSlot) {
		humanoidModel.setAllVisible(false);
		switch (equipmentSlot) {
			case HELMET -> {
				humanoidModel.head.visible = true;
				humanoidModel.hat.visible = true;
			}
			case CHESTPLATE -> {
				humanoidModel.body.visible = true;
				humanoidModel.rightArm.visible = true;
				humanoidModel.leftArm.visible = true;
			}
			case LEGGINGS -> {
				humanoidModel.body.visible = true;
				humanoidModel.rightLeg.visible = true;
				humanoidModel.leftLeg.visible = true;
			}
			case BOOTS -> {
				humanoidModel.rightLeg.visible = true;
				humanoidModel.leftLeg.visible = true;
			}
		}

	}

	private ChocoDisguiseModel getArmorModel(ArmorType equipmentSlot) {
		return this.chocoDisguiseMap.getOrDefault(equipmentSlot, null);
	}

	private Identifier getArmorLocation() {
		return ARMOR_LOCATION;
	}
}
