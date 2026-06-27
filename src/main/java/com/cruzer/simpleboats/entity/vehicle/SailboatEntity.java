package com.cruzer.simpleboats.entity.vehicle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import java.util.function.Supplier;

public class SailboatEntity extends AbstractPoweredBoatEntity
{
    private static final float SAIL_HOIST_RATE = 0.04f;
    private static final int MAX_PAX = 5;

    protected static float thrustForce = 0.05f / DRAG_COMPENSATOR;

    private static float rudderFullEffectSpd = 0.5f;
    private static float maxTurn = 0.3f;
    private static float minTurn = 0.05f;

    @Environment(EnvType.CLIENT)
    private float tillerYaw = 0f;

    public SailboatEntity(EntityType<? extends SailboatEntity> entityType, Level world, Supplier<Item> supplier) {
        super(entityType, world, supplier);
    }

    public static void updateThrustValues(float thrustFactor)
    {
        thrustForce = thrustFactor / DRAG_COMPENSATOR;
        rudderFullEffectSpd = thrustFactor * 10;
    }

    public static void updateTurnRate(float newMaxTurn, float newMinTurn)
    {
        maxTurn = newMaxTurn;
        minTurn = newMinTurn;
    }

    @Override
    protected int getPoweredBoatMaxPax()
    {
        return MAX_PAX;
    }

    @Override
    protected float getPowerControlRate()
    {
        return SAIL_HOIST_RATE;
    }

    @Override
    protected float getPowerThrust()
    {
        return thrustForce;
    }

    @Override
    protected float getMinPowerLevel()
    {
        return 0f;
    }

    @Override
    protected void applyTurning()
    {
        float forwardSpeed = Math.max(0.0f, getForwardSpeed());

        float t = Mth.clamp(
                forwardSpeed / rudderFullEffectSpd,
                0f,
                1f
        );

        t = 1 - (1 - t) * (1 - t);

        float turnStrength = minTurn + t * (maxTurn - minTurn);

        if (leftInput) {
            this.deltaRotation -= turnStrength;
        }
        if (rightInput) {
            this.deltaRotation += turnStrength;
        }
    }

    @Environment(EnvType.CLIENT)
    public float getTillerYaw()
    {
        float targetYaw = 0f;

        if (leftInput) targetYaw =  0.4f;
        if (rightInput) targetYaw = -0.4f;

        // Easing factor (0 = instant, 1 = never moves)
        float ease = 0.15f;

        tillerYaw += (targetYaw - tillerYaw) * ease;

        return tillerYaw;
    }
}
