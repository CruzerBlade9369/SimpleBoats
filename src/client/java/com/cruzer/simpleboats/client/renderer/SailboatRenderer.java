package com.cruzer.simpleboats.client.renderer;

import com.cruzer.simpleboats.client.model.GenericBoatModel;
import com.cruzer.simpleboats.client.model.SailModel;
import com.cruzer.simpleboats.client.model.SimpleBoatsModelLayers;
import com.cruzer.simpleboats.client.model.TillerModel;
import com.cruzer.simpleboats.client.renderer.state.SailboatRenderState;
import com.cruzer.simpleboats.entity.vehicle.SailboatEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

public class SailboatRenderer extends AbstractPoweredBoatRenderer<SailboatEntity, SailboatRenderState>
{
    private final TillerModel tiller;
    private final SailModel sailModel;

    public SailboatRenderer(EntityRendererProvider.Context ctx, Identifier texture)
    {
        super(ctx, SimpleBoatsModelLayers.BOAT_HULL, texture);
        this.tiller = new TillerModel(ctx.bakeLayer(SimpleBoatsModelLayers.SAILBOAT_TILLER));
        this.sailModel = new SailModel(ctx.bakeLayer(SimpleBoatsModelLayers.SAILBOAT_SAIL_BASE));
    }

    @Override
    public SailboatRenderState createRenderState()
    {
        return new SailboatRenderState();
    }

    @Override
    protected ModelLayerLocation getWaterMaskLayer()
    {
        return SimpleBoatsModelLayers.BOAT_WATER_MASK;
    }

    @Override
    protected EntityModel<SailboatRenderState> createBaseModel(
            EntityRendererProvider.Context ctx,
            ModelLayerLocation layer
    )
    {
        return new GenericBoatModel<>(ctx.bakeLayer(layer));
    }

    @Override
    protected void updateSubclassState(
            SailboatEntity entity,
            SailboatRenderState state,
            float tick
    )
    {
        state.tillerYaw = entity.getTillerYaw();
        float throttle = entity.getPowerLevel();
        state.sailLevel = (int)Math.min(5, Math.ceil(throttle * 4.01f));
    }

    @Override
    protected void renderAttachments(
            SailboatRenderState state,
            PoseStack matrices,
            SubmitNodeCollector queue
    )
    {
        queue.submitModel(
                tiller,
                state,
                matrices,
                RenderTypes.entityTranslucent(state.texture),
                state.lightCoords,
                OverlayTexture.NO_OVERLAY,
                state.outlineColor,
                null
        );

        queue.submitModel(
                sailModel,
                state,
                matrices,
                RenderTypes.entityTranslucent(state.texture),
                state.lightCoords,
                OverlayTexture.NO_OVERLAY,
                state.outlineColor,
                null
        );
    }
}
