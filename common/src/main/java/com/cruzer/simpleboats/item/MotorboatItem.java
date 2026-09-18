package com.cruzer.simpleboats.item;

import java.util.function.Supplier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;

public class MotorboatItem extends AbstractPoweredBoatItem
{
    public MotorboatItem(Supplier<? extends EntityType<? extends AbstractBoat>> type, Properties settings) {
        super(type, settings);
    }
}