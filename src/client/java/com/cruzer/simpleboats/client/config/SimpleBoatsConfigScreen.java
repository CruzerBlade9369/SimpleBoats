package com.cruzer.simpleboats.client.config;

import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.FloatSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class SimpleBoatsConfigScreen
{
    public static Screen create(Screen parent)
    {
        return YetAnotherConfigLib.createBuilder()

                .title(Component.literal("Simple Boats"))

                .category(createAnimationCategory())
                .category(createParticleCategory())

                .save(SimpleBoatsConfigManagerClient::save)

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
}
