package com.cruzer.simpleboats.entity.vehicle;

import com.cruzer.simpleboats.registry.SimpleBoatsSounds;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Supplier;

public class MotorboatEntity extends AbstractPoweredBoatEntity
{
    @Environment(EnvType.CLIENT)
    private float propellerAngle = 0f;
    @Environment(EnvType.CLIENT)
    private float lastPropellerAngle = 0f;
    @Environment(EnvType.CLIENT)
    private float propDirYaw = 0f;

    private static final float THROTTLE_RATE = 0.03f;
    private static final float ENGINE_THRUST = 0.05f / DRAG_COMPENSATOR;
    private static final float PROP_MAX_SPIN_SPEED = 0.8f;
    private static final float MAX_TURN = 0.3f;
    private static final int MAX_PAX = 5;

    public MotorboatEntity(EntityType<? extends MotorboatEntity> entityType, World world, Supplier<Item> supplier) {
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
        return THROTTLE_RATE;
    }

    @Override
    protected float getPowerThrust()
    {
        return ENGINE_THRUST;
    }

    @Override
    protected float getMinPowerLevel()
    {
        return 0f;
    }

    @Override
    protected void applyTurning()
    {
        float throttle = getPowerLevel();
        float turnStrength = (1 - (1 - throttle) * (1 - throttle)) * MAX_TURN;

        if (leftInput)
            this.yawVelocity -= turnStrength;
        if (rightInput)
            this.yawVelocity += turnStrength;
    }

    @Environment(EnvType.CLIENT)
    @Override
    protected void clientVisualTick(float powerLevel) {
        calculatePropellerRotation(powerLevel);
        if (this.isTouchingWater() && powerLevel > 0.05f)
            spawnPropellerBubbles();
    }

    @Override
    protected void addPassenger(Entity passenger)
    {
        boolean hasDriver = this.getControllingPassenger() instanceof PlayerEntity;
        if (!hasDriver
                && passenger instanceof PlayerEntity
                && !this.isSilent()
                && !this.getEntityWorld().isClient() && this.age > 0)
        {
            this.getEntityWorld().playSound(
                    null,
                    getX(), getY(), getZ(),
                    SimpleBoatsSounds.BOAT_MOTOR_START,
                    getSoundCategory(),
                    0.8f,
                    1.0f
            );
        }

        super.addPassenger(passenger);
    }

    @Environment(EnvType.CLIENT)
    public float[] getPropellerAngle()
    {
        return new float[]{propellerAngle, lastPropellerAngle};
    }

    @Environment(EnvType.CLIENT)
    private void calculatePropellerRotation(float throttle)
    {
        lastPropellerAngle = propellerAngle;
        propellerAngle += (float)(1 - Math.pow(1 - throttle * PROP_MAX_SPIN_SPEED, 4));
        propellerAngle %= (float)(2 * Math.PI);
    }

    @Environment(EnvType.CLIENT)
    private void spawnPropellerBubbles() {
        World world = this.getEntityWorld();
        Vec3d pos = getPropellerWorldPos();
        float throttle = getPowerLevel();
        int count = MathHelper.clamp((int) (throttle * 4), 1, 4);

        Vec3d vel = this.getVelocity();
        double speedSq = this.getVelocity().horizontalLengthSquared();

        double maxSpeed = 0.6;
        double maxSpeedSq = maxSpeed * maxSpeed;

        double speedFactor = 1.0 - MathHelper.clamp(
                speedSq / maxSpeedSq,
                0.0,
                1.0
        );

        float yawRad = getYawRad();
        Vec3d propWashDir = new Vec3d(
                -MathHelper.sin(yawRad),
                0.0,
                MathHelper.cos(yawRad)
        ).normalize();

        // fine tune here for visual exaggeration
        double washStrength = 1;

        for (int i = 0; i < count; i++)
        {
            Vec3d particleV = vel.add(propWashDir.multiply(-washStrength * throttle * speedFactor));

            world.addParticleClient(
                    ParticleTypes.BUBBLE,
                    pos.x + (this.random.nextDouble() - 0.5) * 0.2,
                    pos.y,
                    pos.z + (this.random.nextDouble() - 0.5) * 0.2,
                    particleV.x, particleV.y, particleV.z
            );
        }
    }

    @Environment(EnvType.CLIENT)
    private Vec3d getPropellerWorldPos() {
        float axisBackOffset = -2.9f;
        float spawnBackOffset = -3.45f;
        float verticalOffset = -1.25f;

        float boatYawRad  = this.getYawRad();
        float tillerYawRad = getPropDirYaw();

        double pivotX = this.getX() - MathHelper.sin(boatYawRad) * axisBackOffset;
        double pivotZ = this.getZ() + MathHelper.cos(boatYawRad) * axisBackOffset;
        double pivotY = this.getY() + verticalOffset;

        float localBack = spawnBackOffset - axisBackOffset;

        double localX = -MathHelper.sin(tillerYawRad) * localBack;
        double localZ =  MathHelper.cos(tillerYawRad) * localBack;

        double worldX = localX * MathHelper.cos(boatYawRad) - localZ * MathHelper.sin(boatYawRad);
        double worldZ = localX * MathHelper.sin(boatYawRad) + localZ * MathHelper.cos(boatYawRad);

        return new Vec3d(pivotX + worldX, pivotY, pivotZ + worldZ);
    }

    @Environment(EnvType.CLIENT)
    public float getPropDirYaw()
    {
        float targetYaw = 0f;

        if (leftInput) targetYaw =  0.4f;
        if (rightInput) targetYaw = -0.4f;

        // Easing factor (0 = instant, 1 = never moves)
        float ease = 0.15f;

        propDirYaw += (targetYaw - propDirYaw) * ease;

        return propDirYaw;
    }
}
