package net.chococraft.client.models.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.chococraft.common.entity.AbstractChocobo;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

/**
 * AdultChocoboModel - Kraeheart
 */
public class AdultChocoboModel<T extends AbstractChocobo> extends EntityModel<AbstractChocobo> {
	private final ModelPart root;
	private final ModelPart wing_left;
	private final ModelPart wing_right;
	private final ModelPart neck;
	private final ModelPart head;
	private final ModelPart leg_left;
	private final ModelPart leg_right;

	public AdultChocoboModel(ModelPart root) {
		this.root = root.getChild("root");
		ModelPart body = this.root.getChild("body");

		this.neck = body.getChild("chest").getChild("neck");
		this.head = this.neck.getChild("head");

		this.leg_left = body.getChild("leg_left");
		this.leg_right = body.getChild("leg_right");

		this.wing_left = body.getChild("wing_left");
		this.wing_right = body.getChild("wing_right");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(),
				PartPose.offset(0.0F, 24.0F, -4.0F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create(),
				PartPose.offset(0.0F, -22.0F, 1.0F));

		PartDefinition saddlebag_right_r1 = body.addOrReplaceChild("saddlebag_right_r1", CubeListBuilder.create()
						.texOffs(81, 6).mirror().addBox(-7.5F, -5.0F, -3.0F, 2.0F, 8.0F, 6.0F,
								new CubeDeformation(-0.1F)).mirror(false)
						.texOffs(81, 6).addBox(5.5F, -5.0F, -3.0F, 2.0F, 8.0F, 6.0F,
								new CubeDeformation(-0.1F)),
				PartPose.offsetAndRotation(0.0F, -3.0F, 6.0F, -0.0873F, 0.0F, 0.0F));

		PartDefinition storage_back_r1 = body.addOrReplaceChild("storage_back_r1", CubeListBuilder.create()
						.texOffs(36, 2).addBox(-5.0F, -6.0F, -3.0F, 10.0F, 6.0F, 12.0F,
								new CubeDeformation(-0.1F)),
				PartPose.offsetAndRotation(0.0F, -7.0F, 0.0F, -0.0873F, 0.0F, 0.0F));

		PartDefinition fan_bottom_r1 = body.addOrReplaceChild("fan_bottom_r1", CubeListBuilder.create()
						.texOffs(101, 29).addBox(-3.5F, -4.5F, 0.0F, 7.0F, 6.0F, 0.0F),
				PartPose.offsetAndRotation(0.0F, 0.0F, 9.0F, -0.9163F, 0.0F, 0.0F));

		PartDefinition fan_top_r1 = body.addOrReplaceChild("fan_top_r1", CubeListBuilder.create()
						.texOffs(55, 25).addBox(-5.5F, -10.0F, -2.0F, 11.0F, 10.0F, 0.0F),
				PartPose.offsetAndRotation(0.0F, -5.0F, 10.0F, -0.5672F, 0.0F, 0.0F));

		PartDefinition fan_right_r1 = body.addOrReplaceChild("fan_right_r1", CubeListBuilder.create()
						.texOffs(45, 28).addBox(-1.0F, -11.0F, -1.0F, 0.0F, 14.0F, 9.0F),
				PartPose.offsetAndRotation(-2.0F, 0.0F, 9.0F, -0.1309F, -0.7418F, -0.1309F));

		PartDefinition fan_left_r1 = body.addOrReplaceChild("fan_left_r1", CubeListBuilder.create()
						.texOffs(45, 28).addBox(1.0F, -11.0F, -1.0F, 0.0F, 14.0F, 9.0F),
				PartPose.offsetAndRotation(2.0F, 0.0F, 9.0F, -0.1309F, 0.7418F, 0.1309F));

		PartDefinition body_r1 = body.addOrReplaceChild("body_r1", CubeListBuilder.create()
						.texOffs(0, 36).addBox(-6.0F, -7.0F, -8.0F, 12.0F, 11.0F, 16.0F),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0873F, 0.0F, 0.0F));

