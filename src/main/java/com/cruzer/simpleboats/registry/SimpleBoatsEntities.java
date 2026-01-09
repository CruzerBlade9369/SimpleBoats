package com.cruzer.simpleboats.registry;

import com.cruzer.simpleboats.SimpleBoats;
import com.cruzer.simpleboats.entity.vehicle.AbstractPoweredBoatEntity;
import com.cruzer.simpleboats.entity.vehicle.MotorboatEntity;
import com.cruzer.simpleboats.entity.vehicle.SailboatEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class SimpleBoatsEntities
{
    public static final Map<SimpleBoatsTypes, EntityType<MotorboatEntity>> MOTORBOAT_TYPES = new HashMap<>();
    public static final Map<SimpleBoatsTypes, EntityType<SailboatEntity>> SAILBOAT_TYPES = new HashMap<>();

    public static void initialize()
    {
        registerMotorboats();
        registerSailboats();
    }

    private static void registerMotorboats()
    {
        for (SimpleBoatsTypes type : SimpleBoatsTypes.values())
        {
            Supplier<Item> itemSupplier = () -> SimpleBoatsItems.MOTORBOAT_ITEMS.get(type);

            RegistryKey<EntityType<?>> key =
                    RegistryKey.of(RegistryKeys.ENTITY_TYPE,
                            Identifier.of(SimpleBoats.MOD_ID, type.getName() + "_motorboat"));

            EntityType<MotorboatEntity> entityType =
                    Registry.register(
                            Registries.ENTITY_TYPE,
                            key,
                            EntityType.Builder.create(getFactory(MotorboatEntity::new, itemSupplier), SpawnGroup.MISC)
                                    .dimensions(4.4f, 0.675f)
                                    .maxTrackingRange(80)
                                    .build(key)
                    );

            MOTORBOAT_TYPES.put(type, entityType);
        }
    }

    private static void registerSailboats()
    {
        for (SimpleBoatsTypes type : SimpleBoatsTypes.values())
        {
            Supplier<Item> itemSupplier = () -> SimpleBoatsItems.SAILBOAT_ITEMS.get(type);

            RegistryKey<EntityType<?>> key =
                    RegistryKey.of(RegistryKeys.ENTITY_TYPE,
                            Identifier.of(SimpleBoats.MOD_ID, type.getName() + "_sailboat"));

            EntityType<SailboatEntity> entityType =
                    Registry.register(
                            Registries.ENTITY_TYPE,
                            key,
                            EntityType.Builder.create(getFactory(SailboatEntity::new, itemSupplier), SpawnGroup.MISC)
                                    .dimensions(4.4f, 0.675f)
                                    .maxTrackingRange(80)
                                    .build(key)
                    );

            SAILBOAT_TYPES.put(type, entityType);
        }
    }

    private static <E extends AbstractPoweredBoatEntity> EntityType.EntityFactory<E> getFactory(
            BoatConstructor<E> constructor,
            Supplier<Item> itemSupplier
    )
    {
        return (entityType, world) -> constructor.create(entityType, world, itemSupplier);
    }

    @FunctionalInterface
    public interface BoatConstructor<T extends AbstractPoweredBoatEntity> {
        T create(
                EntityType<T> entityType,
                World world,
                Supplier<Item> itemSupplier
        );
    }
}
