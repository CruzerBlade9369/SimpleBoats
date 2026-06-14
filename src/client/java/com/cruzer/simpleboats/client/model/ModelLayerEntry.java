package com.cruzer.simpleboats.client.model;

import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;

public record ModelLayerEntry(
        ModelLayerLocation layer,
        EntityModelLayerRegistry.TexturedModelDataProvider provider
) {}
