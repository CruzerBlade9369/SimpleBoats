package com.cruzer.simpleboats.client.renderer;

import com.cruzer.simpleboats.SimpleBoats;
import com.cruzer.simpleboats.client.model.MotorModel;
import com.cruzer.simpleboats.client.model.MotorboatModel;
import com.cruzer.simpleboats.client.model.MotorboatModelLayers;
import com.cruzer.simpleboats.client.renderer.state.MotorboatRenderState;
import com.cruzer.simpleboats.entity.vehicle.MotorboatEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class MotorboatRenderer extends AbstractPoweredBoatRenderer<MotorboatEntity, MotorboatRenderState>
{
    private final Identifier motorTexture;
    private final MotorModel motorModel;

    public MotorboatRenderer(EntityRendererFactory.Context ctx, Identifier texture)
    {
        super(ctx, MotorboatModelLayers.MOTORBOAT, texture);
        this.motorModel = new MotorModel(ctx.getPart(MotorboatModelLayers.MOTORBOAT_MOTOR));
        this.motorTexture = Identifier.of(SimpleBoats.MOD_ID, "textures/entity/motorboat/motor.png");
    }

    @Override
    public MotorboatRenderState createRenderState()
    {
        return new MotorboatRenderState();
    }

    @Override
    protected EntityModelLayer getWaterMaskLayer()
    {
        return MotorboatModelLayers.MOTORBOAT_WATER_MASK;
    }

    @Override
    protected EntityModel<MotorboatRenderState> createBaseModel(
            EntityRendererFactory.Context ctx,
            EntityModelLayer layer
    )
    {
        return new MotorboatModel(ctx.getPart(layer));
    }

    @Override
    protected void updateSubclassState(
            MotorboatEntity entity,
            MotorboatRenderState state,
            float tick
    )
    {
        state.motorTexture = motorTexture;
        state.propellerRotation = MathHelper.lerpAngleRadians(
                tick,
                entity.getPropellerAngle()[1],
                entity.getPropellerAngle()[0]
        );
    }

    @Override
    protected void renderAttachments(
            MotorboatRenderState state,
            MatrixStack matrices,
            OrderedRenderCommandQueue queue
    )
    {
        queue.submitModel(
                motorModel,
                state,
                matrices,
                RenderLayers.entityTranslucent(state.motorTexture),
                state.light,
                OverlayTexture.DEFAULT_UV,
                state.outlineColor,
                null
        );
    }
}
