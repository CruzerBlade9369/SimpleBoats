package com.cruzer.simpleboats.entity;

import com.cruzer.simpleboats.SimpleBoats;
import com.cruzer.simpleboats.entity.vehicle.MotorboatEntity;
import com.cruzer.simpleboats.entity.vehicle.MotorboatType;
import com.cruzer.simpleboats.item.SimpleBoatsItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class SimpleBoatsEntities
{
    public static final Map<MotorboatType, EntityType<MotorboatEntity>> TYPES = new HashMap<>();

    public static void initialize()
    {
        for (MotorboatType type : MotorboatType.values())
        {
            Supplier<Item> itemSupplier = () -> SimpleBoatsItems.MOTORBOAT_ITEMS.get(type);

            RegistryKey<EntityType<?>> key =
                    RegistryKey.of(RegistryKeys.ENTITY_TYPE,
                            Identifier.of(SimpleBoats.MOD_ID, type.getName() + "_motorboat"));

            EntityType<MotorboatEntity> entityType =
                    Registry.register(
                            Registries.ENTITY_TYPE,
                            key,
                            EntityType.Builder.create(getFactory(itemSupplier), SpawnGroup.MISC)
                                    .dimensions(1.375f, 0.5625f)
                                    .maxTrackingRange(80)
                                    .build(key)
                    );

            TYPES.put(type, entityType);
        }
    }

    private static EntityType.EntityFactory<MotorboatEntity> getFactory(Supplier<Item> itemSupplier)
    {
        return (entityType, world) -> new MotorboatEntity(entityType, world, itemSupplier);
    }
}
