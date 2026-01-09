package com.cruzer.simpleboats.datagen;

import com.cruzer.simpleboats.registry.SimpleBoatsItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.Models;
import net.minecraft.item.Item;

public class SimpleBoatsItemsDataGenerator extends FabricModelProvider
{
    public SimpleBoatsItemsDataGenerator(FabricDataOutput output)
    {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator)
    {

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator)
    {
        itemModelGenerator.register(SimpleBoatsItems.OUTBOARD_MOTOR, Models.GENERATED);
        itemModelGenerator.register(SimpleBoatsItems.BOAT_SAIL, Models.GENERATED);
        itemModelGenerator.register(SimpleBoatsItems.BOAT_PROPELLER, Models.GENERATED);

        for (Item boat : SimpleBoatsItems.MOTORBOAT_ITEMS.values()) {
            itemModelGenerator.register(boat, Models.GENERATED);
        }

        for (Item boat : SimpleBoatsItems.SAILBOAT_ITEMS.values()) {
            itemModelGenerator.register(boat, Models.GENERATED);
        }
    }
}
