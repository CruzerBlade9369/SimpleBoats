package com.cruzer.simpleboats.client.config;

import com.cruzer.simpleboats.config.SimpleBoatsConfigSynced;
import com.cruzer.simpleboats.network.SimpleBoatsConfigEditPacket;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.FloatSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class SimpleBoatsConfigScreen
{
    private static boolean isInGame() {
        Minecraft client = Minecraft.getInstance();
        return client.level != null && client.player != null;
    }


    public static Screen create(Screen parent)
    {
        /*ClientPlayNetworking.send(
                new SimpleBoatsConfigRequestPacket()
        );*/

        return YetAnotherConfigLib.createBuilder()

                .title(Component.literal("Simple Boats"))

                .category(createAnimationCategory())
                .category(createParticleCategory())
                .category(createBoatPerformanceCategory())

                .save(() ->
                {
                    SimpleBoatsConfigManagerClient.save();

                    if (isInGame())
                    {
                        ClientPlayNetworking.send(
                                new SimpleBoatsConfigEditPacket(
                                        SimpleBoatsConfigSynced.motorboatThrustFactor,
                                        SimpleBoatsConfigSynced.sailboatThrustFactor,
                                        SimpleBoatsConfigSynced.motorboatTurnRate,
                                        SimpleBoatsConfigSynced.sailboatMaxTurnRate,
                                        SimpleBoatsConfigSynced.sailboatMinTurnRate
                                )
                        );

                    }

                })

                .build()

                .generateScreen(parent);
    }

    private static ConfigCategory createAnimationCategory()
    {
        return ConfigCategory.createBuilder()
                .name(Component.literal("Boat Animations"))
                .option(createMovementPitchOption())
                .option(createTurnLeanOption())
                .option(createSwayAmplitudeOption())
                .option(createSwaySpeedOption())
                .option(createRainSwayOption())
                .build();
    }

    private static Option<Float> createMovementPitchOption()
    {
        return Option.<Float>createBuilder()
                .name(Component.literal("Forward Movement Pitch Strength"))
                .binding(
                        1.0F,
                        () -> SimpleBoatsConfigManagerClient.CONFIG.pitchMultiplier,
                        value -> SimpleBoatsConfigManagerClient.CONFIG.pitchMultiplier = value
                )
                .controller(option ->
                        FloatSliderControllerBuilder.create(option)
                                .range(0.0F, 3.0F)
                                .step(0.05F)
                ).description(
                        OptionDescription.of(
                                Component.literal("Affects how much the boat pitches up when moving forwards.")
                        )
                )
                .build();
    }

    private static Option<Float> createTurnLeanOption()
    {
        return Option.<Float>createBuilder()
                .name(Component.literal("Turn Lean Strength"))
                .binding(
                        1.0F,
                        () -> SimpleBoatsConfigManagerClient.CONFIG.turnLeanMultiplier,
                        value -> SimpleBoatsConfigManagerClient.CONFIG.turnLeanMultiplier = value
                )
                .controller(option ->
                        FloatSliderControllerBuilder.create(option)
                                .range(0.0F, 3.0F)
                                .step(0.05F)
                ).description(
                        OptionDescription.of(
                                Component.literal("Affects how much the boat leans when turning.")
                        )
                )
                .build();
    }

    private static Option<Float> createSwayAmplitudeOption()
    {
        return Option.<Float>createBuilder()
                .name(Component.literal("Ambient Sway Amplitude"))
                .binding(
                        1.0F,
                        () -> SimpleBoatsConfigManagerClient.CONFIG.swayAmplitudeMultiplier,
                        value -> SimpleBoatsConfigManagerClient.CONFIG.swayAmplitudeMultiplier = value
                )
                .controller(option ->
                        FloatSliderControllerBuilder.create(option)
                                .range(0.0F, 3.0F)
                                .step(0.05F)
                ).description(
                        OptionDescription.of(
                                Component.literal("Affects the amplitude of the boat's ambient sway.")
                        )
                )
                .build();
    }

    private static Option<Float> createSwaySpeedOption()
    {
        return Option.<Float>createBuilder()
                .name(Component.literal("Ambient Sway Speed"))
                .binding(
                        1.0F,
                        () -> SimpleBoatsConfigManagerClient.CONFIG.swaySpeedMultiplier,
                        value -> SimpleBoatsConfigManagerClient.CONFIG.swaySpeedMultiplier = value
                )
                .controller(option ->
                        FloatSliderControllerBuilder.create(option)
                                .range(0.0F, 3.0F)
                                .step(0.05F)
                ).description(
                        OptionDescription.of(
                                Component.literal("Affects the speed of the boat's ambient sway.")
                        )
                )
                .build();
    }

    private static Option<Float> createRainSwayOption()
    {
        return Option.<Float>createBuilder()
                .name(Component.literal("Rain Sway Multiplier"))
                .binding(
                        1.0F,
                        () -> SimpleBoatsConfigManagerClient.CONFIG.rainSwayMultiplier,
                        value -> SimpleBoatsConfigManagerClient.CONFIG.rainSwayMultiplier = value
                )
                .controller(option ->
                        FloatSliderControllerBuilder.create(option)
                                .range(0.0F, 3.0F)
                                .step(0.05F)
                ).description(
                        OptionDescription.of(
                                Component.literal("Affects the multiplier for rain-induced sway.")
                        )
                )
                .build();
    }

    private static ConfigCategory createParticleCategory()
    {
        return ConfigCategory.createBuilder()
                .name(Component.literal("Boat Particles"))
                .option(createBubbleOption())
                .build();
    }

    private static Option<Boolean> createBubbleOption()
    {
        return Option.<Boolean>createBuilder()
                .name(Component.literal("Motorboat Propeller Bubbles"))
                .binding(
                        true,
                        () -> SimpleBoatsConfigManagerClient.CONFIG.motorboatPropellerBubbles,
                        value -> SimpleBoatsConfigManagerClient.CONFIG.motorboatPropellerBubbles = value
                )
                .controller(TickBoxControllerBuilder::create)
                .description(
                        OptionDescription.of(
                                Component.literal("Determines whether motorboat propeller bubbles are displayed.")
                        )
                )
                .build();
    }

    private static ConfigCategory createBoatPerformanceCategory()
    {
        return ConfigCategory.createBuilder()
                .name(Component.literal("Boat Performance"))
                .tooltip(Component.literal(
                        "Boat performance settings are synchronized from the server " +
                                "and require operator permissions to modify. " +
                                "Reconnect if you are an operator and these settings are unavailable. "
                ))
                .option(createMotorboatThrustFactorOption())
                .option(createMotorboatTurnRateOption())
                .option(createSailboatThrustFactorOption())
                .option(createSailboatMaxTurnRateOption())
                .option(createSailboatMinTurnRateOption())
                .build();
    }

    private static Option<Float> createMotorboatThrustFactorOption()
    {
        return Option.<Float>createBuilder()
                .name(Component.literal("Motorboat Thrust Factor"))
                .binding(
                        0.06f,
                        () -> SimpleBoatsConfigSynced.motorboatThrustFactor,
                        value -> SimpleBoatsConfigSynced.motorboatThrustFactor = value
                )
                .controller(option ->
                        FloatSliderControllerBuilder.create(option)
                                .range(0.01f, 0.25f)
                                .step(0.01f)
                                .formatValue(value ->
                                        Component.literal(String.format("%.2f", value))
                                )
                )
                .description(
                        OptionDescription.of(
                                Component.literal("Determines the engine thrust factor for the motorboat.")
                        )
                )
                .available(SimpleBoatsConfigSynced.canEditConfig && isInGame())
                .build();
    }

    private static Option<Float> createMotorboatTurnRateOption()
    {
        return Option.<Float>createBuilder()
                .name(Component.literal("Motorboat Turn Rate"))
                .binding(
                        0.3f,
                        () -> SimpleBoatsConfigSynced.motorboatTurnRate,
                        value -> SimpleBoatsConfigSynced.motorboatTurnRate = value
                )
                .controller(option ->
                        FloatSliderControllerBuilder.create(option)
                                .range(0.05f, 0.9f)
                                .step(0.05f)
                                .formatValue(value ->
                                        Component.literal(String.format("%.2f", value))
                                )
                )
                .description(
                        OptionDescription.of(
                                Component.literal("Determines the turn rate for the motorboat.")
                        )
                )
                .available(SimpleBoatsConfigSynced.canEditConfig && isInGame())
                .build();
    }

    private static Option<Float> createSailboatThrustFactorOption()
    {
        return Option.<Float>createBuilder()
                .name(Component.literal("Sailboat Thrust Factor"))
                .binding(
                        0.05f,
                        () -> SimpleBoatsConfigSynced.sailboatThrustFactor,
                        value -> SimpleBoatsConfigSynced.sailboatThrustFactor = value
                )
                .controller(option ->
                        FloatSliderControllerBuilder.create(option)
                                .range(0.01f, 0.25f)
                                .step(0.01f)
                                .formatValue(value ->
                                        Component.literal(String.format("%.2f", value))
                                )
                )
                .description(
                        OptionDescription.of(
                                Component.literal("Determines the sail thrust factor for the sailboat.")
                        )
                )
                .available(SimpleBoatsConfigSynced.canEditConfig && isInGame())
                .build();
    }

    private static Option<Float> createSailboatMaxTurnRateOption()
    {
        return Option.<Float>createBuilder()
                .name(Component.literal("Sailboat Maximum Turn Rate"))
                .binding(
                        0.3f,
                        () -> SimpleBoatsConfigSynced.sailboatMaxTurnRate,
                        value -> SimpleBoatsConfigSynced.sailboatMaxTurnRate = value
                )
                .controller(option ->
                        FloatSliderControllerBuilder.create(option)
                                .range(0.05f, 0.9f)
                                .step(0.05f)
                                .formatValue(value ->
                                        Component.literal(String.format("%.2f", value))
                                )
                )
                .description(
                        OptionDescription.of(
                                Component.literal("Determines the turn rate for the sailboat at its highest speed.")
                        )
                )
                .available(SimpleBoatsConfigSynced.canEditConfig && isInGame())
                .build();
    }

    private static Option<Float> createSailboatMinTurnRateOption()
    {
        return Option.<Float>createBuilder()
                .name(Component.literal("Sailboat Minimum Turn Rate"))
                .binding(
                        0.05f,
                        () -> SimpleBoatsConfigSynced.sailboatMinTurnRate,
                        value -> SimpleBoatsConfigSynced.sailboatMinTurnRate = value
                )
                .controller(option ->
                        FloatSliderControllerBuilder.create(option)
                                .range(0.05f, 0.9f)
                                .step(0.05f)
                                .formatValue(value ->
                                        Component.literal(String.format("%.2f", value))
                                )
                )
                .description(
                        OptionDescription.of(
                                Component.literal("Determines the turn rate for the sailboat while stationary.")
                        )
                )
                .available(SimpleBoatsConfigSynced.canEditConfig && isInGame())
                .build();
    }
}
