package net.chococraft.client.models.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.chococraft.common.entities.ChocoboEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

/**
 * ModelAdultChocobo - Kraeheart
 * Created using Tabula 7.0.0
 */
public class AdultChocoboModel<T extends ChocoboEntity> extends EntityModel<ChocoboEntity> {
    private final ModelPart body;
    private final ModelPart wing_left;
    private final ModelPart wing_right;
    private final ModelPart neck;
    private final ModelPart head;
    private final ModelPart head_crest_left;
    private final ModelPart head_crest_right;
    private final ModelPart head_crest_middle;

    private final ModelPart left_leg_thigh;
    private final ModelPart left_leg_shin;
    private final ModelPart left_leg_heel;
    private final ModelPart left_leg_toe_inner;
    private final ModelPart left_leg_toe_outer;
    
    private final ModelPart right_leg_thigh;
    private final ModelPart right_leg_shin;
    private final ModelPart right_leg_heel;
    private final ModelPart right_leg_toe_inner;
    private final ModelPart right_leg_toe_outer;

    private final ModelPart child_head;
    private final ModelPart child_body;
    private final ModelPart child_left_leg;
    private final ModelPart child_right_leg;

    public AdultChocoboModel(ModelPart root) {
        this.body = root.getChild("body");

        this.neck = body.getChild("joint_chest_to_body").getChild("body_chest").getChild("neck");
        this.head = neck.getChild("joint_neck_to_head").getChild("head");
        this.head_crest_left = head.getChild("head_crest_left");
        this.head_crest_right = head.getChild("head_crest_right");
        this.head_crest_middle = head.getChild("head_crest_middle");

        this.left_leg_thigh = body.getChild("left_leg_joint_hip").getChild("left_leg_thigh");
        this.left_leg_shin = left_leg_thigh.getChild("left_leg_shin");
        this.left_leg_heel = left_leg_shin.getChild("left_leg_heel");
        this.left_leg_toe_inner = left_leg_heel.getChild("left_leg_toe_inner");
        this.left_leg_toe_outer = left_leg_heel.getChild("left_leg_toe_outer");
        
        this.right_leg_thigh = body.getChild("right_leg_joint_hip").getChild("right_leg_thigh");
        this.right_leg_shin = right_leg_thigh.getChild("right_leg_shin");
        this.right_leg_heel = right_leg_shin.getChild("right_leg_heel");
        this.right_leg_toe_inner = right_leg_heel.getChild("right_leg_toe_inner");
        this.right_leg_toe_outer = right_leg_heel.getChild("right_leg_toe_outer");
        
        this.wing_left = body.getChild("wing_left");
        this.wing_right = body.getChild("wing_right");

        this.child_head = root.getChild("child_head");
        this.child_body = root.getChild("child_body");
        this.child_left_leg = root.getChild("child_left_leg");
        this.child_right_leg = root.getChild("child_right_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create()
                        .texOffs(0, 36).addBox(-6.0F, 1.0F, 0.0F, 12, 11, 16),
                PartPose.offsetAndRotation(1.2F, -8.0F, 0.0F, -0.20943951023931953F, 0.0F, 0.0F));

        //Left leg section
        PartDefinition llegJOINThip = body.addOrReplaceChild("left_leg_joint_hip", CubeListBuilder.create(),
                PartPose.offsetAndRotation(4.0F, 13.0F, 7.5F, 0.41887902047863906F, 0.0F, 0.0F));

        PartDefinition llegThigh = llegJOINThip.addOrReplaceChild("left_leg_thigh", CubeListBuilder.create()
                        .texOffs(60, 49).addBox(-2.0F, -2.0F, -2.0F, 4, 9, 5),
                PartPose.ZERO);

        PartDefinition llegShin = llegThigh.addOrReplaceChild("left_leg_shin", CubeListBuilder.create()
                        .texOffs(79, 48).addBox(-1.5F, 0.0F, -1.5F, 3, 12, 3),
                PartPose.offsetAndRotation(0.5F, 6.0F, 0.5F, -0.4886921905584123F, 0.0F, 0.0F));
        
        PartDefinition llegHeel = llegShin.addOrReplaceChild("left_leg_heel", CubeListBuilder.create()
                        .texOffs(104, 53).addBox(-1.0F, 0.0F, 0.0F, 2, 2, 5),
                PartPose.offsetAndRotation(0.0F, 10.5F, 0.0F, 0.2792526803190927F, 0.0F, 0.0F));
        
        llegHeel.addOrReplaceChild("left_leg_toe_inner", CubeListBuilder.create()
                        .texOffs(92, 54).addBox(-1.0F, -1.0F, -6.0F, 2, 2, 7).mirror(),
                PartPose.offsetAndRotation(-1.0F, 0.0F, -2.0F, 0.15707963267948966F, 0.15707963267948966F, 0.0F));
        
        llegHeel.addOrReplaceChild("left_leg_toe_outer", CubeListBuilder.create()
                        .texOffs(92, 54).addBox(-1.0F, -1.0F, -6.0F, 2, 2, 7),
                PartPose.offsetAndRotation(1.0F, 0.0F, -1.5F, 0.15707963267948966F, -0.3141592653589793F, 0.0F));
        
        //Right leg section
        PartDefinition rlegJOINThip = body.addOrReplaceChild("right_leg_joint_hip", CubeListBuilder.create(),
                PartPose.offsetAndRotation(-4.0F, 13.0F, 7.5F, 0.41887902047863906F, 0.0F, 0.0F));

        PartDefinition rlegThigh = rlegJOINThip.addOrReplaceChild("right_leg_thigh", CubeListBuilder.create()
                        .texOffs(60, 49).addBox(-2.0F, -2.0F, -2.0F, 4, 9, 5).mirror(),
                PartPose.ZERO);

        PartDefinition rlegShin = rlegThigh.addOrReplaceChild("right_leg_shin", CubeListBuilder.create()
                        .texOffs(79, 48).addBox(-1.5F, 0.0F, -1.5F, 3, 12, 3).mirror(),
                PartPose.offsetAndRotation(-0.5F, 6.0F, 0.5F, -0.4886921905584123F, 0.0F, 0.0F));

        PartDefinition rlegHeel = rlegShin.addOrReplaceChild("right_leg_heel", CubeListBuilder.create()
                        .texOffs(104, 53).addBox(-1.0F, 0.0F, 0.0F, 2, 2, 5).mirror(),
                PartPose.offsetAndRotation(0.0F, 10.5F, 0.0F, 0.2792526803190927F, 0.0F, 0.0F));

        rlegHeel.addOrReplaceChild("right_leg_toe_inner", CubeListBuilder.create()
                        .texOffs(92, 54).addBox(-1.0F, -1.0F, -6.0F, 2, 2, 7),
                PartPose.offsetAndRotation(1.0F, 0.0F, -1.5F, 0.15707963267948966F, -0.15707963267948966F, 0.0F));

        rlegHeel.addOrReplaceChild("right_leg_toe_outer", CubeListBuilder.create()
                        .texOffs(92, 54).addBox(-1.0F, -1.0F, -6.0F, 2, 2, 7).mirror(),
                PartPose.offsetAndRotation(-1.0F, 0.0F, -2.0F, 0.15707963267948966F, 0.3141592653589793F, 0.0F));
        
        //Chest section
        PartDefinition JOINTChesttoBody = body.addOrReplaceChild("joint_chest_to_body", CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 1.1F));

