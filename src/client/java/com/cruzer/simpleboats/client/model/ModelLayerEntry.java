package com.cruzer.simpleboats.client.model;

import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;

public record ModelLayerEntry(
        EntityModelLayer layer,
        EntityModelLayerRegistry.TexturedModelDataProvider provider
) {}
