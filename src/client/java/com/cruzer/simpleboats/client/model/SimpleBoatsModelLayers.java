package com.cruzer.simpleboats.client.model;

import com.cruzer.simpleboats.SimpleBoats;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

import java.util.List;

public class SimpleBoatsModelLayers
{
    public static final EntityModelLayer BOAT_HULL =
            new EntityModelLayer(Identifier.of(SimpleBoats.MOD_ID, "generic_boat"), "hull");

    public static final EntityModelLayer MOTORBOAT_MOTOR =
            new EntityModelLayer(Identifier.of(SimpleBoats.MOD_ID, "motorboat"), "motor");

    public static final EntityModelLayer SAILBOAT_TILLER =
            new EntityModelLayer(Identifier.of(SimpleBoats.MOD_ID, "sailboat"), "tiller");

    public static final EntityModelLayer BOAT_WATER_MASK =
            new EntityModelLayer(Identifier.of(SimpleBoats.MOD_ID, "generic_boat"), "water");

    public static final EntityModelLayer SAILBOAT_SAIL_BASE =
            new EntityModelLayer(Identifier.of(SimpleBoats.MOD_ID, "sailboat"), "sail");

    public static final List<ModelLayerEntry> ALL = List.of(
            new ModelLayerEntry(BOAT_HULL, GenericBoatModel::getTexturedModelData),
            new ModelLayerEntry(MOTORBOAT_MOTOR, MotorModel::getMotorModelData),
            new ModelLayerEntry(SAILBOAT_TILLER, TillerModel::getTillerModelData),
            new ModelLayerEntry(BOAT_WATER_MASK, GenericBoatModel::getWaterPatchModelData),
            new ModelLayerEntry(SAILBOAT_SAIL_BASE, SailModel::getSailModelData)
    );
}

