// Made with Blockbench 5.0.4
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package com.cruzer.simpleboats.client.model;

import com.cruzer.simpleboats.client.renderer.state.MotorboatRenderState;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;

public class MotorboatModel extends EntityModel<MotorboatRenderState> {
    private final ModelPart bottom;
    private final ModelPart front;
    private final ModelPart back;
    private final ModelPart right;
    private final ModelPart left;

    public MotorboatModel(ModelPart root) {
        super(root);
        this.bottom = root.getChild("bottom");
        this.front = root.getChild("front");
        this.back = root.getChild("back");
        this.right = root.getChild("right");
        this.left = root.getChild("left");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData bottom = modelPartData.addChild("bottom", ModelPartBuilder.create().uv(0, 0).mirrored().cuboid(-14.0F, -8.0F, 0.0F, 28.0F, 16.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 6.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

        ModelPartData front = modelPartData.addChild("front", ModelPartBuilder.create().uv(0, 27).mirrored().cuboid(-8.0F, -3.0F, -1.0F, 16.0F, 6.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(15.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        ModelPartData back = modelPartData.addChild("back", ModelPartBuilder.create().uv(0, 19).mirrored().cuboid(-9.0F, -3.0F, -1.0F, 18.0F, 6.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-15.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        ModelPartData right = modelPartData.addChild("right", ModelPartBuilder.create().uv(0, 35).mirrored().cuboid(-14.0F, -3.0F, -1.0F, 28.0F, 6.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, -9.0F, -3.1416F, 0.0F, 3.1416F));

        ModelPartData left = modelPartData.addChild("left", ModelPartBuilder.create().uv(0, 43).mirrored().cuboid(-14.0F, -3.0F, -1.0F, 28.0F, 6.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.origin(0.0F, 0.0F, 9.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }

    public static TexturedModelData getWaterPatchModelData()
    {
        ModelData modelData = new ModelData();
        ModelPartData root = modelData.getRoot();

        root.addChild(
                "water_patch",
                ModelPartBuilder.create().
                        uv(0, 0).
                        cuboid(-14.0F, -27.0F, -8.0F, 28.0F, 6.0F, 16.0F,
                                new Dilation(0.0F)),
                ModelTransform.origin(0.0F, 24.0F, 0.0F)
        );
        return TexturedModelData.of(modelData, 64, 64);
    }
}