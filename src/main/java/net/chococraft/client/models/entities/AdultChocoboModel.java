package net.chococraft.client.models.entities;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.chococraft.common.entities.ChocoboEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

/**
 * AdultChocoboModel - Kraeheart
 */
public class AdultChocoboModel extends EntityModel<ChocoboEntity> {
	private final ModelRenderer root;
	private final ModelRenderer body;
	private final ModelRenderer saddlebag_right_r1;
	private final ModelRenderer storage_back_r1;
	private final ModelRenderer fan_bottom_r1;
	private final ModelRenderer fan_top_r1;
	private final ModelRenderer fan_right_r1;
	private final ModelRenderer fan_left_r1;
	private final ModelRenderer body_r1;
	private final ModelRenderer wing_left;
	private final ModelRenderer wing_right_r1;
	private final ModelRenderer wing_right;
	private final ModelRenderer wing_right_r2;
	private final ModelRenderer chest;
	private final ModelRenderer chest_r1;
	private final ModelRenderer neck;
	private final ModelRenderer neck_r1;
	private final ModelRenderer head;
	private final ModelRenderer crest_top_r1;
	private final ModelRenderer crest_right_r1;
	private final ModelRenderer crest_left_r1;
	private final ModelRenderer leg_left;
	private final ModelRenderer leg_left_lower_r1;
	private final ModelRenderer leg_left_upper_r1;
	private final ModelRenderer foot_left;
	private final ModelRenderer toe_right_r1;
	private final ModelRenderer toe_left_r1;
	private final ModelRenderer leg_right;
	private final ModelRenderer leg_right_lower_r1;
	private final ModelRenderer leg_right_upper_r1;
	private final ModelRenderer foot_right;
	private final ModelRenderer toe_right_r2;
	private final ModelRenderer toe_left_r2;

