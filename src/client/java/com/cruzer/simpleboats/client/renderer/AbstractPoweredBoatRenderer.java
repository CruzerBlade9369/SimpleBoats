package com.cruzer.simpleboats.client.renderer;

import com.cruzer.simpleboats.client.config.SimpleBoatsConfigManagerClient;
import com.cruzer.simpleboats.client.renderer.state.PoweredBoatRenderState;
import com.cruzer.simpleboats.entity.vehicle.AbstractPoweredBoatEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.util.Unit;
import net.minecraft.world.phys.AABB;
import org.joml.Quaternionf;

public abstract class AbstractPoweredBoatRenderer<
        E extends AbstractPoweredBoatEntity,
        S extends PoweredBoatRenderState
        > extends EntityRenderer<E, S>
{
    protected final EntityModel<S> model;
    protected final Model.Simple waterMaskModel;
    protected final Identifier texture;

    protected AbstractPoweredBoatRenderer(
                EntityRendererProvider.Context ctx,
                ModelLayerLocation baseLayer,
                Identifier texture
    )
    {
        super(ctx);
        this.model = createBaseModel(ctx, baseLayer);
        this.texture = texture;
        this.waterMaskModel = new Model.Simple(
                ctx.bakeLayer(getWaterMaskLayer()),
                id -> RenderTypes.waterMask()
        );
    }

    @Override
    protected AABB getBoundingBoxForCulling(E entity)
    {
        return entity.getBoundingBox().expandTowards(
                3.0, 5.0, 3.0
        );
    }

    protected abstract EntityModel<S> createBaseModel(
            EntityRendererProvider.Context ctx,
            ModelLayerLocation layer
    );

    protected abstract ModelLayerLocation getWaterMaskLayer();

    protected void updateSubclassState(E entity, S state, float tick)
    {
        // For subclasses to override
    }

    protected void renderAttachments(
            S state,
            PoseStack matrices,
            SubmitNodeCollector queue
    )
    {
        // For subclasses to override
    }

    protected void applyBoatTransform(
            PoweredBoatRenderState state,
            PoseStack matrices
    )
    {
        // position/rotation similar to vanilla boats
        matrices.translate(0.0F, 0.375F, 0.0F);
        matrices.mulPose(Axis.YP.rotationDegrees(180.0F - state.yRot));

        // damage wobble / bubble wobble
        float f = state.hurtTime;
        if (f > 0.0F) {
            matrices.mulPose(Axis.XP.rotationDegrees(
                    Mth.sin(f) * f * state.damageTime / 10.0F * state.hurtDir
            ));
        }

        if (!state.isUnderWater && !Mth.equal(state.bubbleAngle, 0.0F)) {
            matrices.mulPose(new Quaternionf().setAngleAxis(state.bubbleAngle * (float) (Math.PI / 180.0), 1.0F, 0.0F, 1.0F));
        }

        // ambient sway
        matrices.mulPose(
                Axis.ZP.rotationDegrees(state.swayRoll)
        );
        matrices.mulPose(
                Axis.XP.rotationDegrees(state.swayPitch)
        );
        // speed pitch
        matrices.mulPose(
                Axis.XP.rotationDegrees(state.speedPitch)
        );
        // turn lean
        matrices.mulPose(
                Axis.ZP.rotationDegrees(state.turnRoll)
        );

        // flip / rotate to match model orientation
        matrices.scale(-1.0F, -1.0F, 1.0F);
    }

    protected void updateBoatSway(E entity, S state, float tick)
    {
        if (!entity.isInWater()) return;

        applyAmbientSway(entity, state, tick);
        applySpeedPitch(entity, state);
        applyTurnRoll(entity, state);
    }

    private void applyAmbientSway(E entity, S state, float tick)
    {
        float phaseOffset = (entity.getUUID().hashCode() & 0xFFFF) * 0.01F;
        float time = entity.tickCount + tick + phaseOffset;

        boolean isRaining = entity.level().isRaining();
        float rainAmplitudeMultiplier = isRaining ? 2.2F * SimpleBoatsConfigManagerClient.CONFIG.rainSwayMultiplier : 1.0F;
        float rainSpeedMultiplier = isRaining ? 2.2F : 1.0F;

        float baseSwayAmp = 0.5F * SimpleBoatsConfigManagerClient.CONFIG.swayAmplitudeMultiplier;
        float baseSwaySpeed = 0.05F * SimpleBoatsConfigManagerClient.CONFIG.swaySpeedMultiplier;

        float swayAmp = baseSwayAmp * rainAmplitudeMultiplier;
        float swaySpeed = baseSwaySpeed * rainSpeedMultiplier  * 0.85f;

        state.swayRoll =
                Mth.sin(time * swaySpeed) * swayAmp;

        state.swayPitch =
                Mth.cos(time * swaySpeed * 0.8F) * swayAmp * 0.6F;
    }

    private void applySpeedPitch(E entity, S state)
    {
        float maxSpeedPitch = 3.5F;
        float pitchMultiplier = SimpleBoatsConfigManagerClient.CONFIG.pitchMultiplier;

        float fwdSpd = entity.getForwardSpeed();
        float speedClamped = Mth.clamp(fwdSpd, 0.0F, 1.0F);

        state.speedPitch = pitchMultiplier *
                Mth.lerp(
                1F,
                state.speedPitch,
                speedClamped * maxSpeedPitch
                );
    }

    private void applyTurnRoll(E entity, S state)
    {
        float turnStrength = 0.6f * SimpleBoatsConfigManagerClient.CONFIG.turnLeanMultiplier;

        state.turnRoll = entity.getYawVelocity() * turnStrength;
    }

    @Override
    public void extractRenderState(E entity, S state, float tick)
    {
        super.extractRenderState(entity, state, tick);

        state.yRot = entity.getYRot(tick);
        state.pitch = Mth.lerp(tick, entity.xRotO, entity.getViewXRot(tick));

        state.hurtTime = entity.getHurtTime() - tick;
        state.hurtDir = entity.getHurtDir();
        state.damageTime = Math.max(entity.getDamage() - tick, 0.0F);
        state.bubbleAngle = entity.getBubbleAngle(tick);
        state.isUnderWater = entity.isUnderWater();

        state.texture = this.texture;

        updateSubclassState(entity, state, tick);
        updateBoatSway(entity, state, tick);
    }

    @Override
    public void submit(
            S state,
            PoseStack matrices,
            SubmitNodeCollector queue,
            CameraRenderState camera
    )
    {
        matrices.pushPose();

        applyBoatTransform(state, matrices);

        model.setupAnim(state);

        queue.submitModel(
                this.model,
                state,
                matrices,
                RenderTypes.entityTranslucent(state.texture),
                state.lightCoords,
                OverlayTexture.NO_OVERLAY,
                state.outlineColor,
                null
        );

        renderAttachments(state, matrices, queue);

        if (!state.isUnderWater) {
            queue.submitModel(
                    this.waterMaskModel,
                    Unit.INSTANCE,
                    matrices,
                    RenderTypes.waterMask(),
                    state.lightCoords,
                    OverlayTexture.NO_OVERLAY,
                    state.outlineColor,
                    null
            );
        }

        matrices.popPose();
        super.submit(state, matrices, queue, camera);
    }
}
