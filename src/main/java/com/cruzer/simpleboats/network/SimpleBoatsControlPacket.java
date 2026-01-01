package com.cruzer.simpleboats.network;

import com.cruzer.simpleboats.SimpleBoats;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record SimpleBoatsControlPacket(
        boolean throttleUp,
        boolean throttleDown
) implements CustomPayload
{
    public static final CustomPayload.Id<SimpleBoatsControlPacket> ID = new CustomPayload.Id<>(Identifier.of(SimpleBoats.MOD_ID, "boats_control"));
    public static final PacketCodec<RegistryByteBuf, SimpleBoatsControlPacket> CODEC =
            PacketCodec.tuple(
                    PacketCodecs.BOOLEAN, SimpleBoatsControlPacket::throttleUp,
                    PacketCodecs.BOOLEAN, SimpleBoatsControlPacket::throttleDown,
                    SimpleBoatsControlPacket::new
            );

    @Override
    public Id<? extends CustomPayload> getId()
    {
        return ID;
    }
}
