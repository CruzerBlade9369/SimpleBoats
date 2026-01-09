package com.cruzer.simpleboats.client.model;

import com.cruzer.simpleboats.client.renderer.state.SailboatRenderState;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;

public class SailModel extends EntityModel<SailboatRenderState>
{
    private final ModelPart mastBase;
    private final ModelPart clothParts;

    private final ModelPart[] ropeStates = new ModelPart[6];
    private final ModelPart[] sailSections = new ModelPart[10];

    public SailModel(ModelPart root) {
        super(root);
        this.mastBase = root.getChild("mast_base");

        ModelPart ropes = root.getChild("mast_base").getChild("rope_states");
        for (int i = 0; i <= 5; i++) {
            ropeStates[i] = ropes.getChild("rope_" + i);
        }

        clothParts = root.getChild("cloth_parts");

        for (int i = 0; i <= 9; i++) {
            sailSections[i] = clothParts.getChild("sail_" + (i+1));
        }
    }

    public static TexturedModelData getSailModelData()
    {
        ModelData modelData = new ModelData();
        ModelPartData root = modelData.getRoot();

        ModelPartData mast_base = root.addChild("mast_base", ModelPartBuilder.create().uv(93, 0).cuboid(-2.0F, -23.0F, -2.0F, 4.0F, 23.0F, 4.0F, new Dilation(0.0F))
                .uv(106, 0).cuboid(-1.5F, -105.0F, -1.5F, 3.0F, 82.0F, 3.0F, new Dilation(0.0F))
                .uv(324, 438).cuboid(-2.5F, -39.0F, -2.5F, 5.0F, 5.0F, 5.0F, new Dilation(0.0F))
                .uv(327, 440).cuboid(-2.0F, -38.5F, -5.0F, 4.0F, 4.0F, 3.0F, new Dilation(0.0F))
                .uv(328, 437).cuboid(35.0F, -38.5F, -5.0F, 3.0F, 4.0F, 4.0F, new Dilation(0.0F))
                .uv(328, 437).cuboid(-38.0F, -38.5F, -5.0F, 3.0F, 4.0F, 4.0F, new Dilation(0.0F))
                .uv(327, 440).cuboid(-2.0F, -99.5F, -5.0F, 4.0F, 4.0F, 3.0F, new Dilation(0.0F))
                .uv(323, 438).cuboid(-2.5F, -100.0F, -2.5F, 5.0F, 5.0F, 5.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData rope_holder_r_r1 = mast_base.addChild("rope_holder_r_r1", ModelPartBuilder.create().uv(250, 1).cuboid(-1.0F, -51.0F, -2.0F, 1.0F, 51.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-16.0F, -14.0F, 38.0F, 1.0821F, 0.4538F, 0.0F));

        ModelPartData rope_holder_l_r1 = mast_base.addChild("rope_holder_l_r1", ModelPartBuilder.create().uv(249, 1).cuboid(0.0F, -51.0F, -2.0F, 1.0F, 51.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(16.0F, -14.0F, 38.0F, 1.0821F, -0.4538F, 0.0F));

        ModelPartData boom_r1 = mast_base.addChild("boom_r1", ModelPartBuilder.create().uv(126, 0).cuboid(-3.0F, -73.0F, 0.0F, 3.0F, 79.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-33.5F, -38.0F, -4.5F, 0.0F, 1.5708F, 1.5708F));

        ModelPartData boom2_r1 = mast_base.addChild("boom2_r1", ModelPartBuilder.create().uv(139, 0).cuboid(-3.0F, -73.0F, 0.0F, 3.0F, 79.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-33.5F, -99.0F, -4.5F, 0.0F, 1.5708F, 1.5708F));

        ModelPartData rope_states = mast_base.addChild("rope_states", ModelPartBuilder.create(), ModelTransform.origin(0.0F, -66.0F, -3.0F));

        ModelPartData rope_r_5_r1 = rope_states.addChild("rope_5", ModelPartBuilder.create().uv(250, 1).cuboid(-0.5F, -4.0F, -0.5F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F))
                .uv(250, 1).cuboid(72.5F, -4.0F, -0.5F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-36.5F, 28.0F, 0.0F, 0.9338F, 0.0F, 0.0F));

        ModelPartData rope_r_4_r1 = rope_states.addChild("rope_4", ModelPartBuilder.create().uv(250, 1).cuboid(-0.5F, -16.0F, -0.5F, 1.0F, 16.0F, 1.0F, new Dilation(0.0F))
                .uv(250, 1).cuboid(72.5F, -16.0F, -0.5F, 1.0F, 16.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-36.5F, 28.0F, 0.0F, 0.7243F, 0.0F, 0.0F));

        ModelPartData rope_r_3_r1 = rope_states.addChild("rope_3", ModelPartBuilder.create().uv(250, 1).cuboid(-0.5F, -27.0F, -0.5F, 1.0F, 27.0F, 1.0F, new Dilation(0.0F))
                .uv(250, 1).cuboid(72.5F, -27.0F, -0.5F, 1.0F, 27.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-36.5F, 28.0F, 0.0F, 0.5411F, 0.0F, 0.0F));

        ModelPartData rope_r_2_r1 = rope_states.addChild("rope_2", ModelPartBuilder.create().uv(250, 1).cuboid(-0.5F, -37.0F, -0.5F, 1.0F, 37.0F, 1.0F, new Dilation(0.0F))
                .uv(250, 1).cuboid(72.5F, -37.0F, -0.5F, 1.0F, 37.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-36.5F, 28.0F, 0.0F, 0.3752F, 0.0F, 0.0F));

        ModelPartData rope_r_1_r1 = rope_states.addChild("rope_1", ModelPartBuilder.create().uv(250, 1).cuboid(-0.5F, -46.0F, -0.5F, 1.0F, 46.0F, 1.0F, new Dilation(0.0F))
                .uv(250, 1).cuboid(72.5F, -46.0F, -0.5F, 1.0F, 46.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-36.5F, 28.0F, 0.0F, 0.192F, 0.0F, 0.0F));

        ModelPartData rope_r_0_r1 = rope_states.addChild("rope_0", ModelPartBuilder.create().uv(250, 1).cuboid(-0.5F, -53.0F, -0.5F, 1.0F, 53.0F, 1.0F, new Dilation(0.0F))
                .uv(250, 1).cuboid(72.5F, -53.0F, -0.5F, 1.0F, 53.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-36.5F, 28.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        ModelPartData hook1 = root.addChild("hook1", ModelPartBuilder.create().uv(100, 35).cuboid(-1.0F, -3.0F, 0.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F))
                .uv(100, 35).cuboid(-1.0F, -3.0F, 5.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F))
                .uv(95, 30).cuboid(-1.0F, -5.0F, 0.0F, 2.0F, 2.0F, 7.0F, new Dilation(0.0F))
                .uv(402, 448).cuboid(-1.5F, -5.5F, 2.0F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -19.0F, -86.0F, -0.3927F, 0.0F, 0.0F));

        ModelPartData cube_r44 = hook1.addChild("cube_r44", ModelPartBuilder.create().uv(248, 0).cuboid(-2.0F, -108.0F, 0.0F, 2.0F, 108.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, -5.5F, 2.5F, -0.4014F, 0.0F, 0.0F));

        ModelPartData hook2 = root.addChild("hook2", ModelPartBuilder.create().uv(100, 35).cuboid(-1.0F, -3.0F, 0.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F))
                .uv(100, 35).cuboid(-1.0F, -3.0F, 5.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F))
                .uv(95, 30).cuboid(-1.0F, -5.0F, 0.0F, 2.0F, 2.0F, 7.0F, new Dilation(0.0F))
                .uv(306, 443).cuboid(-1.5F, -5.5F, 2.0F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -16.0F, 81.0F, 0.3927F, 0.0F, 0.0F));

        ModelPartData cube_r45 = hook2.addChild("cube_r45", ModelPartBuilder.create().uv(248, 0).cuboid(-1.0F, -110.0F, 0.0F, 2.0F, 110.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -4.5F, 2.6F, 0.4276F, 0.0F, 0.0F));

        // cloth parts
        ModelPartData cloth_parts = root.addChild("cloth_parts", ModelPartBuilder.create(), ModelTransform.of(0.0F, -91.142F, -4.1372F, -0.1222F, 0.0F, 0.0F));

        ModelPartData sail_10_r1 = cloth_parts.addChild("sail_10", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -78.0F, -6.0F, 1.0F, 78.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(39.0F, 47.6788F, -0.5F, 0.0F, 2.4435F, -1.5708F));

        ModelPartData sail_9_r1 = cloth_parts.addChild("sail_9", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -78.0F, -6.0F, 1.0F, 78.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(39.0F, 43.0826F, -4.3567F, 0.0F, 2.2689F, -1.5708F));

        ModelPartData sail_8_r1 = cloth_parts.addChild("sail_8", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -78.0F, -6.0F, 1.0F, 78.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(39.0F, 37.8864F, -7.3567F, 0.0F, 2.0944F, -1.5708F));

        ModelPartData sail_7_r1 = cloth_parts.addChild("sail_7", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -78.0F, -6.0F, 1.0F, 78.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(39.0F, 32.2483F, -9.4089F, 0.0F, 1.9199F, -1.5708F));

        ModelPartData sail_6_r1 = cloth_parts.addChild("sail_6", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -78.0F, -6.0F, 1.0F, 78.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(39.0F, 26.3394F, -10.4507F, 0.0F, 1.7453F, -1.5708F));

        ModelPartData sail_5_r1 = cloth_parts.addChild("sail_5", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -78.0F, -6.0F, 1.0F, 78.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(39.0F, 20.3394F, -10.4507F, 0.0F, 1.5708F, -1.5708F));

        ModelPartData sail_4_r1 = cloth_parts.addChild("sail_4", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -78.0F, -6.0F, 1.0F, 78.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(39.0F, 14.4306F, -9.4089F, 0.0F, 1.3963F, -1.5708F));

        ModelPartData sail_3_r1 = cloth_parts.addChild("sail_3", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -78.0F, -6.0F, 1.0F, 78.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(39.0F, 8.7924F, -7.3567F, 0.0F, 1.2217F, -1.5708F));

        ModelPartData sail_2_r1 = cloth_parts.addChild("sail_2", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -78.0F, -6.0F, 1.0F, 78.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(39.0F, 3.5963F, -4.3567F, 0.0F, 1.0472F, -1.5708F));

        ModelPartData sail_1_r1 = cloth_parts.addChild("sail_1", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -78.0F, -6.0F, 1.0F, 78.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(39.0F, -1.0F, -0.5F, 0.0F, 0.8727F, -1.5708F));

        ModelPartData sail_clump_r1 = cloth_parts.addChild("sail_clump", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -39.0F, -2.5F, 5.0F, 78.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -1.858F, 1.6372F, 0.0F, 0.7854F, -1.5708F));

        return TexturedModelData.of(modelData, 512, 512);
    }

    @Override
    public void setAngles(
            SailboatRenderState state
    )
    {
        int level = state.sailLevel;

        for (int i = 0; i < ropeStates.length; i++) {
            if (ropeStates[i] != null) {
                ropeStates[i].visible = (i == level);
            }
        }

        int visibleSails;
        switch (level) {
            default -> visibleSails = 0;
            case 1 -> visibleSails = 2;
            case 2 -> visibleSails = 4;
            case 3 -> visibleSails = 6;
            case 4 -> visibleSails = 8;
            case 5 -> visibleSails = 10;
        }

        for (int i = 0; i < sailSections.length; i++) {
            if (sailSections[i] != null) {
                sailSections[i].visible = i < visibleSails;
            }
        }
    }
}
