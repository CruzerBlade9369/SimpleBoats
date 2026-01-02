package com.cruzer.simpleboats.entity.vehicle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.function.Supplier;

public abstract class AbstractPoweredBoatEntity extends BoatEntity
{
    protected static final TrackedData<Boolean> UP_INPUT =
            DataTracker.registerData(AbstractPoweredBoatEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    protected static final TrackedData<Boolean> DOWN_INPUT =
            DataTracker.registerData(AbstractPoweredBoatEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    protected static final TrackedData<Float> POWER_THRUST_LEVEL =
            DataTracker.registerData(AbstractPoweredBoatEntity.class, TrackedDataHandlerRegistry.FLOAT);

    private boolean leftInput;
    private boolean rightInput;

    public AbstractPoweredBoatEntity(EntityType<? extends BoatEntity> entityType, World world, Supplier<Item> supplier)
    {
        super(entityType, world, supplier);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder)
    {
        super.initDataTracker(builder);
        builder.add(UP_INPUT, false);
        builder.add(DOWN_INPUT, false);
        builder.add(POWER_THRUST_LEVEL, 0.0f);
    }

    public float getThrottle()
    {
        return this.dataTracker.get(POWER_THRUST_LEVEL);
    }

    protected void setThrottle(float throttle)
    {
        this.dataTracker.set(POWER_THRUST_LEVEL, throttle);
    }

    public void applyThrottleIntent(PlayerEntity player, boolean up, boolean down) {
        if (this.getControllingPassenger() != player) return;

        this.dataTracker.set(UP_INPUT, up);
        this.dataTracker.set(DOWN_INPUT, down);
    }

    protected void applyThrottle()
    {
        float throttle = this.dataTracker.get(POWER_THRUST_LEVEL);

        if (this.dataTracker.get(UP_INPUT)) {
            throttle += getPowerControlRate();
        }
        if (this.dataTracker.get(DOWN_INPUT)) {
            throttle -= getPowerControlRate();
        }

        throttle = MathHelper.clamp(throttle, 0.0f, 1.0f);
        setThrottle(throttle);
    }

    protected void applyThrottleMovement(float throttle)
    {
        float thrust = throttle * getPowerThrust();

        float yawRad = this.getYaw() * ((float)Math.PI / 180F);

        Vec3d boost = new Vec3d(
                -MathHelper.sin(yawRad) * thrust,
                0.0,
                MathHelper.cos(yawRad) * thrust
        );

        this.setVelocity(this.getVelocity().add(boost));
    }

    protected void applyTurning(float throttle)
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
    public void tick()
    {
        boolean canUpdateMovement = this.isLogicalSideForUpdatingMovement();
        boolean isServer = !this.getEntityWorld().isClient();
        boolean hasDriver = this.getControllingPassenger() instanceof PlayerEntity;

        if (isServer) {
            if (hasDriver) applyThrottle();
            else resetThrottle();
        }

        if (canUpdateMovement)
        {
            float throttle = getThrottle();
            applyTurning(throttle);
            applyThrottleMovement(throttle);

            if (!isServer)
            {
                clientVisualTick(throttle);
            }
        }

        super.tick();
    }

    protected void resetThrottle()
    {
        setThrottle(0.0f);
    }

    @Environment(EnvType.CLIENT)
    protected void clientVisualTick(float throttle)
    {
        // Client-side visual effects can be implemented in subclasses
    }

    protected abstract float getPowerControlRate();
    protected abstract float getPowerThrust();
}
