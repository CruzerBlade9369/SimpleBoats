package com.cruzer.simpleboats.registry;

import net.minecraft.world.item.Item;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class SimpleBoatsItems
{
    public static Supplier<Item> BOAT_PROPELLER;
    public static Supplier<Item> OUTBOARD_MOTOR;
    public static Supplier<Item> BOAT_SAIL;

    public static final Map<SimpleBoatsTypes, Supplier<Item>> MOTORBOAT_ITEMS = new HashMap<>();
    public static final Map<SimpleBoatsTypes, Supplier<Item>> SAILBOAT_ITEMS = new HashMap<>();
}
