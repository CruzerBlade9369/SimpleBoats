package com.cruzer.simpleboats.network;

import com.cruzer.simpleboats.SimpleBoats;
import com.cruzer.simpleboats.entity.vehicle.AbstractPoweredBoatEntity;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;

public class SimpleBoatsNetworking {

    public static final Identifier BOAT_POWER_LEVEL =
            Identifier.fromNamespaceAndPath(SimpleBoats.MOD_ID, "boat_power_level");

    public static void register() {
        PayloadTypeRegistry.serverboundPlay().register(
                SimpleBoatsControlPacket.ID,
                SimpleBoatsControlPacket.CODEC
        );

        ServerPlayNetworking.registerGlobalReceiver(
                SimpleBoatsControlPacket.ID,
                (payload, context) ->
                {
                    ServerPlayer player = context.player();

                    context.server().execute(() ->
                    {
                        if (player.getVehicle() instanceof AbstractPoweredBoatEntity pb)
                        {
                            pb.applyThrottleIntent(
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
