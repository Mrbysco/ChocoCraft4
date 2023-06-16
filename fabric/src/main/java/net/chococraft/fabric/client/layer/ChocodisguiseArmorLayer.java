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
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ChocodisguiseArmorLayer<T extends LivingEntity, M extends HumanoidModel<T>> extends RenderLayer<T, M> {
	private static final ResourceLocation ARMOR_LOCATION = new ResourceLocation(Chococraft.MOD_ID, "textures/models/armor/chocodisguise.png");
	private final Map<ArmorItem.Type, ChocoDisguiseModel> chocoDisguiseMap = new HashMap<>();

	public ChocodisguiseArmorLayer(RenderLayerParent<T, M> renderLayerParent, EntityModelSet modelSet) {
		super(renderLayerParent);
		this.chocoDisguiseMap.put(ArmorItem.Type.CHESTPLATE, new ChocoDisguiseModel(modelSet.bakeLayer(ChococraftClient.CHOCO_DISGUISE), ArmorItem.Type.CHESTPLATE));
		this.chocoDisguiseMap.put(ArmorItem.Type.LEGGINGS, new ChocoDisguiseModel(modelSet.bakeLayer(ChococraftClient.CHOCO_DISGUISE), ArmorItem.Type.LEGGINGS));
		this.chocoDisguiseMap.put(ArmorItem.Type.BOOTS, new ChocoDisguiseModel(modelSet.bakeLayer(ChococraftClient.CHOCO_DISGUISE), ArmorItem.Type.BOOTS));
		this.chocoDisguiseMap.put(ArmorItem.Type.HELMET, new ChocoDisguiseModel(modelSet.bakeLayer(ChococraftClient.CHOCO_DISGUISE), ArmorItem.Type.HELMET));
	}

	public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, T livingEntity, float f, float g, float h, float j, float k, float l) {
		this.renderArmorPiece(poseStack, multiBufferSource, livingEntity, EquipmentSlot.CHEST, i, this.getArmorModel(ArmorItem.Type.CHESTPLATE));
		this.renderArmorPiece(poseStack, multiBufferSource, livingEntity, EquipmentSlot.LEGS, i, this.getArmorModel(ArmorItem.Type.LEGGINGS));
		this.renderArmorPiece(poseStack, multiBufferSource, livingEntity, EquipmentSlot.FEET, i, this.getArmorModel(ArmorItem.Type.BOOTS));
		this.renderArmorPiece(poseStack, multiBufferSource, livingEntity, EquipmentSlot.HEAD, i, this.getArmorModel(ArmorItem.Type.HELMET));
	}

	private void renderArmorPiece(PoseStack poseStack, MultiBufferSource multiBufferSource, T livingEntity, EquipmentSlot slot, int i, ChocoDisguiseModel humanoidModel) {
		if (humanoidModel == null) return;
		ItemStack itemStack = livingEntity.getItemBySlot(slot);
		if (itemStack.getItem() instanceof FabricChocoDisguiseItem) {
			FabricChocoDisguiseItem armorItem = (FabricChocoDisguiseItem) itemStack.getItem();
			((HumanoidModel) this.getParentModel()).copyPropertiesTo(humanoidModel);
			this.setPartVisibility(humanoidModel, convertSlot(slot));
			boolean bl2 = itemStack.hasFoil();
			this.renderModel(poseStack, multiBufferSource, i, armorItem, bl2, humanoidModel, 1.0F, 1.0F, 1.0F, (String) null);
		}
	}

	private ArmorItem.Type convertSlot(EquipmentSlot slot) {
		return switch (slot) {
			case HEAD -> ArmorItem.Type.HELMET;
			case CHEST -> ArmorItem.Type.CHESTPLATE;
			case LEGS -> ArmorItem.Type.LEGGINGS;
			default -> ArmorItem.Type.BOOTS;
		};
	}

	protected void setPartVisibility(ChocoDisguiseModel humanoidModel, ArmorItem.Type equipmentSlot) {
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
							 float f, float g, float h, @Nullable String string) {
		VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(multiBufferSource, RenderType.armorCutoutNoCull(this.getArmorLocation()), false, bl);
		humanoidModel.renderToBuffer(poseStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY, f, g, h, 1.0F);
	}

	private ChocoDisguiseModel getArmorModel(ArmorItem.Type equipmentSlot) {
		return this.chocoDisguiseMap.getOrDefault(equipmentSlot, null);
	}

	private ResourceLocation getArmorLocation() {
		return ARMOR_LOCATION;
	}
}
