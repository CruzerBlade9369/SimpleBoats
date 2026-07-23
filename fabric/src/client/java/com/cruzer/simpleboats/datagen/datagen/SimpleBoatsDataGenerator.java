package com.cruzer.simpleboats.datagen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

@Environment(EnvType.CLIENT)
public class SimpleBoatsDataGenerator implements DataGeneratorEntrypoint
{
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator gen)
    {
        FabricDataGenerator.Pack pack = gen.createPack();
        pack.addProvider(SimpleBoatsRecipeGenerator::new);
        pack.addProvider(SimpleBoatsItemsDataGenerator::new);
    }
}
