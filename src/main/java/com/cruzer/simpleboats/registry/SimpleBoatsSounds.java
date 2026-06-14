package com.cruzer.simpleboats.registry;

import com.cruzer.simpleboats.SimpleBoats;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

public class SimpleBoatsSounds
{
    public static final SoundEvent BOAT_MOTOR = register("boat.motor");
    public static final SoundEvent BOAT_MOTOR_START = register("boat.motor.start");
    public static final SoundEvent BOAT_SAIL = register("boat.sail.ambient");

    private static SoundEvent register(String id) {
        Identifier identifier = Identifier.fromNamespaceAndPath(SimpleBoats.MOD_ID, id);
        return Registry.register(
                BuiltInRegistries.SOUND_EVENT,
                identifier,
                SoundEvent.createVariableRangeEvent(identifier)
        );
    }

    public static void registerAll() {}
}