        PartDefinition bodyChest = JOINTChesttoBody.addOrReplaceChild("body_chest", CubeListBuilder.create()
                        .texOffs(0, 18).addBox(-4.0F, -2.0F, -9.0F, 8, 8, 10),
                PartPose.offsetAndRotation(0.0F, 1.0F, -2.0F, 1.0471975511965976F, 0.0F, 0.0F));

        PartDefinition neck = bodyChest.addOrReplaceChild("neck", CubeListBuilder.create()
                        .texOffs(36, 20).addBox(-2.0F, -8.5F, -6.0F, 4, 12, 4),
                PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, -0.8377580409572781F, 0.0F, 0.0F));

        PartDefinition JOINTNecktoHead = neck.addOrReplaceChild("joint_neck_to_head", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, -8.0F, -4.0F, 0.13962634015954636F, 0.0F, 0.0F));

        PartDefinition head = JOINTNecktoHead.addOrReplaceChild("head", CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3.0F, -6.0F, -10.0F, 6, 6, 12),
                PartPose.offset(0.0F, -0.0F, 1.0F));

        head.addOrReplaceChild("head_crest_left", CubeListBuilder.create()
                        .texOffs(1, 0).addBox(-0.5F, -4.0F, 0.0F, 1, 7, 4),
                PartPose.offsetAndRotation(-2.5F, -4.0F, 2.0F, -0.20943951023931953F, 0.3839724354387525F, 0.20943951023931953F));

        head.addOrReplaceChild("head_crest_middle", CubeListBuilder.create()
                        .texOffs(25, 0).addBox(-2.5F, -0.5F, 0.0F, 5, 1, 6),
                PartPose.offsetAndRotation(0.0F, -5.0F, 2.0F, 0.45378560551852565F, 0.0F, 0.0F));

        head.addOrReplaceChild("head_crest_right", CubeListBuilder.create()
                        .texOffs(1, 0).addBox(-0.5F, -4.0F, 0.0F, 1, 7, 4).mirror(),
                PartPose.offsetAndRotation(2.5F, -4.0F, 2.0F, -0.20943951023931953F, -0.3839724354387525F, -0.20943951023931953F));


        //Bag section
        PartDefinition packBag = body.addOrReplaceChild("pack_bag", CubeListBuilder.create()
                        .texOffs(36, 2).addBox(-5.0F, -6.0F, -6.0F, 10, 6, 12),
                PartPose.offset(0.0F, 1.0F, 8.5F));

        PartDefinition saddleBagLeft = body.addOrReplaceChild("saddle_bag_left", CubeListBuilder.create()
                        .texOffs(81, 6).addBox(0.0F, -4.0F, -3.0F, 2, 8, 6),
                PartPose.offset(5.0F, 4.0F, 14.0F));

        PartDefinition saddleBagRight = body.addOrReplaceChild("saddle_bag_right", CubeListBuilder.create()
                        .texOffs(81, 6).addBox(-2.0F, -4.0F, -3.0F, 2, 8, 6).mirror(),
                PartPose.offset(-5.0F, 4.0F, 14.0F));

        //Wing section
        body.addOrReplaceChild("wing_left", CubeListBuilder.create()
                        .texOffs(83, 21).addBox(-0.5F, -1.0F, -1.0F, 1, 10, 16),
                PartPose.offsetAndRotation(6.5F, 1.0F, 0.0F, 0.06981317007977318F, 0.0F, 0.0F));

        body.addOrReplaceChild("wing_right", CubeListBuilder.create()
                        .texOffs(83, 21).addBox(-0.5F, -1.0F, -1.0F, 1, 10, 16).mirror(),
                PartPose.offsetAndRotation(-6.5F, 1.0F, 0.0F, 0.06981317007977318F, 0.0F, 0.0F));

        //Tail feather section
        body.addOrReplaceChild("tail_feathers_top", CubeListBuilder.create()
                        .texOffs(56, 25).addBox(-5.5F, 0.0F, 0.0F, 11, 1, 10),
                PartPose.offsetAndRotation(0.0F, 3.0F, 16.0F, 0.8726646259971648F, 0.0F, 0.0F));

        body.addOrReplaceChild("tail_feathers_bottom", CubeListBuilder.create()
                        .texOffs(102, 29).addBox(-3.5F, 0.0F, 0.0F, 7, 1, 6),
                PartPose.offsetAndRotation(0.0F, 6.0F, 16.0F, 0.3490658503988659F, 0.0F, 0.0F));

        body.addOrReplaceChild("tail_feathers_left", CubeListBuilder.create()
                        .texOffs(44, 28).addBox(-0.5F, -10.0F, 0.0F, 1, 14, 9),
                PartPose.offsetAndRotation(3.5F, 5.0F, 15.5F, -0.20943951023931953F, 0.3839724354387525F, 0.3490658503988659F));

        body.addOrReplaceChild("tail_feathers_right", CubeListBuilder.create()
                        .texOffs(44, 28).addBox(-0.5F, -10.0F, 0.0F, 1, 14, 9).mirror(),
                PartPose.offsetAndRotation(-3.5F, 5.0F, 15.5F, -0.20943951023931953F, -0.3839724354387525F, -0.3490658503988659F));



        PartDefinition childBody = partdefinition.addOrReplaceChild("child_body", CubeListBuilder.create()
                        .texOffs(0, 6).addBox(-2F, -2.5F, -2F, 4, 4, 4),
                PartPose.offset(0.0F, 20F, 0.0F));

        PartDefinition childHead = partdefinition.addOrReplaceChild("child_head", CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1.5F, -3F, -1.5F, 3, 3, 3),
                PartPose.offset(0.0F, 18F, -2.5F));

        PartDefinition childLeftLeg = partdefinition.addOrReplaceChild("child_left_leg", CubeListBuilder.create()
                        .texOffs(12, 0).addBox(-0.5F, 0.0F, -1F, 1, 2, 1),
                PartPose.offset(1.0F, 22F, 0.5F));

        PartDefinition childRightLeg = partdefinition.addOrReplaceChild("child_right_leg", CubeListBuilder.create()
                        .texOffs(12, 0).addBox(-0.5F, 0.0F, -1F, 1, 2, 1).mirror(),
                PartPose.offset(-1F, 22F, 0.5F));

        return LayerDefinition.create(meshdefinition, 128, 64);
    }

    @Override
    public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (young) {
            child_head.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            child_body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            child_right_leg.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            child_left_leg.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        } else {
            this.body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        }
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
    public void setupAnim(ChocoboEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entityIn.isBaby()) {
            child_head.xRot = -(headPitch / 57.29578F);
            child_head.yRot = netHeadYaw / 57.29578F;
            child_right_leg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
            child_left_leg.yRot = Mth.cos(limbSwing * 0.6662F + 3.141593F) * 1.4F * limbSwingAmount;
        } else {
            // ageInTicks = wing z movement (flutter)
            // netHeadYaw = head y movement
            // headPitch = head x movement
            // cos(limbSwing) and limbSwingAmount = leg movement

            float pi = (float) Math.PI;

            // head/neck movement
            head.xRot = headPitch * (pi / 180F);
            head.yRot = netHeadYaw * (pi / 180F);
            neck.xRot = -0.8F;
            neck.yRot = 0.0F;
            head_crest_left.xRot = head.xRot + 0.1745329F;
            head_crest_left.yRot = head.yRot;
            head_crest_right.xRot = head.xRot;
            head_crest_right.yRot = head.yRot;
            head_crest_middle.xRot = head.xRot;
            head_crest_middle.yRot = head.yRot;


            // walking animation
            this.setRightLegXRotation(Mth.cos(limbSwing * 0.6662F) * 0.8F * limbSwingAmount);
            this.setLeftLegXRotation(Mth.cos(limbSwing * 0.6662F + pi) * 0.8F * limbSwingAmount);

            // riding animation
            Vec3 motion = entityIn.getDeltaMovement();
            if (Math.abs(motion.x) > 0.1F || Math.abs(motion.z) > 0.1F) {
                neck.xRot = -0.5F;
            } else {
                neck.xRot = -0.8F;
            }

            // flying animation
            if (Math.abs(motion.y) > 0.1F || !entityIn.isOnGround()) {
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
    }

    private void setLeftLegXRotation(float deltaX) {
        setRotateAngle(left_leg_thigh, 0.2094395F + deltaX, 0F, 0F);
        setRotateAngle(left_leg_shin, -0.1919862F + deltaX, 0F, 0F);
        setRotateAngle(left_leg_toe_inner, 0.3490659F + deltaX, 0.1570796F, 0F);
        setRotateAngle(left_leg_toe_outer, 0.3490659F + deltaX, -0.1919862F, 0F);
        setRotateAngle(left_leg_heel, -0.3F + deltaX, 0F, 0F);
    }

    private void setRightLegXRotation(float deltaX) {
        setRotateAngle(right_leg_thigh, 0.2094395F + deltaX, 0F, 0F);
        setRotateAngle(right_leg_shin, -0.1919862F + deltaX, 0F, 0F);
        setRotateAngle(right_leg_toe_outer, 0.3490659F + deltaX, 0.1919862F, 0F);
        setRotateAngle(right_leg_toe_inner, 0.3490659F + deltaX, -0.1570796F, 0F);
        setRotateAngle(right_leg_heel, -0.3F + deltaX, 0F, 0F);
    }
}
