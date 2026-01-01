package com.cruzer.simpleboats.entity.vehicle;

import com.cruzer.simpleboats.registry.SimpleBoatsSounds;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class MotorboatEntity extends BoatEntity
{
    private static final TrackedData<Boolean> UP_INPUT =
            DataTracker.registerData(MotorboatEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> DOWN_INPUT =
            DataTracker.registerData(MotorboatEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    private static final TrackedData<Float> THROTTLE =
            DataTracker.registerData(MotorboatEntity.class, TrackedDataHandlerRegistry.FLOAT);

    @Environment(EnvType.CLIENT)
    private float propellerAngle = 0f;
    @Environment(EnvType.CLIENT)
    private float lastPropellerAngle = 0f;

    private boolean leftInput;
    private boolean rightInput;

    private static final float THROTTLE_RATE = 0.05f;
    private static final float ENGINE_THRUST = 0.065f;
    private static final float PROP_MAX_SPIN_SPEED = 0.8f;

    public MotorboatEntity(EntityType<? extends MotorboatEntity> entityType, World world, Supplier<Item> supplier) {
        super(entityType, world, supplier);
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
        this.dataTracker.set(THROTTLE, 0.0f);
    }

    public float getThrottle()
    {
        return this.dataTracker.get(THROTTLE);
    }

    public void setThrottle(float throttle)
    {
        this.dataTracker.set(THROTTLE, throttle);
    }

    public void applyThrottleIntent(PlayerEntity player, boolean up, boolean down) {
        if (this.getControllingPassenger() != player) return;

        this.dataTracker.set(UP_INPUT, up);
        this.dataTracker.set(DOWN_INPUT, down);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder)
    {
        super.initDataTracker(builder);
        builder.add(UP_INPUT, false);
        builder.add(DOWN_INPUT, false);
        builder.add(THROTTLE, 0.0f);
    }

    @Override
    public void tick()
    {
        boolean canUpdateMovement = this.isLogicalSideForUpdatingMovement();
        boolean isServer = !this.getEntityWorld().isClient();
        boolean hasDriver = this.getControllingPassenger() instanceof PlayerEntity;

        if (isServer) {
            if (hasDriver) applyThrottle();
            else resetInputs();
        }

        if (canUpdateMovement)
        {
            float throttle = getThrottle();
            applyTurning(throttle);
            applyThrottleMovement(throttle);

            if (!isServer)
            {
                calculatePropellerRotation(throttle);
                if (this.isTouchingWater() && getThrottle() > 0.05f)
                    spawnPropellerBubbles();
            }
        }

        super.tick();
    }

    private void applyThrottle()
    {
        float throttle = this.dataTracker.get(THROTTLE);

        if (this.dataTracker.get(UP_INPUT)) {
            throttle += THROTTLE_RATE;
        }
        if (this.dataTracker.get(DOWN_INPUT)) {
            throttle -= THROTTLE_RATE;
        }

        throttle = MathHelper.clamp(throttle, 0.0f, 1.0f);
        setThrottle(throttle);
    }

    private void applyThrottleMovement(float throttle)
    {
        float thrust = throttle * ENGINE_THRUST;

        float yawRad = this.getYaw() * ((float)Math.PI / 180F);

        Vec3d boost = new Vec3d(
                -MathHelper.sin(yawRad) * thrust,
                0.0,
                MathHelper.cos(yawRad) * thrust
        );

        this.setVelocity(this.getVelocity().add(boost));
    }

    private void applyTurning(float throttle)
    {
        float turnStrength = (float) (1 - Math.pow(1 - throttle, 2));

        if (leftInput)
            this.yawVelocity -= turnStrength;
        if (rightInput)
            this.yawVelocity += turnStrength;
    }

    @Override
    public void setInputs(boolean left, boolean right, boolean forward, boolean back)
    {
        this.leftInput = left;
        this.rightInput = right;
    }

    @Override
    protected void addPassenger(Entity passenger)
    {
        boolean wasEmpty = !this.hasPassengers();
        super.addPassenger(passenger);
        if (wasEmpty && passenger instanceof PlayerEntity && !this.isSilent() && !this.getEntityWorld().isClient() && this.age > 0) {
            this.getEntityWorld().playSound(
                    null,
                    this.getX(),
                    this.getY(),
                    this.getZ(),
                    SimpleBoatsSounds.BOAT_MOTOR_START,
                    this.getSoundCategory(),
                    0.8f,
                    1.0f
            );
        }
    }
}
