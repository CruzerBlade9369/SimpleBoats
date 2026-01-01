package com.cruzer.simpleboats;

import com.cruzer.simpleboats.client.model.MotorModel;
import com.cruzer.simpleboats.client.model.MotorboatModel;
import com.cruzer.simpleboats.client.model.MotorboatModelLayers;
import com.cruzer.simpleboats.client.renderer.state.MotorboatRenderer;
import com.cruzer.simpleboats.entity.SimpleBoatsEntities;
import com.cruzer.simpleboats.entity.vehicle.MotorboatEntity;
import com.cruzer.simpleboats.entity.vehicle.MotorboatType;
import com.cruzer.simpleboats.network.SimpleBoatsControlPacket;
import com.cruzer.simpleboats.network.SimpleBoatsNetworking;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public class SimpleBoatsClient implements ClientModInitializer
{
    /*public static KeyBinding ACCELERATE;
    public static KeyBinding DECELERATE;
    private static final KeyBinding.Category SIMPLEBOATS_KEYBIND_CATEGORY =
            KeyBinding.Category.create(Identifier.of(SimpleBoats.MOD_ID));*/

    private static final String TEXTURE_DIR = "textures/entity/motorboat/";

    private static boolean throttleLastUp;
    private static boolean throttleLastDown;

    @Override
    public void onInitializeClient()
    {
        /*ACCELERATE = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.simpleboats.accelerate",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_W,
                SIMPLEBOATS_KEYBIND_CATEGORY
        ));

        DECELERATE = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.simpleboats.decelerate",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_S,
                SIMPLEBOATS_KEYBIND_CATEGORY
        ));*/

        EntityModelLayerRegistry.registerModelLayer(
                MotorboatModelLayers.MOTORBOAT,
                MotorboatModel::getTexturedModelData
        );

        EntityModelLayerRegistry.registerModelLayer(
                MotorboatModelLayers.MOTORBOAT_MOTOR,
                MotorModel::getMotorModelData
        );

        EntityModelLayerRegistry.registerModelLayer(
                MotorboatModelLayers.MOTORBOAT_WATER_MASK,
                MotorboatModel::getWaterPatchModelData
        );

        // Register motorboat entity renderers for all wood variants
        for (MotorboatType type : MotorboatType.values())
        {
            EntityType<MotorboatEntity> entityType =
                    SimpleBoatsEntities.TYPES.get(type);

            Identifier hullTexture = Identifier.of(SimpleBoats.MOD_ID,
                    TEXTURE_DIR + type.getName() + "_motorboat.png");

            EntityRendererRegistry.register(
                    entityType,
                    ctx -> new MotorboatRenderer(ctx, hullTexture)
            );
        }

        ClientTickEvents.END_CLIENT_TICK.register(client ->
        {
            if (client.player == null) return;

            if (!(client.player.getVehicle() instanceof MotorboatEntity)) return;

            boolean up = client.options.forwardKey.isPressed();
            boolean down = client.options.backKey.isPressed();

            if (up == throttleLastUp && down == throttleLastDown) return;

            throttleLastUp = up;
            throttleLastDown = down;

            ClientPlayNetworking.send(new SimpleBoatsControlPacket(up, down));
        });
    }
}