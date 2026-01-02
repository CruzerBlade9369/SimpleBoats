package com.cruzer.simpleboats.client.renderer;

import com.cruzer.simpleboats.client.renderer.state.PoweredBoatRenderState;
import com.cruzer.simpleboats.entity.vehicle.AbstractPoweredBoatEntity;
import net.minecraft.client.model.Model;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Unit;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.joml.Quaternionf;

public abstract class AbstractPoweredBoatRenderer<
        E extends AbstractPoweredBoatEntity,
        S extends PoweredBoatRenderState
        > extends EntityRenderer<E, S>
{
    protected final EntityModel<S> model;
    protected final Model.SinglePartModel waterMaskModel;
    protected final Identifier texture;

    protected AbstractPoweredBoatRenderer(
                EntityRendererFactory.Context ctx,
                EntityModelLayer baseLayer,
                Identifier texture
    )
    {
        super(ctx);
        this.model = createBaseModel(ctx, baseLayer);
        this.texture = texture;
        this.waterMaskModel = new Model.SinglePartModel(
                ctx.getPart(getWaterMaskLayer()),
                id -> RenderLayers.waterMask()
        );
    }

    protected abstract EntityModel<S> createBaseModel(
            EntityRendererFactory.Context ctx,
            EntityModelLayer layer
    );

    protected abstract EntityModelLayer getWaterMaskLayer();

    protected void updateSubclassState(E entity, S state, float tick)
    {
        // For subclasses to override
    }

    protected void renderAttachments(
            S state,
            MatrixStack matrices,
            OrderedRenderCommandQueue queue
    )
    {
        // For subclasses to override
    }

    protected void applyBoatTransform(
            PoweredBoatRenderState state,
            MatrixStack matrices
    )
    {
        // position/rotation similar to vanilla boats
        matrices.translate(0.0F, 0.375F, 0.0F);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F - state.yaw));

        // damage wobble / bubble wobble
        float f = state.damageWobbleTicks;
        if (f > 0.0F) {
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(
                    MathHelper.sin(f) * f * state.damageWobbleStrength / 10.0F * state.damageWobbleSide
            ));
        }

        if (!state.submergedInWater && !MathHelper.approximatelyEquals(state.bubbleWobble, 0.0F)) {
            matrices.multiply(new Quaternionf().setAngleAxis(state.bubbleWobble * (float) (Math.PI / 180.0), 1.0F, 0.0F, 1.0F));
        }

        // flip / rotate to match model orientation
        matrices.scale(-1.0F, -1.0F, 1.0F);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90.0F));
    }

    @Override
    public void updateRenderState(E entity, S state, float tick)
    {
        super.updateRenderState(entity, state, tick);

        state.yaw = entity.getLerpedYaw(tick);
        state.pitch = MathHelper.lerp(tick, entity.lastPitch, entity.getPitch());

        state.damageWobbleTicks = entity.getDamageWobbleTicks() - tick;
        state.damageWobbleSide = entity.getDamageWobbleSide();
        state.damageWobbleStrength = Math.max(entity.getDamageWobbleStrength() - tick, 0.0F);
        state.bubbleWobble = entity.lerpBubbleWobble(tick);
        state.submergedInWater = entity.isSubmergedInWater();

        state.texture = this.texture;

        updateSubclassState(entity, state, tick);
    }

    @Override
    public void render(
            S state,
            MatrixStack matrices,
            OrderedRenderCommandQueue queue,
            CameraRenderState camera
    )
    {
        matrices.push();

        applyBoatTransform(state, matrices);

        model.setAngles(state);

        queue.submitModel(
                this.model,
                state,
                matrices,
                RenderLayers.entityTranslucent(state.texture),
                state.light,
                OverlayTexture.DEFAULT_UV,
                state.outlineColor,
                null
        );

        renderAttachments(state, matrices, queue);

        if (!state.submergedInWater) {
            queue.submitModel(
                    this.waterMaskModel,
                    Unit.INSTANCE,
                    matrices,
                    RenderLayers.waterMask(),
                    state.light,
                    OverlayTexture.DEFAULT_UV,
                    state.outlineColor,
                    null
            );
        }

        matrices.pop();
        super.render(state, matrices, queue, camera);
    }
}
