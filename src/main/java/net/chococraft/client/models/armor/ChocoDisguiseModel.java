package net.chococraft.client.models.armor;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;

public class ChocoDisguiseModel extends HumanoidModel<LivingEntity> {
	private EquipmentSlot slot;

	private final ModelPart chocobo_head;
	private final ModelPart chocobo_body;
	private final ModelPart chocobo_right_arm;
	private final ModelPart chocobo_left_arm;
	private final ModelPart chocobo_leg_right;
	private final ModelPart chocobo_leg_left;
	private final ModelPart chocobo_claw_right;
	private final ModelPart chocobo_claw_left;

	public ChocoDisguiseModel(ModelPart root, EquipmentSlot slot) {
		super(root);
		this.slot = slot;

		chocobo_head = root.getChild("chocobo_head");
		chocobo_body = root.getChild("chocobo_body");
		chocobo_right_arm = root.getChild("chocobo_right_arm");
		chocobo_left_arm = root.getChild("chocobo_left_arm");
		chocobo_leg_right = root.getChild("chocobo_leg_right");
		chocobo_leg_left = root.getChild("chocobo_leg_left");
		chocobo_claw_right = root.getChild("chocobo_claw_right");
		chocobo_claw_left = root.getChild("chocobo_claw_left");
	}

