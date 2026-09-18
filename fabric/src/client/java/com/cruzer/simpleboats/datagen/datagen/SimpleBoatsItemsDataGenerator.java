package com.cruzer.simpleboats.datagen;

import com.cruzer.simpleboats.registry.SimpleBoatsItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.world.item.Item;

public class SimpleBoatsItemsDataGenerator extends FabricModelProvider
{
    public SimpleBoatsItemsDataGenerator(FabricPackOutput output)
    {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator)
    {

    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator)
    {
        itemModelGenerator.generateFlatItem(SimpleBoatsItems.OUTBOARD_MOTOR.get(), ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(SimpleBoatsItems.BOAT_SAIL.get(), ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(SimpleBoatsItems.BOAT_PROPELLER.get(), ModelTemplates.FLAT_ITEM);

        for (java.util.function.Supplier<Item> boatSupplier : SimpleBoatsItems.MOTORBOAT_ITEMS.values()) {
            itemModelGenerator.generateFlatItem(boatSupplier.get(), ModelTemplates.FLAT_ITEM);
        }

        for (java.util.function.Supplier<Item> boatSupplier : SimpleBoatsItems.SAILBOAT_ITEMS.values()) {
            itemModelGenerator.generateFlatItem(boatSupplier.get(), ModelTemplates.FLAT_ITEM);
        }
    }
}
