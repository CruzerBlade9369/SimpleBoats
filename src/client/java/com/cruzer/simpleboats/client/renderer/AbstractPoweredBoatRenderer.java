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
import net.minecraft.entity.vehicle.AbstractBoatEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.Unit;
import net.minecraft.util.math.Box;
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

    @Override
    protected Box getBoundingBox(E entity)
    {
        return entity.getBoundingBox().expand(
                2.5, 1.0, 2.5
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

        // ambient sway
        matrices.multiply(
                RotationAxis.POSITIVE_Z.rotationDegrees(state.swayRoll)
        );
        matrices.multiply(
                RotationAxis.POSITIVE_X.rotationDegrees(state.swayPitch)
        );
        // speed pitch
        matrices.multiply(
                RotationAxis.POSITIVE_X.rotationDegrees(state.speedPitch)
        );
        // turn lean
        matrices.multiply(
                RotationAxis.POSITIVE_Z.rotationDegrees(state.turnRoll)
        );

        // flip / rotate to match model orientation
        matrices.scale(-1.0F, -1.0F, 1.0F);
        // matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90.0F));
    }

    protected void updateBoatSway(E entity, S state, float tick)
    {
        if (entity.getLocation() != AbstractBoatEntity.Location.IN_WATER) return;

        applyAmbientSway(entity, state, tick);
        applySpeedPitch(entity, state);
        applyTurnRoll(entity, state);
    }

    private void applyAmbientSway(E entity, S state, float tick)
    {
        int hash = entity.getUuid().hashCode();
        float phaseOffset =
                (hash & 0xFFFF) / (float) 0xFFFF * MathHelper.TAU;

        float time = entity.age + tick + phaseOffset;

        boolean isRaining = entity.getEntityWorld().isRaining() && entity.getEntityWorld().isSkyVisibleAllowingSea(entity.getBlockPos());
        float rainMultiplier = isRaining ? 2.2F : 1.0F;

        float baseSwayAmp = 0.5F;
        float baseSwaySpeed = 0.05F;

        float swayAmp = baseSwayAmp * rainMultiplier;
        float swaySpeed = baseSwaySpeed * rainMultiplier * 0.85f;

        state.swayRoll =
                MathHelper.sin(time * swaySpeed) * swayAmp;

        state.swayPitch =
                MathHelper.cos(time * swaySpeed * 0.8F) * swayAmp * 0.6F;
    }

    private void applySpeedPitch(E entity, S state)
    {
        float maxSpeedPitch = 3.5F;

        float fwdSpd = entity.getForwardSpeed();
        float speedClamped = MathHelper.clamp(fwdSpd, 0.0F, 1.0F);

        state.speedPitch = MathHelper.lerp(
                1F,
                state.speedPitch,
                speedClamped * maxSpeedPitch
        );
    }

    private void applyTurnRoll(E entity, S state)
    {
        float turnStrength = 0.55F;

        state.turnRoll = entity.getYawVelocity() * turnStrength;
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
        updateBoatSway(entity, state, tick);
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
