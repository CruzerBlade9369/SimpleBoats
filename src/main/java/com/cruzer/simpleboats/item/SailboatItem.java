package com.cruzer.simpleboats.item;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;

public class SailboatItem extends AbstractPoweredBoatItem
{
    public SailboatItem(EntityType<? extends AbstractBoat> type, Properties settings) {
        super(type, settings);
    }
}
