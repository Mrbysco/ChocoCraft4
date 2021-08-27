package net.chococraft.client.models.entities;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.chococraft.common.entities.ChocoboEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

/**
 * ModelAdultChocobo - Kraeheart
 * Created using Tabula 7.0.0
 */
public class AdultChocoboModel<T extends ChocoboEntity> extends EntityModel<ChocoboEntity> {
    public ModelRenderer BodyMain;
    public ModelRenderer JOINTChesttoBody;
    public ModelRenderer llegJOINThip;
    public ModelRenderer rlegJOINThip;
    public ModelRenderer WingLeft;
    public ModelRenderer WingRight;
    public ModelRenderer TailFeathersLeft;
    public ModelRenderer TailFeathersRight;
    public ModelRenderer TailFeathersTop;
    public ModelRenderer TailFeathersBottom;
    public ModelRenderer PackBag;
    public ModelRenderer SaddleBagLeft;
    public ModelRenderer SaddleBagRight;
    public ModelRenderer BodyChest;
    public ModelRenderer Neck;
    public ModelRenderer JOINTNecktoHead;
    public ModelRenderer Head;
    public ModelRenderer HeadCrestLeft;
    public ModelRenderer HeadCrestRight;
    public ModelRenderer HeadCrestMiddle;
    public ModelRenderer llegThigh;
    public ModelRenderer llegShin;
    public ModelRenderer llegHeel;
    public ModelRenderer llegToeOuter;
    public ModelRenderer llegToeInner;
    public ModelRenderer rlegThigh;
    public ModelRenderer rlegShin;
    public ModelRenderer rlegHeel;
    public ModelRenderer rlegToeInner;
    public ModelRenderer rlegToeOuter;

    public ModelRenderer child_head;
    public ModelRenderer child_body;
    public ModelRenderer child_rightleg;
    public ModelRenderer child_leftleg;

