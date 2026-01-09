package com.cruzer.simpleboats.entity.vehicle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.AbstractBoatEntity;
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

    protected boolean leftInput;
    protected boolean rightInput;

    protected static final float DRAG_COMPENSATOR = 0.9f;

    public AbstractPoweredBoatEntity(EntityType<? extends BoatEntity> entityType, World world, Supplier<Item> supplier)
    {
        super(entityType, world, supplier);
    }

    public float getPowerLevel()
    {
        return this.dataTracker.get(POWER_THRUST_LEVEL);
    }

    public float getYawVelocity()
    {
        return this.yawVelocity;
    }

    public Location getLocation()
    {
        return this.checkLocation();
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder)
    {
        super.initDataTracker(builder);
        builder.add(UP_INPUT, false);
        builder.add(DOWN_INPUT, false);
        builder.add(POWER_THRUST_LEVEL, 0.0f);
    }

    @Override
    protected int getMaxPassengers()
    {
        return getPoweredBoatMaxPax();
    }

    @Override
    protected Vec3d getPassengerAttachmentPos(
            Entity pax,
            EntityDimensions dimensions,
            float scaleFactor
    )
    {
        int index = this.getPassengerList().indexOf(pax);
        double y = this.getPassengerAttachmentY(dimensions);

        Vec3d offset = getPaxOffsets(index, y + 0.15);

        return offset.rotateY(-this.getYawRad());
    }

    @Override
    protected double getPassengerAttachmentY(EntityDimensions dimensions)
    {
        return 0.4 + dimensions.height() * 0.25;
    }

    @Override
    protected void updatePassengerPosition(Entity pax, PositionUpdater pos)
    {
        super.updatePassengerPosition(pax, pos);
        this.clampPassengerYaw(pax);
    }

    protected Vec3d getPaxOffsets(int index, double y)
    {
        return switch (index)
        {
            case 3 -> new Vec3d(-0.5, y,  0.6); // front-left
            case 4 -> new Vec3d( 0.5, y,  0.6); // front-right
            case 1 -> new Vec3d(-0.5, y, -0.8); // back-left
            case 2 -> new Vec3d( 0.5, y, -0.8); // back-right
            case 0 -> new Vec3d(0, y + 0.35, -2); // back-center, driver
            default -> Vec3d.ZERO;
        };
    }

    protected float getYawRad()
    {
        return this.getYaw() * ((float)Math.PI / 180F);
    }

    public float getForwardSpeed()
    {
        Vec3d vel = this.getVelocity();

        float yawRad = getYawRad();

        double forwardX = -MathHelper.sin(yawRad);
        double forwardZ =  MathHelper.cos(yawRad);

        return MathHelper.abs((float)(vel.x * forwardX + vel.z * forwardZ));
    }

    protected void setPowerLevel(float throttle)
    {
        this.dataTracker.set(POWER_THRUST_LEVEL, throttle);
    }

    public void applyThrottleIntent(PlayerEntity player, boolean up, boolean down) {
        if (this.getControllingPassenger() != player) return;

        this.dataTracker.set(UP_INPUT, up);
        this.dataTracker.set(DOWN_INPUT, down);
    }

    protected void applyPower()
    {
        float powerLevel = this.dataTracker.get(POWER_THRUST_LEVEL);

        if (this.dataTracker.get(UP_INPUT)) {
            powerLevel += getPowerControlRate();
        }
        if (this.dataTracker.get(DOWN_INPUT)) {
            powerLevel -= getPowerControlRate();
        }

        powerLevel = MathHelper.clamp(powerLevel, getMinPowerLevel(), 1.0f);
        setPowerLevel(powerLevel);
    }

    protected void applyPoweredMovement(float powerLevel)
    {
        float thrust = powerLevel * getPowerThrust();

        float yawRad = getYawRad();

        Vec3d boost = new Vec3d(
                -MathHelper.sin(yawRad) * thrust,
                0.0,
                MathHelper.cos(yawRad) * thrust
        );

        this.setVelocity(this.getVelocity().add(boost));
    }

    protected abstract void applyTurning();

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
            if (hasDriver) applyPower();
            else resetPower();
        }

        if (canUpdateMovement)
        {
            float powerLevel = getPowerLevel();
            applyTurning();
            applyPoweredMovement(powerLevel);

            System.out.println(getForwardSpeed());

            if (!isServer)
            {
                clientVisualTick(powerLevel);
            }
        }

        super.tick();
    }

    protected void resetPower()
    {
        setPowerLevel(0.0f);
    }

    @Environment(EnvType.CLIENT)
    protected void clientVisualTick(float powerLevel)
    {
        // Client-side visual effects can be implemented in subclasses
    }

    protected abstract float getPowerControlRate();
    protected abstract float getPowerThrust();
    protected abstract float getMinPowerLevel();
    protected abstract int getPoweredBoatMaxPax();
}