	public AdultChocoboModel() {
		texWidth = 128;
		texHeight = 64;

		root = new ModelRenderer(this);
		root.setPos(0.0F, 24.0F, 0.0F);


		body = new ModelRenderer(this);
		body.setPos(0.0F, -22.0F, 1.0F);
		root.addChild(body);


		saddlebag_right_r1 = new ModelRenderer(this);
		saddlebag_right_r1.setPos(0.0F, -3.0F, 6.0F);
		body.addChild(saddlebag_right_r1);
		setRotationAngle(saddlebag_right_r1, -0.0873F, 0.0F, 0.0F);
		saddlebag_right_r1.texOffs(81, 6).addBox(-7.5F, -5.0F, -3.0F, 2.0F, 8.0F, 6.0F, -0.1F, true);
		saddlebag_right_r1.texOffs(81, 6).addBox(5.5F, -5.0F, -3.0F, 2.0F, 8.0F, 6.0F, -0.1F, false);

		storage_back_r1 = new ModelRenderer(this);
		storage_back_r1.setPos(0.0F, -7.0F, 0.0F);
		body.addChild(storage_back_r1);
		setRotationAngle(storage_back_r1, -0.0873F, 0.0F, 0.0F);
		storage_back_r1.texOffs(36, 2).addBox(-5.0F, -6.0F, -3.0F, 10.0F, 6.0F, 12.0F, -0.1F, false);

		fan_bottom_r1 = new ModelRenderer(this);
		fan_bottom_r1.setPos(0.0F, 0.0F, 9.0F);
		body.addChild(fan_bottom_r1);
		setRotationAngle(fan_bottom_r1, -0.9163F, 0.0F, 0.0F);
		fan_bottom_r1.texOffs(101, 29).addBox(-3.5F, -4.5F, 0.0F, 7.0F, 6.0F, 0.0F, 0.0F, false);

		fan_top_r1 = new ModelRenderer(this);
		fan_top_r1.setPos(0.0F, -5.0F, 10.0F);
		body.addChild(fan_top_r1);
		setRotationAngle(fan_top_r1, -0.5672F, 0.0F, 0.0F);
		fan_top_r1.texOffs(55, 25).addBox(-5.5F, -10.0F, -2.0F, 11.0F, 10.0F, 0.0F, 0.0F, false);

		fan_right_r1 = new ModelRenderer(this);
		fan_right_r1.setPos(-2.0F, 0.0F, 9.0F);
		body.addChild(fan_right_r1);
		setRotationAngle(fan_right_r1, -0.1309F, -0.7418F, -0.1309F);
		fan_right_r1.texOffs(45, 28).addBox(-1.0F, -11.0F, -1.0F, 0.0F, 14.0F, 9.0F, 0.0F, false);

		fan_left_r1 = new ModelRenderer(this);
		fan_left_r1.setPos(2.0F, 0.0F, 9.0F);
		body.addChild(fan_left_r1);
		setRotationAngle(fan_left_r1, -0.1309F, 0.7418F, 0.1309F);
		fan_left_r1.texOffs(45, 28).addBox(1.0F, -11.0F, -1.0F, 0.0F, 14.0F, 9.0F, 0.0F, false);

		body_r1 = new ModelRenderer(this);
		body_r1.setPos(0.0F, 0.0F, 0.0F);
		body.addChild(body_r1);
		setRotationAngle(body_r1, -0.0873F, 0.0F, 0.0F);
		body_r1.texOffs(0, 36).addBox(-6.0F, -7.0F, -8.0F, 12.0F, 11.0F, 16.0F, 0.0F, false);

		wing_left = new ModelRenderer(this);
		wing_left.setPos(6.0F, -5.0F, -3.0F);
		body.addChild(wing_left);


		wing_right_r1 = new ModelRenderer(this);
		wing_right_r1.setPos(0.0F, 0.0F, 0.0F);
		wing_left.addChild(wing_right_r1);
		setRotationAngle(wing_right_r1, -0.0436F, 0.0436F, -0.0873F);
		wing_right_r1.texOffs(83, 21).addBox(0.0F, -2.0F, -3.0F, 1.0F, 10.0F, 16.0F, 0.1F, false);

		wing_right = new ModelRenderer(this);
		wing_right.setPos(-6.0F, -5.0F, -3.0F);
		body.addChild(wing_right);


		wing_right_r2 = new ModelRenderer(this);
		wing_right_r2.setPos(-1.0F, 0.0F, 0.0F);
		wing_right.addChild(wing_right_r2);
		setRotationAngle(wing_right_r2, -0.0436F, -0.0436F, 0.0873F);
		wing_right_r2.texOffs(83, 21).addBox(0.0F, -2.0F, -3.0F, 1.0F, 10.0F, 16.0F, 0.1F, false);

		chest = new ModelRenderer(this);
		chest.setPos(0.0F, -4.0F, -6.0F);
		body.addChild(chest);


		chest_r1 = new ModelRenderer(this);
		chest_r1.setPos(0.0F, -1.0F, -1.0F);
		chest.addChild(chest_r1);
		setRotationAngle(chest_r1, 0.7854F, 0.0F, 0.0F);
		chest_r1.texOffs(0, 18).addBox(-4.0F, -4.0F, -5.0F, 8.0F, 8.0F, 10.0F, 0.1F, false);

		neck = new ModelRenderer(this);
		neck.setPos(0.0F, -4.0F, -2.0F);
		chest.addChild(neck);


		neck_r1 = new ModelRenderer(this);
		neck_r1.setPos(0.0F, 1.0F, 0.0F);
		neck.addChild(neck_r1);
		setRotationAngle(neck_r1, 0.1309F, 0.0F, 0.0F);
		neck_r1.texOffs(36, 20).addBox(-2.0F, -12.0F, -3.0F, 4.0F, 12.0F, 4.0F, 0.2F, false);

		head = new ModelRenderer(this);
		head.setPos(0.0F, -11.0F, -2.0F);
		neck.addChild(head);
		head.texOffs(0, 0).addBox(-3.0F, -5.0F, -9.0F, 6.0F, 6.0F, 12.0F, 0.0F, false);

		crest_top_r1 = new ModelRenderer(this);
		crest_top_r1.setPos(0.0F, -5.0F, 3.0F);
		head.addChild(crest_top_r1);
		setRotationAngle(crest_top_r1, 0.2618F, 0.0F, 0.0F);
		crest_top_r1.texOffs(20, 0).addBox(-2.5F, 0.0F, 0.0F, 5.0F, 0.0F, 6.0F, 0.0F, false);

		crest_right_r1 = new ModelRenderer(this);
		crest_right_r1.setPos(-3.0F, 0.0F, 3.0F);
		head.addChild(crest_right_r1);
		setRotationAngle(crest_right_r1, 0.0F, -0.2182F, 0.0F);
		crest_right_r1.texOffs(2, 2).addBox(0.0F, -5.0F, 0.0F, 0.0F, 6.0F, 4.0F, 0.0F, true);

		crest_left_r1 = new ModelRenderer(this);
		crest_left_r1.setPos(3.0F, 0.0F, 3.0F);
		head.addChild(crest_left_r1);
		setRotationAngle(crest_left_r1, 0.0F, 0.2182F, 0.0F);
		crest_left_r1.texOffs(2, 2).addBox(0.0F, -5.0F, 0.0F, 0.0F, 6.0F, 4.0F, 0.0F, false);

		leg_left = new ModelRenderer(this);
		leg_left.setPos(3.5F, 3.0F, 1.0F);
		body.addChild(leg_left);


		leg_left_lower_r1 = new ModelRenderer(this);
		leg_left_lower_r1.setPos(0.0F, 8.0F, 0.0F);
		leg_left.addChild(leg_left_lower_r1);
		setRotationAngle(leg_left_lower_r1, -0.3927F, 0.0F, 0.0F);
		leg_left_lower_r1.texOffs(79, 48).addBox(-1.5F, -2.0F, 1.0F, 3.0F, 12.0F, 3.0F, 0.1F, false);

		leg_left_upper_r1 = new ModelRenderer(this);
		leg_left_upper_r1.setPos(0.0F, 0.0F, 0.0F);
		leg_left.addChild(leg_left_upper_r1);
		setRotationAngle(leg_left_upper_r1, 0.2618F, 0.0F, 0.0F);
		leg_left_upper_r1.texOffs(60, 49).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 9.0F, 5.0F, 0.0F, false);

