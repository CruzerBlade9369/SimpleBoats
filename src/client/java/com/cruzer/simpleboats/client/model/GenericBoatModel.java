package com.cruzer.simpleboats.client.model;

import com.cruzer.simpleboats.client.renderer.state.PoweredBoatRenderState;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;

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

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData root = modelData.getRoot();

        ModelPartData rope = root.addChild("rope", ModelPartBuilder.create().uv(58, 407).cuboid(-13.5F, -13.5F, -49.9688F, 27.0F, 14.0F, 5.0F, new Dilation(0.0F))
                .uv(414, 313).cuboid(-11.5117F, 0.0F, -51.2344F, 23.0234F, 5.0F, 5.0F, new Dilation(0.0F))
                .uv(248, 0).cuboid(18.0859F, -13.0F, -37.5F, 1.0F, 15.0F, 75.0F, new Dilation(0.0F))
                .uv(0, 261).cuboid(-19.0859F, -13.0F, -37.5F, 1.0F, 15.0F, 75.0F, new Dilation(0.0F))
                .uv(302, 442).cuboid(46.5F, -3.5F, -38.3F, 5.0F, 4.0F, 4.0F, new Dilation(0.0F))
                .uv(92, 443).cuboid(46.5F, -3.5F, 34.7F, 5.0F, 4.0F, 4.0F, new Dilation(0.0F))
                .uv(110, 443).cuboid(-51.5F, -3.5F, 34.7F, 5.0F, 4.0F, 4.0F, new Dilation(0.0F))
                .uv(398, 446).cuboid(-51.5F, -3.5F, -38.3F, 5.0F, 4.0F, 4.0F, new Dilation(0.0F))
                .uv(252, 453).cuboid(16.0F, -16.5F, 34.5F, 2.0F, 6.0F, 4.0F, new Dilation(0.0F))
                .uv(264, 453).cuboid(-18.0F, -16.5F, 34.5F, 2.0F, 6.0F, 4.0F, new Dilation(0.0F))
                .uv(276, 453).cuboid(-18.0F, -16.5F, -38.5F, 2.0F, 6.0F, 4.0F, new Dilation(0.0F))
                .uv(288, 453).cuboid(16.0F, -16.5F, -38.5F, 2.0F, 6.0F, 4.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData cube_r1 = rope.addChild("cube_r1", ModelPartBuilder.create().uv(70, 455).cuboid(-27.0F, -0.5F, -2.1F, 2.0F, 4.0F, 4.0F, new Dilation(0.0F))
                .uv(58, 455).cuboid(-27.0F, -0.5F, 70.9F, 2.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-26.0F, -16.0F, -36.5F, 0.0F, 0.0F, -0.3927F));

        ModelPartData cube_r2 = rope.addChild("cube_r2", ModelPartBuilder.create().uv(404, 454).cuboid(25.0F, -0.5F, -2.1F, 2.0F, 4.0F, 4.0F, new Dilation(0.0F))
                .uv(392, 454).cuboid(25.0F, -0.5F, -75.1F, 2.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(26.0F, -16.0F, 36.5F, 0.0F, 0.0F, 0.3927F));

        ModelPartData cube_r3 = rope.addChild("cube_r3", ModelPartBuilder.create().uv(206, 441).cuboid(-13.1094F, 0.2852F, -9.4F, 13.1094F, 4.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(6.5547F, -8.781F, -72.6539F, -0.3927F, 0.0F, 0.0F));

        ModelPartData cube_r4 = rope.addChild("cube_r4", ModelPartBuilder.create().uv(322, 426).cuboid(-15.0F, -6.5F, -9.0F, 17.0F, 8.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(6.5F, -8.9875F, -72.9916F, -0.3927F, 0.0F, 0.0F));

        ModelPartData cube_r5 = rope.addChild("cube_r5", ModelPartBuilder.create().uv(122, 407).cuboid(-6.5F, 1.1172F, 2.2383F, 13.0F, 3.0F, 2.0F, new Dilation(0.0F))
                .uv(168, 441).cuboid(-8.5F, -6.8828F, 2.2383F, 17.0F, 8.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -6.2664F, 67.3394F, 0.3927F, 0.0F, 0.0F));

        ModelPartData cube_r6 = rope.addChild("cube_r6", ModelPartBuilder.create().uv(378, 164).cuboid(-6.5F, 1.1172F, -4.2383F, 13.0F, 3.0F, 2.0F, new Dilation(0.0F))
                .uv(322, 440).cuboid(-8.5F, -6.8828F, -4.2383F, 17.0F, 8.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -6.2664F, -67.3394F, -0.3927F, 0.0F, 0.0F));

        ModelPartData cube_r7 = rope.addChild("cube_r7", ModelPartBuilder.create().uv(220, 140).cuboid(-5.0F, -2.5F, -7.75F, 9.0F, 5.0F, 4.0F, new Dilation(0.0F))
                .uv(24, 443).cuboid(-5.0F, -2.5F, 0.5F, 9.0F, 5.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.5F, -24.9837F, 94.2596F, 0.7854F, 0.0F, 0.0F));

        ModelPartData cube_r8 = rope.addChild("cube_r8", ModelPartBuilder.create().uv(220, 131).cuboid(-4.0F, -2.5F, 3.8F, 9.0F, 5.0F, 4.0F, new Dilation(0.0F))
                .uv(0, 443).cuboid(-4.0F, -2.5F, -3.5F, 9.0F, 5.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-0.5F, -24.9837F, -94.2596F, -0.7854F, 0.0F, 0.0F));

        ModelPartData floater = root.addChild("floater", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData cube_r9 = floater.addChild("cube_r9", ModelPartBuilder.create().uv(196, 459).cuboid(-2.0F, -0.5F, -6.0F, 3.5391F, 1.0F, 4.0F, new Dilation(0.0F))
                .uv(140, 459).cuboid(-2.0F, -0.5F, 2.0F, 3.5391F, 1.0F, 4.0F, new Dilation(0.0F))
                .uv(124, 453).cuboid(-1.5F, 2.5F, -2.5F, 3.0F, 3.0F, 5.0F, new Dilation(0.0F))
                .uv(452, 448).cuboid(-1.5F, -5.5F, -2.5F, 3.0F, 3.0F, 5.0F, new Dilation(0.0F))
                .uv(350, 450).cuboid(-1.5F, -5.5F, 2.5F, 3.0F, 11.0F, 3.0F, new Dilation(0.0F))
                .uv(338, 450).cuboid(-1.5F, -5.5F, -5.5F, 3.0F, 11.0F, 3.0F, new Dilation(0.0F))
                .uv(458, 59).cuboid(39.4609F, -0.5F, -6.0F, 3.5391F, 1.0F, 4.0F, new Dilation(0.0F))
                .uv(458, 44).cuboid(39.4609F, -0.5F, 2.0F, 3.5391F, 1.0F, 4.0F, new Dilation(0.0F))
                .uv(108, 451).cuboid(39.5F, 2.5F, -2.5F, 3.0F, 3.0F, 5.0F, new Dilation(0.0F))
                .uv(92, 451).cuboid(39.5F, -5.5F, -2.5F, 3.0F, 3.0F, 5.0F, new Dilation(0.0F))
                .uv(428, 448).cuboid(39.5F, -5.5F, 2.5F, 3.0F, 11.0F, 3.0F, new Dilation(0.0F))
                .uv(448, 380).cuboid(39.5F, -5.5F, -5.5F, 3.0F, 11.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-20.5F, -5.5F, 0.0F, 0.7854F, 0.0F, 0.0F));

        ModelPartData cube_r10 = floater.addChild("cube_r10", ModelPartBuilder.create().uv(182, 459).cuboid(-2.0F, 3.0F, 2.0F, 3.5391F, 1.0F, 4.0F, new Dilation(0.0F))
                .uv(168, 459).cuboid(-2.0F, 3.0F, -6.0F, 3.5391F, 1.0F, 4.0F, new Dilation(0.0F))
                .uv(458, 54).cuboid(39.4609F, 3.0F, 2.0F, 3.5391F, 1.0F, 4.0F, new Dilation(0.0F))
                .uv(458, 49).cuboid(39.4609F, 3.0F, -6.0F, 3.5391F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-20.5F, -7.9749F, 2.4749F, -0.7854F, 0.0F, 0.0F));

        ModelPartData cube_r11 = floater.addChild("cube_r11", ModelPartBuilder.create().uv(110, 459).cuboid(-2.0F, -0.5F, -6.0F, 3.5391F, 1.0F, 4.0F, new Dilation(0.0F))
                .uv(28, 459).cuboid(-2.0F, -0.5F, 2.0F, 3.5391F, 1.0F, 4.0F, new Dilation(0.0F))
                .uv(216, 451).cuboid(-1.5F, 2.5F, -2.5F, 3.0F, 3.0F, 5.0F, new Dilation(0.0F))
                .uv(200, 451).cuboid(-1.5F, -5.5F, -2.5F, 3.0F, 3.0F, 5.0F, new Dilation(0.0F))
                .uv(326, 450).cuboid(-1.5F, -5.5F, 2.5F, 3.0F, 11.0F, 3.0F, new Dilation(0.0F))
                .uv(314, 450).cuboid(-1.5F, -5.5F, -5.5F, 3.0F, 11.0F, 3.0F, new Dilation(0.0F))
                .uv(452, 456).cuboid(39.4609F, -0.5F, -6.0F, 3.5391F, 1.0F, 4.0F, new Dilation(0.0F))
                .uv(436, 164).cuboid(39.4609F, -0.5F, 2.0F, 3.5391F, 1.0F, 4.0F, new Dilation(0.0F))
                .uv(32, 451).cuboid(39.5F, 2.5F, -2.5F, 3.0F, 3.0F, 5.0F, new Dilation(0.0F))
                .uv(16, 451).cuboid(39.5F, -5.5F, -2.5F, 3.0F, 3.0F, 5.0F, new Dilation(0.0F))
                .uv(448, 366).cuboid(39.5F, -5.5F, 2.5F, 3.0F, 11.0F, 3.0F, new Dilation(0.0F))
                .uv(448, 352).cuboid(39.5F, -5.5F, -5.5F, 3.0F, 11.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-20.5F, -5.5F, 26.5F, 0.7854F, 0.0F, 0.0F));

        ModelPartData cube_r12 = floater.addChild("cube_r12", ModelPartBuilder.create().uv(96, 459).cuboid(-2.0F, 3.0F, 2.0F, 3.5391F, 1.0F, 4.0F, new Dilation(0.0F))
                .uv(82, 459).cuboid(-2.0F, 3.0F, -6.0F, 3.5391F, 1.0F, 4.0F, new Dilation(0.0F))
                .uv(450, 164).cuboid(39.4609F, 3.0F, 2.0F, 3.5391F, 1.0F, 4.0F, new Dilation(0.0F))
                .uv(448, 394).cuboid(39.4609F, 3.0F, -6.0F, 3.5391F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-20.5F, -7.9749F, 28.9749F, -0.7854F, 0.0F, 0.0F));

        ModelPartData cube_r13 = floater.addChild("cube_r13", ModelPartBuilder.create().uv(14, 459).cuboid(-2.0F, -0.5F, -6.0F, 3.5391F, 1.0F, 4.0F, new Dilation(0.0F))
                .uv(184, 451).cuboid(-1.5F, 2.5F, -2.5F, 3.0F, 3.0F, 5.0F, new Dilation(0.0F))
                .uv(376, 458).cuboid(-2.0F, -0.5F, 2.0F, 3.5391F, 1.0F, 4.0F, new Dilation(0.0F))
                .uv(168, 451).cuboid(-1.5F, -5.5F, -2.5F, 3.0F, 3.0F, 5.0F, new Dilation(0.0F))
                .uv(302, 450).cuboid(-1.5F, -5.5F, 2.5F, 3.0F, 11.0F, 3.0F, new Dilation(0.0F))
                .uv(440, 448).cuboid(-1.5F, -5.5F, -5.5F, 3.0F, 11.0F, 3.0F, new Dilation(0.0F))
                .uv(422, 164).cuboid(39.4609F, -0.5F, -6.0F, 3.5391F, 1.0F, 4.0F, new Dilation(0.0F))
                .uv(0, 451).cuboid(39.5F, 2.5F, -2.5F, 3.0F, 3.0F, 5.0F, new Dilation(0.0F))
                .uv(408, 164).cuboid(39.4609F, -0.5F, 2.0F, 3.5391F, 1.0F, 4.0F, new Dilation(0.0F))
                .uv(376, 450).cuboid(39.5F, -5.5F, -2.5F, 3.0F, 3.0F, 5.0F, new Dilation(0.0F))
                .uv(448, 338).cuboid(39.5F, -5.5F, 2.5F, 3.0F, 11.0F, 3.0F, new Dilation(0.0F))
                .uv(416, 446).cuboid(39.5F, -5.5F, -5.5F, 3.0F, 11.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-20.5F, -5.5F, -26.5F, 0.7854F, 0.0F, 0.0F));

        ModelPartData cube_r14 = floater.addChild("cube_r14", ModelPartBuilder.create().uv(0, 459).cuboid(-2.0F, 3.0F, 2.0F, 3.5391F, 1.0F, 4.0F, new Dilation(0.0F))
                .uv(458, 64).cuboid(-2.0F, 3.0F, -6.0F, 3.5391F, 1.0F, 4.0F, new Dilation(0.0F))
                .uv(138, 422).cuboid(39.4609F, 3.0F, 2.0F, 3.5391F, 1.0F, 4.0F, new Dilation(0.0F))
                .uv(330, 169).cuboid(39.4609F, 3.0F, -6.0F, 3.5391F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-20.5F, -7.9749F, -24.0251F, -0.7854F, 0.0F, 0.0F));

        ModelPartData wood_hull = root.addChild("wood_hull", ModelPartBuilder.create().uv(0, 0).cuboid(-16.0F, -1.0F, -46.0F, 32.0F, 7.0F, 92.0F, new Dilation(0.0F))
                .uv(282, 399).cuboid(-18.0F, -6.0F, -16.0F, 36.0F, 2.0F, 10.0F, new Dilation(0.0F))
                .uv(374, 399).cuboid(-18.0F, -6.0F, 6.0F, 36.0F, 2.0F, 10.0F, new Dilation(0.0F))
                .uv(379, 404).cuboid(-18.0F, -11.0F, 27.0F, 36.0F, 2.0F, 5.0F, new Dilation(0.0F))
                .uv(0, 99).cuboid(-17.0F, -2.0F, -32.0F, 34.0F, 1.0F, 76.0F, new Dilation(0.0F))
                .uv(434, 432).cuboid(-11.0F, -0.1732F, 45.7612F, 5.0F, 4.0F, 12.0F, new Dilation(0.0F))
                .uv(436, 235).cuboid(6.0F, -0.1732F, 45.7612F, 5.0F, 4.0F, 12.0F, new Dilation(0.0F))
                .uv(368, 426).cuboid(-13.0F, -13.0F, 46.0F, 4.0F, 13.0F, 11.0F, new Dilation(0.0F))
                .uv(138, 429).cuboid(9.0F, -13.0F, 46.0F, 4.0F, 13.0F, 11.0F, new Dilation(0.0F))
                .uv(400, 0).cuboid(-13.0F, -13.0F, 57.0F, 26.0F, 13.0F, 10.0F, new Dilation(0.0F))
                .uv(338, 411).cuboid(-10.9961F, -0.4078F, -55.9134F, 21.9922F, 5.0F, 10.0F, new Dilation(0.0F))
                .uv(0, 351).cuboid(-13.0F, -13.0F, -67.0F, 26.0F, 13.0F, 21.0F, new Dilation(0.0F))
                .uv(344, 235).cuboid(-16.0F, -13.0F, -46.0F, 32.0F, 12.0F, 14.0F, new Dilation(0.0F))
                .uv(400, 72).cuboid(-16.0F, -9.0F, 42.0F, 32.0F, 8.0F, 4.0F, new Dilation(0.0F))
                .uv(0, 385).cuboid(-16.0F, -13.0F, 32.0F, 32.0F, 4.0F, 14.0F, new Dilation(0.0F))
                .uv(220, 169).cuboid(-26.0F, -16.0F, 34.9961F, 52.0F, 3.0F, 3.0F, new Dilation(0.0F))
                .uv(0, 176).cuboid(-51.0039F, -3.0F, -41.0F, 4.0F, 3.0F, 82.0F, new Dilation(0.0F))
                .uv(172, 176).cuboid(47.0039F, -3.0F, -41.0F, 4.0F, 3.0F, 82.0F, new Dilation(0.0F))
                .uv(378, 158).cuboid(-26.0F, -16.0F, -38.0039F, 52.0F, 3.0F, 3.0F, new Dilation(0.0F))
                .uv(248, 90).cuboid(18.0F, -13.0F, -32.0F, 1.0F, 15.0F, 64.0F, new Dilation(0.0F))
                .uv(152, 340).cuboid(17.0F, -13.0F, -32.0F, 1.0F, 2.0F, 64.0F, new Dilation(0.0F))
                .uv(414, 338).cuboid(16.0F, -13.0F, -46.0F, 3.0F, 15.0F, 14.0F, new Dilation(0.0F))
                .uv(414, 367).cuboid(16.0F, -13.0F, 32.0F, 3.0F, 15.0F, 14.0F, new Dilation(0.0F))
                .uv(282, 261).cuboid(16.0F, -3.0F, -32.0F, 2.0F, 5.0F, 64.0F, new Dilation(0.0F))
                .uv(152, 261).cuboid(-19.0F, -13.0F, -32.0F, 1.0F, 15.0F, 64.0F, new Dilation(0.0F))
                .uv(344, 169).cuboid(-18.0F, -13.0F, -32.0F, 1.0F, 2.0F, 64.0F, new Dilation(0.0F))
                .uv(238, 149).cuboid(17.0F, -11.0F, 20.0F, 1.0F, 9.0F, 4.0F, new Dilation(0.0F))
                .uv(48, 440).cuboid(17.0F, -11.0F, -2.0F, 1.0F, 9.0F, 4.0F, new Dilation(0.0F))
                .uv(232, 451).cuboid(17.0F, -11.0F, -24.0F, 1.0F, 9.0F, 4.0F, new Dilation(0.0F))
                .uv(48, 453).cuboid(-18.0F, -11.0F, -24.0F, 1.0F, 9.0F, 4.0F, new Dilation(0.0F))
                .uv(158, 453).cuboid(-18.0F, -11.0F, 20.0F, 1.0F, 9.0F, 4.0F, new Dilation(0.0F))
                .uv(242, 453).cuboid(-18.0F, -11.0F, -2.0F, 1.0F, 9.0F, 4.0F, new Dilation(0.0F))
                .uv(400, 417).cuboid(-19.0F, -13.0F, -46.0F, 3.0F, 15.0F, 14.0F, new Dilation(0.0F))
                .uv(58, 426).cuboid(-19.0F, -13.0F, 32.0F, 3.0F, 15.0F, 14.0F, new Dilation(0.0F))
                .uv(282, 330).cuboid(-18.0F, -3.0F, -32.0F, 2.0F, 5.0F, 64.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData cube_r15 = wood_hull.addChild("cube_r15", ModelPartBuilder.create().uv(414, 267).cuboid(-29.0F, 0.0F, -1.5F, 29.0F, 3.0F, 3.0F, new Dilation(0.0F))
                .uv(400, 84).cuboid(-29.0F, 0.0F, 71.5F, 29.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-26.0F, -16.0F, -36.5F, 0.0F, 0.0F, -0.3927F));

        ModelPartData cube_r16 = wood_hull.addChild("cube_r16", ModelPartBuilder.create().uv(414, 261).cuboid(0.0F, 0.0F, -1.5F, 29.0F, 3.0F, 3.0F, new Dilation(0.0F))
                .uv(400, 411).cuboid(0.0F, 0.0F, 71.5F, 29.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(26.0F, -16.0F, -36.5F, 0.0F, 0.0F, 0.3927F));

        ModelPartData cube_r17 = wood_hull.addChild("cube_r17", ModelPartBuilder.create().uv(0, 403).cuboid(-2.0F, -1.5F, -12.5F, 4.0F, 3.0F, 25.0F, new Dilation(0.0F))
                .uv(94, 379).cuboid(-100.0F, -1.5F, -12.5F, 4.0F, 3.0F, 25.0F, new Dilation(0.0F)), ModelTransform.of(49.0F, -6.1694F, -51.9745F, -0.3927F, 0.0F, 0.0F));

        ModelPartData cube_r18 = wood_hull.addChild("cube_r18", ModelPartBuilder.create().uv(400, 44).cuboid(-2.0F, -1.5F, -12.5F, 4.0F, 3.0F, 25.0F, new Dilation(0.0F))
                .uv(94, 351).cuboid(-100.0F, -1.5F, -12.5F, 4.0F, 3.0F, 25.0F, new Dilation(0.0F)), ModelTransform.of(49.0F, -6.1694F, 51.9745F, 0.3927F, 0.0F, 0.0F));

        ModelPartData cube_r19 = wood_hull.addChild("cube_r19", ModelPartBuilder.create().uv(400, 23).cuboid(-11.0F, -9.0F, 0.0F, 22.0F, 9.0F, 12.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -67.0F, -0.3927F, 0.0F, 0.0F));

        ModelPartData cube_r20 = wood_hull.addChild("cube_r20", ModelPartBuilder.create().uv(378, 124).cuboid(-15.0F, -6.0F, -19.0F, 16.0F, 7.0F, 27.0F, new Dilation(0.0F)), ModelTransform.of(7.0F, -8.9875F, -72.9916F, -0.3927F, 0.0F, 0.0F));

        ModelPartData cube_r21 = wood_hull.addChild("cube_r21", ModelPartBuilder.create().uv(208, 429).cuboid(-8.0F, -4.0F, 0.0F, 12.0F, 4.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(2.0F, -15.3346F, -90.928F, -0.7854F, 0.0F, 0.0F));

        ModelPartData cube_r22 = wood_hull.addChild("cube_r22", ModelPartBuilder.create().uv(214, 406).cuboid(-12.9922F, 1.0F, -12.0F, 11.9922F, 3.0F, 20.0F, new Dilation(0.0F)), ModelTransform.of(6.9961F, -8.781F, -72.6539F, -0.3927F, 0.0F, 0.0F));

        ModelPartData cube_r23 = wood_hull.addChild("cube_r23", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, 0.0F, 0.0F, 16.0F, 0.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -15.3346F, -90.928F, -0.3927F, 0.0F, 0.0F));

        ModelPartData cube_r24 = wood_hull.addChild("cube_r24", ModelPartBuilder.create().uv(414, 293).cuboid(-4.0F, -2.0F, -7.5F, 8.0F, 4.0F, 16.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -24.9837F, -94.2596F, -0.7854F, 0.0F, 0.0F));

        ModelPartData cube_r25 = wood_hull.addChild("cube_r25", ModelPartBuilder.create().uv(414, 273).cuboid(-4.0F, -2.0F, -8.5F, 8.0F, 4.0F, 16.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -24.9837F, 94.2596F, 0.7854F, 0.0F, 0.0F));

        ModelPartData cube_r26 = wood_hull.addChild("cube_r26", ModelPartBuilder.create().uv(378, 90).cuboid(-15.0F, -6.0F, -8.0F, 16.0F, 7.0F, 27.0F, new Dilation(0.0F)), ModelTransform.of(7.0F, -8.9875F, 72.9916F, 0.3927F, 0.0F, 0.0F));

        ModelPartData cube_r27 = wood_hull.addChild("cube_r27", ModelPartBuilder.create().uv(276, 411).cuboid(-10.9922F, -5.0F, -10.0F, 21.9922F, 5.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(-0.0039F, 0.0F, 67.0F, 0.3927F, 0.0F, 0.0F));

        ModelPartData cube_r28 = wood_hull.addChild("cube_r28", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, 0.0F, 0.0F, 16.0F, 0.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -15.3346F, 90.928F, 0.3927F, 0.0F, 0.0F));

        ModelPartData cube_r29 = wood_hull.addChild("cube_r29", ModelPartBuilder.create().uv(152, 406).cuboid(-12.9922F, 1.0F, -8.0F, 11.9922F, 3.0F, 20.0F, new Dilation(0.0F)), ModelTransform.of(6.9961F, -8.781F, 72.6539F, 0.3927F, 0.0F, 0.0F));

        ModelPartData cube_r30 = wood_hull.addChild("cube_r30", ModelPartBuilder.create().uv(168, 429).cuboid(-8.0F, -4.0F, -8.0F, 12.0F, 4.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(2.0F, -15.3346F, 90.928F, 0.7854F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 512, 512);
    }

    public static TexturedModelData getWaterPatchModelData()
    {
        ModelData modelData = new ModelData();
        ModelPartData root = modelData.getRoot();

        root.addChild("water_patch", ModelPartBuilder.create().uv(0, 0).cuboid(-18.0F, -13.0F, -33.0F, 36.0F, 14.0F, 76.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 512, 512);
    }
}
