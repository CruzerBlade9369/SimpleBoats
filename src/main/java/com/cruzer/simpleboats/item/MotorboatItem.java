package com.cruzer.simpleboats.item;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.AbstractBoatEntity;

import java.util.List;

public class MotorboatItem extends AbstractPoweredBoatItem
{
    public MotorboatItem(EntityType<? extends AbstractBoatEntity> type, Settings settings) {
        super(type, settings);
    }
}