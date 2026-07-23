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

public class TillerModel extends EntityModel<SailboatRenderState>
{
    private final ModelPart tiller;
    private final ModelPart tiller_pivot;

    public TillerModel(ModelPart root) {
        super(root);
        this.tiller = root.getChild("tiller");
        this.tiller_pivot = tiller.getChild("tiller_pivot");
    }

    public static LayerDefinition getTillerModelData()
    {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition root = modelData.getRoot();

        PartDefinition tiller = root.addOrReplaceChild("tiller", CubeListBuilder.create().texOffs(92, 0).addBox(-2.5F, 2.0F, -1.5F, 5.0F, 19.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -15.0F, 47.5F));

        PartDefinition tiller_pivot = tiller.addOrReplaceChild("tiller_pivot", CubeListBuilder.create().texOffs(136, 0).addBox(-1.5F, 21.0F, -1.5F, 3.0F, 12.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(138, 1).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(95, 45).addBox(-0.5F, -6.0F, -9.5F, 1.0F, 1.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(98, 13).addBox(-1.0F, 23.0F, -0.5F, 2.0F, 16.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r41 = tiller_pivot.addOrReplaceChild("cube_r41", CubeListBuilder.create().texOffs(334, 460).addBox(-1.0F, -2.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.5F, -3.5F, 0.0F, 1.5708F, 0.0F));

        return LayerDefinition.create(modelData, 512, 512);
    }

    @Override
    public void setupAnim(SailboatRenderState rs)
    {
        tiller_pivot.yRot = rs.tillerYaw;
    }
}
