package net.chococraft.client.models.armor;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.item.equipment.ArmorType;

public class ChocoDisguiseModel extends HumanoidModel<HumanoidRenderState> {
	private final ArmorType armorType;

	public ChocoDisguiseModel(ModelPart root, ArmorType type) {
		super(root);
		this.armorType = type;
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		// Head
		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.ZERO);
		head.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
		PartDefinition chocobo_neck = head.addOrReplaceChild("chocobo_neck", CubeListBuilder.create().texOffs(0, 18).addBox(-4.5F, -8.5F, -4.5F, 9.0F, 9.0F, 9.0F), PartPose.ZERO);
		PartDefinition chocobo_head = chocobo_neck.addOrReplaceChild("chocobo_head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.2063F, -3.8667F, -9.081F, 6.0F, 6.0F, 12.0F)
				.texOffs(36, 12).addBox(2.0F, -3.0F, -3.0F, 1.0F, 3.0F, 3.0F)
				.texOffs(36, 12).mirror().addBox(-3.5F, -3.0F, -3.0F, 1.0F, 3.0F, 3.0F).mirror(false), PartPose.offsetAndRotation(0.2063F, -10.1333F, -0.919F, 0.2618F, 0.0F, 0.0F));
		chocobo_head.addOrReplaceChild("crest_right_r1", CubeListBuilder.create().texOffs(1, 0).mirror().addBox(-0.5F, -4.5F, 0.0F, 1.0F, 7.0F, 4.0F).mirror(false), PartPose.offsetAndRotation(-2.7063F, -0.3667F, 2.919F, 0.2094F, -0.384F, 0.2094F));
		chocobo_head.addOrReplaceChild("crest_top_r1", CubeListBuilder.create().texOffs(25, 0).addBox(-3.0F, -0.5F, 0.0F, 5.0F, 1.0F, 6.0F), PartPose.offsetAndRotation(0.2937F, -2.8667F, 2.919F, 0.4538F, 0.0F, 0.0F));
		chocobo_head.addOrReplaceChild("crest_left_r1", CubeListBuilder.create().texOffs(1, 0).addBox(-0.5F, -4.5F, 0.0F, 1.0F, 7.0F, 4.0F), PartPose.offsetAndRotation(2.2937F, -0.3667F, 2.919F, 0.2094F, 0.384F, -0.2094F));

		// Body
		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.ZERO);
		PartDefinition chocobo_body = body.addOrReplaceChild("chocobo_body", CubeListBuilder.create().texOffs(36, 18).addBox(-4.5F, -0.5F, -2.5F, 9.0F, 13.0F, 5.0F), PartPose.ZERO);
		chocobo_body.addOrReplaceChild("feather_middle_r1", CubeListBuilder.create().texOffs(102, 93).addBox(-3.5F, 0.5F, -0.5F, 7.0F, 1.0F, 6.0F), PartPose.offsetAndRotation(-0.5F, 8.0F, 2.5F, 0.3491F, 0.0F, 0.0F));
		chocobo_body.addOrReplaceChild("feather_left_r1", CubeListBuilder.create().texOffs(44, 92).addBox(1.5F, -8.5F, -0.5F, 1.0F, 14.0F, 9.0F), PartPose.offsetAndRotation(-0.5F, 8.0F, 2.5F, -0.2094F, 0.384F, 0.3491F));
		chocobo_body.addOrReplaceChild("feather_right_r1", CubeListBuilder.create().texOffs(44, 92).addBox(-2.5F, -8.5F, -0.5F, 1.0F, 14.0F, 9.0F), PartPose.offsetAndRotation(-0.5F, 8.0F, 2.5F, -0.2094F, -0.384F, -0.3491F));

		// Right Arm
		PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offset(-5.0F, 2.0F, 0.0F));
		right_arm.addOrReplaceChild("right_arm_feather", CubeListBuilder.create().texOffs(0, 36).mirror().addBox(-4.25F, -3.0F, -2.0F, 5.0F, 15.0F, 5.0F, new CubeDeformation(-0.25F)).mirror(false), PartPose.offset(0.75F, 0.5F, -0.5F));

		// Left Arm
		PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offset(5.0F, 2.0F, 0.0F));
		left_arm.addOrReplaceChild("left_arm_feather", CubeListBuilder.create().texOffs(20, 36).addBox(-1.5F, -2.5F, -2.5F, 5.0F, 15.0F, 5.0F, new CubeDeformation(-0.25F)), PartPose.ZERO);

		// Right Leg
		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(-1.9F, 12.0F, 0.1F));
		right_leg.addOrReplaceChild("chocobo_leg_right", CubeListBuilder.create().texOffs(0, 56).mirror().addBox(-2.6F, -0.5F, -2.6F, 5.0F, 7.0F, 5.0F).mirror(false), PartPose.ZERO);
		PartDefinition chocobo_claw_right = right_leg.addOrReplaceChild("chocobo_claw_right", CubeListBuilder.create().texOffs(20, 56).mirror().addBox(-2.6F, 6.0F, -2.6F, 5.0F, 6.0F, 5.0F, new CubeDeformation(-0.25F)).mirror(false), PartPose.ZERO);
		PartDefinition left_claw = chocobo_claw_right.addOrReplaceChild("left_claw", CubeListBuilder.create(), PartPose.ZERO);
		left_claw.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(40, 37).addBox(-1.0F, -1.25F, -6.5F, 2.0F, 2.0F, 7.0F), PartPose.offsetAndRotation(-1.0F, 11.0F, -0.5F, 0.0F, 0.3054F, 0.0F));
		PartDefinition right_claw = chocobo_claw_right.addOrReplaceChild("right_claw", CubeListBuilder.create(), PartPose.ZERO);
		right_claw.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(40, 37).addBox(0.0F, -1.25F, -5.5F, 2.0F, 2.0F, 7.0F), PartPose.offsetAndRotation(-1.0F, 11.0F, -0.5F, 0.0F, -0.1745F, 0.0F));

		// Left Leg
		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(1.9F, 12.0F, 0.1F));
		left_leg.addOrReplaceChild("chocobo_leg_left", CubeListBuilder.create().texOffs(0, 56).mirror().addBox(-2.5F, -6.5F, -2.5F, 5.0F, 7.0F, 5.0F).mirror(false), PartPose.offset(0.1F, 6.0F, -0.1F));
		PartDefinition chocobo_claw_left = left_leg.addOrReplaceChild("chocobo_claw_left", CubeListBuilder.create().texOffs(20, 56).mirror().addBox(-2.4F, 6.0F, -2.6F, 5.0F, 6.0F, 5.0F, new CubeDeformation(-0.25F)).mirror(false), PartPose.ZERO);
		PartDefinition left_claw2 = chocobo_claw_left.addOrReplaceChild("left_claw2", CubeListBuilder.create(), PartPose.offset(0.1F, 6.0F, -0.1F));
		left_claw2.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(40, 37).addBox(-1.0F, -1.25F, -6.5F, 2.0F, 2.0F, 7.0F), PartPose.offsetAndRotation(1.0F, 5.0F, -0.5F, 0.0F, -0.3054F, 0.0F));
		PartDefinition right_claw2 = chocobo_claw_left.addOrReplaceChild("right_claw2", CubeListBuilder.create(), PartPose.offset(0.1F, 6.0F, -0.1F));
		right_claw2.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(40, 37).addBox(-2.0F, -1.25F, -5.5F, 2.0F, 2.0F, 7.0F), PartPose.offsetAndRotation(1.0F, 5.0F, -0.5F, 0.0F, 0.1745F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

}