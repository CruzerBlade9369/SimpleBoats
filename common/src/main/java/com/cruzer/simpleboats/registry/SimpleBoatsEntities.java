package com.cruzer.simpleboats.registry;

import com.cruzer.simpleboats.entity.vehicle.AbstractPoweredBoatEntity;
import com.cruzer.simpleboats.entity.vehicle.MotorboatEntity;
import com.cruzer.simpleboats.entity.vehicle.SailboatEntity;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class SimpleBoatsEntities
{
    public static final Map<SimpleBoatsTypes, Supplier<EntityType<MotorboatEntity>>> MOTORBOAT_TYPES = new HashMap<>();
    public static final Map<SimpleBoatsTypes, Supplier<EntityType<SailboatEntity>>> SAILBOAT_TYPES = new HashMap<>();

    @FunctionalInterface
    public interface BoatConstructor<T extends AbstractPoweredBoatEntity> {
        T create(
                EntityType<T> entityType,
                Level world,
                Supplier<Item> itemSupplier
        );
    }
}
