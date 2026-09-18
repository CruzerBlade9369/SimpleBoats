package com.cruzer.simpleboats.client.model;

import com.cruzer.simpleboats.client.renderer.state.MotorboatRenderState;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class MotorModel extends EntityModel<MotorboatRenderState>
{
    private final ModelPart engine;
    private final ModelPart throttle;
    private final ModelPart prop_dir;
    private final ModelPart prop_parent;

    public MotorModel(ModelPart root) {
        super(root);
        this.engine = root.getChild("engine");
        this.prop_dir = root.getChild("propeller_director");
        this.prop_parent = prop_dir.getChild("propeller");
        this.throttle = engine.getChild("throttle");
    }

    public static LayerDefinition getMotorModelData()
    {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition root = modelData.getRoot();

        PartDefinition engine = root.addOrReplaceChild("engine", CubeListBuilder.create().texOffs(276, 426).addBox(-5.0F, -8.0F, -6.0F, 10.0F, 3.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(92, 426).addBox(-5.0F, -4.0F, -6.0F, 10.0F, 4.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(414, 323).addBox(-5.5F, -5.0F, -6.5F, 11.0F, 1.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(210, 459).addBox(-2.0F, -6.0F, -7.5F, 4.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(460, 344).addBox(-6.0F, -3.5F, -5.0F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(244, 442).addBox(-5.0F, -11.0F, -6.0F, 10.0F, 3.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(220, 149).addBox(-2.0F, 0.0F, -4.0F, 4.0F, 11.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(220, 115).addBox(-3.0F, 11.0F, -5.0F, 6.0F, 9.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(122, 412).addBox(-2.5F, 7.0F, -5.5F, 5.0F, 3.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(248, 429).addBox(-2.5F, 3.0F, -5.5F, 5.0F, 3.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(140, 453).addBox(-2.0F, 20.0F, -4.0F, 4.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -13.0F, 48.0F));

        PartDefinition cube_r31 = engine.addOrReplaceChild("cube_r31", CubeListBuilder.create().texOffs(0, 431).addBox(-6.0F, 0.0F, -2.0F, 11.0F, 3.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -11.0F, 2.0F, -0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r32 = engine.addOrReplaceChild("cube_r32", CubeListBuilder.create().texOffs(58, 403).addBox(-1.5F, -1.0F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -11.0F, -3.5F, 0.0F, -0.7854F, 0.0F));

        PartDefinition throttle = engine.addOrReplaceChild("throttle", CubeListBuilder.create(), PartPose.offset(-7.5F, -2.0F, -3.5F));

        PartDefinition cube_r33 = throttle.addOrReplaceChild("cube_r33", CubeListBuilder.create().texOffs(70, 403).addBox(-1.5F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r34 = throttle.addOrReplaceChild("cube_r34", CubeListBuilder.create().texOffs(40, 431).addBox(-0.5F, -0.5F, -0.75F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(238, 162).addBox(-0.5F, -0.5F, 0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.2F))
                .texOffs(460, 338).addBox(-0.5F, -0.5F, -5.75F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(-0.75F, -3.4994F, -6.9922F, -0.3927F, 0.0F, 0.0F));

        PartDefinition propeller_director = root.addOrReplaceChild("propeller_director", CubeListBuilder.create().texOffs(362, 450).addBox(-1.0F, 12.0F, -2.5F, 2.0F, 6.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(220, 99).addBox(-3.0F, 0.0F, -3.5F, 6.0F, 9.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(436, 251).addBox(-7.0F, 6.0F, -4.5F, 14.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(434, 417).addBox(-3.0F, 9.0F, -3.5F, 6.0F, 3.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 8.0F, 46.5F));

        PartDefinition cube_r35 = propeller_director.addOrReplaceChild("cube_r35", CubeListBuilder.create().texOffs(280, 442).addBox(-1.5F, -1.5F, -3.5F, 3.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 18.5F, 0.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition propeller = propeller_director.addOrReplaceChild("propeller", CubeListBuilder.create(), PartPose.offset(0.0F, 18.5F, 6.0F));

        PartDefinition cube_r36 = propeller.addOrReplaceChild("cube_r36", CubeListBuilder.create().texOffs(242, 162).addBox(-0.5F, -0.5F, -1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(222, 459).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition propeller1 = propeller.addOrReplaceChild("propeller1", CubeListBuilder.create(), PartPose.offset(0.4619F, 3.0F, 0.8087F));

        PartDefinition cube_r37 = propeller1.addOrReplaceChild("cube_r37", CubeListBuilder.create().texOffs(360, 440).addBox(-1.5F, -3.0F, -0.5F, 3.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.3927F, 0.0F));

        PartDefinition propeller2 = propeller.addOrReplaceChild("propeller2", CubeListBuilder.create(), PartPose.offset(3.0F, -0.4619F, 0.8087F));

        PartDefinition cube_r38 = propeller2.addOrReplaceChild("cube_r38", CubeListBuilder.create().texOffs(220, 165).addBox(-3.0F, -1.5F, -0.5F, 6.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

        PartDefinition propeller3 = propeller.addOrReplaceChild("propeller3", CubeListBuilder.create(), PartPose.offset(-0.4619F, -3.0F, 0.8087F));

        PartDefinition cube_r39 = propeller3.addOrReplaceChild("cube_r39", CubeListBuilder.create().texOffs(128, 443).addBox(-1.5F, -3.0F, -0.5F, 3.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.3927F, 0.0F));

        PartDefinition propeller4 = propeller.addOrReplaceChild("propeller4", CubeListBuilder.create(), PartPose.offset(-3.0F, 0.4619F, 0.8087F));

        PartDefinition cube_r40 = propeller4.addOrReplaceChild("cube_r40", CubeListBuilder.create().texOffs(234, 165).addBox(-3.0F, -1.5F, -0.5F, 6.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3927F, 0.0F, 0.0F));


        return LayerDefinition.create(modelData, 512, 512);
    }

    @Override
    public void setupAnim(MotorboatRenderState rs)
    {
        prop_parent.zRot = rs.propellerRotation;
        prop_dir.yRot = rs.propDirYaw;
        throttle.xRot = rs.throttleLeverPitch;
    }
}
