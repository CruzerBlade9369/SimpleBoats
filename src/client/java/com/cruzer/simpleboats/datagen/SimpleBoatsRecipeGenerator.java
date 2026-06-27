package com.cruzer.simpleboats.datagen;

import com.cruzer.simpleboats.registry.SimpleBoatsTypes;
import com.cruzer.simpleboats.registry.SimpleBoatsItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class SimpleBoatsRecipeGenerator extends FabricRecipeProvider
{
    SimpleBoatsRecipeGenerator(FabricPackOutput gen, CompletableFuture<HolderLookup.Provider> registriesFuture)
    {
        super(gen, registriesFuture);
    }

    @Override
    public String getName()
    {
        return "SimpleBoats Recipe Provider";
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registryLookup, RecipeOutput exporter)
    {
        return new RecipeProvider(registryLookup, exporter) {
            @Override
            public void buildRecipes()
            {
                for (Map.Entry<SimpleBoatsTypes, Item> entry : SimpleBoatsItems.MOTORBOAT_ITEMS.entrySet())
                {
                    SimpleBoatsTypes type = entry.getKey();
                    Item boat = entry.getValue();

                    shaped(RecipeCategory.TRANSPORTATION, boat)
                            .define('#', Items.BAMBOO)
                            .define('S', type.getStrippedLog())
                            .define('E', SimpleBoatsItems.OUTBOARD_MOTOR)
                            .pattern("#S#")
                            .pattern("#S#")
                            .pattern("#E#")
                            .group("simpleboats_motorboats")
                            .unlockedBy(getHasName(SimpleBoatsItems.OUTBOARD_MOTOR), has(SimpleBoatsItems.OUTBOARD_MOTOR))
                            .save(
                                    this.output
                            );
                }

                for (Map.Entry<SimpleBoatsTypes, Item> entry : SimpleBoatsItems.SAILBOAT_ITEMS.entrySet())
                {
                    SimpleBoatsTypes type = entry.getKey();
                    Item boat = entry.getValue();

                    shaped(RecipeCategory.TRANSPORTATION, boat)
                            .define('#', Items.BAMBOO)
                            .define('S', type.getStrippedLog())
                            .define('E', SimpleBoatsItems.BOAT_SAIL)
                            .pattern("#S#")
                            .pattern("#E#")
                            .pattern("#S#")
                            .group("simpleboats_sailboats")
                            .unlockedBy(getHasName(SimpleBoatsItems.BOAT_SAIL), has(SimpleBoatsItems.BOAT_SAIL))
                            .save(
                                    this.output
                            );
                }

                shaped(RecipeCategory.MISC, SimpleBoatsItems.OUTBOARD_MOTOR)
                        .define('I', Items.IRON_INGOT)
                        .define('P', Items.PISTON)
                        .define('X', SimpleBoatsItems.BOAT_PROPELLER)
                        .define('R', Items.REDSTONE)
                        .pattern("IRI")
                        .pattern("IPI")
                        .pattern("IXI")
                        .unlockedBy(getHasName(SimpleBoatsItems.BOAT_PROPELLER), has(SimpleBoatsItems.BOAT_PROPELLER))
                        .save(this.output);

                shaped(RecipeCategory.MISC, SimpleBoatsItems.BOAT_PROPELLER)
                        .define('I', Items.IRON_INGOT)
                        .pattern(" I ")
                        .pattern("III")
                        .pattern(" I ")
                        .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                        .save(this.output);

                shaped(RecipeCategory.MISC, SimpleBoatsItems.BOAT_SAIL)
                        .define('W', ItemTags.WOOL)
                        .define('P', ItemTags.LOGS)
                        .define('S', Items.STICK)
                        .pattern("WSW")
                        .pattern("WPW")
                        .pattern(" S ")
                        .unlockedBy("has_wool", has(ItemTags.WOOL))
                        .save(this.output);
            }
        };
    }
}
