package com.cruzer.simpleboats.neoforge.client;

import com.cruzer.simpleboats.SimpleBoats;
import com.cruzer.simpleboats.SimpleBoatsClient;
import com.cruzer.simpleboats.SimpleBoatsPlatform;
import com.cruzer.simpleboats.client.model.SimpleBoatsModelLayers;
import com.cruzer.simpleboats.client.renderer.MotorboatRenderer;
import com.cruzer.simpleboats.client.renderer.SailboatRenderer;
import com.cruzer.simpleboats.registry.SimpleBoatsEntities;
import com.cruzer.simpleboats.registry.SimpleBoatsTypes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public class SimpleBoatsNeoForgeClient {
    private static final String TEXTURE_DIR = "textures/entity/generic_boat/";

    public static void onClientSetup(FMLClientSetupEvent event) {
        SimpleBoatsPlatform.setPacketSender(new SimpleBoatsPlatform.PacketSender() {
            @Override
            public void sendToServer(CustomPacketPayload payload) {
                ClientPacketDistributor.sendToServer(payload);
            }

            @Override
            public void sendToPlayer(Player player, CustomPacketPayload payload) {
                // No-op on client
            }
        });
    }

    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        SimpleBoatsModelLayers.ALL.forEach(entry ->
                event.registerLayerDefinition(entry.layer(), entry.provider()::get)
        );
    }

    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        for (SimpleBoatsTypes type : SimpleBoatsTypes.values()) {
            EntityType<?> mbEntityType = SimpleBoatsEntities.MOTORBOAT_TYPES.get(type).get();
            EntityType<?> sbEntityType = SimpleBoatsEntities.SAILBOAT_TYPES.get(type).get();

            Identifier baseTexture = Identifier.fromNamespaceAndPath(SimpleBoats.MOD_ID,
                    TEXTURE_DIR + type.getName() + "_simpleboat.png");

            event.registerEntityRenderer((EntityType) mbEntityType, ctx -> new MotorboatRenderer(ctx, baseTexture));
            event.registerEntityRenderer((EntityType) sbEntityType, ctx -> new SailboatRenderer(ctx, baseTexture));
        }
    }

    @EventBusSubscriber(modid = SimpleBoats.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {
        @SubscribeEvent
        public static void onClientTick(ClientTickEvent.Post event) {
            SimpleBoatsClient.handleClientTick(Minecraft.getInstance());
        }
    }
}
