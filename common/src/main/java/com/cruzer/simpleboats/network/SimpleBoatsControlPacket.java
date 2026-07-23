package com.cruzer.simpleboats.network;

import com.cruzer.simpleboats.SimpleBoats;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record SimpleBoatsControlPacket(
        boolean throttleUp,
        boolean throttleDown
) implements CustomPacketPayload
{
    public static final CustomPacketPayload.Type<SimpleBoatsControlPacket> ID =
            new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(SimpleBoats.MOD_ID, "boats_control"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SimpleBoatsControlPacket> CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.BOOL, SimpleBoatsControlPacket::throttleUp,
                    ByteBufCodecs.BOOL, SimpleBoatsControlPacket::throttleDown,
                    SimpleBoatsControlPacket::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type()
    {
        return ID;
    }
}
