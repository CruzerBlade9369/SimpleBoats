package com.cruzer.simpleboats.client.model;

import com.cruzer.simpleboats.client.renderer.state.MotorboatRenderState;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;

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

    public static TexturedModelData getMotorModelData()
    {
        ModelData modelData = new ModelData();
        ModelPartData root = modelData.getRoot();

        ModelPartData engine = root.addChild("engine", ModelPartBuilder.create().uv(276, 426).cuboid(-5.0F, -8.0F, -6.0F, 10.0F, 3.0F, 13.0F, new Dilation(0.0F))
                .uv(92, 426).cuboid(-5.0F, -4.0F, -6.0F, 10.0F, 4.0F, 13.0F, new Dilation(0.0F))
                .uv(414, 323).cuboid(-5.5F, -5.0F, -6.5F, 11.0F, 1.0F, 14.0F, new Dilation(0.0F))
                .uv(210, 459).cuboid(-2.0F, -6.0F, -7.5F, 4.0F, 5.0F, 2.0F, new Dilation(0.0F))
                .uv(460, 344).cuboid(-6.0F, -3.5F, -5.0F, 1.0F, 3.0F, 3.0F, new Dilation(0.0F))
                .uv(244, 442).cuboid(-5.0F, -11.0F, -6.0F, 10.0F, 3.0F, 8.0F, new Dilation(0.0F))
                .uv(220, 149).cuboid(-2.0F, 0.0F, -4.0F, 4.0F, 11.0F, 5.0F, new Dilation(0.0F))
                .uv(220, 115).cuboid(-3.0F, 11.0F, -5.0F, 6.0F, 9.0F, 7.0F, new Dilation(0.0F))
                .uv(122, 412).cuboid(-2.5F, 7.0F, -5.5F, 5.0F, 3.0F, 7.0F, new Dilation(0.0F))
                .uv(248, 429).cuboid(-2.5F, 3.0F, -5.5F, 5.0F, 3.0F, 7.0F, new Dilation(0.0F))
                .uv(140, 453).cuboid(-2.0F, 20.0F, -4.0F, 4.0F, 1.0F, 5.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, -13.0F, 48.0F));

        ModelPartData cube_r31 = engine.addChild("cube_r31", ModelPartBuilder.create().uv(0, 431).cuboid(-6.0F, 0.0F, -2.0F, 11.0F, 3.0F, 9.0F, new Dilation(0.0F)), ModelTransform.of(0.5F, -11.0F, 2.0F, -0.3927F, 0.0F, 0.0F));

        ModelPartData cube_r32 = engine.addChild("cube_r32", ModelPartBuilder.create().uv(58, 403).cuboid(-1.5F, -1.0F, -1.5F, 3.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -11.0F, -3.5F, 0.0F, -0.7854F, 0.0F));

        ModelPartData throttle = engine.addChild("throttle", ModelPartBuilder.create(), ModelTransform.origin(-7.5F, -2.0F, -3.5F));

        ModelPartData cube_r33 = throttle.addChild("cube_r33", ModelPartBuilder.create().uv(70, 403).cuboid(-1.5F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

        ModelPartData cube_r34 = throttle.addChild("cube_r34", ModelPartBuilder.create().uv(40, 431).cuboid(-0.5F, -0.5F, -0.75F, 1.0F, 1.0F, 8.0F, new Dilation(0.0F))
                .uv(238, 162).cuboid(-0.5F, -0.5F, 0.25F, 1.0F, 1.0F, 1.0F, new Dilation(0.2F))
                .uv(460, 338).cuboid(-0.5F, -0.5F, -5.75F, 1.0F, 1.0F, 5.0F, new Dilation(0.2F)), ModelTransform.of(-0.75F, -3.4994F, -6.9922F, -0.3927F, 0.0F, 0.0F));

        ModelPartData propeller_director = root.addChild("propeller_director", ModelPartBuilder.create().uv(362, 450).cuboid(-1.0F, 12.0F, -2.5F, 2.0F, 6.0F, 5.0F, new Dilation(0.0F))
                .uv(220, 99).cuboid(-3.0F, 0.0F, -3.5F, 6.0F, 9.0F, 7.0F, new Dilation(0.0F))
                .uv(436, 251).cuboid(-7.0F, 6.0F, -4.5F, 14.0F, 1.0F, 7.0F, new Dilation(0.0F))
                .uv(434, 417).cuboid(-3.0F, 9.0F, -3.5F, 6.0F, 3.0F, 12.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 8.0F, 46.5F));

        ModelPartData cube_r35 = propeller_director.addChild("cube_r35", ModelPartBuilder.create().uv(280, 442).cuboid(-1.5F, -1.5F, -3.5F, 3.0F, 3.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 18.5F, 0.0F, 0.0F, 0.0F, 0.7854F));

        ModelPartData propeller = propeller_director.addChild("propeller", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 18.5F, 6.0F));

        ModelPartData cube_r36 = propeller.addChild("cube_r36", ModelPartBuilder.create().uv(242, 162).cuboid(-0.5F, -0.5F, -1.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(222, 459).cuboid(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

        ModelPartData propeller1 = propeller.addChild("propeller1", ModelPartBuilder.create(), ModelTransform.origin(0.4619F, 3.0F, 0.8087F));

        ModelPartData cube_r37 = propeller1.addChild("cube_r37", ModelPartBuilder.create().uv(360, 440).cuboid(-1.5F, -3.0F, -0.5F, 3.0F, 6.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.3927F, 0.0F));

        ModelPartData propeller2 = propeller.addChild("propeller2", ModelPartBuilder.create(), ModelTransform.origin(3.0F, -0.4619F, 0.8087F));

        ModelPartData cube_r38 = propeller2.addChild("cube_r38", ModelPartBuilder.create().uv(220, 165).cuboid(-3.0F, -1.5F, -0.5F, 6.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

        ModelPartData propeller3 = propeller.addChild("propeller3", ModelPartBuilder.create(), ModelTransform.origin(-0.4619F, -3.0F, 0.8087F));

        ModelPartData cube_r39 = propeller3.addChild("cube_r39", ModelPartBuilder.create().uv(128, 443).cuboid(-1.5F, -3.0F, -0.5F, 3.0F, 6.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -0.3927F, 0.0F));

        ModelPartData propeller4 = propeller.addChild("propeller4", ModelPartBuilder.create(), ModelTransform.origin(-3.0F, 0.4619F, 0.8087F));

        ModelPartData cube_r40 = propeller4.addChild("cube_r40", ModelPartBuilder.create().uv(234, 165).cuboid(-3.0F, -1.5F, -0.5F, 6.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.3927F, 0.0F, 0.0F));


        return TexturedModelData.of(modelData, 512, 512);
    }

    @Override
    public void setAngles(MotorboatRenderState rs)
    {
        prop_parent.roll = rs.propellerRotation;
        prop_dir.yaw = rs.propDirYaw;
        throttle.pitch = rs.throttleLeverPitch;
    }
}
