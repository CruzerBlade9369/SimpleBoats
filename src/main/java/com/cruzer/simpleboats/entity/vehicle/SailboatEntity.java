package com.cruzer.simpleboats.entity.vehicle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class SailboatEntity extends AbstractPoweredBoatEntity
{
    private static final float SAIL_HOIST_RATE = 0.04f;
    private static final float SAIL_FORCE = 0.045f / DRAG_COMPENSATOR;
    private static final float RUDDER_FULL_EFFECT_SPD = 0.5f;
    private static final float MAX_TURN = 0.3f;
    private static final float MIN_TURN = 0.05f;
    private static final int MAX_PAX = 5;

    @Environment(EnvType.CLIENT)
    private float tillerYaw = 0f;

    public SailboatEntity(EntityType<? extends SailboatEntity> entityType, World world, Supplier<Item> supplier) {
        super(entityType, world, supplier);
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
        return SAIL_FORCE;
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

        float t = MathHelper.clamp(
                forwardSpeed / RUDDER_FULL_EFFECT_SPD,
                0f,
                1f
        );

        t = 1 - (1 - t) * (1 - t);

        float turnStrength = MIN_TURN + t * (MAX_TURN - MIN_TURN);

        if (leftInput) {
            this.yawVelocity -= turnStrength;
        }
        if (rightInput) {
            this.yawVelocity += turnStrength;
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
