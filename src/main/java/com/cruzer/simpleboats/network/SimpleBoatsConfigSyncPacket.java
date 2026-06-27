package com.cruzer.simpleboats.network;

import com.cruzer.simpleboats.SimpleBoats;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record SimpleBoatsConfigSyncPacket(
        float motorboatThrustFactor,
        float sailboatThrustFactor,
        float motorboatTurnRate,
        float sailboatMaxTurnRate,
        float sailboatMinTurnRate,
        boolean canEditConfig
) implements CustomPacketPayload
{
    public static final Type<SimpleBoatsConfigSyncPacket> ID =
            new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(SimpleBoats.MOD_ID, "global_config_sync"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SimpleBoatsConfigSyncPacket> CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.FLOAT, SimpleBoatsConfigSyncPacket::motorboatThrustFactor,
                    ByteBufCodecs.FLOAT, SimpleBoatsConfigSyncPacket::sailboatThrustFactor,
                    ByteBufCodecs.FLOAT, SimpleBoatsConfigSyncPacket::motorboatTurnRate,
                    ByteBufCodecs.FLOAT, SimpleBoatsConfigSyncPacket::sailboatMaxTurnRate,
                    ByteBufCodecs.FLOAT, SimpleBoatsConfigSyncPacket::sailboatMinTurnRate,
                    ByteBufCodecs.BOOL, SimpleBoatsConfigSyncPacket::canEditConfig,
                    SimpleBoatsConfigSyncPacket::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type()
    {
        return ID;
    }
}
