package com.cruzer.simpleboats;

import com.cruzer.simpleboats.client.model.*;
import com.cruzer.simpleboats.client.renderer.MotorboatRenderer;
import com.cruzer.simpleboats.client.renderer.SailboatRenderer;
import com.cruzer.simpleboats.registry.SimpleBoatsEntities;
import com.cruzer.simpleboats.entity.vehicle.MotorboatEntity;
import com.cruzer.simpleboats.entity.vehicle.SailboatEntity;
import com.cruzer.simpleboats.registry.SimpleBoatsTypes;
import com.cruzer.simpleboats.network.SimpleBoatsControlPacket;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.EntityRendererFactories;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.AbstractBoatEntity;
import net.minecraft.util.Identifier;

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
                EntityModelLayerRegistry.registerModelLayer(
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

            Identifier hullTexture = Identifier.of(SimpleBoats.MOD_ID,
                    TEXTURE_DIR + type.getName() + "_motorboat.png");

            EntityRendererFactories.register(
                    mbEntityType,
                    ctx -> new MotorboatRenderer(ctx, hullTexture)
            );

            EntityRendererFactories.register(
                    sbEntityType,
                    ctx -> new SailboatRenderer(ctx, hullTexture)
            );
        }

        ClientTickEvents.END_CLIENT_TICK.register(client ->
        {
            if (client.player == null) return;

            if (!(client.player.getVehicle() instanceof AbstractBoatEntity)) return;

            boolean up = client.options.forwardKey.isPressed();
            boolean down = client.options.backKey.isPressed();

            if (up == throttleLastUp && down == throttleLastDown) return;

            throttleLastUp = up;
            throttleLastDown = down;

            ClientPlayNetworking.send(new SimpleBoatsControlPacket(up, down));
        });
    }
}