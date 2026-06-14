package com.cruzer.simpleboats.client.model;

import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;

public record ModelLayerEntry(
        ModelLayerLocation layer,
        ModelLayerRegistry.TexturedLayerDefinitionProvider provider
) {}
