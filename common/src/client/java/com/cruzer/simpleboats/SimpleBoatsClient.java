package com.cruzer.simpleboats;

import com.cruzer.simpleboats.client.config.SimpleBoatsConfigManagerClient;
import com.cruzer.simpleboats.config.SimpleBoatsConfigSynced;
import com.cruzer.simpleboats.entity.vehicle.MotorboatEntity;
import com.cruzer.simpleboats.entity.vehicle.SailboatEntity;
import com.cruzer.simpleboats.network.SimpleBoatsConfigSyncPacket;
import com.cruzer.simpleboats.network.SimpleBoatsControlPacket;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;

public class SimpleBoatsClient
{
    private static boolean throttleLastUp;
    private static boolean throttleLastDown;

    public static void initialize() {
        SimpleBoatsConfigManagerClient.load();
    }

    public static void handleClientTick(net.minecraft.client.Minecraft client)
    {
        if (client.player == null) return;
        if (!(client.player.getVehicle() instanceof AbstractBoat)) return;

        boolean up = client.options.keyUp.isDown();
        boolean down = client.options.keyDown.isDown();

        if (up == throttleLastUp && down == throttleLastDown) return;

        throttleLastUp = up;
        throttleLastDown = down;

        SimpleBoatsPlatform.sendToServer(new SimpleBoatsControlPacket(up, down));
    }

    public static void handleConfigSync(SimpleBoatsConfigSyncPacket payload)
    {
        SimpleBoatsConfigSynced.motorboatThrustFactor = payload.motorboatThrustFactor();
        SimpleBoatsConfigSynced.sailboatThrustFactor = payload.sailboatThrustFactor();
        SimpleBoatsConfigSynced.motorboatTurnRate = payload.motorboatTurnRate();
        SimpleBoatsConfigSynced.sailboatMaxTurnRate = payload.sailboatMaxTurnRate();
        SimpleBoatsConfigSynced.sailboatMinTurnRate = payload.sailboatMinTurnRate();
        SimpleBoatsConfigSynced.canEditConfig = payload.canEditConfig();

        MotorboatEntity.updateThrustValues(SimpleBoatsConfigSynced.motorboatThrustFactor);
        MotorboatEntity.updateTurnRate(SimpleBoatsConfigSynced.motorboatTurnRate);
        SailboatEntity.updateThrustValues(SimpleBoatsConfigSynced.sailboatThrustFactor);
        SailboatEntity.updateTurnRate(SimpleBoatsConfigSynced.sailboatMaxTurnRate, SimpleBoatsConfigSynced.sailboatMinTurnRate);
    }
}