		foot_left = new ModelRenderer(this);
		foot_left.setPos(0.0F, 19.0F, -2.0F);
		leg_left.addChild(foot_left);
		setRotationAngle(foot_left, 0.0F, -0.2182F, 0.0F);
		foot_left.texOffs(104, 53).addBox(-1.0F, -2.0F, 1.5F, 2.0F, 2.0F, 5.0F, 0.0F, false);

		toe_right_r1 = new ModelRenderer(this);
		toe_right_r1.setPos(0.0F, 0.0F, 0.0F);
		foot_left.addChild(toe_right_r1);
		setRotationAngle(toe_right_r1, 0.2182F, 0.2618F, 0.0F);
		toe_right_r1.texOffs(92, 54).addBox(-2.0F, -3.0F, -5.0F, 2.0F, 2.0F, 7.0F, 0.0F, false);

		toe_left_r1 = new ModelRenderer(this);
		toe_left_r1.setPos(0.0F, 0.0F, 0.0F);
		foot_left.addChild(toe_left_r1);
		setRotationAngle(toe_left_r1, 0.2182F, -0.2618F, 0.0F);
		toe_left_r1.texOffs(92, 54).addBox(0.0F, -3.0F, -5.0F, 2.0F, 2.0F, 7.0F, 0.0F, false);

		leg_right = new ModelRenderer(this);
		leg_right.setPos(-3.5F, 3.0F, 1.0F);
		body.addChild(leg_right);


		leg_right_lower_r1 = new ModelRenderer(this);
		leg_right_lower_r1.setPos(0.0F, 8.0F, 0.0F);
		leg_right.addChild(leg_right_lower_r1);
		setRotationAngle(leg_right_lower_r1, -0.3927F, 0.0F, 0.0F);
		leg_right_lower_r1.texOffs(79, 48).addBox(-1.5F, -2.0F, 1.0F, 3.0F, 12.0F, 3.0F, 0.1F, false);

