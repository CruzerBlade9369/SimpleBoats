package com.cruzer.simpleboats.network;

import com.cruzer.simpleboats.SimpleBoats;
import com.cruzer.simpleboats.config.SimpleBoatsConfigManagerServer;
import com.cruzer.simpleboats.config.SimpleBoatsConfigServer;
import com.cruzer.simpleboats.config.SimpleBoatsConfigSynced;
import com.cruzer.simpleboats.entity.vehicle.AbstractPoweredBoatEntity;
import com.cruzer.simpleboats.entity.vehicle.MotorboatEntity;
import com.cruzer.simpleboats.entity.vehicle.SailboatEntity;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.permissions.Permission;
import net.minecraft.server.permissions.Permissions;

public class SimpleBoatsNetworking
{
    public static void register() {
        PayloadTypeRegistry.playC2S().register(
                SimpleBoatsControlPacket.ID,
                SimpleBoatsControlPacket.CODEC
        );

        PayloadTypeRegistry.playC2S().register(
                SimpleBoatsConfigEditPacket.ID,
                SimpleBoatsConfigEditPacket.CODEC
        );

        /*PayloadTypeRegistry.serverboundPlay().register(
                SimpleBoatsConfigRequestPacket.ID,
                SimpleBoatsConfigRequestPacket.CODEC
        );*/

        PayloadTypeRegistry.playS2C().register(
                SimpleBoatsConfigSyncPacket.ID,
                SimpleBoatsConfigSyncPacket.CODEC
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

        ServerPlayNetworking.registerGlobalReceiver(
                SimpleBoatsConfigEditPacket.ID,
                (payload, context) ->
                {
                    ServerPlayer player = context.player();

                    context.server().execute(() ->
                    {
                        if (!player.permissions().hasPermission(Permissions.COMMANDS_ADMIN))
                        {
                            SimpleBoats.LOGGER.warn(
                                    "Aha! I see you, {}. It seems you got around the UI lock. But good luck trying to get around this one!",
                                    player.getPlainTextName());
                            return;
                        }

                        float mb = payload.motorboatThrustFactor();
                        float sb = payload.sailboatThrustFactor();
                        float mbtr = payload.motorboatTurnRate();
                        float sbmxtr = payload.sailboatMaxTurnRate();
                        float sbmntr = payload.sailboatMinTurnRate();

                        SimpleBoatsConfigManagerServer.CONFIG.motorboatThrustFactor = mb;
                        SimpleBoatsConfigManagerServer.CONFIG.sailboatThrustFactor = sb;
                        SimpleBoatsConfigManagerServer.CONFIG.motorboatTurnRate = mbtr;
                        SimpleBoatsConfigManagerServer.CONFIG.sailboatMaxTurnRate = sbmxtr;
                        SimpleBoatsConfigManagerServer.CONFIG.sailboatMinTurnRate = sbmntr;

                        MotorboatEntity.updateThrustValues(mb);
                        MotorboatEntity.updateTurnRate(mbtr);
                        SailboatEntity.updateThrustValues(sb);
                        SailboatEntity.updateTurnRate(sbmxtr, sbmntr);

                        SimpleBoatsConfigManagerServer.save();

                        broadcastConfigSync(context.server());
                    });
                }
        );

        /*ServerPlayNetworking.registerGlobalReceiver(
                SimpleBoatsConfigRequestPacket.ID,
                (payload, context) ->
                        context.server().execute(() ->
                                sendConfig(context.player())
                        )
        );*/

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) ->
                sendConfig(handler.player)
        );
    }

    private static void broadcastConfigSync(MinecraftServer server)
    {
        for (ServerPlayer player : PlayerLookup.all(server))
        {
            sendConfig(player);
        }
    }

    private static void sendConfig(ServerPlayer player)
    {
        ServerPlayNetworking.send(
                player,
                new SimpleBoatsConfigSyncPacket(
                        SimpleBoatsConfigManagerServer.CONFIG.motorboatThrustFactor,
                        SimpleBoatsConfigManagerServer.CONFIG.sailboatThrustFactor,
                        SimpleBoatsConfigManagerServer.CONFIG.motorboatTurnRate,
                        SimpleBoatsConfigManagerServer.CONFIG.sailboatMaxTurnRate,
                        SimpleBoatsConfigManagerServer.CONFIG.sailboatMinTurnRate,
                        player.permissions().hasPermission(Permissions.COMMANDS_ADMIN)
                )
        );
    }
}
