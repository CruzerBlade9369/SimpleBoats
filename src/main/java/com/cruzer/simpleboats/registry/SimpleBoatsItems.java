package com.cruzer.simpleboats.registry;

import com.cruzer.simpleboats.SimpleBoats;
import com.cruzer.simpleboats.item.MotorboatItem;
import com.cruzer.simpleboats.item.SailboatItem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

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

        addToItemGroup(BOAT_PROPELLER, ItemGroups.INGREDIENTS);
        addToItemGroup(OUTBOARD_MOTOR, ItemGroups.INGREDIENTS);
        addToItemGroup(BOAT_SAIL, ItemGroups.INGREDIENTS);
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
                    new Item.Settings().maxCount(1)
            );

            MOTORBOAT_ITEMS.put(type, item);
            addToItemGroup(item, ItemGroups.TOOLS);
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
                    new Item.Settings().maxCount(1)
            );

            SAILBOAT_ITEMS.put(type, item);
            addToItemGroup(item, ItemGroups.TOOLS);
        }
    }

    private static void addToItemGroup(Item item, RegistryKey<ItemGroup> group)
    {
        ItemGroupEvents.modifyEntriesEvent(group)
                .register((itemGroup) -> itemGroup.add(item));
    }

    private static RegistryKey<Item> keyOf(String id)
    {
        return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(SimpleBoats.MOD_ID, id));
    }

    private static Item register(String id)
    {
        return register(keyOf(id), Item::new, new Item.Settings());
    }

    private static Item register(RegistryKey<Item> key, Function<Item.Settings, Item> factory, Item.Settings settings)
    {
        Item item = factory.apply(settings.registryKey(key));
        if (item instanceof BlockItem blockItem)
        {
            blockItem.appendBlocks(Item.BLOCK_ITEMS, item);
        }

        return Registry.register(Registries.ITEM, key, item);
    }
}
