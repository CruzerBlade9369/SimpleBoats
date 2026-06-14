package com.cruzer.simpleboats.item;

import java.util.List;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;

public class MotorboatItem extends AbstractPoweredBoatItem
{
    public MotorboatItem(EntityType<? extends AbstractBoat> type, Properties settings) {
        super(type, settings);
    }
}