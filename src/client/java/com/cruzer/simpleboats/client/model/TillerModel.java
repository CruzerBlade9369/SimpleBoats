package com.cruzer.simpleboats.client.model;

import com.cruzer.simpleboats.client.renderer.state.SailboatRenderState;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;

public class TillerModel extends EntityModel<SailboatRenderState>
{
    private final ModelPart tiller;
    private final ModelPart tiller_pivot;

    public TillerModel(ModelPart root) {
        super(root);
        this.tiller = root.getChild("tiller");
        this.tiller_pivot = tiller.getChild("tiller_pivot");
    }

    public static TexturedModelData getTillerModelData()
    {
        ModelData modelData = new ModelData();
        ModelPartData root = modelData.getRoot();

        ModelPartData tiller = root.addChild("tiller", ModelPartBuilder.create().uv(92, 0).cuboid(-2.5F, 2.0F, -1.5F, 5.0F, 19.0F, 4.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, -15.0F, 47.5F));

        ModelPartData tiller_pivot = tiller.addChild("tiller_pivot", ModelPartBuilder.create().uv(136, 0).cuboid(-1.5F, 21.0F, -1.5F, 3.0F, 12.0F, 3.0F, new Dilation(0.0F))
                .uv(138, 1).cuboid(-1.0F, -8.0F, -1.0F, 2.0F, 10.0F, 2.0F, new Dilation(0.0F))
                .uv(95, 45).cuboid(-0.5F, -6.0F, -9.5F, 1.0F, 1.0F, 12.0F, new Dilation(0.0F))
                .uv(98, 13).cuboid(-1.0F, 23.0F, -0.5F, 2.0F, 16.0F, 10.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData cube_r41 = tiller_pivot.addChild("cube_r41", ModelPartBuilder.create().uv(334, 460).cuboid(-1.0F, -2.0F, -1.0F, 6.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -4.5F, -3.5F, 0.0F, 1.5708F, 0.0F));

        return TexturedModelData.of(modelData, 512, 512);
    }

    @Override
    public void setAngles(SailboatRenderState rs)
    {
        tiller_pivot.yaw = rs.tillerYaw;
    }
}
