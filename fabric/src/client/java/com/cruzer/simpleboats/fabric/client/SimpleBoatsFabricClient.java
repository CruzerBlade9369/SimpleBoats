package com.cruzer.simpleboats.fabric.client;

import com.cruzer.simpleboats.SimpleBoats;
import com.cruzer.simpleboats.SimpleBoatsClient;
import com.cruzer.simpleboats.SimpleBoatsPlatform;
import com.cruzer.simpleboats.client.model.SimpleBoatsModelLayers;
import com.cruzer.simpleboats.client.renderer.MotorboatRenderer;
import com.cruzer.simpleboats.client.renderer.SailboatRenderer;
import com.cruzer.simpleboats.network.SimpleBoatsConfigSyncPacket;
import com.cruzer.simpleboats.registry.SimpleBoatsEntities;
import com.cruzer.simpleboats.registry.SimpleBoatsTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public class SimpleBoatsFabricClient implements ClientModInitializer {
    private static final String TEXTURE_DIR = "textures/entity/generic_boat/";

    @Override
    public void onInitializeClient() {
        // Set up client-side packet sending
        SimpleBoatsPlatform.setPacketSender(new SimpleBoatsPlatform.PacketSender() {
            @Override
            public void sendToServer(CustomPacketPayload payload) {
                ClientPlayNetworking.send(payload);
            }

            @Override
            public void sendToPlayer(Player player, CustomPacketPayload payload) {
                // No-op on client
            }
        });

        // Initialize general client configuration loading
        SimpleBoatsClient.initialize();

        // Register all model layers
        SimpleBoatsModelLayers.ALL.forEach(entry ->
                ModelLayerRegistry.registerModelLayer(
                        entry.layer(),
                        () -> entry.provider().get()
                )
        );

        // Register boats entity renderers for all wood variants
        for (SimpleBoatsTypes type : SimpleBoatsTypes.values()) {
            EntityType<?> mbEntityType = SimpleBoatsEntities.MOTORBOAT_TYPES.get(type).get();
            EntityType<?> sbEntityType = SimpleBoatsEntities.SAILBOAT_TYPES.get(type).get();

            Identifier baseTexture = Identifier.fromNamespaceAndPath(SimpleBoats.MOD_ID,
                    TEXTURE_DIR + type.getName() + "_simpleboat.png");

            EntityRenderers.register(
                    (EntityType) mbEntityType,
                    ctx -> new MotorboatRenderer(ctx, baseTexture)
            );

            EntityRenderers.register(
                    (EntityType) sbEntityType,
                    ctx -> new SailboatRenderer(ctx, baseTexture)
            );
        }

        // Client Tick Handler
        ClientTickEvents.END_CLIENT_TICK.register(SimpleBoatsClient::handleClientTick);

        // Client Config Sync Receiver
        ClientPlayNetworking.registerGlobalReceiver(
                SimpleBoatsConfigSyncPacket.ID,
                (payload, context) -> context.client().execute(() -> SimpleBoatsClient.handleConfigSync(payload))
        );
    }
}