		leg_right_upper_r1 = new ModelRenderer(this);
		leg_right_upper_r1.setPos(0.0F, 0.0F, 0.0F);
		leg_right.addChild(leg_right_upper_r1);
		setRotationAngle(leg_right_upper_r1, 0.2618F, 0.0F, 0.0F);
		leg_right_upper_r1.texOffs(60, 49).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 9.0F, 5.0F, 0.0F, false);

		foot_right = new ModelRenderer(this);
		foot_right.setPos(0.0F, 19.0F, -2.0F);
		leg_right.addChild(foot_right);
		setRotationAngle(foot_right, 0.0F, 0.2182F, 0.0F);
		foot_right.texOffs(104, 53).addBox(-1.0F, -2.0F, 1.5F, 2.0F, 2.0F, 5.0F, 0.0F, false);

		toe_right_r2 = new ModelRenderer(this);
		toe_right_r2.setPos(0.0F, 0.0F, 0.0F);
		foot_right.addChild(toe_right_r2);
		setRotationAngle(toe_right_r2, 0.2182F, 0.2618F, 0.0F);
		toe_right_r2.texOffs(92, 54).addBox(-2.0F, -3.0F, -5.0F, 2.0F, 2.0F, 7.0F, 0.0F, false);

		toe_left_r2 = new ModelRenderer(this);
		toe_left_r2.setPos(0.0F, 0.0F, 0.0F);
		foot_right.addChild(toe_left_r2);
		setRotationAngle(toe_left_r2, 0.2182F, -0.2618F, 0.0F);
		toe_left_r2.texOffs(92, 54).addBox(0.0F, -3.0F, -5.0F, 2.0F, 2.0F, 7.0F, 0.0F, false);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		root.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	private void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}

	@Override
	public void setupAnim(ChocoboEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		// ageInTicks = wing z movement (flutter)
		// netHeadYaw = head y movement
		// headPitch = head x movement
		// cos(limbSwing) and limbSwingAmount = leg movement

		float pi = (float) Math.PI;

		// head/neck movement
		head.xRot = headPitch * (pi / 180F);
		head.yRot = netHeadYaw * (pi / 180F);
//		neck.xRot = -0.8F;
//		neck.yRot = 0.0F;

		// walking animation
		this.setRightLegXRotation(MathHelper.cos(limbSwing * 0.6662F) * 0.8F * limbSwingAmount);
		this.setLeftLegXRotation(MathHelper.cos(limbSwing * 0.6662F + pi) * 0.8F * limbSwingAmount);

		// riding animation
		Vector3d motion = entityIn.getDeltaMovement();
//		if (Math.abs(motion.x) > 0.1F || Math.abs(motion.z) > 0.1F) {
//			neck.xRot = -0.5F;
//		} else {
//			neck.xRot = 0.8F;
//		}

		// flying animation
		if (Math.abs(motion.y) > 0.1F || !entityIn.isOnGround()) {
			setRotateAngle(wing_right, (pi / 2F) - (pi / 12), -0.0174533F, -90 + MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount);
			setRotateAngle(wing_left, (pi / 2F) - (pi / 12), 0.0174533F, 90 + MathHelper.cos(limbSwing * 0.6662F + pi) * 1.4F * limbSwingAmount);
			this.setLeftLegXRotation(0.6F);
			this.setRightLegXRotation(0.6F);
		} else {
			// reset wings
			setRotateAngle(wing_right, 0F, -0.0174533F, 0F);
			setRotateAngle(wing_left, 0F, 0.0174533F, 0F);
		}
	}

	private void setLeftLegXRotation(float deltaX) {
		leg_left.xRot = 0.2094395F + deltaX;
	}

	private void setRightLegXRotation(float deltaX) {
		leg_right.xRot = 0.2094395F + deltaX;
	}
}
