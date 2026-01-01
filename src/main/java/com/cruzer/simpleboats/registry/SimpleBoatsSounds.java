package com.cruzer.simpleboats.registry;

import com.cruzer.simpleboats.SimpleBoats;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class SimpleBoatsSounds
{
    public static final SoundEvent BOAT_MOTOR = register("boat.motor");
    public static final SoundEvent BOAT_MOTOR_START = register("boat.motor.start");

    private static SoundEvent register(String id) {
        Identifier identifier = Identifier.of(SimpleBoats.MOD_ID, id);
        return Registry.register(
                Registries.SOUND_EVENT,
                identifier,
                SoundEvent.of(identifier)
        );
    }

    public static void registerAll() {

    }
}
