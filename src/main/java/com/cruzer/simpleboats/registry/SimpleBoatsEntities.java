package com.cruzer.simpleboats.registry;

import com.cruzer.simpleboats.SimpleBoats;
import com.cruzer.simpleboats.entity.vehicle.AbstractPoweredBoatEntity;
import com.cruzer.simpleboats.entity.vehicle.MotorboatEntity;
import com.cruzer.simpleboats.entity.vehicle.SailboatEntity;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

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

            ResourceKey<EntityType<?>> key =
                    ResourceKey.create(Registries.ENTITY_TYPE,
                            Identifier.fromNamespaceAndPath(SimpleBoats.MOD_ID, type.getName() + "_motorboat"));

            EntityType<MotorboatEntity> entityType =
                    Registry.register(
                            BuiltInRegistries.ENTITY_TYPE,
                            key,
                            EntityType.Builder.of(getFactory(MotorboatEntity::new, itemSupplier), MobCategory.MISC)
                                    .sized(4.4f, 0.675f)
                                    .clientTrackingRange(80)
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

            ResourceKey<EntityType<?>> key =
                    ResourceKey.create(Registries.ENTITY_TYPE,
                            Identifier.fromNamespaceAndPath(SimpleBoats.MOD_ID, type.getName() + "_sailboat"));

            EntityType<SailboatEntity> entityType =
                    Registry.register(
                            BuiltInRegistries.ENTITY_TYPE,
                            key,
                            EntityType.Builder.of(getFactory(SailboatEntity::new, itemSupplier), MobCategory.MISC)
                                    .sized(4.4f, 0.675f)
                                    .clientTrackingRange(80)
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
                Level world,
                Supplier<Item> itemSupplier
        );
    }
}
