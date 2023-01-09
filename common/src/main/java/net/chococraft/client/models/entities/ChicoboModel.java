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

/**
 * ChicoboModel - Kraeheart
 */
public class ChicoboModel<T extends AbstractChocobo> extends EntityModel<AbstractChocobo> {
	private final ModelPart root;

	private final ModelPart head;
	private final ModelPart leg_left;
	private final ModelPart leg_right;

	public ChicoboModel(ModelPart root) {
		this.root = root.getChild("root");

		this.head = this.root.getChild("head");
		this.leg_left = this.root.getChild("leg_left");
		this.leg_right = this.root.getChild("leg_right");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(),
				PartPose.offset(0.0F, 19.0F, 0.0F));

		PartDefinition fan_bottom_r1 = root.addOrReplaceChild("fan_bottom_r1", CubeListBuilder.create()
						.texOffs(16, 26).addBox(-1.5F, -2.5F, 2.0F, 3.0F, 3.0F, 0.0F),
				PartPose.offsetAndRotation(0.0F, -5.0F, 4.0F, -1.0036F, 0.0F, 0.0F));

		PartDefinition fan_top_r1 = root.addOrReplaceChild("fan_top_r1", CubeListBuilder.create()
						.texOffs(5, 25).addBox(-2.5F, -4.5F, 0.0F, 5.0F, 4.0F, 0.0F),
				PartPose.offsetAndRotation(0.0F, -5.0F, 4.0F, -0.6981F, 0.0F, 0.0F));

		PartDefinition body_r1 = root.addOrReplaceChild("body_r1", CubeListBuilder.create()
						.texOffs(30, 14).addBox(-4.0F, -7.5F, -4.0F, 8.0F, 9.0F, 9.0F),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1309F, 0.0F, 0.0F));

		PartDefinition leg_left = root.addOrReplaceChild("leg_left", CubeListBuilder.create(),
				PartPose.offsetAndRotation(2.0F, 1.0F, 0.0F, 0.0F, -0.1745F, 0.0F));

		PartDefinition toe_right_r1 = leg_left.addOrReplaceChild("toe_right_r1", CubeListBuilder.create()
						.texOffs(1, 17).addBox(-1.0F, -1.5F, -2.75F, 1.0F, 1.0F, 4.0F,
								new CubeDeformation(0.2F)),
				PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, 0.1309F, 0.2182F, 0.0F));

		PartDefinition toe_left_r1 = leg_left.addOrReplaceChild("toe_left_r1", CubeListBuilder.create()
						.texOffs(1, 17).addBox(0.0F, -1.5F, -3.0F, 1.0F, 1.0F, 4.0F,
								new CubeDeformation(0.2F)),
				PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, 0.1309F, -0.2182F, 0.0F));

		PartDefinition toe_back_r1 = leg_left.addOrReplaceChild("toe_back_r1", CubeListBuilder.create()
						.texOffs(0, 10).addBox(-0.5F, -1.0F, -2.5F, 1.0F, 1.0F, 3.0F,
								new CubeDeformation(0.2F)),
				PartPose.offsetAndRotation(0.0F, 4.0F, 1.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition leg_r1 = leg_left.addOrReplaceChild("leg_r1", CubeListBuilder.create()
						.texOffs(0, 0).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 5.0F, 2.0F),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1309F, 0.0F, 0.0F));

		PartDefinition leg_right = root.addOrReplaceChild("leg_right", CubeListBuilder.create(),
				PartPose.offsetAndRotation(-2.0F, 1.0F, 0.0F, 0.0F, 0.1745F, 0.0F));

		PartDefinition toe_right_r2 = leg_right.addOrReplaceChild("toe_right_r2", CubeListBuilder.create()
						.texOffs(1, 17).addBox(-1.0F, -1.5F, -3.0F, 1.0F, 1.0F, 4.0F,
								new CubeDeformation(0.2F)),
				PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, 0.1309F, 0.2182F, 0.0F));

		PartDefinition toe_left_r2 = leg_right.addOrReplaceChild("toe_left_r2", CubeListBuilder.create()
						.texOffs(1, 17).addBox(0.0F, -1.5F, -3.0F, 1.0F, 1.0F, 4.0F,
								new CubeDeformation(0.2F)),
				PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, 0.1309F, -0.2182F, 0.0F));

		PartDefinition toe_back_r2 = leg_right.addOrReplaceChild("toe_back_r2", CubeListBuilder.create()
						.texOffs(0, 10).addBox(-0.5F, -1.0F, -2.5F, 1.0F, 1.0F, 3.0F,
								new CubeDeformation(0.2F)),
				PartPose.offsetAndRotation(0.0F, 4.0F, 1.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition leg_r2 = leg_right.addOrReplaceChild("leg_r2", CubeListBuilder.create()
						.texOffs(0, 0).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 5.0F, 2.0F),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1309F, 0.0F, 0.0F));

		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create()
						.texOffs(42, 0).addBox(-2.5F, -4.5F, -5.5F, 5.0F, 6.0F, 6.0F,
								new CubeDeformation(0.2F)),
				PartPose.offset(0.0F, -6.0F, -2.0F));

		PartDefinition crest_r1 = head.addOrReplaceChild("crest_r1", CubeListBuilder.create()
						.texOffs(16, 26).addBox(-1.5F, -4.0F, 1.0F, 3.0F, 3.0F, 0.0F),
				PartPose.offsetAndRotation(0.0F, -5.0F, -1.0F, -1.0472F, 0.0F, 0.0F));

		PartDefinition beak_r1 = head.addOrReplaceChild("beak_r1", CubeListBuilder.create()
						.texOffs(22, 5).addBox(-2.5F, -3.0F, -2.0F, 5.0F, 4.0F, 3.0F),
				PartPose.offsetAndRotation(0.0F, 0.0F, -6.0F, -0.0873F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}

	@Override
	public void setupAnim(AbstractChocobo entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		head.xRot = -(headPitch / 57.29578F);
		head.yRot = netHeadYaw / 57.29578F;
		leg_right.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		leg_left.yRot = Mth.cos(limbSwing * 0.6662F + 3.141593F) * 1.4F * limbSwingAmount;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}