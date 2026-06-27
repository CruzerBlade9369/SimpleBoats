package com.cruzer.simpleboats.network;

import com.cruzer.simpleboats.SimpleBoats;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record SimpleBoatsConfigEditPacket(
        float motorboatThrustFactor,
        float sailboatThrustFactor,
        float motorboatTurnRate,
        float sailboatMaxTurnRate,
        float sailboatMinTurnRate
) implements CustomPacketPayload
{
    public static final Type<SimpleBoatsConfigEditPacket> ID =
            new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(SimpleBoats.MOD_ID, "global_config_edit"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SimpleBoatsConfigEditPacket> CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.FLOAT, SimpleBoatsConfigEditPacket::motorboatThrustFactor,
                    ByteBufCodecs.FLOAT, SimpleBoatsConfigEditPacket::sailboatThrustFactor,
                    ByteBufCodecs.FLOAT, SimpleBoatsConfigEditPacket::motorboatTurnRate,
                    ByteBufCodecs.FLOAT, SimpleBoatsConfigEditPacket::sailboatMaxTurnRate,
                    ByteBufCodecs.FLOAT, SimpleBoatsConfigEditPacket::sailboatMinTurnRate,
                    SimpleBoatsConfigEditPacket::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type()
    {
        return ID;
    }
}
