package com.cruzer.simpleboats.client.renderer;

import com.cruzer.simpleboats.client.model.GenericBoatModel;
import com.cruzer.simpleboats.client.model.SailModel;
import com.cruzer.simpleboats.client.model.SimpleBoatsModelLayers;
import com.cruzer.simpleboats.client.model.TillerModel;
import com.cruzer.simpleboats.client.renderer.state.SailboatRenderState;
import com.cruzer.simpleboats.entity.vehicle.SailboatEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class SailboatRenderer extends AbstractPoweredBoatRenderer<SailboatEntity, SailboatRenderState>
{
    private final TillerModel tiller;
    private final SailModel sailModel;

    public SailboatRenderer(EntityRendererFactory.Context ctx, Identifier texture)
    {
        super(ctx, SimpleBoatsModelLayers.BOAT_HULL, texture);
        this.tiller = new TillerModel(ctx.getPart(SimpleBoatsModelLayers.SAILBOAT_TILLER));
        this.sailModel = new SailModel(ctx.getPart(SimpleBoatsModelLayers.SAILBOAT_SAIL_BASE));
    }

    @Override
    public SailboatRenderState createRenderState()
    {
        return new SailboatRenderState();
    }

    @Override
    protected EntityModelLayer getWaterMaskLayer()
    {
        return SimpleBoatsModelLayers.BOAT_WATER_MASK;
    }

    @Override
    protected EntityModel<SailboatRenderState> createBaseModel(
            EntityRendererFactory.Context ctx,
            EntityModelLayer layer
    )
    {
        return new GenericBoatModel<>(ctx.getPart(layer));
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
            MatrixStack matrices,
            OrderedRenderCommandQueue queue
    )
    {
        queue.submitModel(
                tiller,
                state,
                matrices,
                RenderLayers.entityTranslucent(state.texture),
                state.light,
                OverlayTexture.DEFAULT_UV,
                state.outlineColor,
                null
        );

        queue.submitModel(
                sailModel,
                state,
                matrices,
                RenderLayers.entityTranslucent(state.texture),
                state.light,
                OverlayTexture.DEFAULT_UV,
                state.outlineColor,
                null
        );
    }
}
