package com.cruzer.simpleboats.client.model;

import com.cruzer.simpleboats.client.renderer.state.SailboatRenderState;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

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

    public static LayerDefinition getSailModelData()
    {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition root = modelData.getRoot();

        PartDefinition mast_base = root.addOrReplaceChild("mast_base", CubeListBuilder.create().texOffs(93, 0).addBox(-2.0F, -23.0F, -2.0F, 4.0F, 23.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(106, 0).addBox(-1.5F, -105.0F, -1.5F, 3.0F, 82.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(324, 438).addBox(-2.5F, -39.0F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(327, 440).addBox(-2.0F, -38.5F, -5.0F, 4.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(328, 437).addBox(35.0F, -38.5F, -5.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(328, 437).addBox(-38.0F, -38.5F, -5.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(327, 440).addBox(-2.0F, -99.5F, -5.0F, 4.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(323, 438).addBox(-2.5F, -100.0F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rope_holder_r_r1 = mast_base.addOrReplaceChild("rope_holder_r_r1", CubeListBuilder.create().texOffs(250, 1).addBox(-1.0F, -51.0F, -2.0F, 1.0F, 51.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-16.0F, -14.0F, 38.0F, 1.0821F, 0.4538F, 0.0F));

        PartDefinition rope_holder_l_r1 = mast_base.addOrReplaceChild("rope_holder_l_r1", CubeListBuilder.create().texOffs(249, 1).addBox(0.0F, -51.0F, -2.0F, 1.0F, 51.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(16.0F, -14.0F, 38.0F, 1.0821F, -0.4538F, 0.0F));

        PartDefinition boom_r1 = mast_base.addOrReplaceChild("boom_r1", CubeListBuilder.create().texOffs(126, 0).addBox(-3.0F, -73.0F, 0.0F, 3.0F, 79.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-33.5F, -38.0F, -4.5F, 0.0F, 1.5708F, 1.5708F));

        PartDefinition boom2_r1 = mast_base.addOrReplaceChild("boom2_r1", CubeListBuilder.create().texOffs(139, 0).addBox(-3.0F, -73.0F, 0.0F, 3.0F, 79.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-33.5F, -99.0F, -4.5F, 0.0F, 1.5708F, 1.5708F));

        PartDefinition rope_states = mast_base.addOrReplaceChild("rope_states", CubeListBuilder.create(), PartPose.offset(0.0F, -66.0F, -3.0F));

        PartDefinition rope_r_5_r1 = rope_states.addOrReplaceChild("rope_5", CubeListBuilder.create().texOffs(250, 1).addBox(-0.5F, -4.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(250, 1).addBox(72.5F, -4.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-36.5F, 28.0F, 0.0F, 0.9338F, 0.0F, 0.0F));

        PartDefinition rope_r_4_r1 = rope_states.addOrReplaceChild("rope_4", CubeListBuilder.create().texOffs(250, 1).addBox(-0.5F, -16.0F, -0.5F, 1.0F, 16.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(250, 1).addBox(72.5F, -16.0F, -0.5F, 1.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-36.5F, 28.0F, 0.0F, 0.7243F, 0.0F, 0.0F));

        PartDefinition rope_r_3_r1 = rope_states.addOrReplaceChild("rope_3", CubeListBuilder.create().texOffs(250, 1).addBox(-0.5F, -27.0F, -0.5F, 1.0F, 27.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(250, 1).addBox(72.5F, -27.0F, -0.5F, 1.0F, 27.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-36.5F, 28.0F, 0.0F, 0.5411F, 0.0F, 0.0F));

        PartDefinition rope_r_2_r1 = rope_states.addOrReplaceChild("rope_2", CubeListBuilder.create().texOffs(250, 1).addBox(-0.5F, -37.0F, -0.5F, 1.0F, 37.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(250, 1).addBox(72.5F, -37.0F, -0.5F, 1.0F, 37.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-36.5F, 28.0F, 0.0F, 0.3752F, 0.0F, 0.0F));

        PartDefinition rope_r_1_r1 = rope_states.addOrReplaceChild("rope_1", CubeListBuilder.create().texOffs(250, 1).addBox(-0.5F, -46.0F, -0.5F, 1.0F, 46.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(250, 1).addBox(72.5F, -46.0F, -0.5F, 1.0F, 46.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-36.5F, 28.0F, 0.0F, 0.192F, 0.0F, 0.0F));

        PartDefinition rope_r_0_r1 = rope_states.addOrReplaceChild("rope_0", CubeListBuilder.create().texOffs(250, 1).addBox(-0.5F, -53.0F, -0.5F, 1.0F, 53.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(250, 1).addBox(72.5F, -53.0F, -0.5F, 1.0F, 53.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-36.5F, 28.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        PartDefinition hook1 = root.addOrReplaceChild("hook1", CubeListBuilder.create().texOffs(100, 35).addBox(-1.0F, -3.0F, 0.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(100, 35).addBox(-1.0F, -3.0F, 5.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(95, 30).addBox(-1.0F, -5.0F, 0.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(402, 448).addBox(-1.5F, -5.5F, 2.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -19.0F, -86.0F, -0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r44 = hook1.addOrReplaceChild("cube_r44", CubeListBuilder.create().texOffs(248, 0).addBox(-2.0F, -108.0F, 0.0F, 2.0F, 108.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -5.5F, 2.5F, -0.4014F, 0.0F, 0.0F));

        PartDefinition hook2 = root.addOrReplaceChild("hook2", CubeListBuilder.create().texOffs(100, 35).addBox(-1.0F, -3.0F, 0.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(100, 35).addBox(-1.0F, -3.0F, 5.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(95, 30).addBox(-1.0F, -5.0F, 0.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(306, 443).addBox(-1.5F, -5.5F, 2.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -16.0F, 81.0F, 0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r45 = hook2.addOrReplaceChild("cube_r45", CubeListBuilder.create().texOffs(248, 0).addBox(-1.0F, -110.0F, 0.0F, 2.0F, 110.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.5F, 2.6F, 0.4276F, 0.0F, 0.0F));

        // cloth parts
        PartDefinition cloth_parts = root.addOrReplaceChild("cloth_parts", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -91.142F, -4.1372F, -0.1222F, 0.0F, 0.0F));

        PartDefinition sail_10_r1 = cloth_parts.addOrReplaceChild("sail_10", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -78.0F, -6.0F, 1.0F, 78.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(39.0F, 47.6788F, -0.5F, 0.0F, 2.4435F, -1.5708F));

        PartDefinition sail_9_r1 = cloth_parts.addOrReplaceChild("sail_9", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -78.0F, -6.0F, 1.0F, 78.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(39.0F, 43.0826F, -4.3567F, 0.0F, 2.2689F, -1.5708F));

        PartDefinition sail_8_r1 = cloth_parts.addOrReplaceChild("sail_8", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -78.0F, -6.0F, 1.0F, 78.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(39.0F, 37.8864F, -7.3567F, 0.0F, 2.0944F, -1.5708F));

        PartDefinition sail_7_r1 = cloth_parts.addOrReplaceChild("sail_7", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -78.0F, -6.0F, 1.0F, 78.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(39.0F, 32.2483F, -9.4089F, 0.0F, 1.9199F, -1.5708F));

        PartDefinition sail_6_r1 = cloth_parts.addOrReplaceChild("sail_6", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -78.0F, -6.0F, 1.0F, 78.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(39.0F, 26.3394F, -10.4507F, 0.0F, 1.7453F, -1.5708F));

        PartDefinition sail_5_r1 = cloth_parts.addOrReplaceChild("sail_5", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -78.0F, -6.0F, 1.0F, 78.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(39.0F, 20.3394F, -10.4507F, 0.0F, 1.5708F, -1.5708F));

        PartDefinition sail_4_r1 = cloth_parts.addOrReplaceChild("sail_4", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -78.0F, -6.0F, 1.0F, 78.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(39.0F, 14.4306F, -9.4089F, 0.0F, 1.3963F, -1.5708F));

        PartDefinition sail_3_r1 = cloth_parts.addOrReplaceChild("sail_3", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -78.0F, -6.0F, 1.0F, 78.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(39.0F, 8.7924F, -7.3567F, 0.0F, 1.2217F, -1.5708F));

        PartDefinition sail_2_r1 = cloth_parts.addOrReplaceChild("sail_2", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -78.0F, -6.0F, 1.0F, 78.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(39.0F, 3.5963F, -4.3567F, 0.0F, 1.0472F, -1.5708F));

        PartDefinition sail_1_r1 = cloth_parts.addOrReplaceChild("sail_1", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -78.0F, -6.0F, 1.0F, 78.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(39.0F, -1.0F, -0.5F, 0.0F, 0.8727F, -1.5708F));

        PartDefinition sail_clump_r1 = cloth_parts.addOrReplaceChild("sail_clump", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -39.0F, -2.5F, 5.0F, 78.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.858F, 1.6372F, 0.0F, 0.7854F, -1.5708F));

        return LayerDefinition.create(modelData, 512, 512);
    }

    @Override
    public void setupAnim(
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
