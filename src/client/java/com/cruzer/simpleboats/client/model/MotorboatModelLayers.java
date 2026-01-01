package com.cruzer.simpleboats.client.model;

import com.cruzer.simpleboats.SimpleBoats;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class MotorboatModelLayers
{
    public static final EntityModelLayer MOTORBOAT =
            new EntityModelLayer(Identifier.of(SimpleBoats.MOD_ID, "motorboat"), "main");

    public static final EntityModelLayer MOTORBOAT_MOTOR =
            new EntityModelLayer(Identifier.of(SimpleBoats.MOD_ID, "motorboat"), "motor");

    public static final EntityModelLayer MOTORBOAT_WATER_MASK =
            new EntityModelLayer(Identifier.of(SimpleBoats.MOD_ID, "motorboat"), "water");
}