	public static LayerDefinition createArmorDefinition() {
		MeshDefinition meshdefinition = HumanoidModel.createMesh(CubeDeformation.NONE, 1.0F);
		PartDefinition partdefinition = meshdefinition.getRoot();

		//Head
		PartDefinition chocobo_head = partdefinition.addOrReplaceChild("chocobo_head",
				CubeListBuilder.create()
						.texOffs(0, 18).addBox(-4.5F, -8.5F, -4.5F, 9.0F, 9.0F, 9.0F)
				, PartPose.ZERO);

		PartDefinition the_head = chocobo_head.addOrReplaceChild("the_head",
				CubeListBuilder.create()
						.texOffs(0, 0).addBox(-3.2063F, -3.8667F, -9.081F, 6.0F, 6.0F, 12.0F)
						.texOffs(36, 12).addBox(2.0F, -3.0F, -3.0F, 1.0F, 3.0F, 3.0F)
						.texOffs(36, 12).addBox(-3.5F, -3.0F, -3.0F, 1.0F, 3.0F, 3.0F).mirror()
				, PartPose.offsetAndRotation(0.2063F, -10.1333F, -0.919F, 0.2618F, 0.0F, 0.0F));

		the_head.addOrReplaceChild("crest_right",
				CubeListBuilder.create()
						.texOffs(1, 0).addBox(-0.5F, -4.5F, 0.0F, 1.0F, 7.0F, 4.0F).mirror()
				, PartPose.offsetAndRotation(-2.7063F, -0.3667F, 2.919F, 0.2094F, -0.384F, 0.2094F));

		the_head.addOrReplaceChild("crest_top",
				CubeListBuilder.create()
						.texOffs(25, 0).addBox(-3.0F, -0.5F, 0.0F, 5.0F, 1.0F, 6.0F)
				, PartPose.offsetAndRotation(0.2937F, -2.8667F, 2.919F, 0.4538F, 0.0F, 0.0F));

		the_head.addOrReplaceChild("crest_left",
				CubeListBuilder.create()
						.texOffs(1, 0).addBox(-0.5F, -4.5F, 0.0F, 1.0F, 7.0F, 4.0F)
				, PartPose.offsetAndRotation(2.2937F, -0.3667F, 2.919F, 0.2094F, 0.384F, -0.2094F));

		//Body
		PartDefinition chocobo_body = partdefinition.addOrReplaceChild("chocobo_body",
				CubeListBuilder.create()
						.texOffs(36, 18).addBox(-4.5F, -0.5F, -2.5F, 9.0F, 13.0F, 5.0F)
				, PartPose.ZERO);

		chocobo_body.addOrReplaceChild("feathers_middle",
				CubeListBuilder.create()
						.texOffs(102, 93).addBox(-3.5F, 0.5F, -0.5F, 7.0F, 1.0F, 6.0F)
				, PartPose.offsetAndRotation(-0.5F, 8.0F, 2.5F, 0.3491F, 0.0F, 0.0F));

		chocobo_body.addOrReplaceChild("feathers_left",
				CubeListBuilder.create()
						.texOffs(44, 92).addBox(1.5F, -8.5F, -0.5F, 1.0F, 14.0F, 9.0F)
				, PartPose.offsetAndRotation(-0.5F, 8.0F, 2.5F, -0.2094F, 0.384F, 0.3491F));

		chocobo_body.addOrReplaceChild("feathers_right",
				CubeListBuilder.create()
						.texOffs(44, 92).addBox(1.5F, -8.5F, -0.5F, 1.0F, 14.0F, 9.0F)
				, PartPose.offsetAndRotation(-0.5F, 8.0F, 2.5F, -0.2094F, -0.384F, -0.3491F));

		//Right arm
		partdefinition.addOrReplaceChild("chocobo_right_arm",
				CubeListBuilder.create()
						.texOffs(0, 36).addBox(-3.5F, -3.0F, -2.5F, 5.0F, 15.0F, 5.0F, new CubeDeformation(-0.25F)).mirror()
				, PartPose.ZERO);

		//Left arm
		partdefinition.addOrReplaceChild("chocobo_left_arm",
				CubeListBuilder.create()
						.texOffs(20, 36).addBox(-1.5F, -2.5F, -2.5F, 5.0F, 15.0F, 5.0F, new CubeDeformation(-0.25F)).mirror()
				, PartPose.ZERO);

		//Right leg
		partdefinition.addOrReplaceChild("chocobo_leg_right",
				CubeListBuilder.create()
						.texOffs(0, 56).addBox(-2.6F, -0.5F, -2.6F, 5.0F, 7.0F, 5.0F, new CubeDeformation(0.125F)).mirror()
				, PartPose.ZERO);

		//Left leg
		partdefinition.addOrReplaceChild("chocobo_leg_left",
				CubeListBuilder.create()
						.texOffs(0, 56).addBox(-2.5F, -0.5F, -2.5F, 5.0F, 7.0F, 5.0F, new CubeDeformation(0.125F)).mirror()
				, PartPose.ZERO);

		//Right Claw
		PartDefinition chocobo_claw_right = partdefinition.addOrReplaceChild("chocobo_claw_right",
				CubeListBuilder.create()
						.texOffs(20, 56).addBox(-2.5F, 6.25F, -2.5F, 5.0F, 6.0F, 5.0F, new CubeDeformation(-0.25F)).mirror()
				, PartPose.ZERO);

		chocobo_claw_right.addOrReplaceChild("claw_right",
				CubeListBuilder.create()
						.texOffs(40, 37).addBox(-1.0F, -1.25F, -6.5F, 2.0F, 2.0F, 7.0F)
				, PartPose.offsetAndRotation(-1.0F, 11.0F, -0.5F, 0.0F, 0.3054F, 0.0F));
		chocobo_claw_right.addOrReplaceChild("claw_left",
				CubeListBuilder.create()
						.texOffs(40, 37).addBox(0.0F, -1.25F, -5.5F, 2.0F, 2.0F, 7.0F)
				, PartPose.offsetAndRotation(-1.0F, 11.0F, -0.5F, 0.0F, -0.1745F, 0.0F));

		//Left Claw
		PartDefinition chocobo_claw_left = partdefinition.addOrReplaceChild("chocobo_claw_left",
				CubeListBuilder.create()
						.texOffs(20, 56).addBox(-2.4F, 6.25F, -2.6F, 5.0F, 6.0F, 5.0F, new CubeDeformation(-0.25F)).mirror()
				, PartPose.ZERO);

		chocobo_claw_left.addOrReplaceChild("claw_right",
				CubeListBuilder.create()
						.texOffs(40, 37).addBox(-1.0F, -1.25F, -6.5F, 2.0F, 2.0F, 7.0F)
				, PartPose.offsetAndRotation(1.0F, 11.0F, -0.5F, 0.0F, -0.3054F, 0.0F));
		chocobo_claw_left.addOrReplaceChild("claw_left",
				CubeListBuilder.create()
						.texOffs(40, 37).addBox(-2.0F, -1.25F, -5.5F, 2.0F, 2.0F, 7.0F)
				, PartPose.offsetAndRotation(1.0F, 11.0F, -0.5F, 0.0F, 0.1745F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		if (livingEntity instanceof ArmorStand armorStand) {
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
	public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		matrixStack.pushPose();

		this.setHeadRotation();
		this.setChestRotation();
		this.setLegsRotation();
		this.setBootRotation();

		chocobo_head.visible = slot == EquipmentSlot.HEAD;
		chocobo_body.visible = slot == EquipmentSlot.CHEST;
		chocobo_right_arm.visible = slot == EquipmentSlot.CHEST;
		chocobo_left_arm.visible = slot == EquipmentSlot.CHEST;
		chocobo_leg_right.visible = slot == EquipmentSlot.LEGS;
		chocobo_leg_left.visible = slot == EquipmentSlot.LEGS;
		chocobo_claw_right.visible = slot == EquipmentSlot.FEET;
		chocobo_claw_left.visible = slot == EquipmentSlot.FEET;
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

	public void setRotation(ModelPart modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}