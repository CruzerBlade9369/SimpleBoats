package com.cruzer.simpleboats.network;

import com.cruzer.simpleboats.SimpleBoats;
import com.cruzer.simpleboats.entity.vehicle.MotorboatEntity;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class SimpleBoatsNetworking {

    public static final Identifier MOTORBOAT_THROTTLE =
            Identifier.of(SimpleBoats.MOD_ID, "motorboat_throttle");

    public static void register() {
        PayloadTypeRegistry.playC2S().register(
                SimpleBoatsControlPacket.ID,
                SimpleBoatsControlPacket.CODEC
        );

        ServerPlayNetworking.registerGlobalReceiver(
                SimpleBoatsControlPacket.ID,
                (payload, context) ->
                {
                    ServerPlayerEntity player = context.player();

                    context.server().execute(() ->
                    {
                        if (player.getVehicle() instanceof MotorboatEntity mb)
                        {
                            mb.applyThrottleIntent(
                                    player,
                                    payload.throttleUp(),
                                    payload.throttleDown()
                            );
                        }
                    });
                }
        );
    }
}
