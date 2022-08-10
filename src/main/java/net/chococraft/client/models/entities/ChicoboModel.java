package net.chococraft.client.models.entities;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.chococraft.common.entities.ChocoboEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

/**
 * AdultChocoboModel - Kraeheart
 */
public class ChicoboModel extends EntityModel<ChocoboEntity> {
	private final ModelRenderer root;
	private final ModelRenderer fan_bottom_r1;
	private final ModelRenderer fan_top_r1;
	private final ModelRenderer body_r1;
	private final ModelRenderer leg_left;
	private final ModelRenderer toe_right_r1;
	private final ModelRenderer toe_left_r1;
	private final ModelRenderer toe_back_r1;
	private final ModelRenderer leg_r1;
	private final ModelRenderer leg_right;
	private final ModelRenderer toe_right_r2;
	private final ModelRenderer toe_left_r2;
	private final ModelRenderer toe_back_r2;
	private final ModelRenderer leg_r2;
	private final ModelRenderer head;
	private final ModelRenderer crest_r1;
	private final ModelRenderer beak_r1;

	public ChicoboModel() {
		texWidth = 64;
		texHeight = 32;

		root = new ModelRenderer(this);
		root.setPos(0.0F, 19.0F, 0.0F);


		fan_bottom_r1 = new ModelRenderer(this);
		fan_bottom_r1.setPos(0.0F, -5.0F, 4.0F);
		root.addChild(fan_bottom_r1);
		setRotationAngle(fan_bottom_r1, -1.0036F, 0.0F, 0.0F);
		fan_bottom_r1.texOffs(16, 26).addBox(-1.5F, -2.5F, 2.0F, 3.0F, 3.0F, 0.0F, 0.0F, false);

		fan_top_r1 = new ModelRenderer(this);
		fan_top_r1.setPos(0.0F, -5.0F, 4.0F);
		root.addChild(fan_top_r1);
		setRotationAngle(fan_top_r1, -0.6981F, 0.0F, 0.0F);
		fan_top_r1.texOffs(5, 25).addBox(-2.5F, -4.5F, 0.0F, 5.0F, 4.0F, 0.0F, 0.0F, false);

		body_r1 = new ModelRenderer(this);
		body_r1.setPos(0.0F, 0.0F, 0.0F);
		root.addChild(body_r1);
		setRotationAngle(body_r1, 0.1309F, 0.0F, 0.0F);
		body_r1.texOffs(30, 14).addBox(-4.0F, -7.5F, -4.0F, 8.0F, 9.0F, 9.0F, 0.0F, false);

		leg_left = new ModelRenderer(this);
		leg_left.setPos(2.0F, 1.0F, 0.0F);
		root.addChild(leg_left);
		setRotationAngle(leg_left, 0.0F, -0.1745F, 0.0F);


		toe_right_r1 = new ModelRenderer(this);
		toe_right_r1.setPos(0.0F, 4.0F, 0.0F);
		leg_left.addChild(toe_right_r1);
		setRotationAngle(toe_right_r1, 0.1309F, 0.2182F, 0.0F);
		toe_right_r1.texOffs(1, 17).addBox(-1.0F, -1.5F, -2.75F, 1.0F, 1.0F, 4.0F, 0.2F, false);

		toe_left_r1 = new ModelRenderer(this);
		toe_left_r1.setPos(0.0F, 4.0F, 0.0F);
		leg_left.addChild(toe_left_r1);
		setRotationAngle(toe_left_r1, 0.1309F, -0.2182F, 0.0F);
		toe_left_r1.texOffs(1, 17).addBox(0.0F, -1.5F, -3.0F, 1.0F, 1.0F, 4.0F, 0.2F, false);

		toe_back_r1 = new ModelRenderer(this);
		toe_back_r1.setPos(0.0F, 4.0F, 1.0F);
		leg_left.addChild(toe_back_r1);
		setRotationAngle(toe_back_r1, 0.0F, 3.1416F, 0.0F);
		toe_back_r1.texOffs(0, 10).addBox(-0.5F, -1.0F, -2.5F, 1.0F, 1.0F, 3.0F, 0.2F, false);

		leg_r1 = new ModelRenderer(this);
		leg_r1.setPos(0.0F, 0.0F, 0.0F);
		leg_left.addChild(leg_r1);
		setRotationAngle(leg_r1, -0.1309F, 0.0F, 0.0F);
		leg_r1.texOffs(0, 0).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 5.0F, 2.0F, 0.0F, false);

