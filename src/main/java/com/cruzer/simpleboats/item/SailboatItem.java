package com.cruzer.simpleboats.item;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.AbstractBoatEntity;

public class SailboatItem extends AbstractPoweredBoatItem
{
    public SailboatItem(EntityType<? extends AbstractBoatEntity> type, Settings settings) {
        super(type, settings);
    }
}
