package net.chococraft.client.models.armor;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.inventory.EquipmentSlotType;

public class ChocoDisguiseModel extends BipedModel<LivingEntity> {
	private EquipmentSlotType slot;

	private final ModelRenderer chocobo_head;
	private final ModelRenderer chocobo_body;
	private final ModelRenderer chocobo_right_arm;
	private final ModelRenderer chocobo_left_arm;
	private final ModelRenderer chocobo_leg_right;
	private final ModelRenderer chocobo_leg_left;
	private final ModelRenderer chocobo_claw_right;
	private final ModelRenderer chocobo_claw_left;

	public ChocoDisguiseModel(EquipmentSlotType slot) {
		super(0.0F, 1.0f, 128, 128);
		this.slot = slot;
		texWidth = 128;
		texHeight = 128;

		chocobo_head = new ModelRenderer(this);
		chocobo_head.setPos(0.0F, 0.0F, 0.0F);
		chocobo_head.texOffs(0, 18).addBox(-4.5F, -8.5F, -4.5F, 9.0F, 9.0F, 9.0F, 0.0F, false);

		ModelRenderer the_head = new ModelRenderer(this);
		the_head.setPos(0.2063F, -10.1333F, -0.919F);
		chocobo_head.addChild(the_head);
		setRotation(the_head, 0.2618F, 0.0F, 0.0F);
		the_head.texOffs(0, 0).addBox(-3.2063F, -3.8667F, -9.081F, 6.0F, 6.0F, 12.0F, 0.0F, false);
		the_head.texOffs(36, 12).addBox(2.0F, -3.0F, -3.0F, 1.0F, 3.0F, 3.0F, 0.0F, false);
		the_head.texOffs(36, 12).addBox(-3.5F, -3.0F, -3.0F, 1.0F, 3.0F, 3.0F, 0.0F, true);

		ModelRenderer crest_right = new ModelRenderer(this);
		crest_right.setPos(-2.7063F, -0.3667F, 2.919F);
		the_head.addChild(crest_right);
		setRotation(crest_right, 0.2094F, -0.384F, 0.2094F);
		crest_right.texOffs(1, 0).addBox(-0.5F, -4.5F, 0.0F, 1.0F, 7.0F, 4.0F, 0.0F, true);

		ModelRenderer crest_top = new ModelRenderer(this);
		crest_top.setPos(0.2937F, -2.8667F, 2.919F);
		the_head.addChild(crest_top);
		setRotation(crest_top, 0.4538F, 0.0F, 0.0F);
		crest_top.texOffs(25, 0).addBox(-3.0F, -0.5F, 0.0F, 5.0F, 1.0F, 6.0F, 0.0F, false);

		ModelRenderer crest_left = new ModelRenderer(this);
		crest_left.setPos(2.2937F, -0.3667F, 2.919F);
		the_head.addChild(crest_left);
		setRotation(crest_left, 0.2094F, 0.384F, -0.2094F);
		crest_left.texOffs(1, 0).addBox(-0.5F, -4.5F, 0.0F, 1.0F, 7.0F, 4.0F, 0.0F, false);

		chocobo_body = new ModelRenderer(this);
		chocobo_body.setPos(0.0F, 0.0F, 0.0F);
		chocobo_body.texOffs(36, 18).addBox(-4.5F, -0.5F, -2.5F, 9.0F, 13.0F, 5.0F, 0.0F, false);

		ModelRenderer feathers_middle = new ModelRenderer(this);
		feathers_middle.setPos(-0.5F, 8.0F, 2.5F);
		chocobo_body.addChild(feathers_middle);
		setRotation(feathers_middle, 0.3491F, 0.0F, 0.0F);
		feathers_middle.texOffs(102, 93).addBox(-3.5F, 0.5F, -0.5F, 7.0F, 1.0F, 6.0F, 0.0F, false);

		ModelRenderer feathers_left = new ModelRenderer(this);
		feathers_left.setPos(-0.5F, 8.0F, 2.5F);
		chocobo_body.addChild(feathers_left);
		setRotation(feathers_left, -0.2094F, 0.384F, 0.3491F);
		feathers_left.texOffs(44, 92).addBox(1.5F, -8.5F, -0.5F, 1.0F, 14.0F, 9.0F, 0.0F, false);

		ModelRenderer feathers_right = new ModelRenderer(this);
		feathers_right.setPos(-0.5F, 8.0F, 2.5F);
		chocobo_body.addChild(feathers_right);
		setRotation(feathers_right, -0.2094F, -0.384F, -0.3491F);
		feathers_right.texOffs(44, 92).addBox(-2.5F, -8.5F, -0.5F, 1.0F, 14.0F, 9.0F, 0.0F, false);

		chocobo_right_arm = new ModelRenderer(this);
		chocobo_right_arm.setPos(0.0F, 0.0F, 0.0F);
		chocobo_right_arm.texOffs(0, 36).addBox(-3.5F, -3.0F, -2.5F, 5.0F, 15.0F, 5.0F, -0.25F, true);

		chocobo_left_arm = new ModelRenderer(this);
		chocobo_left_arm.setPos(0.0F, 0.0F, 0.0F);
		chocobo_left_arm.texOffs(20, 36).addBox(-1.5F, -2.5F, -2.5F, 5.0F, 15.0F, 5.0F, -0.25F, false);

		chocobo_leg_right = new ModelRenderer(this);
		chocobo_leg_right.setPos(0.0F, 0.0F, 0.0F);
		rightLeg.addChild(chocobo_leg_right);
		chocobo_leg_right.texOffs(0, 56).addBox(-2.6F, -0.5F, -2.6F, 5.0F, 7.0F, 5.0F, 0.125F, true);

		chocobo_claw_right = new ModelRenderer(this);
		chocobo_claw_right.setPos(0.0F, 0.0F, 0.0F);
		rightLeg.addChild(chocobo_claw_right);
		chocobo_claw_right.texOffs(20, 56).addBox(-2.5F, 6.25F, -2.5F, 5.0F, 6.0F, 5.0F, -0.25F, true);

		ModelRenderer chocobo_claw_right_1 = new ModelRenderer(this);
		chocobo_claw_right_1.setPos(0.0F, 0.0F, 0.0F);
		chocobo_claw_right.addChild(chocobo_claw_right_1);


		ModelRenderer chocobo_claw_right_1_cube = new ModelRenderer(this);
		chocobo_claw_right_1_cube.setPos(-1.0F, 11.0F, -0.5F);
		chocobo_claw_right_1.addChild(chocobo_claw_right_1_cube);
		setRotation(chocobo_claw_right_1_cube, 0.0F, 0.3054F, 0.0F);
		chocobo_claw_right_1_cube.texOffs(40, 37).addBox(-1.0F, -1.25F, -6.5F, 2.0F, 2.0F, 7.0F, 0.0F, false);

		ModelRenderer chocobo_claw_right_2 = new ModelRenderer(this);
		chocobo_claw_right_2.setPos(0.0F, 0.0F, 0.0F);
		chocobo_claw_right.addChild(chocobo_claw_right_2);


		ModelRenderer chocobo_claw_right_2_cube = new ModelRenderer(this);
		chocobo_claw_right_2_cube.setPos(-1.0F, 11.0F, -0.5F);
		chocobo_claw_right_2.addChild(chocobo_claw_right_2_cube);
		setRotation(chocobo_claw_right_2_cube, 0.0F, -0.1745F, 0.0F);
		chocobo_claw_right_2_cube.texOffs(40, 37).addBox(0.0F, -1.25F, -5.5F, 2.0F, 2.0F, 7.0F, 0.0F, false);

		leftLeg = new ModelRenderer(this);
		leftLeg.setPos(1.9F, 12.0F, 0.1F);


		chocobo_leg_left = new ModelRenderer(this);
		chocobo_leg_left.setPos(0.0F, 0.0F, 0.0F);
		leftLeg.addChild(chocobo_leg_left);
		chocobo_leg_left.texOffs(0, 56).addBox(-2.5F, -0.5F, -2.5F, 5.0F, 7.0F, 5.0F, 0.125F, true);

		chocobo_claw_left = new ModelRenderer(this);
		chocobo_claw_left.setPos(0.0F, 0.0F, 0.0F);
		leftLeg.addChild(chocobo_claw_left);
		chocobo_claw_left.texOffs(20, 56).addBox(-2.4F, 6.25F, -2.6F, 5.0F, 6.0F, 5.0F, -0.25F, true);

		ModelRenderer chocobo_claw_left_1 = new ModelRenderer(this);
		chocobo_claw_left_1.setPos(0.1F, 6.0F, -0.1F);
		chocobo_claw_left.addChild(chocobo_claw_left_1);


		ModelRenderer chocobo_claw_left_1_cube = new ModelRenderer(this);
		chocobo_claw_left_1_cube.setPos(1.0F, 5.0F, -0.5F);
		chocobo_claw_left_1.addChild(chocobo_claw_left_1_cube);
		setRotation(chocobo_claw_left_1_cube, 0.0F, -0.3054F, 0.0F);
		chocobo_claw_left_1_cube.texOffs(40, 37).addBox(-1.0F, -1.25F, -6.5F, 2.0F, 2.0F, 7.0F, 0.0F, false);

		ModelRenderer chocobo_claw_left_2 = new ModelRenderer(this);
		chocobo_claw_left_2.setPos(0.1F, 6.0F, -0.1F);
		chocobo_claw_left.addChild(chocobo_claw_left_2);


		ModelRenderer chocobo_claw_left_2_cube = new ModelRenderer(this);
		chocobo_claw_left_2_cube.setPos(1.0F, 5.0F, -0.5F);
		chocobo_claw_left_2.addChild(chocobo_claw_left_2_cube);
		setRotation(chocobo_claw_left_2_cube, 0.0F, 0.1745F, 0.0F);
		chocobo_claw_left_2_cube.texOffs(40, 37).addBox(-2.0F, -1.25F, -5.5F, 2.0F, 2.0F, 7.0F, 0.0F, false);
	}

