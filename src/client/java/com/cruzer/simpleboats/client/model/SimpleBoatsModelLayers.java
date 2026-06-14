package com.cruzer.simpleboats.client.model;

import com.cruzer.simpleboats.SimpleBoats;
import java.util.List;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.Identifier;

public class SimpleBoatsModelLayers
{
    public static final ModelLayerLocation BOAT_HULL =
            new ModelLayerLocation(Identifier.fromNamespaceAndPath(SimpleBoats.MOD_ID, "generic_boat"), "hull");

    public static final ModelLayerLocation MOTORBOAT_MOTOR =
            new ModelLayerLocation(Identifier.fromNamespaceAndPath(SimpleBoats.MOD_ID, "motorboat"), "motor");

    public static final ModelLayerLocation SAILBOAT_TILLER =
            new ModelLayerLocation(Identifier.fromNamespaceAndPath(SimpleBoats.MOD_ID, "sailboat"), "tiller");

    public static final ModelLayerLocation BOAT_WATER_MASK =
            new ModelLayerLocation(Identifier.fromNamespaceAndPath(SimpleBoats.MOD_ID, "generic_boat"), "water");

    public static final ModelLayerLocation SAILBOAT_SAIL_BASE =
            new ModelLayerLocation(Identifier.fromNamespaceAndPath(SimpleBoats.MOD_ID, "sailboat"), "sail");

    public static final List<ModelLayerEntry> ALL = List.of(
            new ModelLayerEntry(BOAT_HULL, GenericBoatModel::getTexturedModelData),
            new ModelLayerEntry(MOTORBOAT_MOTOR, MotorModel::getMotorModelData),
            new ModelLayerEntry(SAILBOAT_TILLER, TillerModel::getTillerModelData),
            new ModelLayerEntry(BOAT_WATER_MASK, GenericBoatModel::getWaterPatchModelData),
            new ModelLayerEntry(SAILBOAT_SAIL_BASE, SailModel::getSailModelData)
    );
}

