package com.cruzer.simpleboats.client.model;

import com.cruzer.simpleboats.client.renderer.state.MotorboatRenderState;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;

public class MotorModel extends EntityModel<MotorboatRenderState>
{
    private final ModelPart motor;
    private final ModelPart propeller;

    public MotorModel(ModelPart root) {
        super(root);
        this.motor = root.getChild("motor");
        this.propeller = root.getChild("propeller");
    }

    public static TexturedModelData getMotorModelData()
    {
        ModelData modelData = new ModelData();
        ModelPartData root = modelData.getRoot();

        ModelPartData motor = root.addChild(
                "motor",
                ModelPartBuilder.create().
                        uv(0, 0).
                        cuboid(-3.0F, -5.0F, -5.0F, 7.0F, 12.0F, 10.0F,
                                new Dilation(0.0F))
                        .uv(0, 22).
                        cuboid(-1.0F, 7.0F, -1.0F, 2.0F, 4.0F, 2.0F,
                                new Dilation(0.0F)),
                ModelTransform.of(-16.0F, -3.0F, 0.0F, -3.1416F, 0.0F, 3.1416F));

        /*motor.addChild(
                "prop_2_r1",
                ModelPartBuilder.create().
                        uv(8, 22).
                        cuboid(-1.0F, -2.0F, -1.0F, 1.0F, 4.0F, 2.0F,
                                new Dilation(0.0F)),
                ModelTransform.of(2.0F, 10.0F, 0.0F, -1.5708F, 0.0F, 0.0F));*/

        ModelPartData propeller = root.addChild(
                "propeller",
                ModelPartBuilder.create(),
                ModelTransform.of(-18.0F, 7.0F, 0.0F, 0.0F, 0.0F, 0.0F)
        );

        propeller.addChild(
                "blade",
                ModelPartBuilder.create().
                        uv(8, 22).
                        cuboid(0.0F, -2.0F, -1.0F, 1.0F, 4.0F, 2.0F),
                ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F)
        );

        propeller.addChild(
                "blade_2_r1",
                ModelPartBuilder.create().
                        uv(8, 22).
                        cuboid(-1.0F, -2.0F, -1.0F, 1.0F, 4.0F, 2.0F),
                ModelTransform.of(1.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F)
        );

        return TexturedModelData.of(modelData, 34, 28);
    }

    @Override
    public void setAngles(MotorboatRenderState rs)
    {
        propeller.pitch = rs.propellerRotation;
    }
}