	@Override
	public void setupAnim(LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		if (livingEntity instanceof ArmorStandEntity) {
			ArmorStandEntity armorStand = (ArmorStandEntity) livingEntity;
			this.chocobo_head.xRot = ((float) Math.PI / 180F) * armorStand.getHeadPose().getX();
			this.chocobo_head.yRot = ((float) Math.PI / 180F) * armorStand.getHeadPose().getY();
			this.chocobo_head.zRot = ((float) Math.PI / 180F) * armorStand.getHeadPose().getZ();
			this.chocobo_head.setPos(0.0F, 1.0F, 0.0F);
			this.chocobo_body.xRot = ((float) Math.PI / 180F) * armorStand.getBodyPose().getX();
			this.chocobo_body.yRot = ((float) Math.PI / 180F) * armorStand.getBodyPose().getY();
			this.chocobo_body.zRot = ((float) Math.PI / 180F) * armorStand.getBodyPose().getZ();
			this.chocobo_left_arm.xRot = ((float) Math.PI / 180F) * armorStand.getLeftArmPose().getX();
			this.chocobo_left_arm.yRot = ((float) Math.PI / 180F) * armorStand.getLeftArmPose().getY();
			this.chocobo_left_arm.zRot = ((float) Math.PI / 180F) * armorStand.getLeftArmPose().getZ();
			this.chocobo_right_arm.xRot = ((float) Math.PI / 180F) * armorStand.getRightArmPose().getX();
			this.chocobo_right_arm.yRot = ((float) Math.PI / 180F) * armorStand.getRightArmPose().getY();
			this.chocobo_right_arm.zRot = ((float) Math.PI / 180F) * armorStand.getRightArmPose().getZ();
			this.chocobo_leg_left.xRot = ((float) Math.PI / 180F) * armorStand.getLeftLegPose().getX();
			this.chocobo_leg_left.yRot = ((float) Math.PI / 180F) * armorStand.getLeftLegPose().getY();
			this.chocobo_leg_left.zRot = ((float) Math.PI / 180F) * armorStand.getLeftLegPose().getZ();
			this.chocobo_leg_left.setPos(1.9F, 11.0F, 0.0F);
			this.chocobo_leg_right.xRot = ((float) Math.PI / 180F) * armorStand.getRightLegPose().getX();
			this.chocobo_leg_right.yRot = ((float) Math.PI / 180F) * armorStand.getRightLegPose().getY();
			this.chocobo_leg_right.zRot = ((float) Math.PI / 180F) * armorStand.getRightLegPose().getZ();
			this.chocobo_leg_right.setPos(-1.9F, 11.0F, 0.0F);
			this.hat.copyFrom(this.chocobo_head);
		} else {
			super.setupAnim(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		}
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		matrixStack.pushPose();

		this.setHeadRotation();
		this.setChestRotation();
		this.setLegsRotation();
		this.setBootRotation();

		chocobo_head.visible = slot == EquipmentSlotType.HEAD;
		chocobo_body.visible = slot == EquipmentSlotType.CHEST;
		chocobo_right_arm.visible = slot == EquipmentSlotType.CHEST;
		chocobo_left_arm.visible = slot == EquipmentSlotType.CHEST;
		chocobo_leg_right.visible = slot == EquipmentSlotType.LEGS;
		chocobo_leg_left.visible = slot == EquipmentSlotType.LEGS;
		chocobo_claw_right.visible = slot == EquipmentSlotType.FEET;
		chocobo_claw_left.visible = slot == EquipmentSlotType.FEET;
		if (this.young) {
			float f = 2.0F;
			matrixStack.scale(1.5F / f, 1.5F / f, 1.5F / f);
			matrixStack.translate(0.0F, 16.0F * 1, 0.0F);
			chocobo_head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			matrixStack.popPose();
			matrixStack.pushPose();
			matrixStack.scale(1.0F / f, 1.0F / f, 1.0F / f);
			matrixStack.translate(0.0F, 24.0F * 1, 0.0F);
			chocobo_body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		} else {
			chocobo_head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			chocobo_body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			chocobo_right_arm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			chocobo_left_arm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		}
		chocobo_leg_right.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		chocobo_leg_left.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		chocobo_claw_right.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		chocobo_claw_left.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		matrixStack.popPose();
	}

	public void setHeadRotation() {
		chocobo_head.x = head.x;
		chocobo_head.y = head.y;
		chocobo_head.z = head.z;
		setRotation(chocobo_head, head.xRot, head.yRot, head.zRot);
	}

	public void setChestRotation() {
		/* if (e instanceof EntityPlayer){ ((EntityPlayer)e).get } */
		chocobo_body.x = body.x;
		chocobo_body.y = body.y;
		chocobo_body.z = body.z;
		chocobo_right_arm.x = rightArm.x;
		chocobo_right_arm.y = rightArm.y;
		chocobo_right_arm.z = rightArm.z;
		chocobo_left_arm.x = leftArm.x;
		chocobo_left_arm.y = leftArm.y;
		chocobo_left_arm.z = leftArm.z;
		setRotation(chocobo_body, body.xRot, body.yRot, body.zRot);
		setRotation(chocobo_right_arm, rightArm.xRot, rightArm.yRot, rightArm.zRot);
		setRotation(chocobo_left_arm, leftArm.xRot, leftArm.yRot, leftArm.zRot);
	}

	public void setLegsRotation() {
		chocobo_leg_right.x = rightLeg.x;
		chocobo_leg_right.y = rightLeg.y;
		chocobo_leg_right.z = rightLeg.z;
		chocobo_leg_left.x = leftLeg.x;
		chocobo_leg_left.y = leftLeg.y;
		chocobo_leg_left.z = leftLeg.z;
		setRotation(chocobo_leg_right, rightLeg.xRot, rightLeg.yRot, rightLeg.zRot);
		setRotation(chocobo_leg_left, leftLeg.xRot, leftLeg.yRot, leftLeg.zRot);
	}

	public void setBootRotation() {
		chocobo_claw_right.x = rightLeg.x;
		chocobo_claw_right.y = rightLeg.y;
		chocobo_claw_right.z = rightLeg.z;
		chocobo_claw_left.x = leftLeg.x;
		chocobo_claw_left.y = leftLeg.y;
		chocobo_claw_left.z = leftLeg.z;
		setRotation(chocobo_claw_right, rightLeg.xRot, rightLeg.yRot, rightLeg.zRot);
		setRotation(chocobo_claw_left, leftLeg.xRot, leftLeg.yRot, leftLeg.zRot);
	}

	public void setRotation(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}