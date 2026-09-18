package com.cruzer.simpleboats.client.renderer;

import com.cruzer.simpleboats.client.config.SimpleBoatsConfigManagerClient;
import com.cruzer.simpleboats.client.model.*;
import com.cruzer.simpleboats.client.renderer.state.MotorboatRenderState;
import com.cruzer.simpleboats.entity.vehicle.MotorboatEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

public class MotorboatRenderer extends AbstractPoweredBoatRenderer<MotorboatEntity, MotorboatRenderState>
{
    private final Identifier motorTexture;
    private final MotorModel motorModel;

    public MotorboatRenderer(EntityRendererProvider.Context ctx, Identifier texture)
    {
        super(ctx, SimpleBoatsModelLayers.BOAT_HULL, texture);
        this.motorModel = new MotorModel(ctx.bakeLayer(SimpleBoatsModelLayers.MOTORBOAT_MOTOR));
        this.motorTexture = texture;
    }

    @Override
    public MotorboatRenderState createRenderState()
    {
        return new MotorboatRenderState();
    }

    @Override
    protected ModelLayerLocation getWaterMaskLayer()
    {
        return SimpleBoatsModelLayers.BOAT_WATER_MASK;
    }

    @Override
    protected EntityModel<MotorboatRenderState> createBaseModel(
            EntityRendererProvider.Context ctx,
            ModelLayerLocation layer
    )
    {
        return new GenericBoatModel<>(ctx.bakeLayer(layer));
    }

    @Override
    protected void updateSubclassState(
            MotorboatEntity entity,
            MotorboatRenderState state,
            float tick
    )
    {
        state.motorTexture = motorTexture;
        state.propellerRotation = -Mth.rotLerpRad(
                tick,
                entity.getPropellerAngle()[1],
                entity.getPropellerAngle()[0]
        );
        state.propDirYaw = entity.getPropDirYaw();
        state.throttleLeverPitch = -entity.getPowerLevel() * 0.2f;
        if (SimpleBoatsConfigManagerClient.CONFIG.motorboatPropellerBubbles)
        {
            entity.tickPropellerEffects();
        }
    }

    @Override
    protected void renderAttachments(
            MotorboatRenderState state,
            PoseStack matrices,
            SubmitNodeCollector queue
    )
    {
        queue.submitModel(
                motorModel,
                state,
                matrices,
                RenderTypes.entityTranslucent(state.motorTexture),
                state.lightCoords,
                OverlayTexture.NO_OVERLAY,
                state.outlineColor,
                null
        );
    }
}
