package net.chococraft.fabric.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.chococraft.Chococraft;
import net.chococraft.ChococraftClient;
import net.chococraft.client.models.armor.ChocoDisguiseModel;
import net.chococraft.fabric.common.items.FabricChocoDisguiseItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.ArmorType;

import java.util.HashMap;
import java.util.Map;

public class ChocodisguiseArmorLayer<T extends HumanoidRenderState, M extends HumanoidModel<T>> extends RenderLayer<T, M> {
	private static final ResourceLocation ARMOR_LOCATION = ResourceLocation.fromNamespaceAndPath(Chococraft.MOD_ID, "textures/models/armor/chocodisguise.png");
	private final Map<ArmorType, ChocoDisguiseModel> chocoDisguiseMap = new HashMap<>();

	public ChocodisguiseArmorLayer(RenderLayerParent<T, M> renderLayerParent, EntityModelSet modelSet) {
		super(renderLayerParent);
		this.chocoDisguiseMap.put(ArmorType.CHESTPLATE, new ChocoDisguiseModel(modelSet.bakeLayer(ChococraftClient.CHOCO_DISGUISE), ArmorType.CHESTPLATE));
		this.chocoDisguiseMap.put(ArmorType.LEGGINGS, new ChocoDisguiseModel(modelSet.bakeLayer(ChococraftClient.CHOCO_DISGUISE), ArmorType.LEGGINGS));
		this.chocoDisguiseMap.put(ArmorType.BOOTS, new ChocoDisguiseModel(modelSet.bakeLayer(ChococraftClient.CHOCO_DISGUISE), ArmorType.BOOTS));
		this.chocoDisguiseMap.put(ArmorType.HELMET, new ChocoDisguiseModel(modelSet.bakeLayer(ChococraftClient.CHOCO_DISGUISE), ArmorType.HELMET));
	}

	@Override
	public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, T renderState, float f, float g) {
		this.renderArmorPiece(poseStack, multiBufferSource, renderState, EquipmentSlot.CHEST, i, this.getArmorModel(ArmorType.CHESTPLATE));
		this.renderArmorPiece(poseStack, multiBufferSource, renderState, EquipmentSlot.LEGS, i, this.getArmorModel(ArmorType.LEGGINGS));
		this.renderArmorPiece(poseStack, multiBufferSource, renderState, EquipmentSlot.FEET, i, this.getArmorModel(ArmorType.BOOTS));
		this.renderArmorPiece(poseStack, multiBufferSource, renderState, EquipmentSlot.HEAD, i, this.getArmorModel(ArmorType.HELMET));
	}

	private void renderArmorPiece(PoseStack poseStack, MultiBufferSource multiBufferSource, T renderState, EquipmentSlot slot, int i, ChocoDisguiseModel humanoidModel) {
		if (humanoidModel == null) return;
		ItemStack itemStack = switch(slot) {
			case HEAD -> renderState.headEquipment;
			case CHEST -> renderState.chestEquipment;
			case LEGS -> renderState.legsEquipment;
			default -> renderState.feetEquipment;
		};
		if (itemStack.getItem() instanceof FabricChocoDisguiseItem armorItem) {
			((HumanoidModel) this.getParentModel()).copyPropertiesTo(humanoidModel);
			this.setPartVisibility(humanoidModel, convertSlot(slot));
			boolean bl2 = itemStack.hasFoil();
			this.renderModel(poseStack, multiBufferSource, i, armorItem, bl2, humanoidModel, -1);
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

	private void renderModel(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, ArmorItem armorItem, boolean bl, ChocoDisguiseModel humanoidModel,
							 int color) {
		VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(multiBufferSource, RenderType.armorCutoutNoCull(this.getArmorLocation()), bl);
		humanoidModel.renderToBuffer(poseStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY, color);
	}

	private ChocoDisguiseModel getArmorModel(ArmorType equipmentSlot) {
		return this.chocoDisguiseMap.getOrDefault(equipmentSlot, null);
	}

	private ResourceLocation getArmorLocation() {
		return ARMOR_LOCATION;
	}
}