    public AdultChocoboModel() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.TailFeathersTop = new ModelRenderer(this, 56, 25);
        this.TailFeathersTop.setRotationPoint(0.0F, 3.0F, 16.0F);
        this.TailFeathersTop.addBox(-5.5F, 0.0F, 0.0F, 11, 1, 10, 0.0F);
        this.setRotateAngle(TailFeathersTop, 0.8726646259971648F, 0.0F, 0.0F);
        this.llegJOINThip = new ModelRenderer(this, 0, 0);
        this.llegJOINThip.setRotationPoint(4.0F, 13.0F, 7.5F);
        this.llegJOINThip.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.setRotateAngle(llegJOINThip, 0.41887902047863906F, 0.0F, 0.0F);
        this.PackBag = new ModelRenderer(this, 36, 2);
        this.PackBag.setRotationPoint(0.0F, 1.0F, 8.5F);
        this.PackBag.addBox(-5.0F, -6.0F, -6.0F, 10, 6, 12, 0.0F);
        this.llegToeInner = new ModelRenderer(this, 92, 54);
        this.llegToeInner.mirror = true;
        this.llegToeInner.setRotationPoint(-1.0F, 0.0F, -2.0F);
        this.llegToeInner.addBox(-1.0F, -1.0F, -6.0F, 2, 2, 7, 0.0F);
        this.setRotateAngle(llegToeInner, 0.15707963267948966F, 0.15707963267948966F, 0.0F);
        this.WingLeft = new ModelRenderer(this, 83, 21);
        this.WingLeft.setRotationPoint(6.5F, 1.0F, 0.0F);
        this.WingLeft.addBox(-0.5F, -1.0F, -1.0F, 1, 10, 16, 0.0F);
        this.setRotateAngle(WingLeft, 0.06981317007977318F, 0.0F, 0.0F);
        this.Neck = new ModelRenderer(this, 36, 20);
        this.Neck.setRotationPoint(0.0F, 0.5F, 0.0F);
        this.Neck.addBox(-2.0F, -8.5F, -6.0F, 4, 12, 4, 0.0F);
        this.setRotateAngle(Neck, -0.8377580409572781F, 0.0F, 0.0F);
        this.rlegToeOuter = new ModelRenderer(this, 92, 54);
        this.rlegToeOuter.mirror = true;
        this.rlegToeOuter.setRotationPoint(-1.0F, 0.0F, -2.0F);
        this.rlegToeOuter.addBox(-1.0F, -1.0F, -6.0F, 2, 2, 7, 0.0F);
        this.setRotateAngle(rlegToeOuter, 0.15707963267948966F, 0.3141592653589793F, 0.0F);
        this.SaddleBagLeft = new ModelRenderer(this, 81, 6);
        this.SaddleBagLeft.setRotationPoint(5.0F, 4.0F, 14.0F);
        this.SaddleBagLeft.addBox(0.0F, -4.0F, -3.0F, 2, 8, 6, 0.0F);
        this.BodyMain = new ModelRenderer(this, 0, 36);
        this.BodyMain.setRotationPoint(1.2F, -8.0F, 0.0F);
        this.BodyMain.addBox(-6.0F, 1.0F, 0.0F, 12, 11, 16, 0.0F);
        this.setRotateAngle(BodyMain, -0.20943951023931953F, 0.0F, 0.0F);
        this.HeadCrestRight = new ModelRenderer(this, 1, 0);
        this.HeadCrestRight.mirror = true;
        this.HeadCrestRight.setRotationPoint(-2.5F, -4.0F, 2.0F);
        this.HeadCrestRight.addBox(-0.5F, -4.0F, 0.0F, 1, 7, 4, 0.0F);
        this.setRotateAngle(HeadCrestRight, -0.20943951023931953F, -0.3839724354387525F, -0.20943951023931953F);
        this.TailFeathersBottom = new ModelRenderer(this, 102, 29);
        this.TailFeathersBottom.setRotationPoint(0.0F, 6.0F, 16.0F);
        this.TailFeathersBottom.addBox(-3.5F, 0.0F, 0.0F, 7, 1, 6, 0.0F);
        this.setRotateAngle(TailFeathersBottom, 0.3490658503988659F, 0.0F, 0.0F);
        this.BodyChest = new ModelRenderer(this, 0, 18);
        this.BodyChest.setRotationPoint(0.0F, 1.0F, -2.0F);
        this.BodyChest.addBox(-4.0F, -2.0F, -9.0F, 8, 8, 10, 0.0F);
        this.setRotateAngle(BodyChest, 1.0471975511965976F, 0.0F, 0.0F);
        this.JOINTNecktoHead = new ModelRenderer(this, 0, 0);
        this.JOINTNecktoHead.setRotationPoint(0.0F, -8.0F, -4.0F);
        this.JOINTNecktoHead.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.setRotateAngle(JOINTNecktoHead, 0.13962634015954636F, 0.0F, 0.0F);
        this.TailFeathersRight = new ModelRenderer(this, 44, 28);
        this.TailFeathersRight.mirror = true;
        this.TailFeathersRight.setRotationPoint(-3.5F, 5.0F, 15.5F);
        this.TailFeathersRight.addBox(-0.5F, -10.0F, 0.0F, 1, 14, 9, 0.0F);
        this.setRotateAngle(TailFeathersRight, -0.20943951023931953F, -0.3839724354387525F, -0.3490658503988659F);
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setRotationPoint(0.0F, -0.0F, 1.0F);
        this.Head.addBox(-3.0F, -6.0F, -10.0F, 6, 6, 12, 0.0F);
        this.llegToeOuter = new ModelRenderer(this, 92, 54);
        this.llegToeOuter.setRotationPoint(1.0F, 0.0F, -1.5F);
        this.llegToeOuter.addBox(-1.0F, -1.0F, -6.0F, 2, 2, 7, 0.0F);
        this.setRotateAngle(llegToeOuter, 0.15707963267948966F, -0.3141592653589793F, 0.0F);
        this.rlegHeel = new ModelRenderer(this, 104, 53);
        this.rlegHeel.mirror = true;
        this.rlegHeel.setRotationPoint(0.0F, 10.5F, 0.0F);
        this.rlegHeel.addBox(-1.0F, 0.0F, 0.0F, 2, 2, 5, 0.0F);
        this.setRotateAngle(rlegHeel, 0.2792526803190927F, 0.0F, 0.0F);
        this.WingRight = new ModelRenderer(this, 83, 21);
        this.WingRight.mirror = true;
        this.WingRight.setRotationPoint(-6.5F, 1.0F, 0.0F);
        this.WingRight.addBox(-0.5F, -1.0F, -1.0F, 1, 10, 16, 0.0F);
        this.setRotateAngle(WingRight, 0.06981317007977318F, 0.0F, 0.0F);
        this.llegThigh = new ModelRenderer(this, 60, 49);
        this.llegThigh.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.llegThigh.addBox(-2.0F, -2.0F, -2.0F, 4, 9, 5, 0.0F);
        this.TailFeathersLeft = new ModelRenderer(this, 44, 28);
        this.TailFeathersLeft.setRotationPoint(3.5F, 5.0F, 15.5F);
        this.TailFeathersLeft.addBox(-0.5F, -10.0F, 0.0F, 1, 14, 9, 0.0F);
        this.setRotateAngle(TailFeathersLeft, -0.20943951023931953F, 0.3839724354387525F, 0.3490658503988659F);
        this.JOINTChesttoBody = new ModelRenderer(this, 0, 0);
        this.JOINTChesttoBody.setRotationPoint(0.0F, 0.0F, 1.1F);
        this.JOINTChesttoBody.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.SaddleBagRight = new ModelRenderer(this, 81, 6);
        this.SaddleBagRight.mirror = true;
        this.SaddleBagRight.setRotationPoint(-5.0F, 4.0F, 14.0F);
        this.SaddleBagRight.addBox(-2.0F, -4.0F, -3.0F, 2, 8, 6, 0.0F);
        this.rlegToeInner = new ModelRenderer(this, 92, 54);
        this.rlegToeInner.setRotationPoint(1.0F, 0.0F, -1.5F);
        this.rlegToeInner.addBox(-1.0F, -1.0F, -6.0F, 2, 2, 7, 0.0F);
        this.setRotateAngle(rlegToeInner, 0.15707963267948966F, -0.15707963267948966F, 0.0F);
        this.HeadCrestLeft = new ModelRenderer(this, 1, 0);
        this.HeadCrestLeft.setRotationPoint(2.5F, -4.0F, 2.0F);
        this.HeadCrestLeft.addBox(-0.5F, -4.0F, 0.0F, 1, 7, 4, 0.0F);
        this.setRotateAngle(HeadCrestLeft, -0.20943951023931953F, 0.3839724354387525F, 0.20943951023931953F);
        this.llegHeel = new ModelRenderer(this, 104, 53);
        this.llegHeel.setRotationPoint(0.0F, 10.5F, 0.0F);
        this.llegHeel.addBox(-1.0F, 0.0F, 0.0F, 2, 2, 5, 0.0F);
        this.setRotateAngle(llegHeel, 0.2792526803190927F, 0.0F, 0.0F);
        this.rlegThigh = new ModelRenderer(this, 60, 49);
        this.rlegThigh.mirror = true;
        this.rlegThigh.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.rlegThigh.addBox(-2.0F, -2.0F, -2.0F, 4, 9, 5, 0.0F);
        this.llegShin = new ModelRenderer(this, 79, 48);
        this.llegShin.setRotationPoint(0.5F, 6.0F, 0.5F);
        this.llegShin.addBox(-1.5F, 0.0F, -1.5F, 3, 12, 3, 0.0F);
        this.setRotateAngle(llegShin, -0.4886921905584123F, 0.0F, 0.0F);
        this.rlegJOINThip = new ModelRenderer(this, 0, 0);
        this.rlegJOINThip.setRotationPoint(-4.0F, 13.0F, 7.5F);
        this.rlegJOINThip.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.setRotateAngle(rlegJOINThip, 0.41887902047863906F, 0.0F, 0.0F);
        this.rlegShin = new ModelRenderer(this, 79, 48);
        this.rlegShin.mirror = true;
        this.rlegShin.setRotationPoint(-0.5F, 6.0F, 0.5F);
        this.rlegShin.addBox(-1.5F, 0.0F, -1.5F, 3, 12, 3, 0.0F);
        this.setRotateAngle(rlegShin, -0.4886921905584123F, 0.0F, 0.0F);
        this.HeadCrestMiddle = new ModelRenderer(this, 25, 0);
        this.HeadCrestMiddle.setRotationPoint(0.0F, -5.0F, 2.0F);
        this.HeadCrestMiddle.addBox(-2.5F, -0.5F, 0.0F, 5, 1, 6, 0.0F);
        this.setRotateAngle(HeadCrestMiddle, 0.45378560551852565F, 0.0F, 0.0F);
        this.BodyMain.addChild(this.TailFeathersTop);
        this.BodyMain.addChild(this.llegJOINThip);
        this.BodyMain.addChild(this.PackBag);
        this.llegHeel.addChild(this.llegToeInner);
        this.BodyMain.addChild(this.WingLeft);
        this.BodyChest.addChild(this.Neck);
        this.rlegHeel.addChild(this.rlegToeOuter);
        this.BodyMain.addChild(this.SaddleBagLeft);
        this.Head.addChild(this.HeadCrestRight);
        this.BodyMain.addChild(this.TailFeathersBottom);
        this.JOINTChesttoBody.addChild(this.BodyChest);
        this.Neck.addChild(this.JOINTNecktoHead);
        this.BodyMain.addChild(this.TailFeathersRight);
        this.JOINTNecktoHead.addChild(this.Head);
        this.llegHeel.addChild(this.llegToeOuter);
        this.rlegShin.addChild(this.rlegHeel);
        this.BodyMain.addChild(this.WingRight);
        this.llegJOINThip.addChild(this.llegThigh);
        this.BodyMain.addChild(this.TailFeathersLeft);
        this.BodyMain.addChild(this.JOINTChesttoBody);
        this.BodyMain.addChild(this.SaddleBagRight);
        this.rlegHeel.addChild(this.rlegToeInner);
        this.Head.addChild(this.HeadCrestLeft);
        this.llegShin.addChild(this.llegHeel);
        this.rlegJOINThip.addChild(this.rlegThigh);
        this.llegThigh.addChild(this.llegShin);
        this.BodyMain.addChild(this.rlegJOINThip);
        this.rlegThigh.addChild(this.rlegShin);
        this.Head.addChild(this.HeadCrestMiddle);

