package com.cruzer.simpleboats.registry;

import net.minecraft.item.Item;
import net.minecraft.item.Items;

public enum SimpleBoatsTypes {
    OAK("oak", Items.STRIPPED_OAK_LOG),
    BIRCH("birch", Items.STRIPPED_BIRCH_LOG),
    SPRUCE("spruce", Items.STRIPPED_SPRUCE_LOG),
    JUNGLE("jungle", Items.STRIPPED_JUNGLE_LOG),
    ACACIA("acacia", Items.STRIPPED_ACACIA_LOG),
    DARK_OAK("dark_oak", Items.STRIPPED_DARK_OAK_LOG),
    MANGROVE("mangrove", Items.STRIPPED_MANGROVE_LOG),
    CHERRY("cherry", Items.STRIPPED_CHERRY_LOG),
    PALE_OAK("pale_oak", Items.STRIPPED_PALE_OAK_LOG);

    private final String name;
    private final Item sL;

    SimpleBoatsTypes(String name, Item strippedLog)
    {
        this.name = name;
        this.sL = strippedLog;
    }

    public String getName()
    {
        return name;
    }

    public Item getStrippedLog()
    {
        return sL;
    }
}
