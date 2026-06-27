package com.cruzer.simpleboats;

import com.cruzer.simpleboats.client.config.SimpleBoatsConfigManagerClient;
import com.cruzer.simpleboats.client.model.*;
import com.cruzer.simpleboats.client.renderer.MotorboatRenderer;
import com.cruzer.simpleboats.client.renderer.SailboatRenderer;
import com.cruzer.simpleboats.config.SimpleBoatsConfigSynced;
import com.cruzer.simpleboats.entity.vehicle.AbstractPoweredBoatEntity;
import com.cruzer.simpleboats.network.SimpleBoatsConfigSyncPacket;
import com.cruzer.simpleboats.registry.SimpleBoatsEntities;
import com.cruzer.simpleboats.entity.vehicle.MotorboatEntity;
import com.cruzer.simpleboats.entity.vehicle.SailboatEntity;
import com.cruzer.simpleboats.registry.SimpleBoatsTypes;
import com.cruzer.simpleboats.network.SimpleBoatsControlPacket;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;

public class SimpleBoatsClient implements ClientModInitializer
{
    /*public static KeyBinding ACCELERATE;
    public static KeyBinding DECELERATE;
    private static final KeyBinding.Category SIMPLEBOATS_KEYBIND_CATEGORY =
            KeyBinding.Category.create(Identifier.of(SimpleBoats.MOD_ID));*/

    private static final String TEXTURE_DIR = "textures/entity/generic_boat/";

    private static boolean throttleLastUp;
    private static boolean throttleLastDown;

    @Override
    public void onInitializeClient()
    {
        // Register all model layers
        SimpleBoatsModelLayers.ALL.forEach(entry ->
                ModelLayerRegistry.registerModelLayer(
                        entry.layer(),
                        entry.provider()
                )
        );

        // Register boats entity renderers for all wood variants
        for (SimpleBoatsTypes type : SimpleBoatsTypes.values())
        {
            EntityType<MotorboatEntity> mbEntityType =
                    SimpleBoatsEntities.MOTORBOAT_TYPES.get(type);
            EntityType<SailboatEntity> sbEntityType =
                    SimpleBoatsEntities.SAILBOAT_TYPES.get(type);

            Identifier baseTexture = Identifier.fromNamespaceAndPath(SimpleBoats.MOD_ID,
                    TEXTURE_DIR + type.getName() + "_simpleboat.png");

            EntityRenderers.register(
                    mbEntityType,
                    ctx -> new MotorboatRenderer(ctx, baseTexture)
            );

            EntityRenderers.register(
                    sbEntityType,
                    ctx -> new SailboatRenderer(ctx, baseTexture)
            );
        }

        ClientTickEvents.END_CLIENT_TICK.register(client ->
        {
            if (client.player == null) return;

            if (!(client.player.getVehicle() instanceof AbstractBoat)) return;

            boolean up = client.options.keyUp.isDown();
            boolean down = client.options.keyDown.isDown();

            if (up == throttleLastUp && down == throttleLastDown) return;

            throttleLastUp = up;
            throttleLastDown = down;

            ClientPlayNetworking.send(new SimpleBoatsControlPacket(up, down));
        });

        ClientPlayNetworking.registerGlobalReceiver(
                SimpleBoatsConfigSyncPacket.ID,
                (payload, context) ->
                        context.client().execute(() ->
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
                        })
        );
        SimpleBoatsConfigManagerClient.load();
    }
}