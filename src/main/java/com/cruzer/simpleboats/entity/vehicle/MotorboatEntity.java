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

import java.util.function.Supplier;

public class MotorboatEntity extends AbstractPoweredBoatEntity
{
    @Environment(EnvType.CLIENT)
    private float propellerAngle = 0f;
    @Environment(EnvType.CLIENT)
    private float lastPropellerAngle = 0f;

    private static final float THROTTLE_RATE = 0.05f;
    private static final float ENGINE_THRUST = 0.065f;
    private static final float PROP_MAX_SPIN_SPEED = 0.8f;

    public MotorboatEntity(EntityType<? extends BoatEntity> entityType, World world, Supplier<Item> supplier) {
        super(entityType, world, supplier);
    }

    @Override
    protected float getPowerControlRate()
    {
        return THROTTLE_RATE;
    }

    @Override
    protected float getPowerThrust() {
        return ENGINE_THRUST;
    }

    @Environment(EnvType.CLIENT)
    @Override
    protected void clientVisualTick(float throttle) {
        calculatePropellerRotation(throttle);
        if (this.isTouchingWater() && throttle > 0.05f)
            spawnPropellerBubbles();
    }

    @Override
    protected void addPassenger(Entity passenger) {
        boolean wasEmpty = !this.hasPassengers();
        super.addPassenger(passenger);
        if (wasEmpty && passenger instanceof PlayerEntity && !this.isSilent()
                && !this.getEntityWorld().isClient() && this.age > 0) {

            this.getEntityWorld().playSound(
                    null,
                    getX(), getY(), getZ(),
                    SimpleBoatsSounds.BOAT_MOTOR_START,
                    getSoundCategory(),
                    0.8f,
                    1.0f
            );
        }
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
        float throttle = getThrottle();
        int count = MathHelper.clamp((int) (throttle * 4), 1, 4);

        Vec3d vel = this.getVelocity();
        double speedSq = this.getVelocity().horizontalLengthSquared();

        double maxSpeed = 0.6;                 // same tuning value as before
        double maxSpeedSq = maxSpeed * maxSpeed;

        double speedFactor = 1.0 - MathHelper.clamp(
                speedSq / maxSpeedSq,
                0.0,
                1.0
        );

        float yawRad = this.getYaw() * ((float)Math.PI / 180F);
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
        float backOffset = -1.2f;

        float yawRad = this.getYaw() * ((float)Math.PI / 180F);

        double x = this.getX() - MathHelper.sin(yawRad) * backOffset;
        double z = this.getZ() + MathHelper.cos(yawRad) * backOffset;
        double y = this.getY();

        return new Vec3d(x, y, z);
    }

    private void resetInputs()
    {
        this.dataTracker.set(POWER_THRUST_LEVEL, 0.0f);
    }
}
