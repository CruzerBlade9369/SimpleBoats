package com.cruzer.simpleboats.network;

import com.cruzer.simpleboats.SimpleBoats;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record SimpleBoatsConfigRequestPacket()
        implements CustomPacketPayload
{
    public static final Type<SimpleBoatsConfigRequestPacket> ID =
            new Type<>(Identifier.fromNamespaceAndPath(
                    SimpleBoats.MOD_ID,
                    "config_request"
            ));

    public static final StreamCodec<
                RegistryFriendlyByteBuf,
                SimpleBoatsConfigRequestPacket
                > CODEC =
            StreamCodec.unit(new SimpleBoatsConfigRequestPacket());

    @Override
    public Type<? extends CustomPacketPayload> type()
    {
        return ID;
    }
}
