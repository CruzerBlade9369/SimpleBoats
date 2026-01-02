package com.cruzer.simpleboats.entity.vehicle;

import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class SailboatEntity extends AbstractPoweredBoatEntity
{
    private static final float SAIL_HOIST_RATE = 0.05f;
    private static final float SAIL_FORCE = 0.04f;

    public SailboatEntity(EntityType<? extends SailboatEntity> entityType, World world, Supplier<Item> supplier) {
        super(entityType, world, supplier);
    }

    @Override
    protected float getPowerControlRate()
    {
        return SAIL_HOIST_RATE;
    }

    @Override
    protected float getPowerThrust() {
        return SAIL_FORCE;
    }
}
