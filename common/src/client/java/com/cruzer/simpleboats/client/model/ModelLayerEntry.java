package com.cruzer.simpleboats.client.model;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import java.util.function.Supplier;

public record ModelLayerEntry(
        ModelLayerLocation layer,
        Supplier<LayerDefinition> provider
) {}