		PartDefinition wing_left = body.addOrReplaceChild("wing_left", CubeListBuilder.create(),
				PartPose.offset(6.0F, -5.0F, -3.0F));

		PartDefinition wing_right_r1 = wing_left.addOrReplaceChild("wing_right_r1", CubeListBuilder.create()
						.texOffs(83, 21).addBox(0.0F, -2.0F, -3.0F, 1.0F, 10.0F, 16.0F,
								new CubeDeformation(0.1F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0436F, 0.0436F, -0.0873F));

		PartDefinition wing_right = body.addOrReplaceChild("wing_right", CubeListBuilder.create(),
				PartPose.offset(-6.0F, -5.0F, -3.0F));

		PartDefinition wing_right_r2 = wing_right.addOrReplaceChild("wing_right_r2", CubeListBuilder.create()
						.texOffs(83, 21).addBox(0.0F, -2.0F, -3.0F, 1.0F, 10.0F, 16.0F,
								new CubeDeformation(0.1F)),
				PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, -0.0436F, -0.0436F, 0.0873F));

		PartDefinition chest = body.addOrReplaceChild("chest", CubeListBuilder.create(),
				PartPose.offset(0.0F, -4.0F, -6.0F));

		PartDefinition chest_r1 = chest.addOrReplaceChild("chest_r1", CubeListBuilder.create()
						.texOffs(0, 18).addBox(-4.0F, -4.0F, -5.0F, 8.0F, 8.0F, 10.0F,
								new CubeDeformation(0.1F)),
				PartPose.offsetAndRotation(0.0F, -1.0F, -1.0F, 0.7854F, 0.0F, 0.0F));

		PartDefinition neck = chest.addOrReplaceChild("neck", CubeListBuilder.create(),
				PartPose.offset(0.0F, -4.0F, -2.0F));

