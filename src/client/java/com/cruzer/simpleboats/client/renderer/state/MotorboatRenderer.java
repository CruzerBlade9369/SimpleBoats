package com.cruzer.simpleboats.client.renderer.state;

import com.cruzer.simpleboats.SimpleBoats;
import com.cruzer.simpleboats.client.model.MotorModel;
import com.cruzer.simpleboats.client.model.MotorboatModel;
import com.cruzer.simpleboats.client.model.MotorboatModelLayers;
import com.cruzer.simpleboats.entity.vehicle.MotorboatEntity;
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

public class MotorboatRenderer extends EntityRenderer<MotorboatEntity, MotorboatRenderState>
{
    private final Model.SinglePartModel waterMaskModel;
    private final Identifier texture;
    private final Identifier motorTexture;
    private final EntityModel<MotorboatRenderState> model;
    private final MotorModel motorModel;

    public MotorboatRenderer(EntityRendererFactory.Context ctx, Identifier texture)
    {
        super(ctx);
        EntityModelLayer layer = MotorboatModelLayers.MOTORBOAT;
        this.model = new MotorboatModel(ctx.getPart(layer));
        this.motorModel = new MotorModel(ctx.getPart(MotorboatModelLayers.MOTORBOAT_MOTOR));
        this.waterMaskModel = new Model.SinglePartModel(
                ctx.getPart(MotorboatModelLayers.MOTORBOAT_WATER_MASK),
                id -> RenderLayers.waterMask()
        );
        this.texture = texture;
        this.motorTexture = Identifier.of(SimpleBoats.MOD_ID, "textures/entity/motorboat/motor.png");
    }

    @Override
    public MotorboatRenderState createRenderState()
    {
        return new MotorboatRenderState();
    }

    @Override
    public void updateRenderState(MotorboatEntity entity, MotorboatRenderState state, float tick)
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
        state.motorTexture = this.motorTexture;

        state.propellerRotation = MathHelper.lerpAngleRadians(
                tick,
                entity.getPropellerAngle()[1],
                entity.getPropellerAngle()[0]
        );
    }

    @Override
    public void render(
            MotorboatRenderState state,
            MatrixStack matrices,
            OrderedRenderCommandQueue queue,
            CameraRenderState camera
    )
    {
        matrices.push();

        // position/rotation similar to vanilla boats (tweak if needed)
        matrices.translate(0.0F, 0.375F, 0.0F);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F - state.yaw));

        // damage wobble / bubble wobble if you want to replicate vanilla effects:
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

        // Ensure the model angles are applied from the state
        this.model.setAngles(state);

        // Submit the model via the queue (this is the key step)
        queue.submitModel(
                this.model,                                        // the Model (EntityModel / Model subclass)
                state,                                             // render state with texture + lighting info
                matrices,                                          // current matrices
                RenderLayers.entityTranslucent(state.texture),   // RenderLayer obtained from model + texture
                state.light,                                       // packed light
                OverlayTexture.DEFAULT_UV,                         // overlay UV (vanilla uses DEFAULT_UV here)
                state.outlineColor,                                // outline color for outlines if any
                null
        );

        queue.submitModel(
                this.motorModel,
                state,
                matrices,
                RenderLayers.entityTranslucent(state.motorTexture),
                state.light,
                OverlayTexture.DEFAULT_UV,
                state.outlineColor,
                null
        );

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
