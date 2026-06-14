package com.cruzer.simpleboats.registry;

import com.cruzer.simpleboats.SimpleBoats;
import com.cruzer.simpleboats.item.MotorboatItem;
import com.cruzer.simpleboats.item.SailboatItem;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class SimpleBoatsItems
{
    public static final Item BOAT_PROPELLER = register("boat_propeller");
    public static final Item OUTBOARD_MOTOR = register("outboard_motor");
    public static final Item BOAT_SAIL = register("boat_sail");

    public static final Map<SimpleBoatsTypes, Item> MOTORBOAT_ITEMS = new HashMap<>();
    public static final Map<SimpleBoatsTypes, Item> SAILBOAT_ITEMS = new HashMap<>();

    public static void initialize()
    {
        registerMotorboats();
        registerSailboats();

        addToItemGroup(BOAT_PROPELLER, CreativeModeTabs.INGREDIENTS);
        addToItemGroup(OUTBOARD_MOTOR, CreativeModeTabs.INGREDIENTS);
        addToItemGroup(BOAT_SAIL, CreativeModeTabs.INGREDIENTS);
    }

    private static void registerMotorboats()
    {
        for (SimpleBoatsTypes type : SimpleBoatsTypes.values())
        {
            Item item = register(
                    keyOf(type.getName() + "_motorboat"),
                    settings -> new MotorboatItem(
                            SimpleBoatsEntities.MOTORBOAT_TYPES.get(type),
                            settings
                    ),
                    new Item.Properties().stacksTo(1)
            );

            MOTORBOAT_ITEMS.put(type, item);
            addToItemGroup(item, CreativeModeTabs.TOOLS_AND_UTILITIES);
        }
    }

    private static void registerSailboats()
    {
        for (SimpleBoatsTypes type : SimpleBoatsTypes.values())
        {
            Item item = register(
                    keyOf(type.getName() + "_sailboat"),
                    settings -> new SailboatItem(
                            SimpleBoatsEntities.SAILBOAT_TYPES.get(type),
                            settings
                    ),
                    new Item.Properties().stacksTo(1)
            );

            SAILBOAT_ITEMS.put(type, item);
            addToItemGroup(item, CreativeModeTabs.TOOLS_AND_UTILITIES);
        }
    }

    private static void addToItemGroup(Item item, ResourceKey<CreativeModeTab> group)
    {
        CreativeModeTabEvents.modifyOutputEvent(group)
                .register((itemGroup) -> itemGroup.accept(item));
    }

    private static ResourceKey<Item> keyOf(String id)
    {
        return ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(SimpleBoats.MOD_ID, id));
    }

    private static Item register(String id)
    {
        return register(keyOf(id), Item::new, new Item.Properties());
    }

    private static Item register(ResourceKey<Item> key, Function<Item.Properties, Item> factory, Item.Properties settings)
    {
        Item item = factory.apply(settings.setId(key));
        if (item instanceof BlockItem blockItem)
        {
            blockItem.registerBlocks(Item.BY_BLOCK, item);
        }

        return Registry.register(BuiltInRegistries.ITEM, key, item);
    }
}
