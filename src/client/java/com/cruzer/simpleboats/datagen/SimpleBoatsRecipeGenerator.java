package com.cruzer.simpleboats.datagen;

import com.cruzer.simpleboats.registry.SimpleBoatsTypes;
import com.cruzer.simpleboats.registry.SimpleBoatsItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class SimpleBoatsRecipeGenerator extends FabricRecipeProvider
{
    SimpleBoatsRecipeGenerator(FabricDataOutput gen, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture)
    {
        super(gen, registriesFuture);
    }

    @Override
    public String getName()
    {
        return "SimpleBoats Recipe Provider";
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup registryLookup, RecipeExporter exporter)
    {
        return new RecipeGenerator(registryLookup, exporter) {
            @Override
            public void generate()
            {
                for (Map.Entry<SimpleBoatsTypes, Item> entry : SimpleBoatsItems.MOTORBOAT_ITEMS.entrySet())
                {
                    SimpleBoatsTypes type = entry.getKey();
                    Item boat = entry.getValue();

                    createShaped(RecipeCategory.TRANSPORTATION, boat)
                            .input('#', Items.BAMBOO)
                            .input('S', type.getStrippedLog())
                            .input('E', SimpleBoatsItems.OUTBOARD_MOTOR)
                            .pattern("#S#")
                            .pattern("#S#")
                            .pattern("#E#")
                            .criterion(hasItem(SimpleBoatsItems.OUTBOARD_MOTOR), conditionsFromItem(SimpleBoatsItems.OUTBOARD_MOTOR))
                            .offerTo(
                                    this.exporter
                            );
                }

                for (Map.Entry<SimpleBoatsTypes, Item> entry : SimpleBoatsItems.SAILBOAT_ITEMS.entrySet())
                {
                    SimpleBoatsTypes type = entry.getKey();
                    Item boat = entry.getValue();

                    createShaped(RecipeCategory.TRANSPORTATION, boat)
                            .input('#', Items.BAMBOO)
                            .input('S', type.getStrippedLog())
                            .input('E', SimpleBoatsItems.BOAT_SAIL)
                            .pattern("#S#")
                            .pattern("#E#")
                            .pattern("#S#")
                            .criterion(hasItem(SimpleBoatsItems.BOAT_SAIL), conditionsFromItem(SimpleBoatsItems.BOAT_SAIL))
                            .offerTo(
                                    this.exporter
                            );
                }

                createShaped(RecipeCategory.MISC, SimpleBoatsItems.OUTBOARD_MOTOR)
                        .input('I', Items.IRON_INGOT)
                        .input('P', Items.PISTON)
                        .input('X', SimpleBoatsItems.BOAT_PROPELLER)
                        .input('R', Items.REDSTONE)
                        .pattern("IRI")
                        .pattern("IPI")
                        .pattern("IXI")
                        .criterion(hasItem(SimpleBoatsItems.BOAT_PROPELLER), conditionsFromItem(SimpleBoatsItems.BOAT_PROPELLER))
                        .offerTo(this.exporter);

                createShaped(RecipeCategory.MISC, SimpleBoatsItems.BOAT_PROPELLER)
                        .input('I', Items.IRON_INGOT)
                        .pattern(" I ")
                        .pattern("III")
                        .pattern(" I ")
                        .criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
                        .offerTo(this.exporter);

                createShaped(RecipeCategory.MISC, SimpleBoatsItems.BOAT_SAIL)
                        .input('W', ItemTags.WOOL)
                        .input('P', ItemTags.LOGS)
                        .input('S', Items.STICK)
                        .pattern("WSW")
                        .pattern("WPW")
                        .pattern(" S ")
                        .criterion("has_wool", conditionsFromTag(ItemTags.WOOL))
                        .offerTo(this.exporter);
            }
        };
    }
}
