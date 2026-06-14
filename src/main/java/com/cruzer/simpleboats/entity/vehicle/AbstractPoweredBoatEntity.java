package com.cruzer.simpleboats.entity.vehicle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.boat.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import java.util.function.Supplier;

public abstract class AbstractPoweredBoatEntity extends Boat
{
    protected static final EntityDataAccessor<Boolean> UP_INPUT =
            SynchedEntityData.defineId(AbstractPoweredBoatEntity.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Boolean> DOWN_INPUT =
            SynchedEntityData.defineId(AbstractPoweredBoatEntity.class, EntityDataSerializers.BOOLEAN);

    protected static final EntityDataAccessor<Float> POWER_THRUST_LEVEL =
            SynchedEntityData.defineId(AbstractPoweredBoatEntity.class, EntityDataSerializers.FLOAT);

    protected boolean leftInput;
    protected boolean rightInput;

    protected static final float DRAG_COMPENSATOR = 0.9f;

    public AbstractPoweredBoatEntity(EntityType<? extends Boat> entityType, Level world, Supplier<Item> supplier)
    {
        super(entityType, world, supplier);
    }

    public float getPowerLevel()
    {
        return this.entityData.get(POWER_THRUST_LEVEL);
    }
    public float getYawVelocity()
    {
        return this.deltaRotation;
    }
    public Status getBoatStatus()
    {
        return this.getStatus();
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder)
    {
        super.defineSynchedData(builder);
        builder.define(UP_INPUT, false);
        builder.define(DOWN_INPUT, false);
        builder.define(POWER_THRUST_LEVEL, 0.0f);
    }

    @Override
    protected int getMaxPassengers()
    {
        return getPoweredBoatMaxPax();
    }

    @Override
    protected Vec3 getPassengerAttachmentPoint(
            Entity pax,
            EntityDimensions dimensions,
            float scaleFactor
    )
    {
        int index = this.getPassengers().indexOf(pax);
        double y = this.rideHeight(dimensions);

        Vec3 offset = getPaxOffsets(index, y + 0.15);

        return offset.yRot(-this.getYawRad());
    }

    @Override
    protected double rideHeight(EntityDimensions dimensions)
    {
        return 0.4 + dimensions.height() * 0.25;
    }

    @Override
    protected void positionRider(Entity pax, MoveFunction pos)
    {
        super.positionRider(pax, pos);
        this.clampRotation(pax);
    }

    protected Vec3 getPaxOffsets(int index, double y)
    {
        return switch (index)
        {
            case 3 -> new Vec3(-0.5, y,  0.6); // front-left
            case 4 -> new Vec3( 0.5, y,  0.6); // front-right
            case 1 -> new Vec3(-0.5, y, -0.8); // back-left
            case 2 -> new Vec3( 0.5, y, -0.8); // back-right
            case 0 -> new Vec3(0, y + 0.35, -2); // back-center, driver
            default -> Vec3.ZERO;
        };
    }

    protected float getYawRad()
    {
        return this.getYRot() * ((float)Math.PI / 180F);
    }

    public float getForwardSpeed()
    {
        Vec3 vel = this.getDeltaMovement();

        float yawRad = getYawRad();

        double forwardX = -Mth.sin(yawRad);
        double forwardZ =  Mth.cos(yawRad);

        return Mth.abs((float)(vel.x * forwardX + vel.z * forwardZ));
    }

    protected void setPowerLevel(float throttle)
    {
        this.entityData.set(POWER_THRUST_LEVEL, throttle);
    }

    public void applyThrottleIntent(Player player, boolean up, boolean down) {
        if (this.getControllingPassenger() != player) return;

        this.entityData.set(UP_INPUT, up);
        this.entityData.set(DOWN_INPUT, down);
    }

    protected void applyPower()
    {
        float powerLevel = this.entityData.get(POWER_THRUST_LEVEL);

        if (this.entityData.get(UP_INPUT)) {
            powerLevel += getPowerControlRate();
        }
        if (this.entityData.get(DOWN_INPUT)) {
            powerLevel -= getPowerControlRate();
        }

        powerLevel = Mth.clamp(powerLevel, getMinPowerLevel(), 1.0f);
        setPowerLevel(powerLevel);
    }

    protected void applyPoweredMovement(float powerLevel)
    {
        float thrust = powerLevel * getPowerThrust();

        float yawRad = getYawRad();

        Vec3 boost = new Vec3(
                -Mth.sin(yawRad) * thrust,
                0.0,
                Mth.cos(yawRad) * thrust
        );

        this.setDeltaMovement(this.getDeltaMovement().add(boost));
    }

    protected abstract void applyTurning();

    @Override
    public void setInput(boolean left, boolean right, boolean forward, boolean back)
    {
        this.leftInput = left;
        this.rightInput = right;
    }

    @Override
    public void tick()
    {
        boolean canUpdateMovement = this.isLocalInstanceAuthoritative();
        boolean isServer = !this.level().isClientSide();
        boolean hasDriver = this.getControllingPassenger() instanceof Player;

        if(hasDriver)
        {
            if (isServer){
                applyPower();
            }
        }
        else
        {
            resetPower();
        }

        if (canUpdateMovement)
        {
            float powerLevel = getPowerLevel();
            applyTurning();
            applyPoweredMovement(powerLevel);

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