        // child
        this.textureWidth = 64;
        this.textureHeight = 32;
        child_head = new ModelRenderer(this, 0, 0);
        child_head.addBox(-1.5F, -3F, -1.5F, 3, 3, 3, 0.0F);
        child_head.setRotationPoint(0.0F, 18F, -2.5F);
        child_body = new ModelRenderer(this, 0, 6);
        child_body.addBox(-2F, -2.5F, -2F, 4, 4, 4, 0.0F);
        child_body.setRotationPoint(0.0F, 20F, 0.0F);
        child_rightleg = new ModelRenderer(this, 12, 0);
        child_rightleg.addBox(-0.5F, 0.0F, -1F, 1, 2, 1, 0.0F);
        child_rightleg.setRotationPoint(-1F, 22F, 0.5F);
        child_leftleg = new ModelRenderer(this, 12, 0);
        child_leftleg.addBox(-0.5F, 0.0F, -1F, 1, 2, 1, 0.0F);
        child_leftleg.setRotationPoint(1.0F, 22F, 0.5F);
    }


    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (isChild) {
            child_head.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            child_body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            child_rightleg.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            child_leftleg.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        } else
            this.BodyMain.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    @Override
    public void setRotationAngles(ChocoboEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!(entityIn instanceof ChocoboEntity)) return;

        if (((ChocoboEntity) entityIn).isChild()) {
            child_head.rotateAngleX = -(headPitch / 57.29578F);
            child_head.rotateAngleY = netHeadYaw / 57.29578F;
            child_rightleg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
            child_leftleg.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 1.4F * limbSwingAmount;
        } else {
            // ageInTicks = wing z movement (flutter)
            // netHeadYaw = head y movement
            // headPitch = head x movement
            // cos(limbSwing) and limbSwingAmount = leg movement

            float pi = (float) Math.PI;

            // head/neck movement
            Head.rotateAngleX = headPitch * (pi / 180F);
            Head.rotateAngleY = netHeadYaw * (pi / 180F);
            Neck.rotateAngleX = -0.8F;
            Neck.rotateAngleY = 0.0F;
            HeadCrestLeft.rotateAngleX = Head.rotateAngleX + 0.1745329F;
            HeadCrestLeft.rotateAngleY = Head.rotateAngleY;
            HeadCrestRight.rotateAngleX = Head.rotateAngleX;
            HeadCrestRight.rotateAngleY = Head.rotateAngleY;
            HeadCrestMiddle.rotateAngleX = Head.rotateAngleX;
            HeadCrestMiddle.rotateAngleY = Head.rotateAngleY;


            // walking animation
            this.setRightLegXRotation(MathHelper.cos(limbSwing * 0.6662F) * 0.8F * limbSwingAmount);
            this.setLeftLegXRotation(MathHelper.cos(limbSwing * 0.6662F + pi) * 0.8F * limbSwingAmount);

            // riding animation
            Vector3d motion = entityIn.getMotion();
            if (Math.abs(motion.x) > 0.1F || Math.abs(motion.z) > 0.1F) {
                Neck.rotateAngleX = -0.5F;
            } else {
                Neck.rotateAngleX = -0.8F;
            }

            // flying animation
            if (Math.abs(motion.y) > 0.1F || !entityIn.isOnGround()) {
                setRotateAngle(WingRight, (pi / 2F) - (pi / 12), -0.0174533F, -90 + MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount);
                setRotateAngle(WingLeft, (pi / 2F) - (pi / 12), 0.0174533F, 90 + MathHelper.cos(limbSwing * 0.6662F + pi) * 1.4F * limbSwingAmount);
                this.setLeftLegXRotation(0.6F);
                this.setRightLegXRotation(0.6F);
            } else {
                // reset wings
                setRotateAngle(WingRight, 0F, -0.0174533F, 0F);
                setRotateAngle(WingLeft, 0F, 0.0174533F, 0F);
            }
        }
    }

    private void setLeftLegXRotation(float deltaX) {
        setRotateAngle(llegThigh, 0.2094395F + deltaX, 0F, 0F);
        setRotateAngle(llegShin, -0.1919862F + deltaX, 0F, 0F);
        setRotateAngle(llegToeInner, 0.3490659F + deltaX, 0.1570796F, 0F);
        setRotateAngle(llegToeOuter, 0.3490659F + deltaX, -0.1919862F, 0F);
        setRotateAngle(llegHeel, -0.3F + deltaX, 0F, 0F);
    }

    private void setRightLegXRotation(float deltaX) {
        setRotateAngle(rlegThigh, 0.2094395F + deltaX, 0F, 0F);
        setRotateAngle(rlegShin, -0.1919862F + deltaX, 0F, 0F);
        setRotateAngle(rlegToeOuter, 0.3490659F + deltaX, 0.1919862F, 0F);
        setRotateAngle(rlegToeInner, 0.3490659F + deltaX, -0.1570796F, 0F);
        setRotateAngle(rlegHeel, -0.3F + deltaX, 0F, 0F);
    }
}