		PartDefinition neck_r1 = neck.addOrReplaceChild("neck_r1", CubeListBuilder.create()
						.texOffs(36, 20).addBox(-2.0F, -12.0F, -3.0F, 4.0F, 12.0F, 4.0F,
								new CubeDeformation(0.2F)),
				PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0.1309F, 0.0F, 0.0F));

		PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create()
						.texOffs(0, 0).addBox(-3.0F, -5.0F, -9.0F, 6.0F, 6.0F, 12.0F),
				PartPose.offset(0.0F, -11.0F, -2.0F));

		PartDefinition crest_top_r1 = head.addOrReplaceChild("crest_top_r1", CubeListBuilder.create()
						.texOffs(20, 0).addBox(-2.5F, 0.0F, 0.0F, 5.0F, 0.0F, 6.0F),
				PartPose.offsetAndRotation(0.0F, -5.0F, 3.0F, 0.2618F, 0.0F, 0.0F));

		PartDefinition crest_right_r1 = head.addOrReplaceChild("crest_right_r1", CubeListBuilder.create()
				.texOffs(2, 2).mirror().addBox(0.0F, -5.0F, 0.0F, 0.0F, 6.0F, 4.0F).mirror(false), PartPose.offsetAndRotation(-3.0F, 0.0F, 3.0F, 0.0F, -0.2182F, 0.0F));

		PartDefinition crest_left_r1 = head.addOrReplaceChild("crest_left_r1", CubeListBuilder.create()
						.texOffs(2, 2).addBox(0.0F, -5.0F, 0.0F, 0.0F, 6.0F, 4.0F),
				PartPose.offsetAndRotation(3.0F, 0.0F, 3.0F, 0.0F, 0.2182F, 0.0F));

		PartDefinition leg_left = body.addOrReplaceChild("leg_left", CubeListBuilder.create(),
				PartPose.offset(3.5F, 3.0F, 1.0F));

		PartDefinition leg_left_lower_r1 = leg_left.addOrReplaceChild("leg_left_lower_r1", CubeListBuilder.create()
						.texOffs(79, 48).addBox(-1.5F, -2.0F, 1.0F, 3.0F, 12.0F, 3.0F,
								new CubeDeformation(0.1F)),
				PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, -0.3927F, 0.0F, 0.0F));

		PartDefinition leg_left_upper_r1 = leg_left.addOrReplaceChild("leg_left_upper_r1", CubeListBuilder.create()
						.texOffs(60, 49).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 9.0F, 5.0F),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

		PartDefinition foot_left = leg_left.addOrReplaceChild("foot_left", CubeListBuilder.create()
						.texOffs(104, 53).addBox(-1.0F, -2.0F, 1.5F, 2.0F, 2.0F, 5.0F),
				PartPose.offsetAndRotation(0.0F, 19.0F, -2.0F, 0.0F, -0.2182F, 0.0F));

		PartDefinition toe_right_r1 = foot_left.addOrReplaceChild("toe_right_r1", CubeListBuilder.create()
						.texOffs(92, 54).addBox(-2.0F, -3.0F, -5.0F, 2.0F, 2.0F, 7.0F),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2182F, 0.2618F, 0.0F));

		PartDefinition toe_left_r1 = foot_left.addOrReplaceChild("toe_left_r1", CubeListBuilder.create()
						.texOffs(92, 54).addBox(0.0F, -3.0F, -5.0F, 2.0F, 2.0F, 7.0F),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2182F, -0.2618F, 0.0F));

		PartDefinition leg_right = body.addOrReplaceChild("leg_right", CubeListBuilder.create(),
				PartPose.offset(-3.5F, 3.0F, 1.0F));

		PartDefinition leg_right_lower_r1 = leg_right.addOrReplaceChild("leg_right_lower_r1", CubeListBuilder.create()
						.texOffs(79, 48).addBox(-1.5F, -2.0F, 1.0F, 3.0F, 12.0F, 3.0F,
								new CubeDeformation(0.1F)),
				PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, -0.3927F, 0.0F, 0.0F));

		PartDefinition leg_right_upper_r1 = leg_right.addOrReplaceChild("leg_right_upper_r1", CubeListBuilder.create()
						.texOffs(60, 49).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 9.0F, 5.0F),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

		PartDefinition foot_right = leg_right.addOrReplaceChild("foot_right", CubeListBuilder.create()
						.texOffs(104, 53).addBox(-1.0F, -2.0F, 1.5F, 2.0F, 2.0F, 5.0F),
				PartPose.offsetAndRotation(0.0F, 19.0F, -2.0F, 0.0F, 0.2182F, 0.0F));

		PartDefinition toe_right_r2 = foot_right.addOrReplaceChild("toe_right_r2", CubeListBuilder.create()
						.texOffs(92, 54).addBox(-2.0F, -3.0F, -5.0F, 2.0F, 2.0F, 7.0F),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2182F, 0.2618F, 0.0F));

		PartDefinition toe_left_r2 = foot_right.addOrReplaceChild("toe_left_r2", CubeListBuilder.create()
						.texOffs(92, 54).addBox(0.0F, -3.0F, -5.0F, 2.0F, 2.0F, 7.0F),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2182F, -0.2618F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 64);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer consumer, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		this.root.render(poseStack, consumer, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	private void setRotateAngle(ModelPart modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}

	@Override
	public void setupAnim(AbstractChocobo entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
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
		this.setRightLegXRotation(Mth.cos(limbSwing * 0.6662F) * 0.8F * limbSwingAmount);
		this.setLeftLegXRotation(Mth.cos(limbSwing * 0.6662F + pi) * 0.8F * limbSwingAmount);

		// riding animation
		Vec3 motion = entityIn.getDeltaMovement();
//		if (Math.abs(motion.x) > 0.1F || Math.abs(motion.z) > 0.1F) {
//			neck.xRot = -0.5F;
//		} else {
//			neck.xRot = 0.8F;
//		}

		// flying animation
		if (Math.abs(motion.y) > 0.1F || !entityIn.onGround()) {
			setRotateAngle(wing_right, (pi / 2F) - (pi / 12), -0.0174533F, -90 + Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount);
			setRotateAngle(wing_left, (pi / 2F) - (pi / 12), 0.0174533F, 90 + Mth.cos(limbSwing * 0.6662F + pi) * 1.4F * limbSwingAmount);
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
