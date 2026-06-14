package com.cruzer.simpleboats.client.model;

import com.cruzer.simpleboats.client.renderer.state.PoweredBoatRenderState;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class GenericBoatModel<S extends PoweredBoatRenderState> extends EntityModel<S>
{
    private final ModelPart rope;
    private final ModelPart floater;
    private final ModelPart wood_hull;

    public GenericBoatModel(ModelPart root)
    {
        super(root);
        this.rope = root.getChild("rope");
        this.floater = root.getChild("floater");
        this.wood_hull = root.getChild("wood_hull");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition root = modelData.getRoot();

        PartDefinition rope = root.addOrReplaceChild("rope", CubeListBuilder.create().texOffs(58, 407).addBox(-13.5F, -13.5F, -49.9688F, 27.0F, 14.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(414, 313).addBox(-11.5117F, 0.0F, -51.2344F, 23.0234F, 5.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(248, 0).addBox(18.0859F, -13.0F, -37.5F, 1.0F, 15.0F, 75.0F, new CubeDeformation(0.0F))
                .texOffs(0, 261).addBox(-19.0859F, -13.0F, -37.5F, 1.0F, 15.0F, 75.0F, new CubeDeformation(0.0F))
                .texOffs(302, 442).addBox(46.5F, -3.5F, -38.3F, 5.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(92, 443).addBox(46.5F, -3.5F, 34.7F, 5.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(110, 443).addBox(-51.5F, -3.5F, 34.7F, 5.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(398, 446).addBox(-51.5F, -3.5F, -38.3F, 5.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(252, 453).addBox(16.0F, -16.5F, 34.5F, 2.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(264, 453).addBox(-18.0F, -16.5F, 34.5F, 2.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(276, 453).addBox(-18.0F, -16.5F, -38.5F, 2.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(288, 453).addBox(16.0F, -16.5F, -38.5F, 2.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r1 = rope.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(70, 455).addBox(-27.0F, -0.5F, -2.1F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(58, 455).addBox(-27.0F, -0.5F, 70.9F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-26.0F, -16.0F, -36.5F, 0.0F, 0.0F, -0.3927F));

        PartDefinition cube_r2 = rope.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(404, 454).addBox(25.0F, -0.5F, -2.1F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(392, 454).addBox(25.0F, -0.5F, -75.1F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(26.0F, -16.0F, 36.5F, 0.0F, 0.0F, 0.3927F));

        PartDefinition cube_r3 = rope.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(206, 441).addBox(-13.1094F, 0.2852F, -9.4F, 13.1094F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.5547F, -8.781F, -72.6539F, -0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r4 = rope.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(322, 426).addBox(-15.0F, -6.5F, -9.0F, 17.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.5F, -8.9875F, -72.9916F, -0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r5 = rope.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(122, 407).addBox(-6.5F, 1.1172F, 2.2383F, 13.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(168, 441).addBox(-8.5F, -6.8828F, 2.2383F, 17.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.2664F, 67.3394F, 0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r6 = rope.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(378, 164).addBox(-6.5F, 1.1172F, -4.2383F, 13.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(322, 440).addBox(-8.5F, -6.8828F, -4.2383F, 17.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.2664F, -67.3394F, -0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r7 = rope.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(220, 140).addBox(-5.0F, -2.5F, -7.75F, 9.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(24, 443).addBox(-5.0F, -2.5F, 0.5F, 9.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -24.9837F, 94.2596F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r8 = rope.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(220, 131).addBox(-4.0F, -2.5F, 3.8F, 9.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 443).addBox(-4.0F, -2.5F, -3.5F, 9.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -24.9837F, -94.2596F, -0.7854F, 0.0F, 0.0F));

        PartDefinition floater = root.addOrReplaceChild("floater", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r9 = floater.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(196, 459).addBox(-2.0F, -0.5F, -6.0F, 3.5391F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(140, 459).addBox(-2.0F, -0.5F, 2.0F, 3.5391F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(124, 453).addBox(-1.5F, 2.5F, -2.5F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(452, 448).addBox(-1.5F, -5.5F, -2.5F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(350, 450).addBox(-1.5F, -5.5F, 2.5F, 3.0F, 11.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(338, 450).addBox(-1.5F, -5.5F, -5.5F, 3.0F, 11.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(458, 59).addBox(39.4609F, -0.5F, -6.0F, 3.5391F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(458, 44).addBox(39.4609F, -0.5F, 2.0F, 3.5391F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(108, 451).addBox(39.5F, 2.5F, -2.5F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(92, 451).addBox(39.5F, -5.5F, -2.5F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(428, 448).addBox(39.5F, -5.5F, 2.5F, 3.0F, 11.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(448, 380).addBox(39.5F, -5.5F, -5.5F, 3.0F, 11.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-20.5F, -5.5F, 0.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r10 = floater.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(182, 459).addBox(-2.0F, 3.0F, 2.0F, 3.5391F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(168, 459).addBox(-2.0F, 3.0F, -6.0F, 3.5391F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(458, 54).addBox(39.4609F, 3.0F, 2.0F, 3.5391F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(458, 49).addBox(39.4609F, 3.0F, -6.0F, 3.5391F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-20.5F, -7.9749F, 2.4749F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r11 = floater.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(110, 459).addBox(-2.0F, -0.5F, -6.0F, 3.5391F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(28, 459).addBox(-2.0F, -0.5F, 2.0F, 3.5391F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(216, 451).addBox(-1.5F, 2.5F, -2.5F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(200, 451).addBox(-1.5F, -5.5F, -2.5F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(326, 450).addBox(-1.5F, -5.5F, 2.5F, 3.0F, 11.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(314, 450).addBox(-1.5F, -5.5F, -5.5F, 3.0F, 11.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(452, 456).addBox(39.4609F, -0.5F, -6.0F, 3.5391F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(436, 164).addBox(39.4609F, -0.5F, 2.0F, 3.5391F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(32, 451).addBox(39.5F, 2.5F, -2.5F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(16, 451).addBox(39.5F, -5.5F, -2.5F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(448, 366).addBox(39.5F, -5.5F, 2.5F, 3.0F, 11.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(448, 352).addBox(39.5F, -5.5F, -5.5F, 3.0F, 11.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-20.5F, -5.5F, 26.5F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r12 = floater.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(96, 459).addBox(-2.0F, 3.0F, 2.0F, 3.5391F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(82, 459).addBox(-2.0F, 3.0F, -6.0F, 3.5391F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(450, 164).addBox(39.4609F, 3.0F, 2.0F, 3.5391F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(448, 394).addBox(39.4609F, 3.0F, -6.0F, 3.5391F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-20.5F, -7.9749F, 28.9749F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r13 = floater.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(14, 459).addBox(-2.0F, -0.5F, -6.0F, 3.5391F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(184, 451).addBox(-1.5F, 2.5F, -2.5F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(376, 458).addBox(-2.0F, -0.5F, 2.0F, 3.5391F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(168, 451).addBox(-1.5F, -5.5F, -2.5F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(302, 450).addBox(-1.5F, -5.5F, 2.5F, 3.0F, 11.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(440, 448).addBox(-1.5F, -5.5F, -5.5F, 3.0F, 11.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(422, 164).addBox(39.4609F, -0.5F, -6.0F, 3.5391F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 451).addBox(39.5F, 2.5F, -2.5F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(408, 164).addBox(39.4609F, -0.5F, 2.0F, 3.5391F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(376, 450).addBox(39.5F, -5.5F, -2.5F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(448, 338).addBox(39.5F, -5.5F, 2.5F, 3.0F, 11.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(416, 446).addBox(39.5F, -5.5F, -5.5F, 3.0F, 11.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-20.5F, -5.5F, -26.5F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r14 = floater.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(0, 459).addBox(-2.0F, 3.0F, 2.0F, 3.5391F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(458, 64).addBox(-2.0F, 3.0F, -6.0F, 3.5391F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(138, 422).addBox(39.4609F, 3.0F, 2.0F, 3.5391F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(330, 169).addBox(39.4609F, 3.0F, -6.0F, 3.5391F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-20.5F, -7.9749F, -24.0251F, -0.7854F, 0.0F, 0.0F));

        PartDefinition wood_hull = root.addOrReplaceChild("wood_hull", CubeListBuilder.create().texOffs(0, 0).addBox(-16.0F, -1.0F, -46.0F, 32.0F, 7.0F, 92.0F, new CubeDeformation(0.0F))
                .texOffs(282, 399).addBox(-18.0F, -6.0F, -16.0F, 36.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(374, 399).addBox(-18.0F, -6.0F, 6.0F, 36.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(379, 404).addBox(-18.0F, -11.0F, 27.0F, 36.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 99).addBox(-17.0F, -2.0F, -32.0F, 34.0F, 1.0F, 76.0F, new CubeDeformation(0.0F))
                .texOffs(434, 432).addBox(-11.0F, -0.1732F, 45.7612F, 5.0F, 4.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(436, 235).addBox(6.0F, -0.1732F, 45.7612F, 5.0F, 4.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(368, 426).addBox(-13.0F, -13.0F, 46.0F, 4.0F, 13.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(138, 429).addBox(9.0F, -13.0F, 46.0F, 4.0F, 13.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(400, 0).addBox(-13.0F, -13.0F, 57.0F, 26.0F, 13.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(338, 411).addBox(-10.9961F, -0.4078F, -55.9134F, 21.9922F, 5.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(0, 351).addBox(-13.0F, -13.0F, -67.0F, 26.0F, 13.0F, 21.0F, new CubeDeformation(0.0F))
                .texOffs(344, 235).addBox(-16.0F, -13.0F, -46.0F, 32.0F, 12.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(400, 72).addBox(-16.0F, -9.0F, 42.0F, 32.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 385).addBox(-16.0F, -13.0F, 32.0F, 32.0F, 4.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(220, 169).addBox(-26.0F, -16.0F, 34.9961F, 52.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 176).addBox(-51.0039F, -3.0F, -41.0F, 4.0F, 3.0F, 82.0F, new CubeDeformation(0.0F))
                .texOffs(172, 176).addBox(47.0039F, -3.0F, -41.0F, 4.0F, 3.0F, 82.0F, new CubeDeformation(0.0F))
                .texOffs(378, 158).addBox(-26.0F, -16.0F, -38.0039F, 52.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(248, 90).addBox(18.0F, -13.0F, -32.0F, 1.0F, 15.0F, 64.0F, new CubeDeformation(0.0F))
                .texOffs(152, 340).addBox(17.0F, -13.0F, -32.0F, 1.0F, 2.0F, 64.0F, new CubeDeformation(0.0F))
                .texOffs(414, 338).addBox(16.0F, -13.0F, -46.0F, 3.0F, 15.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(414, 367).addBox(16.0F, -13.0F, 32.0F, 3.0F, 15.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(282, 261).addBox(16.0F, -3.0F, -32.0F, 2.0F, 5.0F, 64.0F, new CubeDeformation(0.0F))
                .texOffs(152, 261).addBox(-19.0F, -13.0F, -32.0F, 1.0F, 15.0F, 64.0F, new CubeDeformation(0.0F))
                .texOffs(344, 169).addBox(-18.0F, -13.0F, -32.0F, 1.0F, 2.0F, 64.0F, new CubeDeformation(0.0F))
                .texOffs(238, 149).addBox(17.0F, -11.0F, 20.0F, 1.0F, 9.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(48, 440).addBox(17.0F, -11.0F, -2.0F, 1.0F, 9.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(232, 451).addBox(17.0F, -11.0F, -24.0F, 1.0F, 9.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(48, 453).addBox(-18.0F, -11.0F, -24.0F, 1.0F, 9.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(158, 453).addBox(-18.0F, -11.0F, 20.0F, 1.0F, 9.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(242, 453).addBox(-18.0F, -11.0F, -2.0F, 1.0F, 9.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(400, 417).addBox(-19.0F, -13.0F, -46.0F, 3.0F, 15.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(58, 426).addBox(-19.0F, -13.0F, 32.0F, 3.0F, 15.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(282, 330).addBox(-18.0F, -3.0F, -32.0F, 2.0F, 5.0F, 64.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r15 = wood_hull.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(414, 267).addBox(-29.0F, 0.0F, -1.5F, 29.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(400, 84).addBox(-29.0F, 0.0F, 71.5F, 29.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-26.0F, -16.0F, -36.5F, 0.0F, 0.0F, -0.3927F));

        PartDefinition cube_r16 = wood_hull.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(414, 261).addBox(0.0F, 0.0F, -1.5F, 29.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(400, 411).addBox(0.0F, 0.0F, 71.5F, 29.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(26.0F, -16.0F, -36.5F, 0.0F, 0.0F, 0.3927F));

        PartDefinition cube_r17 = wood_hull.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(0, 403).addBox(-2.0F, -1.5F, -12.5F, 4.0F, 3.0F, 25.0F, new CubeDeformation(0.0F))
                .texOffs(94, 379).addBox(-100.0F, -1.5F, -12.5F, 4.0F, 3.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(49.0F, -6.1694F, -51.9745F, -0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r18 = wood_hull.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(400, 44).addBox(-2.0F, -1.5F, -12.5F, 4.0F, 3.0F, 25.0F, new CubeDeformation(0.0F))
                .texOffs(94, 351).addBox(-100.0F, -1.5F, -12.5F, 4.0F, 3.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(49.0F, -6.1694F, 51.9745F, 0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r19 = wood_hull.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(400, 23).addBox(-11.0F, -9.0F, 0.0F, 22.0F, 9.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -67.0F, -0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r20 = wood_hull.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(378, 124).addBox(-15.0F, -6.0F, -19.0F, 16.0F, 7.0F, 27.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.0F, -8.9875F, -72.9916F, -0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r21 = wood_hull.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(208, 429).addBox(-8.0F, -4.0F, 0.0F, 12.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -15.3346F, -90.928F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r22 = wood_hull.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(214, 406).addBox(-12.9922F, 1.0F, -12.0F, 11.9922F, 3.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.9961F, -8.781F, -72.6539F, -0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r23 = wood_hull.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, 0.0F, 0.0F, 16.0F, 0.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -15.3346F, -90.928F, -0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r24 = wood_hull.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(414, 293).addBox(-4.0F, -2.0F, -7.5F, 8.0F, 4.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -24.9837F, -94.2596F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r25 = wood_hull.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(414, 273).addBox(-4.0F, -2.0F, -8.5F, 8.0F, 4.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -24.9837F, 94.2596F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r26 = wood_hull.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(378, 90).addBox(-15.0F, -6.0F, -8.0F, 16.0F, 7.0F, 27.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.0F, -8.9875F, 72.9916F, 0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r27 = wood_hull.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(276, 411).addBox(-10.9922F, -5.0F, -10.0F, 21.9922F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0039F, 0.0F, 67.0F, 0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r28 = wood_hull.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, 0.0F, 0.0F, 16.0F, 0.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -15.3346F, 90.928F, 0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r29 = wood_hull.addOrReplaceChild("cube_r29", CubeListBuilder.create().texOffs(152, 406).addBox(-12.9922F, 1.0F, -8.0F, 11.9922F, 3.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.9961F, -8.781F, 72.6539F, 0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r30 = wood_hull.addOrReplaceChild("cube_r30", CubeListBuilder.create().texOffs(168, 429).addBox(-8.0F, -4.0F, -8.0F, 12.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -15.3346F, 90.928F, 0.7854F, 0.0F, 0.0F));

        return LayerDefinition.create(modelData, 512, 512);
    }

    public static LayerDefinition getWaterPatchModelData()
    {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition root = modelData.getRoot();

        root.addOrReplaceChild("water_patch", CubeListBuilder.create().texOffs(0, 0).addBox(-18.0F, -13.0F, -33.0F, 36.0F, 14.0F, 76.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(modelData, 512, 512);
    }
}
