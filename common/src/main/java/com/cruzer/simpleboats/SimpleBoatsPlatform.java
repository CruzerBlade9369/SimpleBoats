package com.cruzer.simpleboats;

import java.nio.file.Path;

public class SimpleBoatsPlatform {
    private static Path configDir;
    private static PacketSender packetSender;

    public interface PacketSender {
        void sendToServer(net.minecraft.network.protocol.common.custom.CustomPacketPayload payload);
        void sendToPlayer(net.minecraft.world.entity.player.Player player, net.minecraft.network.protocol.common.custom.CustomPacketPayload payload);
    }

    public static Path getConfigDir() {
        if (configDir == null) {
            configDir = java.nio.file.Paths.get("config"); // Fallback
        }
        return configDir;
    }

    public static void setConfigDir(Path dir) {
        configDir = dir;
    }

    public static void setPacketSender(PacketSender sender) {
        packetSender = sender;
    }

    public static void sendToServer(net.minecraft.network.protocol.common.custom.CustomPacketPayload payload) {
        if (packetSender != null) {
            packetSender.sendToServer(payload);
        }
    }

    public static void sendToPlayer(net.minecraft.world.entity.player.Player player, net.minecraft.network.protocol.common.custom.CustomPacketPayload payload) {
        if (packetSender != null) {
            packetSender.sendToPlayer(player, payload);
        }
    }
}