		leg_right = new ModelRenderer(this);
		leg_right.setPos(-2.0F, 1.0F, 0.0F);
		root.addChild(leg_right);
		setRotationAngle(leg_right, 0.0F, 0.1745F, 0.0F);


		toe_right_r2 = new ModelRenderer(this);
		toe_right_r2.setPos(0.0F, 4.0F, 0.0F);
		leg_right.addChild(toe_right_r2);
		setRotationAngle(toe_right_r2, 0.1309F, 0.2182F, 0.0F);
		toe_right_r2.texOffs(1, 17).addBox(-1.0F, -1.5F, -3.0F, 1.0F, 1.0F, 4.0F, 0.2F, false);

		toe_left_r2 = new ModelRenderer(this);
		toe_left_r2.setPos(0.0F, 4.0F, 0.0F);
		leg_right.addChild(toe_left_r2);
		setRotationAngle(toe_left_r2, 0.1309F, -0.2182F, 0.0F);
		toe_left_r2.texOffs(1, 17).addBox(0.0F, -1.5F, -3.0F, 1.0F, 1.0F, 4.0F, 0.2F, false);

		toe_back_r2 = new ModelRenderer(this);
		toe_back_r2.setPos(0.0F, 4.0F, 1.0F);
		leg_right.addChild(toe_back_r2);
		setRotationAngle(toe_back_r2, 0.0F, 3.1416F, 0.0F);
		toe_back_r2.texOffs(0, 10).addBox(-0.5F, -1.0F, -2.5F, 1.0F, 1.0F, 3.0F, 0.2F, false);

		leg_r2 = new ModelRenderer(this);
		leg_r2.setPos(0.0F, 0.0F, 0.0F);
		leg_right.addChild(leg_r2);
		setRotationAngle(leg_r2, -0.1309F, 0.0F, 0.0F);
		leg_r2.texOffs(0, 0).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 5.0F, 2.0F, 0.0F, false);

		head = new ModelRenderer(this);
		head.setPos(0.0F, -6.0F, -2.0F);
		root.addChild(head);
		head.texOffs(42, 0).addBox(-2.5F, -4.5F, -5.5F, 5.0F, 6.0F, 6.0F, 0.2F, false);

		crest_r1 = new ModelRenderer(this);
		crest_r1.setPos(0.0F, -5.0F, -1.0F);
		head.addChild(crest_r1);
		setRotationAngle(crest_r1, -1.0472F, 0.0F, 0.0F);
		crest_r1.texOffs(16, 26).addBox(-1.5F, -4.0F, 1.0F, 3.0F, 3.0F, 0.0F, 0.0F, false);

		beak_r1 = new ModelRenderer(this);
		beak_r1.setPos(0.0F, 0.0F, -6.0F);
		head.addChild(beak_r1);
		setRotationAngle(beak_r1, -0.0873F, 0.0F, 0.0F);
		beak_r1.texOffs(22, 5).addBox(-2.5F, -3.0F, -2.0F, 5.0F, 4.0F, 3.0F, 0.0F, false);
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

	@Override
	public void setupAnim(ChocoboEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		head.xRot = -(headPitch / 57.29578F);
		head.yRot = netHeadYaw / 57.29578F;
		leg_right.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		leg_left.yRot = MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 1.4F * limbSwingAmount;
	}
}
